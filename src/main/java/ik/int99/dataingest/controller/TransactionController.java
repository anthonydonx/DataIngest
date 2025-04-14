package ik.int99.dataingest.controller;

import ik.int99.dataingest.model.Transaction;
import ik.int99.dataingest.service.TransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright (c) Asanka Anthony
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of organization. You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with organization.
 * 15/4/25 12:28 am
 */
@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Transaction API")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/v1")
    public ResponseEntity<List<Transaction>> getAllTransactions(Pageable pageable) {
        Page<Transaction> allTransactions = transactionService.getAllTransactions(pageable);
        return ResponseEntity.ok(allTransactions.getContent());
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transaction);
    }
    // TODO: Add search functionality -> Implement search functionality using JPA Specification & predicates, so that we can search by any field
   // @GetMapping("/v1/search")


}
