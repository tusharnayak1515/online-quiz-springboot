package com.onlinequizapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onlinequizapp.entities.User;
import com.onlinequizapp.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("User Not Found!");
        }
        else {
        	// System.out.println("user: "+user);
//            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        	return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
    }

    public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		this.userRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(int id) {
		this.userRepo.deleteById(id);
	}

	public User findOne(String email) {
		return this.userRepo.findByEmail(email);
	}

	public User findById(int id) {
		Optional<User> optionalUser = this.userRepo.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    public User update(User userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            user.setId(userDto.getId());
            this.userRepo.save(user);
        }
        return userDto;
    }

    public User createUser(User user) {
		return this.userRepo.save(user); 
    }
    
    public boolean checkPassword(UserDetails userDetails, String password) {
        return passwordEncoder.matches(password, userDetails.getPassword());
    }
    
}

