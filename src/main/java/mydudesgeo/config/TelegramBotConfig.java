package mydudesgeo.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mydudesgeo.telegram.bot.command.BotCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {

    @Value("${mydudes.telegram.bot.token}")
    private String token;

    private final ApplicationContext applicationContext;

    @Bean
    public String botToken() {
        return token;
    }

    @Bean
    public List<BotCommand> botCommands() {
        return Arrays.stream(applicationContext.getBeanNamesForType(BotCommand.class))
                .map(v -> (BotCommand) applicationContext.getBean(v))
                .toList();
    }
}
