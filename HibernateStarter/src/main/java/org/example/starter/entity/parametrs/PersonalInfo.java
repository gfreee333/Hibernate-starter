package org.example.starter.entity.parametrs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.starter.entity.Birthday;
import org.example.starter.entity.Convert.ConvertorBirthday;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;
    @Convert(converter = ConvertorBirthday.class)
    @Column(name = "birthday")
    private Birthday birthDate;
}
