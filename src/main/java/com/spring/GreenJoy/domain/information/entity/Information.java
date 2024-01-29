package com.spring.GreenJoy.domain.information.entity;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Informations")
@Entity
public class Information extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column(length = 50, nullable = false)
    private String title;

    private String content;

    private String image1;

    private String image2;

    private String image3;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
