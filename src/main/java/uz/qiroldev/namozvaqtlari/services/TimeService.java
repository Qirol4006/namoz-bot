package uz.qiroldev.namozvaqtlari.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.qiroldev.namozvaqtlari.model.Configurations;
import uz.qiroldev.namozvaqtlari.model.PrayerDaysInfo;
import uz.qiroldev.namozvaqtlari.model.PrayerTimes;
import uz.qiroldev.namozvaqtlari.repository.ConfigsRepository;
import uz.qiroldev.namozvaqtlari.repository.DaysRepository;
import uz.qiroldev.namozvaqtlari.repository.InfoTimesRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TimeService {

    @Autowired
    ConfigsRepository configsRepository;

    @Autowired
    DaysRepository daysRepository;

    @Autowired
    BotConfigsService configsService;

    @Autowired
    InfoTimesRepository timesRepository;

    @Autowired
    SendMessageService messageService;

    @Autowired
    TimeManagementService timeManagementService;

    @Autowired
    DateUtils utils;

    @Scheduled(cron = "* */5 * * * *")
    public void checkTimes() throws InterruptedException {

        String k = configsRepository.findByName("nextMsg").get().getConfig();

        switch (k){
            case "0":{
                firstMsg();
                break;
            }
            case "1":{
                peshinMsg();
                break;
            }
            case "2":{
                asrMsg();
                break;
            }
            case "3":{
                shomMsg();
                break;
            }
            case "4":{
                xuftonMsg();
                break;
            }
            default:{
                break;
            }
        }
    }

    public void firstMsg() throws InterruptedException {
        if (checkTime("firstMsgTime")){
            String fileId = configsRepository.findByName("firstPhoto").get().getConfig();
            String msgCaption = configsRepository.findByName("firstMsg").get().getConfig();

            LocalDateTime dateTime = LocalDateTime.now();

            PrayerDaysInfo daysInfo = timesRepository.findByDayAndMonth(
                    Integer.toString(dateTime.getDayOfMonth()),
                    Integer.toString(dateTime.getMonthValue())
            );

            PrayerTimes times = daysInfo.getPrayerTimes();

            HijrahDate islamyDate = HijrahChronology.INSTANCE.date(
                    LocalDate.of(
                            dateTime.getYear(),
                            dateTime.getMonthValue(),
                            dateTime.getDayOfMonth()));

            msgCaption = msgCaption.replace("{hkun}", DateTimeFormatter.ofPattern("d").format(islamyDate));
            msgCaption = msgCaption.replace("{hoy}", utils.getNameIslamicMonth(
                    Integer.parseInt(DateTimeFormatter.ofPattern("M").format(islamyDate))));
            msgCaption = msgCaption.replace("{hyil}", DateTimeFormatter.ofPattern("yyyy").format(islamyDate));
            msgCaption = msgCaption.replace("{kun}", Integer.toString(dateTime.getDayOfMonth()));
            msgCaption = msgCaption.replace("{oy}", utils.getNameMonth(dateTime.getMonthValue()));
            msgCaption = msgCaption.replace("{yil}", Integer.toString(dateTime.getYear()));
            msgCaption = msgCaption.replace("{subhi}", times.getSubhi().replace(" ", ":"));
            msgCaption = msgCaption.replace("{bomdod}", times.getBomdod().replace(" ", ":"));
            msgCaption = msgCaption.replace("{quyosh}", times.getQuyosh().replace(" ", ":"));
            msgCaption = msgCaption.replace("{zavol}", times.getZavol().replace(" ", ":"));
            msgCaption = msgCaption.replace("{peshin}", times.getPeshin().replace(" ", ":"));
            msgCaption = msgCaption.replace("{asr}", times.getAsr().replace(" ", ":"));
            msgCaption = msgCaption.replace("{shom}", times.getShom().replace(" ", ":"));
            msgCaption = msgCaption.replace("{xufton}", times.getXufton().replace(" ", ":"));
            SendPhoto sendPhoto = new SendPhoto();

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> listList = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtons1 = new ArrayList<>();

            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText("Телеграм канал");
            inlineKeyboardButton.setUrl("https://t.me/Umarulforuq_uz");

            InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
            inlineKeyboardButton1.setText("Намоз вақтлари бот");
            inlineKeyboardButton1.setUrl("https://t.me/umarulforuqbot");

            inlineKeyboardButtons.add(inlineKeyboardButton);
            inlineKeyboardButtons1.add(inlineKeyboardButton1);

            listList.add(inlineKeyboardButtons);
            listList.add(inlineKeyboardButtons1);

            inlineKeyboardMarkup.setKeyboard(listList);

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setCaption(msgCaption);
            sendPhoto.setReplyMarkup(inlineKeyboardMarkup);


            messageService.sendMsgToAllMembers(sendPhoto);

            messageService.sendMsgToAllAdmins(sendPhoto);

            configsService.setNext("1");
        }
    }

    public void peshinMsg() throws InterruptedException {
        if (checkTime("peshinMsgTime")){
            String fileId = configsRepository.findByName("peshinPhoto").get().getConfig();
            String msgCaption = configsRepository.findByName("peshinMsg").get().getConfig();

            LocalDateTime dateTime = LocalDateTime.now();

            PrayerDaysInfo daysInfo = timesRepository.findByDayAndMonth(
                    Integer.toString(dateTime.getDayOfMonth()),
                    Integer.toString(dateTime.getMonthValue())
            );

            PrayerTimes times = daysInfo.getPrayerTimes();

            msgCaption = msgCaption.replace("{vaqt}", times.getPeshin().replace(" ", ":"));

            SendPhoto sendPhoto = new SendPhoto();

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setCaption(msgCaption);

            messageService.sendMsgToAllMembers(sendPhoto);

            messageService.sendMsgToAllAdmins(sendPhoto);

            configsService.setNext("2");
        }
    }

    public void asrMsg() throws InterruptedException {
        if (checkTime("asrMsgTime")){
            String fileId = configsRepository.findByName("asrPhoto").get().getConfig();
            String msgCaption = configsRepository.findByName("asrMsg").get().getConfig();

            LocalDateTime dateTime = LocalDateTime.now();

            PrayerDaysInfo daysInfo = timesRepository.findByDayAndMonth(
                    Integer.toString(dateTime.getDayOfMonth()),
                    Integer.toString(dateTime.getMonthValue())
            );

            PrayerTimes times = daysInfo.getPrayerTimes();

            msgCaption = msgCaption.replace("{vaqt}", times.getAsr().replace(" ", ":"));

            SendPhoto sendPhoto = new SendPhoto();

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setCaption(msgCaption);

            messageService.sendMsgToAllMembers(sendPhoto);

            messageService.sendMsgToAllAdmins(sendPhoto);

            configsService.setNext("3");
        }
    }

    public void shomMsg() throws InterruptedException {
        if (checkTime("shomMsgTime")){
            String fileId = configsRepository.findByName("shomPhoto").get().getConfig();
            String msgCaption = configsRepository.findByName("shomMsg").get().getConfig();

            LocalDateTime dateTime = LocalDateTime.now();

            PrayerDaysInfo daysInfo = timesRepository.findByDayAndMonth(
                    Integer.toString(dateTime.getDayOfMonth()),
                    Integer.toString(dateTime.getMonthValue())
            );

            PrayerTimes times = daysInfo.getPrayerTimes();

            msgCaption = msgCaption.replace("{vaqt}", times.getShom().replace(" ", ":"));

            SendPhoto sendPhoto = new SendPhoto();

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setCaption(msgCaption);

            messageService.sendMsgToAllMembers(sendPhoto);

            messageService.sendMsgToAllAdmins(sendPhoto);

            configsService.setNext("4");
        }
    }

    public void xuftonMsg() throws InterruptedException {
        if (checkTime("xuftonMsgTime")){
            String fileId = configsRepository.findByName("xuftonPhoto").get().getConfig();
            String msgCaption = configsRepository.findByName("xuftonMsg").get().getConfig();

            LocalDateTime dateTime = LocalDateTime.now();

            PrayerDaysInfo daysInfo = timesRepository.findByDayAndMonth(
                    Integer.toString(dateTime.getDayOfMonth()),
                    Integer.toString(dateTime.getMonthValue())
            );

            PrayerTimes times = daysInfo.getPrayerTimes();

            msgCaption = msgCaption.replace("{vaqt}", times.getXufton().replace(" ", ":"));

            SendPhoto sendPhoto = new SendPhoto();

            sendPhoto.setPhoto(new InputFile(fileId));
            sendPhoto.setCaption(msgCaption);

            messageService.sendMsgToAllMembers(sendPhoto);

            messageService.sendMsgToAllAdmins(sendPhoto);

            timeManagementService.deleteTimeByDate(daysInfo.getDay() + " " + daysInfo.getMonth());

            configsService.setNext("0");
        }
    }

    boolean checkTime(String conf){
        Configurations fistMsgTime = configsRepository.findByName(conf).get();
        Integer h;
        Integer m;

        Integer toTheTime = fistMsgTime.getConfig().indexOf(" ");

        h = Integer.parseInt(fistMsgTime.getConfig().substring(0, toTheTime));
        m = Integer.parseInt(fistMsgTime.getConfig().substring(toTheTime + 1));

        LocalDateTime dateTime = LocalDateTime.now();

        return dateTime.getHour() >= h && dateTime.getMinute() >= m;
    }
}
