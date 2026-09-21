package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import com.CeritaBakmiBE.CB.Enum.PaymentStatus;
import com.CeritaBakmiBE.CB.entity.*;
import com.CeritaBakmiBE.CB.repository.*;
import com.CeritaBakmiBE.CB.request.CheckoutRequest;
import com.CeritaBakmiBE.CB.response.CheckoutResponse;
import com.CeritaBakmiBE.CB.response.TransactionDetailResponse;
import com.CeritaBakmiBE.CB.service.CheckoutService;
import com.CeritaBakmiBE.CB.util.FindAuthenticationUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final BranchRepository branchRepository;
    private final FindAuthenticationUser findAuthenticationUser;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) {

        User currentUser = findAuthenticationUser.getAuthenticatedUser();
        Optional<Cart> userCart = cartRepository.findByUser(currentUser);

        Cart cart;
        Branch branch;
        List<CartItem> cartItems;
        List<TransactionDetailResponse> responses = new ArrayList<>();


        if(userCart.isPresent()){
            cart = userCart.get();
            cartItems = cartItemRepository.findByCart(cart);

            if(cartItems.isEmpty()){
                throw new RuntimeException("Item kosong");
            }
        } else{
            throw new RuntimeException("Cart tidak ditemukan");
        }

        Long branchId = checkoutRequest.getBranchId();
        Optional<Branch> findBranch =  branchRepository.findById(branchId);

        if(findBranch.isPresent()){
            branch = findBranch.get();
        } else{
            throw new RuntimeException("Cabang tidak ditemukan");
        }

        int subtotal = 0;

        for(CartItem cartItem : cartItems){
            subtotal += cartItem.getMenu().getPrice() * cartItem.getQuantity();
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setUser(currentUser);
        newTransaction.setBranch(branch);
        newTransaction.setDeliveryAddress(checkoutRequest.getDeliveryAddress());
        newTransaction.setSubtotal(subtotal);
        newTransaction.setTotalAmount(subtotal);
        newTransaction.setPaymentStatus(PaymentStatus.UNPAID);
        newTransaction.setOrderStatus(OrderStatus.WAITING);
        newTransaction.setCreatedAt(LocalDateTime.now());
        newTransaction.setUpdatedAt(LocalDateTime.now());

        Transaction saveTransaction = transactionRepository.save(newTransaction);

        for(CartItem cartItem : cartItems){
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setMenu(cartItem.getMenu());
            transactionDetail.setQuantity(cartItem.getQuantity());
            transactionDetail.setPrice(cartItem.getMenu().getPrice());
            transactionDetail.setSubtotal(cartItem.getMenu().getPrice() * cartItem.getQuantity());
            transactionDetail.setTransaction(newTransaction);

            transactionDetailRepository.save(transactionDetail);

            TransactionDetailResponse detailResponse = new TransactionDetailResponse(
                    cartItem.getMenu().getMenuTitle(),
                    cartItem.getQuantity(),
                    cartItem.getMenu().getPrice(),
                    cartItem.getMenu().getPrice() * cartItem.getQuantity()
            );

            responses.add(detailResponse);
        }

       cartItemRepository.deleteAll(cartItems);

        String phoneNumber = saveTransaction.getBranch().getPhoneNumber();

        List<TransactionDetail> details =  transactionDetailRepository.findByTransaction(saveTransaction);

        String message =
                "Halo Cerita Bakmi \n\n" +
                "Saya ingin melakukan pemesanan. \n\n" +
                "atas nama: " + saveTransaction.getUser().getUsername() + "\n"+
                "No. Transaksi: #" + saveTransaction.getTransactionId() + "\n\n" +
                "Pesanan:\n";

        for(TransactionDetail transactionDetail : details){

            message += "-"
                    + transactionDetail.getMenu().getMenuTitle()
                    + " x"
                    + transactionDetail.getQuantity()
                    + " =Rp " +transactionDetail.getSubtotal()
                    + "\n";
        }
        message +=
                "\nTotal : Rp" + saveTransaction.getTotalAmount() +
                "\nAlamat Pengiriman: " + saveTransaction.getDeliveryAddress() +
                "\nPembayaran belum termasuk ongkir";

        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        String whatsAppUrl = "https://wa.me/" + phoneNumber + "?text=" + encodedMessage;


        return new CheckoutResponse(
                saveTransaction.getTransactionId(),
                saveTransaction.getBranch().getBranchName(),
                saveTransaction.getDeliveryAddress(),
                saveTransaction.getSubtotal(),
                saveTransaction.getTotalAmount(),
                saveTransaction.getPaymentStatus(),
                saveTransaction.getOrderStatus(),
                whatsAppUrl,
                responses


        );
    }

    @Override
    public List<CheckoutResponse> getAllTransaction() {
        return List.of();
    }

    @Override
    public List<CheckoutResponse> getTransactionByUser() throws Exception{

        User currentUser = findAuthenticationUser.getAuthenticatedUser();
        List<Transaction> userTransaction = transactionRepository.findByUser(currentUser);

       List<CheckoutResponse> responses = new ArrayList<>();

       for(Transaction transaction : userTransaction){

           List<TransactionDetailResponse> details = new ArrayList<>();

           for(TransactionDetail transactionDetail : transaction.getTransactionDetails()){
               TransactionDetailResponse detailResponse =
                       new TransactionDetailResponse(
                               transactionDetail.getMenu().getMenuTitle(),
                               transactionDetail.getQuantity(),
                               transactionDetail.getPrice(),
                               transactionDetail.getSubtotal()
                       );
               details.add(detailResponse);
           }

           CheckoutResponse response = new CheckoutResponse(
                transaction.getTransactionId(),
                transaction.getBranch().getBranchName(),
                transaction.getDeliveryAddress(),
                transaction.getSubtotal(),
                transaction.getTotalAmount(),
                transaction.getPaymentStatus(),
                transaction.getOrderStatus(),
                null,
                details
           );

           responses.add(response);
       }

        return responses;
    }
}
