package uz.qiroldev.namozvaqtlari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qiroldev.namozvaqtlari.model.PrayerDaysInfo;

public interface InfoTimesRepository extends JpaRepository<PrayerDaysInfo, Long> {

    PrayerDaysInfo findByDayAndMonth(String day, String month);
}
