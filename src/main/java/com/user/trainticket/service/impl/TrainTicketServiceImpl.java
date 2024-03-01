package com.user.trainticket.service.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.trainticket.entity.ReceiptVO;
import com.user.trainticket.entity.UserDtlsVO;
import com.user.trainticket.repository.UserRepositoy;
import com.user.trainticket.service.TrainTicketService;

@Service
public class TrainTicketServiceImpl implements TrainTicketService{
	
	private static final String TICKET_PRICE = "$20";
	private static int sectionASeat = 1;
	private static int sectionBSeat = 1;
	
	@Autowired
	private UserRepositoy userRepositoy;

	@Override
	public ReceiptVO purchaseTicket(UserDtlsVO userDtlsVO,String section) {
		
		ReceiptVO receiptVO = null;
		
		try {
			String emailId = userDtlsVO.getEmailId();
			if(emailId != null) {
				userDtlsVO.setSection(section);
				String seatNo = generateSeatNo(section);
				if(seatNo.equalsIgnoreCase("Exit")) {
					throw new ArithmeticException("There is no seat left in section "+section);   
				}
				userDtlsVO.setSeatNo(seatNo);
				
				UserDtlsVO userDtlsVO2 = userRepositoy.save(userDtlsVO);
				
				receiptVO = new ReceiptVO();
			
				if(userDtlsVO2 != null)
					receiptVO.setUserName(userDtlsVO2.getFirstName()+" "+userDtlsVO2.getLastName());
					receiptVO.setUserEmailId(userDtlsVO2.getEmailId());
					receiptVO.setOriginStation(userDtlsVO2.getOriginStaion());
					receiptVO.setDestinationStation(userDtlsVO2.getDestinationStation());
					receiptVO.setPricePaid(TICKET_PRICE);
				return receiptVO;
			}else{
				throw new NullPointerException("Email Id can not be null");
			}
		}catch (Exception e) {
			throw new NullPointerException("Email Id can not be null: "+e);
		}
	
	}

	@Override
	public Iterable<UserDtlsVO> getAllUsers() {
		Iterable<UserDtlsVO> usersList = userRepositoy.findAll();
		return usersList;
	}

	@Override
	public ReceiptVO getReceiptDetails(Long userId) {
		Optional<UserDtlsVO> resData = userRepositoy.findById(userId);
		UserDtlsVO userDtlsVO = resData.get();
		ReceiptVO receiptVO = new ReceiptVO();
		
		if(userDtlsVO != null){
			receiptVO.setUserName(userDtlsVO.getFirstName()+" "+userDtlsVO.getLastName());
			receiptVO.setUserEmailId(userDtlsVO.getEmailId());
			receiptVO.setOriginStation(userDtlsVO.getOriginStaion());
			receiptVO.setDestinationStation(userDtlsVO.getDestinationStation());
			receiptVO.setPricePaid(TICKET_PRICE);
		}
		return receiptVO;
	}

	@Override
	public List<UserDtlsVO> getUserSeatBySection(String section) {
		
		List<UserDtlsVO> userDtlsVOs = new ArrayList<>();
		
		Iterable<UserDtlsVO> usersList = userRepositoy.findAll();
		for(UserDtlsVO userDtlsVO : usersList) {
			if(userDtlsVO.getSection().equals(section)) {
				userDtlsVOs.add(userDtlsVO);
			}
		}
		return userDtlsVOs;
		
	}

	public String generateSeatNo(String section) {
		String generatedSeatNo = "";
		if(section.equalsIgnoreCase("A")) {
			if(sectionASeat > 100) {        // Maximum seat in each section is assumed to 100
				return "Exit";   
			}
			generatedSeatNo += section + "-" + sectionASeat;
			sectionASeat += 1;
		}
		else if(section.equalsIgnoreCase("B")) {
			if(sectionBSeat > 100) {
				return "Exit";   
			}
			generatedSeatNo += section + "-" + sectionBSeat;
			sectionBSeat += 1;
		}
		return generatedSeatNo;
	}

	@Override
	public boolean removeUserFromTrain(Long userId) {
		Long usersCountBeforeDeletionLong = userRepositoy.count();
		userRepositoy.deleteById(userId);
		Long usersCountAfterDeletionLong = userRepositoy.count();
		if((usersCountBeforeDeletionLong - usersCountAfterDeletionLong) == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean modifyUserSeat(Long userId, String section, Long seatNo) {
		
		Optional<UserDtlsVO> userOptional = userRepositoy.findById(userId);
		UserDtlsVO userDtlsVO = userOptional.get();
		
		userDtlsVO.setSection(section);
		userDtlsVO.setSeatNo(section + "-" + seatNo);
		
		UserDtlsVO updatedUserDtlsVO = userRepositoy.save(userDtlsVO);
		String aString = section + "-" + seatNo;
		if(updatedUserDtlsVO.getSeatNo().equalsIgnoreCase(aString))
			return true;
		return false;
	}

}
