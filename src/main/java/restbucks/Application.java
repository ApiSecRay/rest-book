package restbucks;

import java.util.Currency;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import restbucks.domain.menu.Drink;
import restbucks.domain.menu.DrinkRepository;


@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner run(DrinkRepository repository) {
    return args -> {
      repository.save(new Drink("caffe latte", 2.75, Currency.getInstance("USD")));
    };
  }

}
