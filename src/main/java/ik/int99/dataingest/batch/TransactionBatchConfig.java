package ik.int99.dataingest.batch;

import ik.int99.dataingest.model.Transaction;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class TransactionBatchConfig {

    private static final Logger logger = LoggerFactory.getLogger(TransactionBatchConfig.class);

    public static final String DELIMITER = "|";
    public static final String[] STR_COL_NAMES = {"accountNumber", "trxAmount", "description", "trxDate", "trxTime", "customerId"};
    public static final String TRANSACTION_READER = "transactionReader";

    @Value("classpath:dataSource.txt")
    private Resource inputFile;

    @Value("${batch.chunk.size:10}")
    private int chunkSize;

    @Bean
    public FlatFileItemReader<Transaction> transactionReader() {
        return new FlatFileItemReaderBuilder<Transaction>()
                .name(TRANSACTION_READER)
                .resource(inputFile)
                .linesToSkip(1) // Skip header
                .recordSeparatorPolicy(recordSeparatorPolicy())
                .lineMapper(getLineMapper())
                .build();
    }

    private DefaultLineMapper<Transaction> getLineMapper() {
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(getLineTokenizer());
        lineMapper.setFieldSetMapper(getMapper());
        return lineMapper;
    }

    private static FieldSetMapper<Transaction> getMapper() {
        return fieldSet -> {
            try {
                Transaction txn = new Transaction();
                txn.setAccountNumber(fieldSet.readString("accountNumber"));
                txn.setTrxAmount(Double.valueOf(fieldSet.readString("trxAmount")));
                txn.setDescription(fieldSet.readString("description"));
                txn.setTrxDate(fieldSet.readString("trxDate"));
                txn.setTrxTime(fieldSet.readString("trxTime"));
                txn.setCustomerId(fieldSet.readString("customerId"));
                return txn;
            } catch (Exception e) {
                logger.error("Error parsing line: {}", fieldSet.toString(), e);
                return null; // Skip invalid lines
            }
        };
    }

    private DelimitedLineTokenizer getLineTokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(DELIMITER);
        tokenizer.setNames(STR_COL_NAMES);
        return tokenizer;
    }

    @Bean
    public JpaItemWriter<Transaction> transactionWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Transaction> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public DefaultRecordSeparatorPolicy recordSeparatorPolicy() {
        return new DefaultRecordSeparatorPolicy() {
            @Override
            public boolean isEndOfRecord(String line) {
                return line != null && !line.trim().isEmpty();
            }

            @Override
            public String postProcess(String record) {
                return record.trim();
            }
        };
    }

    @Bean
    public Job importTransactionJob(JobRepository jobRepository, Step transactionStep) {
        return new JobBuilder("importTransactionJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transactionStep)
                .end()
                .build();
    }

    @Bean
    public Step transactionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, JpaItemWriter<Transaction> writer, EntityManagerFactory entityManagerFactory) {
        return new StepBuilder("transactionStep", jobRepository)
                .<Transaction, Transaction>chunk(chunkSize, transactionManager)
                .reader(transactionReader())
                .writer(transactionWriter(entityManagerFactory))
                .build();
    }
}