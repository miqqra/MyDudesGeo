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

    private PartyIntegrationService partyIntegrationService;
    private final String command = "/hello";
    private final String description = "Помаши ручкой боту";

    @Override
    public SendMessage handle(Update update) {
        Optional.of(update)
                .map(Update::message)
                .map(MaybeInaccessibleMessage::chat)
                .ifPresent(v -> partyIntegrationService.addUserChatId(v.username(), v.id()));
        return null;
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }
}
