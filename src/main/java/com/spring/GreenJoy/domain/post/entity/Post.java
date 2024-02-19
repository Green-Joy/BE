package com.spring.GreenJoy.domain.post.entity;

import com.spring.GreenJoy.domain.comment.entity.Comment;
import com.spring.GreenJoy.domain.likes.entity.Like;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Posts")
@Entity
@DynamicInsert
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
    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

}
