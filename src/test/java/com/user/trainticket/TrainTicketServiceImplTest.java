package  com.user.trainticket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.user.trainticket.entity.ReceiptVO;
import com.user.trainticket.entity.UserDtlsVO;
import com.user.trainticket.repository.UserRepositoy;
import com.user.trainticket.service.impl.TrainTicketServiceImpl;

@SpringBootTest
public class TrainTicketServiceImplTest {

    @Mock
    private UserRepositoy userRepositoy;

    @InjectMocks
    private TrainTicketServiceImpl trainTicketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPurchaseTicket_Success() {
      
        UserDtlsVO userDtlsVO = new UserDtlsVO();
        userDtlsVO.setEmailId("test@example.com");
        String section = "A";

      
        when(userRepositoy.save(any(UserDtlsVO.class))).thenReturn(userDtlsVO);

        
        ReceiptVO receiptVO = trainTicketService.purchaseTicket(userDtlsVO, section);

       
        assertNotNull(receiptVO);
        assertEquals("test@example.com", receiptVO.getUserEmailId());
    }

    @Test
    public void testPurchaseTicket_NoEmail() {
        
        UserDtlsVO userDtlsVO = new UserDtlsVO();
        String section = "A";

    
        assertThrows(NullPointerException.class, () -> trainTicketService.purchaseTicket(userDtlsVO, section));
    }
    
    @Test
    public void testRemoveUserFromTrain_Success() {
       
        Long userId = 1L;
        when(userRepositoy.count()).thenReturn(2L, 1L); 

       
        boolean result = trainTicketService.removeUserFromTrain(userId);

      
        assertTrue(result);
    }

    @Test
    public void testRemoveUserFromTrain_Failure() {
      
        Long userId = 1L;
        when(userRepositoy.count()).thenReturn(1L); 

        boolean result = trainTicketService.removeUserFromTrain(userId);

        assertFalse(result);
    }
}