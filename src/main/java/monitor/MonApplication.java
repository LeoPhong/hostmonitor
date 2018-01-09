package monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
@MapperScan("monitor.mapper")
public class MonApplication {
    public static void main(String[] args){
        SpringApplication.run(MonApplication.class, args);
    }
}
