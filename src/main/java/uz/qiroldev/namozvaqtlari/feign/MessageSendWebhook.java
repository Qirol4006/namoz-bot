package uz.qiroldev.namozvaqtlari.feign;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FeignClient(name = "brbalo", url = "http://localhost:8081/")
public interface MessageSendWebhook {

    @PostMapping("/bot550407250:AAFMxwSHxfGTRxKhceTpOmNeJp6T5r8_IuU/sendMessage")
    Response sendMessage(@RequestBody SendMessage sendMessage);
}
