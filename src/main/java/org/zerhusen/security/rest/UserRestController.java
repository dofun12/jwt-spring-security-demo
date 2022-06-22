package org.zerhusen.security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.security.model.User;
import org.zerhusen.security.rest.dto.UserDto;
import org.zerhusen.security.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

   private final UserService userService;

   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping("")
   public ResponseEntity<User> getActualUser() {
      return ResponseEntity.ok(userService.getUserWithAuthorities().get());
   }

   @PostMapping("")
   public ResponseEntity<User> postNewUser(@RequestBody UserDto user) {
      return ResponseEntity.ok(userService.addNewUser(user).get());
   }
}
