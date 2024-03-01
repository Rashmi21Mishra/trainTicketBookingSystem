package com.user.trainticket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import com.user.trainticket.entity.ReceiptVO;
import com.user.trainticket.entity.UserDtlsVO;
import com.user.trainticket.controller.TrainTicketController;
import com.user.trainticket.service.TrainTicketService;

public class TrainticketApplicationTests {

    @Mock
    private TrainTicketService trainTicketService;

    @InjectMocks
    private TrainTicketController trainTicketController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPurchaseTicket() {
      
        UserDtlsVO userDtlsVO = new UserDtlsVO(); 
        String section = "A";
        ReceiptVO receiptVO = new ReceiptVO(); 
        when(trainTicketService.purchaseTicket(any(UserDtlsVO.class), any(String.class))).thenReturn(receiptVO);

        ResponseEntity<Object> responseEntity = trainTicketController.purchaseTicket(userDtlsVO, section);

        assert(responseEntity.getBody()).equals(receiptVO);
    }

    @Test
    public void testViewReceiptDetails() {
      
        String userId = "1";
        ReceiptVO receiptVO = new ReceiptVO(); // create a mock receiptVO
        when(trainTicketService.getReceiptDetails(any(Long.class))).thenReturn(receiptVO);

        
        ResponseEntity<?> responseEntity = trainTicketController.viewReceiptDetails(userId);

       
        assert(responseEntity.getBody()).equals(receiptVO);
    }

}