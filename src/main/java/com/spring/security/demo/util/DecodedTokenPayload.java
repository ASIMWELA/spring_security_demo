package com.spring.security.demo.util;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DecodedTokenPayload {
    String userName;
    Collection<SimpleGrantedAuthority> authorities;
}