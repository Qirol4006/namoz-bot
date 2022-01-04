package uz.qiroldev.namozvaqtlari.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import uz.qiroldev.namozvaqtlari.model.Admin;
import uz.qiroldev.namozvaqtlari.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Boolean checkAdmin(User user){

        if (!adminRepository.findByAdminId(user.getId().toString()).isPresent()){
            return false;
        }

        return true;
    }

    public void setMenuLevel(User user, Integer menuLevel, String description){
        Admin admin = adminRepository.findByAdminId(user.getId().toString()).orElse(null);

        admin.setMenuLevel(menuLevel);
        admin.setDescription(description);

        adminRepository.save(admin);
    }
}
