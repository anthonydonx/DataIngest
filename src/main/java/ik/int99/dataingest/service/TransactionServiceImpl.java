package ik.int99.dataingest.service;

import ik.int99.dataingest.model.Transaction;
import ik.int99.dataingest.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) Asanka Anthony
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of organization. You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with organization.
 * 15/4/25 12:35 am
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    /**
     * Retrieves all transactions from the database.
     *
     * @return a list of all transactions
     */
    @Override
    public Page<Transaction> getAllTransactions(Pageable pageable) {
      return  transactionRepository.findAll(pageable);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return the transaction with the specified ID, or null if not found
     */
    @Override
    public Transaction getTransactionById(Long id) {
       return transactionRepository.findById(id).orElseThrow(()->
               new RuntimeException("Transaction not found with id: " + id)););
    }
}
