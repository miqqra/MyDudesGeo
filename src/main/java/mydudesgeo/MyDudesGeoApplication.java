package mydudesgeo;

import java.util.Optional;
import mydudesgeo.telegram.bot.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyDudesGeoApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(MyDudesGeoApplication.class, args);
        Optional.of(ctx.getBean(Bot.class))
                .ifPresent(Bot::start);
    }

}
