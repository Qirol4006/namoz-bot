package uz.qiroldev.namozvaqtlari.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.model.Users;
import uz.qiroldev.namozvaqtlari.repository.ConfigsRepository;
import uz.qiroldev.namozvaqtlari.repository.UsersRepository;

@Service
@Configurable
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    SendMessageService messageService;

    @Autowired
    ConfigsRepository configsRepository;

    public void startMessage(User user){

        String userId = user.getId().toString();

        Users users = new Users();

        if (!usersRepository.findByTelegramId(userId).isPresent()){
            users.setTelegramId(userId);
            usersRepository.save(users);
        }


        try {
            messageService.sayHelloToUser(user);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendFirstMsgToAllUsers(){

    }

}
