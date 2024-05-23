package mydudesgeo.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.Collection;
import java.util.Optional;
import mydudesgeo.model.PartyModel;
import mydudesgeo.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public class TgNotifyService {

    private final TelegramBot telegramBot;

    public TgNotifyService(String botToken) {
        telegramBot = new TelegramBot(botToken);
    }

    public void notify(String message, PartyModel partyModel) {
        Optional.of(partyModel)
                .map(PartyModel::getParticipants)
                .stream()
                .flatMap(Collection::stream)
                .map(UserModel::getTelegramChatId)
                .map(chatId -> new SendMessage(chatId, message))
                .forEach(telegramBot::execute);

        Optional.of(partyModel)
                .map(PartyModel::getCreator)
                .map(UserModel::getTelegramChatId)
                .map(chatId -> new SendMessage(chatId, message))
                .ifPresent(telegramBot::execute);
    }
}
