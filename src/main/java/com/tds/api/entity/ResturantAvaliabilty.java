package com.tds.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resturantavailability")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler", "created_at", "updated_at"})
public class ResturantAvaliabilty {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "time")
    private String time;

    @OneToOne(targetEntity = Resturant.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "resturant_id")
    private Resturant resturant;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date created_at;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updated_at;
}
