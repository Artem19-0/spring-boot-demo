package com.example.demo.security;

import com.example.demo.foodProject.model.Role;
import com.example.demo.foodProject.model.UserFood;
import com.example.demo.foodProject.repository.UserFoodRepository;
import com.example.demo.model.Role1;
import com.example.demo.model.User1;
import com.example.demo.repository.User1Repository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserFoodRepository userFoodRepository;

    public CustomUserDetailsService(UserFoodRepository userFoodRepository) {
        this.userFoodRepository = userFoodRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserFood user1 = userFoodRepository.findUserFoodByEmail(email);
        if (user1 != null) {
            return new org.springframework.security.core.userdetails.User(user1.getEmail(),
                    user1.getPassword(),
                    mapRolesToAuthorities(user1.getRole()));
        }else{
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        Collection < ? extends GrantedAuthority> mapRoles = Arrays.asList(role).stream()
                .map(r -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
        return mapRoles;
    }


}
