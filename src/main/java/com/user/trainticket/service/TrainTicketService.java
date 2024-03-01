package com.user.trainticket.service;

import java.util.List;

import com.user.trainticket.entity.ReceiptVO;
import com.user.trainticket.entity.UserDtlsVO;

public interface TrainTicketService {

	ReceiptVO purchaseTicket(UserDtlsVO userDtlsVO, String section);

	Iterable<UserDtlsVO> getAllUsers();

	ReceiptVO getReceiptDetails(Long userId);

	List<UserDtlsVO> getUserSeatBySection(String section);

	boolean removeUserFromTrain(Long userId);

	boolean modifyUserSeat(Long userId, String section, Long seatNo);

}
