package mydudesgeo.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Optional;
import mydudesgeo.model.PartyModel;
import org.springframework.stereotype.Service;

@Service
public class TgNotifyService {

    private final TelegramBot telegramBot;

    public TgNotifyService(String botToken) {
        telegramBot = new TelegramBot(botToken);
    }

    public void notify(String message, PartyModel partyModel) {
        Optional.of(partyModel)
                .map(PartyModel::getChatIdTelegram)
                .map(chatId -> new SendMessage(chatId, message))
                .ifPresent(telegramBot::execute);
    }
}
