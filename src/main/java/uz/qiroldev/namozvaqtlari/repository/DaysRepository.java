package uz.qiroldev.namozvaqtlari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qiroldev.namozvaqtlari.model.PrayerDays;

import java.util.Optional;

public interface DaysRepository extends JpaRepository<PrayerDays, Long> {

    Optional<PrayerDays> findByDayAndMonth(String day, String month);
}
