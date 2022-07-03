package com.medsavy.authservice.configs;

import com.medsavy.authservice.calls.UserDto;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class MyUserDetailService  {


  @Autowired
  private RestTemplate restTemplate;

  public UserDto fetchUserData(String username) {
    ResponseEntity<UserDto> response =  restTemplate.getForEntity(
        "http://localhost:8080/api/v1/user/customer/role?username={username}",
        UserDto.class,
        username
    );

    return response.getBody();
  }



}

