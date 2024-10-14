package d2a.sn.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class DocumentApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }

}
