package com.spring.security.demo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.security.demo.commons.BaseEntity;
import com.spring.security.demo.role.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users", indexes = {
        @Index(name = "user_uuid_index", columnList = "uuid")})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Column(name = "first_name", nullable = false, length = 90)
    String firstName;
    @Column(name = "last_name", nullable = false, length = 90)
    String lastName;
    @Column(name = "userName", nullable = false, length = 90)
    String userName;
    @Column(name = "password", nullable = false, length = 200)
    @JsonIgnore
    String password;
    @Column(name = "is_member_enabled")
    boolean enabled;
    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles = new ArrayList<>();
}
