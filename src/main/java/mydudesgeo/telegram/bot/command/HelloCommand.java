package mydudesgeo.telegram.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.message.MaybeInaccessibleMessage;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mydudesgeo.service.PartyIntegrationService;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class HelloCommand implements BotCommand {

    private final PartyIntegrationService partyIntegrationService;
    private final String command = "/hello";
    private final String description = "Помаши ручкой боту";

    @Override
    public SendMessage handle(Update update) {
        return Optional.of(update)
                .map(Update::message)
                .map(MaybeInaccessibleMessage::chat)
                .map(v -> {
                    partyIntegrationService.addUserChatId(v.username(), v.id());
                    return new SendMessage(v.id(), "Привет! Я тебя запомнил! :)");
                })
                .orElse(null);
    }
}
