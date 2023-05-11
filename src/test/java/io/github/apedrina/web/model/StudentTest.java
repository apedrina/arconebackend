package io.github.apedrina.web.model;

import io.github.apedrina.web.mock.utils.FixtureUtils;
import io.github.apedrina.web.model.error.StudentBusinessException;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentTest {

    @Test
    public void should_allow_student_with_more_than_16_years_old() {
        var student = FixtureUtils.createStudentRequest();

        var date = "1980-08-23";

        student.setDateOfBirth(date);

        Assert.assertEquals(date, student.getDateOfBirth());

    }

    @Test(expected = StudentBusinessException.class)
    public void should_avoid_student_with_less_than_16_years_old() {
        var date = dateWithLessThan16();
        var student = FixtureUtils.createStudent();

        student.setDateOfBirth(date);

    }

    private String dateWithLessThan16() {
        LocalDate now = LocalDate.now();
        LocalDate dob = now.minusYears(13);

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dob.format(formatters);

    }


}
