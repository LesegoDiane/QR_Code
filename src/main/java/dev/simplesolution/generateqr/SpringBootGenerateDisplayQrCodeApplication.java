package dev.simplesolution.generateqr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootGenerateDisplayQrCodeApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringBootGenerateDisplayQrCodeApplication.class, args);
     
        		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootGenerateDisplayQrCodeApplication.class);
        		builder.headless(false);

        		ConfigurableApplicationContext context = builder.run(args);
  
    }

}
