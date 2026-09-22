package com.CeritaBakmiBE.CB.repository;

import com.CeritaBakmiBE.CB.entity.Branch;
import com.CeritaBakmiBE.CB.entity.Transaction;
import com.CeritaBakmiBE.CB.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByBranch(Branch branch);
}
