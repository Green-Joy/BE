package com.spring.GreenJoy.global.common;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor(access = AccessLevel.MODULE)
@NoArgsConstructor
public class NanoId implements Serializable {

    @Setter(AccessLevel.PRIVATE)
    private String id;

    /**
     * NanoId: generate
     * @return
     */
    public static NanoId makeId() {
        return new NanoId(NanoIdUtils.randomNanoId());
    }

    /**
     * NanoId: from String
     * @param id
     * @return
     */
    public static NanoId of(String id) {
        return new NanoId(id);
    }

    /**
     * NanoId: to String
     * @return
     */
    @Override
    public String toString(){
        return id;
    }

}
