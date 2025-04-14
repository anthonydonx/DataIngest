package ik.int99.dataingest.service;

import ik.int99.dataingest.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
public interface TransactionService {
    /**
     * Retrieves all transactions from the database.
     *
     * @return a list of all transactions
     */
    Page<Transaction> getAllTransactions(Pageable pageable);

    /**
     * Retrieves a transaction by its ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return the transaction with the specified ID, or null if not found
     */
    Transaction getTransactionById(Long id);

}
