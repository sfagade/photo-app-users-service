package com.apps.developer.blog.phot.appuser.service.impl;

import com.apps.developer.blog.phot.appuser.data.UserEntity;
import com.apps.developer.blog.phot.appuser.repository.UsersRepository;
import com.apps.developer.blog.phot.appuser.service.UsersService;
import com.apps.developer.blog.phot.appuser.shared.UserDto;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UsersService {
    
	UsersRepository usersRepository;
	BCryptPasswordEncoder bcryptPasswordEnconder;
	
	@Autowired
	public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bcryptPasswordEnconder) {
		this.usersRepository = usersRepository;
		this.bcryptPasswordEnconder = bcryptPasswordEnconder;
	}
	
	@Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bcryptPasswordEnconder.encode(userDetails.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        
        usersRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if (userEntity == null)  throw new UsernameNotFoundException(email);

	    return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);

        if (userEntity == null)  throw new UsernameNotFoundException(username);

	    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true,
                true, true, new ArrayList<>());
    }
}
