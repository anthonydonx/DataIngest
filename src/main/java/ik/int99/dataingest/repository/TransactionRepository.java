package ik.int99.dataingest.repository;

import ik.int99.dataingest.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
  Page<Transaction> findByAccountNumberIn(List<String> accountNumbers, Pageable pageable);

  Page<Transaction> findByTrxDate(String trxDate, Pageable pageable);

  Page<Transaction> findByCustomerId(String customerId, Pageable pageable);

  // Alternative search method using @Query
  @Query("SELECT t FROM Transaction t WHERE " +
          "(:accountNumbers is null or t.accountNumber IN :accountNumbers) AND " +
          "(:trxDate is null or t.trxDate = :trxDate) AND " +
          "(:customerId is null or t.customerId = :customerId)")
  Page<Transaction> searchTransactions(
          @Param("accountNumbers") List<String> accountNumbers,
          @Param("trxDate") String trxDate,
          @Param("customerId") String customerId,
          Pageable pageable);
}