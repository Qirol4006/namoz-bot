package uz.qiroldev.namozvaqtlari.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.qiroldev.namozvaqtlari.messages.MessageRoad;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    @Autowired
    MessageRoad messageRoad;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();

            if (message.hasPhoto()){
                messageRoad.defaultPhotoMsg(update);
            }

            if (message.hasText()){

                if (message.getReplyToMessage() != null){
                    messageRoad.replyTextMsg(update);
                }else {
                    messageRoad.defaultTextMsg(update);
                }

            }
        }
    }


    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
