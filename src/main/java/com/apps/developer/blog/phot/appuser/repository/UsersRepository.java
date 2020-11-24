package com.apps.developer.blog.phot.appuser.repository;

import org.springframework.data.repository.CrudRepository;

import com.apps.developer.blog.phot.appuser.data.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
}
