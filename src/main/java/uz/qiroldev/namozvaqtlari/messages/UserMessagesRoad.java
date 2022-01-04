package uz.qiroldev.namozvaqtlari.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.services.UsersService;

@Service
public class UserMessagesRoad {

    @Autowired
    UsersService usersService;

    public void textMessages(Update update) throws TelegramApiException {

        Message message = update.getMessage();

        switch (message.getText()){
            case "/start":{
                usersService.startMessage(message.getFrom());
                break;
            }
            default:{
                break;
            }
        }
    }

    public void repliedTextMessages(Update update){

    }

    public void photoMessages(Update update) {
    }
}
