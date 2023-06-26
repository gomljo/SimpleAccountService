package com.simpleAccountService.repository;

import com.simpleAccountService.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(String transactionId);
}
