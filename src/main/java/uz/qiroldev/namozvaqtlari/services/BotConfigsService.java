package uz.qiroldev.namozvaqtlari.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import uz.qiroldev.namozvaqtlari.model.Configurations;
import uz.qiroldev.namozvaqtlari.repository.ConfigsRepository;

@Service("botConfig")
@Configurable
public class BotConfigsService {

    @Autowired
    ConfigsRepository configsRepository;

    public String setHello(String text){

        Configurations configs = new Configurations();

        if (!configsRepository.findByName("helloMsg").isPresent()){
            configs.setName("helloMsg");
            configs.setConfig(text);
            configsRepository.save(configs);

            return text;
        }

        configs = configsRepository.findByName("helloMsg").orElse(null);

        configs.setConfig(text);
        configsRepository.save(configs);

        return text;
    }

    public Boolean setFistMsgTime(String text){

        Configurations conf = new Configurations();

        if (configsRepository.findByName("firstMsgTime").isPresent()){

            conf.setId(
                    configsRepository.findByName("firstMsgTime").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("firstMsgTime");

        configsRepository.save(conf);

        return true;
    }

    public Boolean setPeshinMsgTime(String text){
        Configurations conf = new Configurations();

        if (configsRepository.findByName("peshinMsgTime").isPresent()){

            conf.setId(
                    configsRepository.findByName("peshinMsgTime").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("peshinMsgTime");

        configsRepository.save(conf);

        return true;
    }

    public void setAsrMsgTime(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("asrMsgTime").isPresent()){

            conf.setId(
                    configsRepository.findByName("asrMsgTime").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("asrMsgTime");

        configsRepository.save(conf);
    }

    public void setShomMsgTime(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("shomMsgTime").isPresent()){

            conf.setId(
                    configsRepository.findByName("shomMsgTime").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("shomMsgTime");

        configsRepository.save(conf);
    }

    public void setXuftonMsgTime(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("xuftonMsgTime").isPresent()){

            conf.setId(
                    configsRepository.findByName("xuftonMsgTime").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("xuftonMsgTime");

        configsRepository.save(conf);
    }

    public void setFirstMsg(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("firstMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("firstMsg").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("firstMsg");

        configsRepository.save(conf);
    }

    public void setPeshinMsg(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("peshinMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("peshinMsg").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("peshinMsg");

        configsRepository.save(conf);
    }

    public void setAsrMsg(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("asrMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("asrMsg").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("asrMsg");

        configsRepository.save(conf);
    }

    public void setShomMsg(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("shomMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("shomMsg").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("shomMsg");

        configsRepository.save(conf);
    }

    public void setXuftonMsg(String text) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("xuftonMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("xuftonMsg").get().getId()
            );
        }
        conf.setConfig(text);
        conf.setName("xuftonMsg");

        configsRepository.save(conf);
    }

    public void setFirstPhotoForMsg(String fileId) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("firstPhoto").isPresent()){

            conf.setId(
                    configsRepository.findByName("firstPhoto").get().getId()
            );
        }
        conf.setConfig(fileId);
        conf.setName("firstPhoto");

        configsRepository.save(conf);
    }

    public void setPeshinPhotoForMsg(String fileId) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("peshinPhoto").isPresent()){

            conf.setId(
                    configsRepository.findByName("peshinPhoto").get().getId()
            );
        }
        conf.setConfig(fileId);
        conf.setName("peshinPhoto");

        configsRepository.save(conf);
    }

    public void setAsrPhotoForMsg(String fileId) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("asrPhoto").isPresent()){

            conf.setId(
                    configsRepository.findByName("asrPhoto").get().getId()
            );
        }
        conf.setConfig(fileId);
        conf.setName("asrPhoto");

        configsRepository.save(conf);
    }

    public void setShomPhotoForMsg(String fileId) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("shomPhoto").isPresent()){

            conf.setId(
                    configsRepository.findByName("shomPhoto").get().getId()
            );
        }
        conf.setConfig(fileId);
        conf.setName("shomPhoto");

        configsRepository.save(conf);
    }

    public void setXuftonPhotoForMsg(String fileId) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("xuftonPhoto").isPresent()){

            conf.setId(
                    configsRepository.findByName("xuftonPhoto").get().getId()
            );
        }
        conf.setConfig(fileId);
        conf.setName("xuftonPhoto");

        configsRepository.save(conf);
    }

    public void setNext(String s) {
        Configurations conf = new Configurations();

        if (configsRepository.findByName("nextMsg").isPresent()){

            conf.setId(
                    configsRepository.findByName("nextMsg").get().getId()
            );
        }
        conf.setConfig(s);
        conf.setName("nextMsg");

        configsRepository.save(conf);
    }
}
