package uz.qiroldev.namozvaqtlari.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pryer_times")
public class PrayerTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subhi")
    private String subhi;

    @Column(name = "bomdod")
    private String bomdod;

    @Column(name = "quyosh_chiqishi")
    private String quyosh;

    @Column(name = "zavol")
    private String zavol;

    @Column(name = "peshin")
    private String peshin;

    @Column(name = "asr")
    private String asr;

    @Column(name = "shom")
    private String shom;

    @Column(name = "xufton")
    private String xufton;

    @Column(name = "prayer_days")
    private Long prayerDays;

}
