package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import com.CeritaBakmiBE.CB.entity.Transaction;
import com.CeritaBakmiBE.CB.entity.User;
import com.CeritaBakmiBE.CB.repository.TransactionRepository;
import com.CeritaBakmiBE.CB.service.UpdateOrderStatus;
import com.CeritaBakmiBE.CB.util.FindAuthenticationUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements UpdateOrderStatus {

    private final TransactionRepository transactionRepository;
    private final FindAuthenticationUser findAuthenticationUser;

    @Override
    public void updateOrderStatus(long transactionId, OrderStatus orderStatus) throws Exception {

        User currentUser = findAuthenticationUser.getAuthenticatedUser();

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Transaction Not found"
                        ));
        if(!"ADMIN".equals(currentUser.getRole())){
            throw new Exception("Not Authorized");
        }

        if(transaction.getBranch().getBranchId() == currentUser.getBranch().getBranchId()){
            transaction.setOrderStatus(orderStatus);
        } else{
            throw new RuntimeException("Transaction not found");
        }

        transactionRepository.save(transaction);
    }
}
