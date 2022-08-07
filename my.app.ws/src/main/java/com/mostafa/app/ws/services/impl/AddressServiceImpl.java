package com.mostafa.app.ws.services.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mostafa.app.ws.Entities.AddressEntity;
import com.mostafa.app.ws.Entities.UserEntity;
import com.mostafa.app.ws.repositories.AddressRepository;
import com.mostafa.app.ws.repositories.UserRepository;
import com.mostafa.app.ws.services.AddressService;
import com.mostafa.app.ws.shared.Utils;
import com.mostafa.app.ws.shared.dto.AddressDto;
import com.mostafa.app.ws.shared.dto.UserDto;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils util;

	@Override
	public List<AddressDto> getAllAddresses(String email) {

		UserEntity currentUser = userRepository.findByEmail(email);

		List<AddressEntity> addresses = currentUser.getAdmin() == true
				? (List<AddressEntity>) addressRepository.findAll()
				: (List<AddressEntity>) addressRepository.findByUser(currentUser);

		Type listType = new TypeToken<List<AddressDto>>() {
		}.getType();
		List<AddressDto> addressesDto = new ModelMapper().map(addresses, listType);

		return addressesDto;

	}

	@Override
	public AddressDto createAddress(AddressDto address, String email) {
		UserEntity currentUser = userRepository.findByEmail(email);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);

		address.setAddressId(util.generateAddressId());
		address.setUser(userDto);

		AddressEntity addressEntity = modelMapper.map(address, AddressEntity.class);
		AddressEntity newAddress = addressRepository.save(addressEntity);
		AddressDto addressDto = modelMapper.map(newAddress, AddressDto.class);
		return addressDto;
	}

	@Override
	public AddressDto getAddress(String addressId) {
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		ModelMapper modelMapper = new ModelMapper();
		
		AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
		
		return addressDto;
	}

	@Override
	public void deleteAddress(String addressId) {
		
		AddressEntity address = addressRepository.findByAddressId(addressId);
		
		if(address == null) throw new RuntimeException("Address not found");
		
		System.out.println(address.getId());
		
		addressRepository.delete(address);
	
	}
	
	

}
