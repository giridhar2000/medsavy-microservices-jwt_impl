package com.medsavy.medsavyuserservice.Controller;

import com.medsavy.medsavyuserservice.Entity.JwtEntity;
import com.medsavy.medsavyuserservice.Entity.UserEntity;
import com.medsavy.medsavyuserservice.config.JwtUtil;
import com.medsavy.medsavyuserservice.exchanges.AddCartRequest;
import com.medsavy.medsavyuserservice.exchanges.AddCartResponse;
import com.medsavy.medsavyuserservice.exchanges.DeleteCartRequest;
import com.medsavy.medsavyuserservice.exchanges.DeleteCartResponse;
import com.medsavy.medsavyuserservice.exchanges.GetCartResponse;
import com.medsavy.medsavyuserservice.exchanges.LoginResponse;
import com.medsavy.medsavyuserservice.exchanges.TakeSubscriptionReq;
import com.medsavy.medsavyuserservice.exchanges.TakeSubscriptionResponse;
import com.medsavy.medsavyuserservice.exchanges.UserLoginRequest;
import com.medsavy.medsavyuserservice.exchanges.UserRegistrationRequest;
import com.medsavy.medsavyuserservice.exchanges.UserRegistrationResponse;
import com.medsavy.medsavyuserservice.repository.AuthRepository;
import com.medsavy.medsavyuserservice.services.MyUserDetailService;
import com.medsavy.medsavyuserservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserServiceController {

  @Autowired
  private UserService userService;

  @Autowired
  private MyUserDetailService userDetailService;

  @Autowired
  private AuthRepository authRepository;


  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/register")
  public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest request) {
    return userDetailService.createUser(request);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginRequest loginRequest) throws Exception {

    LoginResponse loginResponse = new LoginResponse();
    // generate token
    UserEntity user = userDetailService.loadUserByUsername(loginRequest.getUsername());

    // auth user
    if(user == null || !user.getPassword().equals(loginRequest.getPassword())) {
      loginResponse.setMessage("Username or password is incorrect");
      loginResponse.setSuccess(false);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
    }

    String token = jwtUtil.generateToken(user);

    JwtEntity jwtEntity = authRepository.findJwtEntityByUserId(user.getId());
    if(jwtEntity == null){
      authRepository.save(JwtEntity.builder().userId(user.getId()).token(token)
          .username(user.getUsername()).build());
    }else {
      jwtEntity.setToken(token);
      authRepository.save(jwtEntity);
    }


    return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRoles(),
        "Login successful", true
    ));
  }

  @GetMapping("/customer/role")
  public ResponseEntity<?> getUserRoleDetails(@RequestParam String username) {
    UserEntity userEntity = userDetailService.loadUserByUsername(username);
    return ResponseEntity.ok(userEntity);
  }

  @PostMapping("/customer/cart")
  public AddCartResponse addMedicineInCart(@RequestBody  AddCartRequest request) {
    return userService.addMedIntoCart(request);
  }

  @GetMapping("/customer/cart/items")
  public GetCartResponse getCartItems(@RequestParam Integer customerId){
    return userService.getCartByCustomerId(customerId);
  }

  @DeleteMapping("/customer-cart/items/")
  public DeleteCartResponse clearCartByUserId(@RequestBody DeleteCartRequest request) {
    return userService.deleteCartItemsByCustomerId(request.getCustomerId());
  }

  @PostMapping("/customer/subscription")
  public TakeSubscriptionResponse takeSubscription(@RequestBody TakeSubscriptionReq req) {
    return userService.takeSubscription(req);
  }
}
