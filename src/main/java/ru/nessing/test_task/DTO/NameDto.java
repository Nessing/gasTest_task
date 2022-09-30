package ru.nessing.test_task.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NameDto {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}
