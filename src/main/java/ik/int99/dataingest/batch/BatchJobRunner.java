package ik.int99.dataingest.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) Asanka Anthony
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of organization. You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with organization.
 * 14/4/25 11:08 pm
 */
@Component
@Slf4j
public class BatchJobRunner implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job importTransactionJob;

    public BatchJobRunner(JobLauncher jobLauncher, Job importTransactionJob) {
        this.jobLauncher = jobLauncher;
        this.importTransactionJob = importTransactionJob;
    }


    @Override
    public void run(String... args) throws Exception {
        JobExecution jobExecution = jobLauncher.run(importTransactionJob, new org.springframework.batch.core.JobParameters());
        log.info("Job Status: {}", jobExecution.getStatus());
    }
}
