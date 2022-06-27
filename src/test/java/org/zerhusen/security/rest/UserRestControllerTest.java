package org.zerhusen.security.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zerhusen.util.AbstractRestControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.zerhusen.util.LogInUtils.getTokenForLogin;

public class UserRestControllerTest extends AbstractRestControllerTest {

   @Before
   public void setUp() {
      SecurityContextHolder.clearContext();
   }

   @Test
   public void getActualUserForUserWithoutToken() throws Exception {
      getMockMvc().perform(get("/api/user")
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }

}
