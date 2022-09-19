package com.spring.security.demo.role;


import com.spring.security.demo.commons.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Table(name = "roles")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name="role")
    ERole roleName;

}