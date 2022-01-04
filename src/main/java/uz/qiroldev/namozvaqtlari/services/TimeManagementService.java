package uz.qiroldev.namozvaqtlari.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.qiroldev.namozvaqtlari.model.PrayerDays;
import uz.qiroldev.namozvaqtlari.model.PrayerDaysInfo;
import uz.qiroldev.namozvaqtlari.model.PrayerTimes;
import uz.qiroldev.namozvaqtlari.repository.DaysRepository;
import uz.qiroldev.namozvaqtlari.repository.InfoTimesRepository;
import uz.qiroldev.namozvaqtlari.repository.TimesRepository;

@Service
public class TimeManagementService {

    @Autowired
    DaysRepository daysRepository;

    @Autowired
    TimesRepository timesRepository;

    @Autowired
    InfoTimesRepository infoTimesRepository;

    public Long setDayAndMoth(String dayAndMoth){

        String timeDay;
        String timeMonth;

        Integer toTheTime = dayAndMoth.indexOf(" ");

        timeMonth = dayAndMoth.substring(0, toTheTime);
        timeDay = dayAndMoth.substring(toTheTime + 1);

        if (timeMonth.startsWith("0")){
            timeMonth = timeMonth.replace("0", "");
        }
        if (timeDay.startsWith("0")){
            timeDay = timeDay.replace("0", "");
        }
        Long id = 0L;
        if (daysRepository.findByDayAndMonth(timeDay, timeMonth).isPresent()){
            id = daysRepository.findByDayAndMonth(timeDay, timeMonth).orElse(null).getId();
        }
        PrayerDays prayerDays = new PrayerDays();

        if (id != 0L){
            prayerDays.setId(id);
        }

        prayerDays.setDay(timeDay);
        prayerDays.setMonth(timeMonth);

        return daysRepository.save(prayerDays).getId();
    }

    public void setSubhi(String subhi, String id){

        PrayerTimes prayerTimes = new PrayerTimes();

        if (timesRepository.findByPrayerDays(Long.parseLong(id)).isPresent()){
            prayerTimes.setId(
                    timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null).getId()
                    );
        }

        prayerTimes.setSubhi(subhi);
        prayerTimes.setPrayerDays(Long.parseLong(id));

        PrayerDaysInfo prayerDaysInfo = infoTimesRepository.findById(Long.parseLong(id)).orElse(null);

        prayerDaysInfo.setPrayerTimes(timesRepository.save(prayerTimes));

        infoTimesRepository.save(prayerDaysInfo);
    }

    public void setBomdod(String text, String id) {

        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setBomdod(text);

        timesRepository.save(prayerTimes);
    }

    public void setQuyosh(String quyosh, String id){
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setQuyosh(quyosh);

        timesRepository.save(prayerTimes);
    }

    public void setZavol(String zavol, String id) {
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setZavol(zavol);

        timesRepository.save(prayerTimes);
    }

    public void setPeshin(String peshin, String id) {
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setPeshin(peshin);

        timesRepository.save(prayerTimes);
    }

    public void setAsr(String asr, String id) {
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setAsr(asr);

        timesRepository.save(prayerTimes);
    }

    public void setShom(String shom, String id) {
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setShom(shom);

        timesRepository.save(prayerTimes);
    }

    public void setXufton(String xufton, String id) {
        PrayerTimes prayerTimes = timesRepository.findByPrayerDays(Long.parseLong(id)).orElse(null);

        prayerTimes.setXufton(xufton);

        timesRepository.save(prayerTimes);
    }

    public Boolean deleteTimeByDate(String date){
        String timeDay;
        String timeMonth;

        Integer toTheTime = date.indexOf(" ");

        timeDay = date.substring(0, toTheTime);
        timeMonth = date.substring(toTheTime + 1);

        if (!daysRepository.findByDayAndMonth(timeDay, timeMonth).isPresent()){
            return false;
        }

        Long id = daysRepository.findByDayAndMonth(timeDay, timeMonth).get().getId();

        infoTimesRepository.deleteById(id);

        timesRepository.deleteById(timesRepository.findByPrayerDays(id).get().getId());
        return true;
    }
}
