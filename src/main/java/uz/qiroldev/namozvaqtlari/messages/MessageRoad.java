package uz.qiroldev.namozvaqtlari.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.services.AdminService;

@Service

public class MessageRoad {

    @Autowired
    AdminMessagesRoad messagesRoad;

    @Autowired
    UserMessagesRoad userMessages;

    @Autowired
    AdminService adminService;

    public void defaultTextMsg(Update update) throws TelegramApiException {

        if (adminService.checkAdmin(update.getMessage().getFrom())){
            messagesRoad.textMessages(update);
        }else {
            userMessages.textMessages(update);
        }
    }

    public void replyTextMsg(Update update) throws TelegramApiException {

        if (adminService.checkAdmin(update.getMessage().getFrom())){
            messagesRoad.repliedTextMessages(update);
        }else {
            userMessages.repliedTextMessages(update);
        }


    }

    public void defaultPhotoMsg(Update update) throws TelegramApiException {
        if (adminService.checkAdmin(update.getMessage().getFrom())){
            messagesRoad.photoMessages(update);
        }else {
            userMessages.photoMessages(update);
        }
    }
}
