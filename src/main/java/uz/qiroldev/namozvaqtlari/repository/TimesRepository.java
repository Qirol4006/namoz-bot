package uz.qiroldev.namozvaqtlari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qiroldev.namozvaqtlari.model.PrayerTimes;

import java.util.Optional;

public interface TimesRepository extends JpaRepository<PrayerTimes, Long> {

    Optional<PrayerTimes> findByPrayerDays(Long id);
}
