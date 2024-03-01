package com.user.trainticket.repository;

import org.springframework.data.repository.CrudRepository;

import com.user.trainticket.entity.UserDtlsVO;

public interface UserRepositoy extends CrudRepository<UserDtlsVO, Long>{

}
