package io.github.apedrina.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentVO {

    public static final String PHONE_INVALID = "[Phone number cannot be empty or null]";
    public static final String NAME_INVALID = "[Name cannot be empty or null]";
    public static final String LASTNAME_INVALID = "[Lastname cannot be empyt or null]";
    public static final String DOB_INVALID = "[Date of birth cannot be empyt or null]";
    public static final String ADDRESS_INVALID = "[Address cannot be empyt or null]";
    public static final String EMAIL_INVALID = "[Email cannot be empty or null]";


    private long id;

    @NotNull(message = NAME_INVALID)
    @NotEmpty(message = NAME_INVALID)
    private String name;

    @NotNull(message = LASTNAME_INVALID)
    @NotEmpty(message = LASTNAME_INVALID)
    private String lastName;

    @NotNull(message = DOB_INVALID)
    @NotEmpty(message = DOB_INVALID)
    private String dateOfBirth;

    @NotNull(message = ADDRESS_INVALID)
    @NotEmpty(message = ADDRESS_INVALID)
    private String address;

    @NotNull(message = EMAIL_INVALID)
    @NotEmpty(message = EMAIL_INVALID)
    private String email;

    @Range(min = 1, message = PHONE_INVALID)
    @NotNull(message = PHONE_INVALID)
    private long phoneNumber;

}