package uz.qiroldev.namozvaqtlari.model;



import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_id")
    private String telegramId;
}
