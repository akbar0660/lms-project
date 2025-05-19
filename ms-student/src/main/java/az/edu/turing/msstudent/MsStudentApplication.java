package az.edu.turing.msstudent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class MsStudentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsStudentApplication.class, args);
    }

}
