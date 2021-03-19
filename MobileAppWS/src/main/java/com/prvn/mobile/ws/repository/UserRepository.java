package com.prvn.mobile.ws.repository;

import com.prvn.mobile.ws.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findByUserId(String userId);
  UserEntity findByEmailId(String emailId);
}
