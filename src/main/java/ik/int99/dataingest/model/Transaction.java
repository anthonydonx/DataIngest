package ik.int99.dataingest.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Copyright (c) Asanka Anthony
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of organization. You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with organization.
 * 14/4/25 8:40 pm
 */
@Entity
@Table(name = "transaction")
@Getter
@Setter
/*
 * Transaction entity class representing a transaction record in the database.
 * It extends the CommonAudit class to inherit common auditing fields.
 * added 3 index fields usefully for searching, index columns are account_number, trx_date and customer_id - added uses liquibase
 */
public class Transaction extends CommonAudit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "trx_amount")
    private Double trxAmount;
    @Column(name = "description")
    private String description;
    @Column(name = "trx_date")
    private String trxDate;
    @Column(name = "trx_time")
    private String trxTime;
    @Column(name = "customer_id")
    private String customerId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(accountNumber, that.accountNumber) && Objects.equals(trxAmount, that.trxAmount) && Objects.equals(description, that.description) && Objects.equals(trxDate, that.trxDate) && Objects.equals(trxTime, that.trxTime) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, trxAmount, description, trxDate, trxTime, customerId);
    }
}
