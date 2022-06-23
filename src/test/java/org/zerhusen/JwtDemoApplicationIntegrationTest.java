package org.zerhusen;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.zerhusen.rest.InitialUserRestController;
import org.zerhusen.security.rest.dto.LoginDto;
import org.zerhusen.security.rest.dto.UserDto;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
   JwtDemoApplication.class,
   InitialUserRestController.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtDemoApplicationIntegrationTest {

   @LocalServerPort
   String port;

   @Autowired
   private WebTestClient webTestClient;

   @Test
   public void login() {
        FluxExchangeResult<String> getInitialUser =
           webTestClient
              .mutate()
              .build()
              .get()
              .uri("/api/initialUser")
              .exchange()
           .expectStatus()
           .isOk().returnResult(String.class);
      final byte[] content = getInitialUser.getResponseBodyContent();
      Assert.notNull(content);



      String data = new String(content);
      ObjectMapper mapper = new ObjectMapper();

      UserDto userDto = null;
      try {
         userDto = mapper.convertValue( mapper.readTree(data),UserDto.class);
      } catch (IOException e) {
         e.printStackTrace();
      }
      Assert.notNull(userDto);

      LoginDto loginDto = new LoginDto();
      loginDto.setUsername(userDto.getUsername());
      loginDto.setPassword(userDto.getPassword());

      FluxExchangeResult<String> postLoginAuthenticate =
         webTestClient
            .mutate()
            .build()
            .post()
            .uri("/api/authenticate")
            .body(BodyInserters.fromObject(userDto))
            .exchange()
            .expectStatus()
            .isOk().expectHeader().exists("Authorization").returnResult(String.class);
   }
}
