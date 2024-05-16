package mydudesgeo.service;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.ChatLocation;
import com.pengrad.telegrambot.request.ExportChatInviteLink;
import com.pengrad.telegrambot.request.GetChat;
import com.pengrad.telegrambot.request.SendLocation;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetChatResponse;
import com.pengrad.telegrambot.response.StringResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mydudesgeo.common.Location;
import mydudesgeo.telegram.bot.Bot;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TgBotService {

    private Bot bot;

    public List<String> getChatMembers(Long chatId) {
        return Optional.of(chatId)
                .map(this::getChatInfo)
                .map(Chat::getActiveUsernames)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public String getChatName(Long chatId) {
        return Optional.of(chatId)
                .map(this::getChatInfo)
                .map(Chat::firstName)
                .orElse(null);
    }

    //takes info from pinned message
    public String getChatDescription(Long chatId) {
        return Optional.of(chatId)
                .map(this::getChatInfo)
                .map(Chat::bio)
                .orElse(null);
    }

    public Location getChatLocation(Long chatId) {
        return Optional.of(chatId)
                .map(this::getChatInfo)
                .map(Chat::location)
                .map(ChatLocation::location)
                .map(v -> new Location()
                        .setLatitude(v.latitude())
                        .setLongitude(v.longitude()))
                .orElse(null);
    }

    public Boolean sendLocationToChat(Long chatId, Location location) {
        return Optional.of(chatId)
                .map(id -> new SendLocation(id, location.getLatitude(), location.getLongitude()))
                .map(bot::execute)
                .map(BaseResponse::isOk)
                .orElse(false);
    }

    public Boolean sendInviteToChat(Long chatId, Long userIdTg, String messageFormatter, String partyName) {
        return Optional.of(chatId)
                .map(ExportChatInviteLink::new)
                .map(bot::execute)
                .map(StringResponse::result)
                .map(inviteLink -> messageFormatter.formatted(partyName, inviteLink))
                .map(v -> new SendMessage(userIdTg, v))
                .map(bot::execute)
                .map(BaseResponse::isOk)
                .orElse(false);
    }

    public Boolean sendMessageToChat(Long chatId, String message) {
        return Optional.of(chatId)
                .map(id -> new SendMessage(id, message))
                .map(bot::execute)
                .map(BaseResponse::isOk)
                .orElse(false);
    }

    private Chat getChatInfo(Long chatId) {
        return Optional.of(chatId)
                .map(GetChat::new)
                .map(bot::execute)
                .map(GetChatResponse::chat)
                .orElse(null); //todo send failed message
    }
}
