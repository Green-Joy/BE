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
@Setter
public class User extends BaseTime {

    @EmbeddedId
    @AttributeOverride(name ="id", column = @Column(name = "user_id"))
    private NanoId userId;

    @AttributeOverride(name ="id", column = @Column(name = "random_id"))
    private NanoId randomId;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String profileImg;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private Role role;

    public User update(String name, String profileImg) {
        this.name = name;
        this.profileImg = profileImg;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
