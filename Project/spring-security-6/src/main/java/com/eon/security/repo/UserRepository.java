package com.eon.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eon.security.entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	User findByUserName(String userName);
}
