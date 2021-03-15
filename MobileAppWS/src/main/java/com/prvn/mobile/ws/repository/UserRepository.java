package com.prvn.mobile.ws.repository;

import com.prvn.mobile.ws.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUserId(String userId);
    User findByEmailId(String emailId);
}
