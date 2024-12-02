package com.itwasjoke.place.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "couples")
public class Couple {

    @Id
    private String hash;

    @OneToOne
    @JoinColumn(name = "partner1_id", referencedColumnName = "id")
    private User partner1;

    @OneToOne
    @JoinColumn(name = "partner2_id", referencedColumnName = "id")
    private User partner2;

    private Date date;
}
