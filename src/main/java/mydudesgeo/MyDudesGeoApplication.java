package mydudesgeo;

import mydudesgeo.telegram.bot.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyDudesGeoApplication {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(MyDudesGeoApplication.class, args);
        ctx.getBean(Bot.class).start();
    }

}
