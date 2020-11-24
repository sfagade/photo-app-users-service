package com.apps.developer.blog.phot.appuser.ui.resources;

import com.apps.developer.blog.phot.appuser.service.UsersService;
import com.apps.developer.blog.phot.appuser.shared.UserDto;
import com.apps.developer.blog.phot.appuser.ui.model.CreateUserResponseModel;
import com.apps.developer.blog.phot.appuser.ui.model.CreateUsersRequestModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * @author samsonfagade
 */
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;

	@Autowired
	UsersService usersService;

	@GetMapping("/status/check")
	public String status() {
		return "Working on port: " + env.getProperty("local.server.port");
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUsersRequestModel userDetails) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		UserDto createdUser = usersService.createUser(userDto);

		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

		return new ResponseEntity<>(returnValue, HttpStatus.CREATED);
	}
}
