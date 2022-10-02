package com.adityacode.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adityacode.photoapp.api.users.data.UserEntity;
import com.adityacode.photoapp.api.users.data.UsersRepository;
import com.adityacode.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository userRepository;
	
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UsersServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		// TODO Auto-generated method stub
		
		userDetails.setUserId(UUID.randomUUID().toString());  // random alpha-numeric string of chars that can be user as a ID
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		//userEntity.setEncryptedPassword("test");
		
		userRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
		
		return returnValue;
	}

}
