package io.github.apedrina.web.model;

import io.github.apedrina.web.model.error.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;

    private String lastName;

    private String dateOfBirth;

    private String address;

    private String email;

    private long phoneNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH})
    private List<Log> logs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> studentsCourses;

    public void setDateOfBirth(String date) {
        Instant instant = null;
        try {
            instant = stringToDate(date).toInstant();
        } catch (ParseException e) {
            throw new BusinessException(e);
        }
        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
        LocalDate givenDate = zone.toLocalDate();
        Period period = Period.between(givenDate, LocalDate.now());
        if (period.getYears() < 16) {
            throw new BusinessException(BusinessException.AGE_NOT_ALLOWED);

        }
        this.dateOfBirth = date;

    }

    private Date stringToDate(String dob) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dob);

        return date;

    }

}
