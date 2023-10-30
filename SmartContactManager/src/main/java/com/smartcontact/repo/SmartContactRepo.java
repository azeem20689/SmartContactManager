package com.smartcontact.repo;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entities.Contact;

public interface SmartContactRepo extends JpaRepository<Contact, Integer> {

	@Query(value = "select * from smart_contact where user_id =:id ",nativeQuery = true)
	Page<Contact> findContactById(@Param("id") int id,Pageable page);
	
	@Query(value = "select * from smart_contact where \"name\" ilike '%'|| :pattern ||'%' and user_id = :userId",nativeQuery = true)
	List<Contact> searchContacts(@Param("pattern") String pattern , @Param("userId") Integer id);
}
