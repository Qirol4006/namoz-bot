package uz.qiroldev.namozvaqtlari.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "prayer_days")
public class PrayerDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moth")
    private String month;

    @Column(name = "day")
    private String day;

}
