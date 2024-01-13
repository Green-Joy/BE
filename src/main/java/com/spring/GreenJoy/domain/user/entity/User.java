package com.spring.GreenJoy.domain.user.entity;

import com.spring.GreenJoy.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Table(name = "Users")
@NoArgsConstructor
@Entity
public class User extends BaseTime {

    //유저 인덱스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;

    // 아이디
    @Column(nullable = false, length = 45)
    private String id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String profileImg;

    @Column(columnDefinition = "int unsigned")
    @ColumnDefault("0")
    private int credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    protected Role role = Role.MEMBER;

    public enum Role {
        ADMIN, MEMBER
    }

    @Builder
    public User(String id, String uuid, String email, String password, String name, String nickname,
                String profileImg, Role role){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.role = role;
        // TODO: UUID?
        this.uuid = uuid;
    }

}
