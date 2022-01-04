package uz.qiroldev.namozvaqtlari.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qiroldev.namozvaqtlari.bot.Bot;
import uz.qiroldev.namozvaqtlari.model.Admin;
import uz.qiroldev.namozvaqtlari.model.PrayerDays;
import uz.qiroldev.namozvaqtlari.model.PrayerDaysInfo;
import uz.qiroldev.namozvaqtlari.model.PrayerTimes;
import uz.qiroldev.namozvaqtlari.repository.AdminRepository;
import uz.qiroldev.namozvaqtlari.repository.DaysRepository;
import uz.qiroldev.namozvaqtlari.repository.InfoTimesRepository;
import uz.qiroldev.namozvaqtlari.services.*;

import java.util.List;

@Service
public class AdminMessages {

    @Autowired
    BotConfigsService configsService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SendMessageService messageService;

    @Autowired
    BtnService btnService;

    @Autowired
    AdminService adminService;

    @Autowired
    DaysRepository daysRepository;

    @Autowired
    TimeManagementService timeManagementService;

    @Autowired
    InfoTimesRepository infoTimesRepository;

    @Autowired
    Bot bot;


    public void msgForSetHelloMsg(Message message) throws TelegramApiException {
        User user = message.getFrom();

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(user.getId().toString());
        sendMessage.setText("Yangi salomlashuv xabarini yuboring\n" +
                        "Eslatma salomlashuvda foydalanuvchi ismini qoshish uchun '{ism}' buyrug'idan foydalaning"
                );
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);
    }

    public void setHelloMsg(Message message) throws TelegramApiException {

        User user = message.getFrom();

        if (!adminRepository.findByAdminId(user.getId().toString()).isPresent()){
            messageService.notAdminMsg(user.getId().toString());
            return;
        }

        String text = configsService.setHello(message.getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(user.getId().toString());
        sendMessage.setText("Salomlashish xabari\n" + text + "\nGa o'zgartirildi");
        sendMessage.setReplyMarkup(btnService.fistLevel());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        admin.setMenuLevel(0);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void cancelProcess(User user) throws TelegramApiException {
        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(user.getId().toString());
        sendMessage.setText("Kerakli bo'limni tanlang \uD83D\uDC47");
        sendMessage.setReplyMarkup(btnService.fistLevel());

        bot.execute(sendMessage);

        admin.setMenuLevel(0);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void sendMsg(String text, User user) throws TelegramApiException {

        if (!adminRepository.findByAdminId(user.getId().toString()).isPresent()){
            messageService.notAdminMsg(user.getId().toString());
            return;
        }

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);

    }

    public void addPrayersTimeCommand(User user) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Oy va sanani kiriting!\nFormati oy sana masalan <b>12 23</b> yoki <b>1 12</b>");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(user.getId().toString());

        adminService.setMenuLevel(user, 100, "");
        bot.execute(sendMessage);
    }

    public void setMothAndDay(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        Long id = timeManagementService.setDayAndMoth(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Subxini kiriting");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);

        admin.setMenuLevel(102);
        admin.setDescription(id.toString());

        adminRepository.save(admin);
    }

    public void setSubhiByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setSubhi(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Bomdod vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(103);

        adminRepository.save(admin);
    }

    public void setBomdodByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setBomdod(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Quyosh chiqish vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(104);

        adminRepository.save(admin);
    }

    public void setQuyoshByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setQuyosh(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Zavol vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(105);

        adminRepository.save(admin);
    }

    public void setZavolByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setZavol(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Peshin vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(106);

        adminRepository.save(admin);
    }

    public void setPeshinByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setPeshin(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Asr vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(107);

        adminRepository.save(admin);
    }

    public void setAsrByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setAsr(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Shom vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(108);

        adminRepository.save(admin);
    }

    public void setShomByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setShom(update.getMessage().getText(), admin.getDescription());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Xufton vaqtini kiriting:");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(109);

        adminRepository.save(admin);
    }

    public void setXuftonByDayAndMonth(Update update) throws TelegramApiException {
        User user = update.getMessage().getFrom();

        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        timeManagementService.setXufton(update.getMessage().getText(), admin.getDescription());

        PrayerDays days = daysRepository.findById(Long.parseLong(admin.getDescription())).orElse(null);

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(days.getDay() + "/" + days.getMonth() + " uchun namoz vaqtlari saqlandi.\n" +
                "Keyingi kun uchun oy va sanani kiriting!");
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        admin.setMenuLevel(100);

        adminRepository.save(admin);
    }

    public void getTimes(User user) throws TelegramApiException {
        String message = "";

        List<PrayerDaysInfo> daysInfosList = infoTimesRepository.findAll();

        for (PrayerDaysInfo k : daysInfosList){

            message += k.getDay() + "/" + k.getMonth() + "\n";

            PrayerTimes times = k.getPrayerTimes();

            message += "Subhi " + times.getSubhi().replace(" ", ":") + "\n";
            message += "Bomdod " + times.getBomdod().replace(" ", ":") + "\n";
            message += "Quyosh chiqishi " + times.getQuyosh().replace(" ", ":") + "\n";
            message += "Zavol " + times.getZavol().replace(" ", ":") + "\n";
            message += "Peshin " + times.getPeshin().replace(" ", ":") + "\n";
            message += "Shom " + times.getShom().replace(" ", ":") + "\n";
            message += "Xufton " + times.getXufton().replace(" ", ":") + "\n";
            message += "➖➖➖➖➖➖➖➖➖➖\n";
        }

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(btnService.fistLevel());
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);
    }

    public void deleteTimesMsg(User user) throws TelegramApiException {
        String message = "";

        List<PrayerDaysInfo> daysInfosList = infoTimesRepository.findAll();

        for (PrayerDaysInfo k : daysInfosList){

            message += "<b>" +k.getDay() + " " + k.getMonth() + "</b>\n";

            PrayerTimes times = k.getPrayerTimes();

            message += "Subhi " + times.getSubhi().replace(" ", ":") + "\n";
            message += "Bomdod " + times.getBomdod().replace(" ", ":") + "\n";
            message += "Quyosh chiqishi " + times.getQuyosh().replace(" ", ":") + "\n";
            message += "Zavol " + times.getZavol().replace(" ", ":") + "\n";
            message += "Peshin " + times.getPeshin().replace(" ", ":") + "\n";
            message += "Shom " + times.getShom().replace(" ", ":") + "\n";
            message += "Xufton " + times.getXufton().replace(" ", ":") + "\n";
            message += "➖➖➖➖➖➖➖➖➖➖\n";
        }

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(user, 700, "Delete");
    }

    public void deleteTimes(Update update) throws TelegramApiException {

        Message message = update.getMessage();

        String text;
        if (!timeManagementService.deleteTimeByDate(message.getText())){
            text = "Sana noto'g'ri kiritildi";
        }else {
            text = "Muaffaqiyatli o'chirib yuborildi";
        }

        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getFrom().getId().toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(btnService.fistLevel());

        bot.execute(sendMessage);

        adminService.setMenuLevel(message.getFrom(), 0, "");
    }

    public void setSendTimes(User user) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Ertalabki birinchi xabar vaqtini kiriting:");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(user.getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(user, 301, "");
    }

    public void setFirstMessageTime(Update update) throws TelegramApiException {

        configsService.setFistMsgTime(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText("Peshin vaqti xabarini yuborish vaqtini kiriting:");
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(update.getMessage().getFrom().getId().toString()).orElse(null);

        admin.setMenuLevel(302);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void setPeshinMsgTime(Update update) throws TelegramApiException {
        configsService.setPeshinMsgTime(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText("Asr vaqti xabarini yuborish vaqtini kiriting:");
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(update.getMessage().getFrom().getId().toString()).orElse(null);

        admin.setMenuLevel(303);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void setAsrMsgTime(Update update) throws TelegramApiException {
        configsService.setAsrMsgTime(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText("Shom vaqti xabarini yuborish vaqtini kiriting:");
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(update.getMessage().getFrom().getId().toString()).orElse(null);

        admin.setMenuLevel(304);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void setShomMsgTime(Update update) throws TelegramApiException {
        configsService.setShomMsgTime(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText("Xufton vaqti xabarini yuborish vaqtini kiriting:");
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(update.getMessage().getFrom().getId().toString()).orElse(null);

        admin.setMenuLevel(305);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void setXuftonMsgTime(Update update) throws TelegramApiException {
        configsService.setXuftonMsgTime(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setText("Barch o'zgarishlar saqlandi!");
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());
        sendMessage.setReplyMarkup(btnService.fistLevel());

        bot.execute(sendMessage);

        Admin admin = adminRepository.findByAdminId(update.getMessage().getFrom().getId().toString()).orElse(null);

        admin.setMenuLevel(0);
        admin.setDescription("");

        adminRepository.save(admin);
    }

    public void setFirstMsgMsg(User user) throws TelegramApiException {

        String text = "Xabar yuborish tekstalrini o'zgartirish\n" +
                "Ertalabki xabar ko'rinishini kiriting masalan:\n\n" +
                "Assalomu alaykum bugungi kuningiz barokotli o'tsin!\n" +
                "Bugungi {kun}.{oy}.2021 uchun namoz vatlari\n" +
                "Subhi {subhi}\n" +
                "Bomdod {bomdod}\n" +
                "Quyosh chiqishi {quyosh}\n" +
                "Zavol {zavol}\n" +
                "Peshin {peshin}\n" +
                "Asr {asr}\n" +
                "Shom {shom}\n" +
                "Xufton {xufton}\n" +
                "  \"Namoz mo'minning\n" +
                "    merojidir\"\n" +
                "@Umarulforuq_uz\n" +
                "Yaqinlaringizga ham ulashing!";

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        adminService.setMenuLevel(user, 401, "");
    }

    public void setFirstMsg(Update update) throws TelegramApiException {
        configsService.setFirstMsg(update.getMessage().getText().toString());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Peshin vaqti xabari uchun ko'rinishni kiriting! Masalan:\n Peshin vaqti {vaqt} da.");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 402, "");
    }

    public void setPeshinMsg(Update update) throws TelegramApiException {
        configsService.setPeshinMsg(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Asr vaqti xabari uchun ko'rinishni kiriting! Masalan:\n Asr vaqti {vaqt} da.");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 403, "");
    }

    public void setAsrMsg(Update update) throws TelegramApiException {
        configsService.setAsrMsg(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Shom vaqti xabari uchun ko'rinishni kiriting! Masalan:\n Shom vaqti {vaqt} da.");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 404, "");
    }

    public void setShomMsg(Update update) throws TelegramApiException {
        configsService.setShomMsg(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Xufton vaqti xabari uchun ko'rinishni kiriting! Masalan:\n Xufton vaqti {vaqt} da.");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 405, "");
    }

    public void setXuftonMsg(Update update) throws TelegramApiException {
        configsService.setXuftonMsg(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Barcha o'zgarishlar saqlandi!");
        sendMessage.setReplyMarkup(btnService.fistLevel());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 0, "");
    }

    public void setPhotoForMsg(User user) throws TelegramApiException {
        String text = "Ertalabki xabar uchun rasm tanlang!";

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        adminService.setMenuLevel(user, 501, "");

    }

    public void setFirstPhoto(Update update) throws TelegramApiException {

        configsService.setFirstPhotoForMsg(update.getMessage().getPhoto().get(0).getFileId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Peshin vaqti xabari uchun rasm tanlang!");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 502, "");
    }

    public void setPeshinPhoto(Update update) throws TelegramApiException {
        configsService.setPeshinPhotoForMsg(update.getMessage().getPhoto().get(0)
                .getFileId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Asr vaqti xabari uchun rasm tanlang!");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 503, "");
    }

    public void setAsrPhoto(Update update) throws TelegramApiException {
        configsService.setAsrPhotoForMsg(update.getMessage().getPhoto().get(0)
                .getFileId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Shom vaqti xabari uchun rasm tanlang!");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 504, "");
    }

    public void setShomPhoto(Update update) throws TelegramApiException {
        configsService.setShomPhotoForMsg(update.getMessage().getPhoto().get(0)
                .getFileId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Xufton vaqti xabari uchun rasm tanlang!");
        sendMessage.setReplyMarkup(btnService.cancelBtn());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 505, "");
    }

    public void setXuftonPhoto(Update update) throws TelegramApiException {
        configsService.setXuftonPhotoForMsg(update.getMessage().getPhoto().get(0)
                .getFileId());

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText("Barcha o'zgarishlar saqlandi.\nKerakli bo'limni tanlang!");
        sendMessage.setReplyMarkup(btnService.fistLevel());
        sendMessage.setChatId(update.getMessage().getFrom().getId().toString());

        bot.execute(sendMessage);

        adminService.setMenuLevel(update.getMessage().getFrom(), 0, "");
    }

    public void sendMsgToAllMembers(User user) throws TelegramApiException {
        String text = "Xabar yozing!";

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableHtml(true);
        sendMessage.setText(text);
        sendMessage.setChatId(user.getId().toString());
        sendMessage.setReplyMarkup(btnService.cancelBtn());

        bot.execute(sendMessage);

        adminService.setMenuLevel(user, 200, "");
    }

    public void sendMsgToAllMembersMsg(Update update) {

    }
}