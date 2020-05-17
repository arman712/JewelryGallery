package com.company.handmade.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String price;
    private String content;
    @Column(columnDefinition = "longtext")
    private String image;
}
