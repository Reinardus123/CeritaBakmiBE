package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import com.CeritaBakmiBE.CB.Enum.PaymentStatus;
import com.CeritaBakmiBE.CB.entity.Branch;
import com.CeritaBakmiBE.CB.entity.Transaction;
import com.CeritaBakmiBE.CB.entity.User;
import com.CeritaBakmiBE.CB.repository.TransactionRepository;
import com.CeritaBakmiBE.CB.service.UpdatePaymentStatus;
import com.CeritaBakmiBE.CB.util.FindAuthenticationUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UpdatePaymentStatusImpl implements UpdatePaymentStatus {

    private final TransactionRepository transactionRepository;
    private final FindAuthenticationUser findAuthenticationUser;

    @Override
    @Transactional
    public void updatePaymentStatus(long transactionId, PaymentStatus paymentStatus) throws Exception {

        User currentUser = findAuthenticationUser.getAuthenticatedUser();

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction not found"
                        ));
        if(!"ADMIN".equals(currentUser.getRole())){
            throw new Exception("Not Authorized");
        }

        if(transaction.getBranch().getBranchId() == currentUser.getBranch().getBranchId()){
            transaction.setPaymentStatus(paymentStatus);
        } else{
            throw new RuntimeException("Transaction not found");
        }

        transactionRepository.save(transaction);

    }
}
