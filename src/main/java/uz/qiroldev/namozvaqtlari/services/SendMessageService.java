package uz.qiroldev.namozvaqtlari.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.bot.Bot;
import uz.qiroldev.namozvaqtlari.model.Admin;
import uz.qiroldev.namozvaqtlari.model.Configurations;
import uz.qiroldev.namozvaqtlari.model.Users;
import uz.qiroldev.namozvaqtlari.repository.AdminRepository;
import uz.qiroldev.namozvaqtlari.repository.ConfigsRepository;
import uz.qiroldev.namozvaqtlari.repository.UsersRepository;

import java.util.List;

@Service("sendMessageService")
@Configurable
public class SendMessageService {

    @Autowired
    ConfigsRepository configsRepository;

    @Autowired
    BtnService btnService;

    @Autowired
    Bot bot;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    AdminRepository adminRepository;

    public void sayHello(User user) throws TelegramApiException {

        Configurations config = configsRepository.findByName("helloMsg").orElse(null);

        String text = config.getConfig();

        text = text.replace("{ism}", user.getFirstName());


        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.fistLevel());

        bot.execute(sendMessage);
    }

    public void sayHelloToUser(User user) throws TelegramApiException {

        Configurations config = configsRepository.findByName("helloMsg").orElse(null);

        String text = config.getConfig();

        text = text.replace("{ism}", user.getFirstName());


        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);
    }

    public void notAdminMsg(String chatId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Kechirasiz siz Ushbu botda admin sifatida ro'xatdan o'tmagansiz.\n@qirol4006 ga murojaat qiling!");
        sendMessage.setChatId(chatId);

        bot.execute(sendMessage);
    }

    public void sendMsgToAllMembers(SendPhoto sendPhoto) throws InterruptedException {
        List<Users> users = usersRepository.findAll();

        for (Users s : users){

            sendPhoto.setChatId(s.getTelegramId());

            try {
                bot.execute(sendPhoto);
            }catch (TelegramApiException e){
                String msg = e.getMessage();
                if (msg.startsWith("Error sending message: [429] Too Many Requests: retry after")){
                    String ex = msg
                        .replace(
                                "Error sending message: [429] Too Many Requests: retry after ",
                                ""
                        );
                Long k = Long.parseLong(ex);
                System.out.println("Sleeping" +
                        "");
                Thread.sleep(1000 * k);
                }

                if (msg == "Error sending message: [403] Forbidden: bot was blocked by the user"){
                    usersRepository.deleteById(s.getId());
                }
            }

            Thread.sleep(200);
        }
    }

    public void sendMsgToAllAdmins(SendPhoto sendPhoto) throws InterruptedException {
        List<Admin> admins = adminRepository.findAll();

        for (Admin s : admins){

            sendPhoto.setChatId(s.getAdminId());

            try {
                bot.execute(sendPhoto);
            }catch (TelegramApiException e){
                String msg = e.getMessage();
                if (msg.startsWith("Error sending message: [429] Too Many Requests: retry after")){
                    String ex = msg
                            .replace(
                                    "Error sending message: [429] Too Many Requests: retry after ",
                                    ""
                            );
                    Long k = Long.parseLong(ex);
                    System.out.println("Sleeping" +
                            "");
                    Thread.sleep(1000 * k);
                }

                if (msg == "Error sending message: [403] Forbidden: bot was blocked by the user"){
                    usersRepository.deleteById(s.getId());
                }
            }

            Thread.sleep(200);
        }
    }

}
