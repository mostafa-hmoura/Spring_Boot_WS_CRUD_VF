package com.mostafa.app.ws.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mostafa.app.ws.Entities.AddressEntity;
import com.mostafa.app.ws.Entities.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
	
	List<AddressEntity> findByUser(UserEntity userEntity);

	AddressEntity findByAddressId(String addressId);
	
}
