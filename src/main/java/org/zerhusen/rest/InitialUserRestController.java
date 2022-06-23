package org.zerhusen.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.StaticSession;
import org.zerhusen.security.rest.dto.UserDto;
import org.zerhusen.security.service.UserService;

@RestController
@RequestMapping("/api/initialUser")
public class InitialUserRestController {

   private final UserService userService;
   public InitialUserRestController(UserService userService){
      this.userService = userService;
   }

   @GetMapping("")
   public ResponseEntity<UserDto> initialUser() {
      if(StaticSession.HAS_USERS){
         return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(userService.generateRootUser().get());
   }
}
