package com.spring.GreenJoy.domain.user.entity;

import com.spring.GreenJoy.global.common.BaseTime;
import com.spring.GreenJoy.global.common.NanoId;
import com.spring.GreenJoy.global.common.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Entity
@ToString
public class User extends BaseTime {

    @EmbeddedId
    @AttributeOverride(name ="id", column = @Column(name = "user_id"))
    private NanoId userId;

    @Column(nullable = false, length = 45)
    private String email;

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
    private Role role;

    private String provider;

    private String providerId;

    public User update(String name, String profileImg) {
        this.name = name;
        this.profileImg = profileImg;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
