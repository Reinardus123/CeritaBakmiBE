package com.CeritaBakmiBE.CB.repository;

import com.CeritaBakmiBE.CB.entity.Transaction;
import com.CeritaBakmiBE.CB.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

    List<TransactionDetail>findByTransaction(Transaction transaction);
}
