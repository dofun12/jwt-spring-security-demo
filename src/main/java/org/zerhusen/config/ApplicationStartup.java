package org.zerhusen.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.zerhusen.StaticSession;
import org.zerhusen.security.service.UserService;

@Component
public class ApplicationStartup
implements ApplicationListener<ApplicationReadyEvent> {

  /**
   * This event is executed as late as conceivably possible to indicate that
   * the application is ready to service requests.
   */
  final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
  final private UserService userService;

  public ApplicationStartup(UserService userService){
     this.userService = userService;
  }

  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    StaticSession.HAS_USERS = userService.hasUsers();
    if(StaticSession.HAS_USERS){
       return;
    }
    userService.createBaseAuthoritys();
    logger.info("First generated user is available on {}", "/api/user/initialUser");
  }
}
