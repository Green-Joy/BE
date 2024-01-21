package com.spring.GreenJoy.domain.post.entity;

import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Posts")
@Entity
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(length = 50, nullable = false)
    private String title;

    private String content;

    private String image1;

    private String image2;

    private String image3;

    @ColumnDefault("0")
    private Integer likeCount;

    @ManyToOne
    private User user;

}
