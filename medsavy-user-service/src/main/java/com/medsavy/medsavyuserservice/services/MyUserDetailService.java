package com.medsavy.medsavyuserservice.services;

import com.medsavy.medsavyuserservice.Entity.UserEntity;
import com.medsavy.medsavyuserservice.exchanges.IVCreateRequest;
import com.medsavy.medsavyuserservice.exchanges.IVCreateResponse;
import com.medsavy.medsavyuserservice.exchanges.UserRegistrationRequest;
import com.medsavy.medsavyuserservice.exchanges.UserRegistrationResponse;
import com.medsavy.medsavyuserservice.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyUserDetailService {


  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RestTemplate restTemplate;

  public UserRegistrationResponse createUser(UserRegistrationRequest request){

    String message = "";


    if(userRepository.existsByUsername(request.getUsername())){
      message = message + "Username already exists";
    }

    if(!message.isEmpty()){

      UserRegistrationResponse response = new UserRegistrationResponse(
          request.getUsername(),
          request.getRoles(),
          message,
          null
      );
      return response;
    }

    UserEntity userEntity = UserEntity.builder().username(request.getUsername())
            .password(request.getPassword())
                .roles(request.getRoles())
                    .build();
    userRepository.save(userEntity);
    UserRegistrationResponse response = new UserRegistrationResponse();



    if(userEntity != null) {
      // Create inventory for user if admin
      IVCreateRequest ivCreateRequest = new IVCreateRequest();
      if(userEntity.getRoles().equals("ADMIN")){

        ivCreateRequest.setUserId(userEntity.getId());
        ResponseEntity<IVCreateResponse> ivCreateResponse = restTemplate.postForEntity(
            "http://localhost:8081/api/v1/ivm/admin/inventory",
            ivCreateRequest,
            IVCreateResponse.class
        );
        if(ivCreateResponse.hasBody() && ivCreateResponse.getBody().isSuccess()) {
          response.setInventoryId(ivCreateResponse.getBody().getInventoryId());
        }
      }

      response.setUsername(userEntity.getUsername());
      response.setRoles(userEntity.getRoles());
      response.setMessage("Registration Successful");
      return response;
    }

    response.setMessage("Registration failed");
    return response;
  }

  public UserEntity loadUserByUsername(String username) {
    Optional<UserEntity> user = userRepository.findByUsername(username);

    if(user.isPresent()) {
      return user.get();
    }

    return null;
  }
}
