package mydudesgeo.telegram.bot.command;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;

public interface BotCommand {

    String getCommand();

    String getDescription();

    SendMessage handle(Update update);

    boolean supports(Update update);

    default Long getChatId(Update update) {
        return Optional.of(update)
                .map(Update::message)
                .map(Message::chat)
                .map(Chat::id)
                .orElse(null);
    }
}
