package ut.webservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author felipe
 */
@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class Application {
    
    @RequestMapping(value={"", "/"})
    String home() {
        return "<h2>Web Services</h2>";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
