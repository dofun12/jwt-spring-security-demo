package org.zerhusen.security.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.security.model.Authority;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.AuthorityRepository;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.security.rest.dto.UserDto;

import java.util.*;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;
   private final AuthorityRepository authorityRepository;

   public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
      this.userRepository = userRepository;
      this.authorityRepository = authorityRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

   @Transactional
   public Optional<User> addNewUser(UserDto userDto) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      Optional<Authority> authority = authorityRepository.findAuthorityByName("ROLE_USER");
      User user = userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(new User());
      user.setUsername(userDto.getUsername());
      user.setPassword(encoder.encode(userDto.getPassword()));
      user.setActivated(userDto.isActivated());
      user.setEmail(userDto.getEmail());
      user.setLastname(userDto.getLastname());
      user.setFirstname(userDto.getFirstname());
      Set<Authority> authoritySet = new HashSet<>(1);
      authoritySet.add(authority.get());
      user.setAuthorities(authoritySet);



      return Optional.of(userRepository.save(user));
   }

}
