package uz.qiroldev.namozvaqtlari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.qiroldev.namozvaqtlari.model.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminId(String adminId);
}
