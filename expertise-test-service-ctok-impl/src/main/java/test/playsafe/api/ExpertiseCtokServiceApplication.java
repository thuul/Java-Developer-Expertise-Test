package test.playsafe.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 *
 * @author walles
 */
@ServletComponentScan
@SpringBootApplication
public class ExpertiseCtokServiceApplication {

    /**
     * Entry function - main
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ExpertiseCtokServiceApplication.class, args);
    }

}
