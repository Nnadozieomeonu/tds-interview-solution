package com.tds.api.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resturantavailability")
@Data
public class ResturantAvaliabilty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = Resturant.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "resturant_id")
    private Resturant resturant;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updated_at;
}
