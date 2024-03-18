package com.tranhunghoan.Online.Food.Order.controller;

import com.tranhunghoan.Online.Food.Order.config.JwtProvider;
import com.tranhunghoan.Online.Food.Order.model.Cart;
import com.tranhunghoan.Online.Food.Order.model.User;
import com.tranhunghoan.Online.Food.Order.model.enumeration.USER_ROLE;
import com.tranhunghoan.Online.Food.Order.repository.CartRepository;
import com.tranhunghoan.Online.Food.Order.repository.UserRepository;
import com.tranhunghoan.Online.Food.Order.request.LoginRequest;
import com.tranhunghoan.Online.Food.Order.response.AuthResponse;
import com.tranhunghoan.Online.Food.Order.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Security;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("register")
    public ResponseEntity<AuthResponse>register(@RequestBody User user) throws Exception {
        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist !=null){
            throw new Exception("Email already used with another account");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCustom(saveUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Register success");
        authResponse.setRole(saveUser.getRole());



        return  new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse>Login(@RequestBody LoginRequest req) throws Exception{

        String email = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(email,password);

        String jwt = jwtProvider.generateToken(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    private Authentication authenticate(String email, String password) {

        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(email);
        if(userDetails == null)
        {
            throw new BadCredentialsException("Invalid email ... ");

        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("invalid password ... ");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
