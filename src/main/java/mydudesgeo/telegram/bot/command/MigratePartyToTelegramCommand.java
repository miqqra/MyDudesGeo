package mydudesgeo.telegram.bot.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mydudesgeo.service.PartyIntegrationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class MigratePartyToTelegramCommand implements BotCommand {

    private final PartyIntegrationService partyIntegrationService;
    private final String command = "/migrateTelegram";
    private final String description = "Миграция мероприятия в Telegram";

    @Override
    public SendMessage handle(Update update) {
        Optional.of(update)
                .map(this::getPartyId)
                .ifPresent(partyId -> partyIntegrationService.createPartyInTelegram(partyId, getChatId(update)));
        return null;
    }

    @Override
    public boolean supports(Update update) {
        try {
            Long.parseLong(update.message().text().split(" ")[1]);
            return supports(update);
        } catch (Exception e) {
            return false;
        }
    }

    private Long getPartyId(Update update) {
        return Optional.of(update)
                .map(Update::message)
                .map(Message::text)
                .filter(StringUtils::isNotBlank)
                .map(v -> v.split(" "))
                .filter(v -> v.length > 0)
                .map(v -> v[1])
                .map(Long::parseLong)
                .orElse(null);
    }
}
