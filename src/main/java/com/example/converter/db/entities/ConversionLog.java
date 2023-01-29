package com.example.converter.db.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "logs")
public class ConversionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String type;
    private String innerValue;
    private String outerValue;
    private LocalDateTime conversionDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
