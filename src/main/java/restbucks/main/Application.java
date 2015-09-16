package restbucks.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import de.escalon.hypermedia.spring.hydra.HydraMessageConverter;


@SpringBootApplication
@ComponentScan("restbucks.rest")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public HydraMessageConverter hydraMessageConverter() {
    return new HydraMessageConverter();
  }
  
}
