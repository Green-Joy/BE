package com.spring.GreenJoy.domain.challenge.entity;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Table(name = "Challenge")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@ToString
public class Challenge extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;

    private String content;

    private String image;

    private LocalDate challengeDate;

    @ColumnDefault("false")
    private boolean active;

}
