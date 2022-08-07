package com.mostafa.app.ws.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mostafa.app.ws.Entities.UserEntity;
import com.mostafa.app.ws.repositories.UserRepository;

import com.mostafa.app.ws.services.UserService;
import com.mostafa.app.ws.shared.Utils;
import com.mostafa.app.ws.shared.dto.AddressDto;
import com.mostafa.app.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Utils util;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());

		if (checkUser != null)
			throw new RuntimeException("User Alrady Exists !");

		for (int i = 0; i < user.getAddresses().size(); i++) {

			AddressDto address = user.getAddresses().get(i);
			address.setUser(user);
			address.setAddressId(util.generateAddressId());
			user.getAddresses().set(i, address);
		}

		user.getContact().setContactId(util.generateContactId());
		user.getContact().setUser(user);

		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		userEntity.setUserId(util.generateUserId());

		UserEntity newUser = userRepository.save(userEntity);

		UserDto userDto = modelMapper.map(newUser, UserDto.class);

		return userDto;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String id) {

		UserEntity userEntity = userRepository.findByUserId(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDto updateUser(String id, UserDto user) {
		UserEntity userEntity = userRepository.findByUserId(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());

		UserEntity userUpdated = userRepository.save(userEntity);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userUpdated, userDto);

		return userDto;
	}

	@Override
	public void deleteUser(String id) {
		UserEntity userEntity = userRepository.findByUserId(id);

		if (userEntity == null)
			throw new UsernameNotFoundException(id);

		userRepository.delete(userEntity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit,String search,int status) {

		if (page > 0)
			page -= 1;

		List<UserDto> usersDto = new ArrayList<>();

		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> userPage;
		
		if(search.isEmpty()) {
			userPage = userRepository.findAllUsers(pageableRequest);
		}
			
		else {
			userPage = userRepository.findAllUsersByFullName(pageableRequest, search, status);
		}
		
		List<UserEntity> users = userPage.getContent();

		for (UserEntity userEntity : users) {

			ModelMapper modelMapper = new ModelMapper();
			UserDto user = modelMapper.map(userEntity, UserDto.class);

			usersDto.add(user);
		}

		return usersDto;

	}

}
