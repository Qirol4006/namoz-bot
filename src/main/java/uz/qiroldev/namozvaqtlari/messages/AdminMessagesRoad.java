package uz.qiroldev.namozvaqtlari.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.model.Admin;
import uz.qiroldev.namozvaqtlari.repository.AdminRepository;
import uz.qiroldev.namozvaqtlari.services.AdminService;
import uz.qiroldev.namozvaqtlari.services.SendMessageService;
import uz.qiroldev.namozvaqtlari.test.TestMessages;

@Service
public class AdminMessagesRoad {

    @Autowired
    AdminMessages adminMessages;

    @Autowired
    SendMessageService sendMessageService;

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TestMessages testMessages;

    public void textMessages(Update update) throws TelegramApiException {
        Message message = update.getMessage();

        User user = message.getFrom();

        String text = message.getText();

        switch (text){
            case "/start":{
                sendMessageService.sayHello(user);
                break;
            }
            case "Bot salomlashishini o'zgartirish":{
                adminMessages.msgForSetHelloMsg(message);
                adminService.setMenuLevel(user, 600, "");
                break;
            }
            case "Namoz vaqtlarini qo'shish":{
                adminMessages.addPrayersTimeCommand(user);
                break;
            }
            case "Bekor qilish":{
                adminMessages.cancelProcess(user);
                break;
            }
            case "Saqlangan vaqtlarni ko'rish":{
                adminMessages.getTimes(user);
                break;
            }
            case "Namozvaqtini o'chirish":{
                adminMessages.deleteTimesMsg(user);
                break;
            }
            case "Vaqtlarni jo'natish vaqti":{
                adminMessages.setSendTimes(user);
                break;
            }
            case "Vaqtlar yuborish uchun xabar ko'rinishi":{
                adminMessages.setFirstMsgMsg(user);
                break;
            }
            case "Vaqtlar yuborish ushun rasmlar tanlash":{
                adminMessages.setPhotoForMsg(user);
                break;
            }
            case "Barchaga xabar yuborish":{
                adminMessages.sendMsgToAllMembers(user);
                break;
            }
            case "Test":{
                testMessages.testAll(user.getId().toString());
                break;
            }
            default:{
                messageByMenuLevel(update);
                break;
            }
        }

    }

    private void messageByMenuLevel(Update update) throws TelegramApiException {

        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        switch (admin.getMenuLevel()){
            case 600:{
                adminMessages.setHelloMsg(update.getMessage());
                break;
            }
            case 100:{
                adminMessages.setMothAndDay(update);
                break;
            }
            case 102:{
                adminMessages.setSubhiByDayAndMonth(update);
                break;
            }
            case 103:{
                adminMessages.setBomdodByDayAndMonth(update);
                break;
            }
            case 104:{
                adminMessages.setQuyoshByDayAndMonth(update);
                break;
            }
            case 105:{
                adminMessages.setZavolByDayAndMonth(update);
                break;
            }
            case 106:{
                adminMessages.setPeshinByDayAndMonth(update);
                break;
            }
            case 107:{
                adminMessages.setAsrByDayAndMonth(update);
                break;
            }
            case 108:{
                adminMessages.setShomByDayAndMonth(update);
                break;
            }
            case 109:{
                adminMessages.setXuftonByDayAndMonth(update);
                break;
            }
            case 700:{
                adminMessages.deleteTimes(update);
                break;
            }
            case 301:{
                adminMessages.setFirstMessageTime(update);
                break;
            }
            case 302:{
                adminMessages.setPeshinMsgTime(update);
                break;
            }
            case 303:{
                adminMessages.setAsrMsgTime(update);
                break;
            }
            case 304:{
                adminMessages.setShomMsgTime(update);
                break;
            }
            case 305:{
                adminMessages.setXuftonMsgTime(update);
                break;
            }
            case 401:{
                adminMessages.setFirstMsg(update);
                break;
            }
            case 402:{
                adminMessages.setPeshinMsg(update);
                break;
            }
            case 403:{
                adminMessages.setAsrMsg(update);
                break;
            }
            case 404:{
                adminMessages.setShomMsg(update);
                break;
            }
            case 405:{
                adminMessages.setXuftonMsg(update);
                break;
            }
            default:{
                break;
            }
        }

    }

    public void repliedTextMessages(Update update) throws TelegramApiException {
        Message message = update.getMessage().getReplyToMessage();
        Message orgMess = update.getMessage();

        User user = update.getMessage().getFrom();

        String text = orgMess.getText();
        String replyTxt = message.getText();

        switch (replyTxt){
            case "Salomlashuvni yuboring.":{
                adminMessages.setHelloMsg(orgMess);
            }
        }
    }

    public void photoMessages(Update update) throws TelegramApiException {

        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        switch (admin.getMenuLevel()){
            case 501:{
                adminMessages.setFirstPhoto(update);
                break;
            }
            case 502:{
                adminMessages.setPeshinPhoto(update);
                break;
            }
            case 503:{
                adminMessages.setAsrPhoto(update);
                break;
            }
            case 504:{
                adminMessages.setShomPhoto(update);
                break;
            }
            case 505:{
                adminMessages.setXuftonPhoto(update);
                break;
            }
            case 200:{
                adminMessages.sendMsgToAllMembersMsg(update);
                break;
            }
            default:{
                break;
            }
        }
    }
}
