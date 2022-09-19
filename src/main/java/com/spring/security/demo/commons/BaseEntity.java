package com.spring.security.demo.commons;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
//eliminate serialization exceptions
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
@SuperBuilder
public abstract class BaseEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "id_sequence_generator", sequenceName = "id_sequence_generator", allocationSize = 1)
    @GeneratedValue(generator = "id_sequence_generator")
    Long id;

    @Column(name="uuid", nullable= false, unique = true, updatable = false, length = 12)
    String uuid;
}
