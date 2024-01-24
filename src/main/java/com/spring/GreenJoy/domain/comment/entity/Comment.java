package com.spring.GreenJoy.domain.comment.entity;

import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Comments")
@Entity
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
