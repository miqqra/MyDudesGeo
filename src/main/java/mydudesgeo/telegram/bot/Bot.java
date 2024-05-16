package mydudesgeo.telegram.bot;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bot {

    @Value("mydudes.telegram.bot.token")
    private String token;

    private TelegramBot telegramBot;
    private final UserMessageProcessor userMessageProcessor;

    public <T extends BaseRequest<T, R>, R extends BaseResponse> R execute(BaseRequest<T, R> request) {
        return telegramBot.execute(request);
    }

    public void process(final List<Update> updates) {
        List<SendMessage> sendMessages = updates
                .stream()
                .map(userMessageProcessor::process)
                .toList();
        sendMessages.forEach(this::execute);
    }


    public void start() {
        telegramBot = new TelegramBot(token);

        telegramBot.setUpdatesListener(updates -> {
            this.process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}
