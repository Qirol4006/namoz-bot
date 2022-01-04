package uz.qiroldev.namozvaqtlari.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bot_configs")
public class Configurations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "config", columnDefinition="TEXT")
    private String config;
}
