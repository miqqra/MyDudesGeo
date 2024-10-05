package mydudesgeo.telegram.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.Data;
import mydudesgeo.telegram.bot.command.BotCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Data
@Service
@ConditionalOnProperty(prefix = "mydudes.telegram.bot", name = "enabled", havingValue = "true")
public class UserMessageProcessor {

    @Autowired
    private List<BotCommand> commands;

    public SendMessage process(Update update) {
        BotCommand correctCommand = commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .orElse(null);
        if (correctCommand != null) {
            return correctCommand.handle(update);
        }
        return null;
    }


}
