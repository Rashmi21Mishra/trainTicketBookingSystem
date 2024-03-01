package com.user.trainticket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user.trainticket.entity.ReceiptVO;
import com.user.trainticket.entity.UserDtlsVO;
import com.user.trainticket.service.TrainTicketService;

@RestController
@RequestMapping("/trainticket")
public class TrainTicketController {
	
	@Autowired
	private TrainTicketService trainTicketService;
	
	 	@PostMapping("/purchase")
	    public ResponseEntity<Object> purchaseTicket(@RequestBody(required = true) UserDtlsVO userDtlsVO, @RequestParam(required = true) String section) {
	 		
	 		ReceiptVO receiptVO = null;
	        try {
	        	 receiptVO = trainTicketService.purchaseTicket(userDtlsVO,section);
	        }catch (ArithmeticException e) {
	        	return ResponseEntity.ok("Error:"+e);
			}catch(NullPointerException e1) {
				return ResponseEntity.ok("Error:"+e1);
			}
	 		
	        if(receiptVO != null) {
	            return ResponseEntity.ok(receiptVO);
	        }else {
	        	return ResponseEntity.ok("Ticket Purchase Failed!");
	        }
	        
	    }

	    @GetMapping("/getReceipt")
	    public ResponseEntity<?> viewReceiptDetails(@RequestParam String userId) {
	        ReceiptVO receipt = trainTicketService.getReceiptDetails(Long.parseLong(userId));
	        return ResponseEntity.ok(receipt);
	    }

	    @GetMapping("/getUserDtlsBySection")
	    public ResponseEntity<List<UserDtlsVO>> viewUserSeatAllocationBySection(@RequestParam String section) {
	        List<UserDtlsVO> usersList = trainTicketService.getUserSeatBySection(section);
	        return ResponseEntity.ok(usersList);
	    }
	    
	    @GetMapping("/gellAllUsers")
	    public ResponseEntity<Iterable<UserDtlsVO>> getAllUsers() {
	    	Iterable<UserDtlsVO> usersList = trainTicketService.getAllUsers();
	        return ResponseEntity.ok(usersList);
	    }

	    @DeleteMapping("/removeUser")
	    public ResponseEntity<String> removeUserFromTrain(@RequestParam String userId) {
	        boolean res = trainTicketService.removeUserFromTrain(Long.parseLong(userId));
	        if(res)
	        	return ResponseEntity.ok("User removed successfully");
	        else {
	        	return ResponseEntity.ok("Error in User Removal");
			}
	    }

	    @PutMapping("/modifyUserSeat")
	    public ResponseEntity<String> modifyUserSeat(@RequestParam String userId, @RequestParam String section, @RequestParam Long seatNo) {
	        boolean res = trainTicketService.modifyUserSeat(Long.parseLong(userId),section,seatNo);
	        if(res)
	        	return ResponseEntity.ok("User's seat modified successfully");
	        else {
	        	return ResponseEntity.ok("Error in User's seat modification");
			}
	        
	    }

}
