package uz.qiroldev.namozvaqtlari.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bot_admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private String adminId;

    @Column(name = "menu_level")
    private Integer menuLevel;

    @Column(name = "description")
    private String description;
}
