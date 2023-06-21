package com.onlinequizapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlinequizapp.dto.ChangePasswordRequest;
import com.onlinequizapp.dto.JwtRequest;
import com.onlinequizapp.dto.JwtResponse;
import com.onlinequizapp.dto.RegisterRequest;
import com.onlinequizapp.dto.UserResponse;
import com.onlinequizapp.entities.User;
import com.onlinequizapp.services.CustomUserDetailsService;
import com.onlinequizapp.utils.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
    private final CustomUserDetailsService customUserDetailsService;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtil jwtUtil;
    
    @Autowired
    public UserController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager,
    		JwtUtil jwtUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) throws Exception {
    	try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword())
            );
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is disabled");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        
        Cookie jwtCookie = new Cookie("authorization", token);
        jwtCookie.setMaxAge(86400000);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        JwtResponse myresponse = new JwtResponse();
        User user = customUserDetailsService.findOne(jwtRequest.getEmail());
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        myresponse.setSuccess(true);
        myresponse.setUser(userResponse);
        return ResponseEntity.ok(myresponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) throws Exception {
    	User isUser = this.customUserDetailsService.findOne(request.getEmail());
        if(isUser != null) {
            JwtResponse myResponse = new JwtResponse();
            myResponse.setSuccess(false);
            myResponse.setError("This email is already taken");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(myResponse);
        }
        User newuser = new User();
        newuser.setName(request.getName());
        newuser.setEmail(request.getEmail());
        newuser.setPassword(passwordEncoder.encode(request.getPassword()));
        newuser.setRole("user");
        User user = this.customUserDetailsService.createUser(newuser);
        
       try {
           authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
           );
       } catch (DisabledException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is disabled");
       } catch (BadCredentialsException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
       }
        
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(user.getEmail());
        String token = this.jwtUtil.generateToken(userDetails);
        
        Cookie jwtCookie = new Cookie("authorization", token);
        jwtCookie.setMaxAge(86400000);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        JwtResponse myresponse = new JwtResponse();
        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
        myresponse.setSuccess(true);
        myresponse.setUser(userResponse);
        return ResponseEntity.ok(myresponse);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<JwtResponse> profile(HttpServletRequest request) throws Exception {
   	   Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String email = authentication.getName();
       User user = this.customUserDetailsService.findOne(email);
       JwtResponse myresponse = new JwtResponse();
       UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
       myresponse.setSuccess(true);
       myresponse.setUser(userResponse);
       return ResponseEntity.ok(myresponse);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User myuser = this.customUserDetailsService.findOne(email);

        if(!myuser.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed");
        }

        user = this.customUserDetailsService.update(user);

        Cookie cookie = new Cookie("authorization", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Profile updated. Login again to continue");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest obj, HttpServletResponse response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User myuser = this.customUserDetailsService.findOne(email);

        if(!myuser.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed");
        }

        myuser.setPassword(this.passwordEncoder.encode(obj.getNewPassword()));
        myuser = this.customUserDetailsService.update(myuser);

        Cookie cookie = new Cookie("authorization", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok("Password updated successfully. Login again to continue.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("authorization", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/users")
    public ResponseEntity<?> allUSers(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User myuser = this.customUserDetailsService.findOne(email);

        if(!myuser.getRole().equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Allowed");
        }
        
        List<User> users = this.customUserDetailsService.findAll();
        return ResponseEntity.ok(users);
    } 
}
