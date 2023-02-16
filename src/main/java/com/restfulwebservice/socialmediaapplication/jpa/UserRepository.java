package com.restfulwebservice.socialmediaapplication.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulwebservice.socialmediaapplication.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
