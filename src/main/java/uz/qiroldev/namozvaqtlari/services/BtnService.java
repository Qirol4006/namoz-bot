package uz.qiroldev.namozvaqtlari.services;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class BtnService {

    public ReplyKeyboardMarkup fistLevel(){


        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add("Namoz vaqtlarini qo'shish");
        keyboardRowList.add(firstRow);

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add("Barchaga xabar yuborish");
        keyboardRowList.add(secondRow);

        KeyboardRow thirdRow = new KeyboardRow();
        thirdRow.add("Vaqtlarni jo'natish vaqti");
        keyboardRowList.add(thirdRow);

        KeyboardRow fourthRow = new KeyboardRow();
        fourthRow.add("Vaqtlar yuborish uchun xabar ko'rinishi");
        keyboardRowList.add(fourthRow);

        KeyboardRow fifthRow = new KeyboardRow();
        fifthRow.add("Vaqtlar yuborish ushun rasmlar tanlash");
        keyboardRowList.add(fifthRow);

        KeyboardRow sixthRow = new KeyboardRow();
        sixthRow.add("Bot salomlashishini o'zgartirish");
        keyboardRowList.add(sixthRow);

        KeyboardRow ninthRow = new KeyboardRow();
        ninthRow.add("Saqlangan vaqtlarni ko'rish");
        keyboardRowList.add(ninthRow);

        KeyboardRow tenthRow = new KeyboardRow();
        tenthRow.add("Namozvaqtini o'chirish");
        keyboardRowList.add(tenthRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setKeyboard(keyboardRowList);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);

        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup cancelBtn(){
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Bekor qilish");
        keyboardRowList.add(keyboardRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        keyboardMarkup.setKeyboard(keyboardRowList);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);

        return keyboardMarkup;
    }
}
