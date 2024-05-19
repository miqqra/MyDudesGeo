package mydudesgeo.telegram.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import lombok.Data;
import mydudesgeo.telegram.bot.command.BotCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserMessageProcessor {

    @Autowired
    private List<BotCommand> commands;

    public SendMessage process(Update update) {
        BotCommand correctCommand = commands
                .stream()
                .filter(command -> command.supports(update))
                .findAny()
                .orElse(null);
        if (correctCommand == null) {
            BotCommand anyCommand = getCommands().getFirst();
            return new SendMessage(
                    anyCommand.getChatId(update), "Некорректная команда");
        }
        return correctCommand.handle(update);
    }


}
