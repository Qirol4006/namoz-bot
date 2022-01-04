package uz.qiroldev.namozvaqtlari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qiroldev.namozvaqtlari.model.Configurations;

import java.util.Optional;


public interface ConfigsRepository extends JpaRepository<Configurations, Long> {

    Optional<Configurations> findByName(String name);
}
