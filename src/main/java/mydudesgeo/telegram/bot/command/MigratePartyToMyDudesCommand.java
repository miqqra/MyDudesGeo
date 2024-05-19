package mydudesgeo.telegram.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import mydudesgeo.service.PartyIntegrationService;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class MigratePartyToMyDudesCommand implements BotCommand {

    private final PartyIntegrationService partyIntegrationService;
    private final String command = "/migrateMyDudes";
    private final String description = "Мигрировать мероприятие в MyDudes";

    @Override
    public SendMessage handle(Update update) {
        Optional.of(update)
                .ifPresent(partyIntegrationService::migratePartyFromTelegram);
        return null;
    }
}
