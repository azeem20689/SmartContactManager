package com.smartcontact.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entities.User;

public interface SmartUserRepo extends JpaRepository<User, Integer>{

	@Query(value = "select * from smart_user where email =:email ", nativeQuery = true)
	User getUserByEmail(@Param("email") String email);
}
