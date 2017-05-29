package com.vmutter.radaptor;

import com.vmutter.radaptor.pojo.Person;
import com.vmutter.radaptor.pojo.Student;
import com.vmutter.radaptor.result.PersonAdapt;
import com.vmutter.radaptor.result.PersonBuilder;
import com.vmutter.radaptor.result.StudentAdapt;
import com.vmutter.radaptor.result.StudentBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RadaptorAdaptTest {

    private Person person;

    private Student student;

    @Before
    public void setup() {
        person = new Person();
        person.setName("name");
        person.setLastName("lastName");

        student = new Student();
        student.setName("name");
        student.setLastName("lastName");
        student.setGrade("grade");
        student.setAge("16");
    }

    @Test
    public void testPerson() throws IllegalAccessException, NoSuchFieldException, InstantiationException,
        InvocationTargetException, NoSuchMethodException {
        final PersonAdapt adaptedPerson = Radaptor.adapt(person, PersonAdapt.class);
        assertThat(adaptedPerson.getName(), is(person.getName()));
        assertThat(adaptedPerson.getSurName(), is(person.getLastName()));
    }

    @Test
    public void testPersonBuilder() throws IllegalAccessException, NoSuchFieldException, InstantiationException,
        InvocationTargetException, NoSuchMethodException {
        final PersonBuilder buildPerson = Radaptor.build(person, PersonBuilder.class);
        assertThat(buildPerson.getName(), is(person.getName()));
        assertThat(buildPerson.getSurName(), is(person.getLastName()));
    }

    @Test
    public void testStudent() throws IllegalAccessException, NoSuchFieldException, InstantiationException,
        InvocationTargetException, NoSuchMethodException {
        final StudentAdapt adaptedStudent = Radaptor.adapt(student, StudentAdapt.class);
        assertThat(adaptedStudent.getName(), is(student.getName()));
        assertThat(adaptedStudent.getSurName(), is(student.getLastName()));
        assertThat(adaptedStudent.getGrade(), is(student.getGrade()));
        assertThat(adaptedStudent.getAge(), is(Integer.parseInt(student.getAge())));
    }

    @Test
    public void testStudentBuilder() throws IllegalAccessException, NoSuchFieldException, InstantiationException,
        InvocationTargetException, NoSuchMethodException {
        final StudentBuilder buildStudent = Radaptor.build(student, StudentBuilder.class);
        assertThat(buildStudent.getName(), is(student.getName()));
        assertThat(buildStudent.getSurName(), is(student.getLastName()));
        assertThat(buildStudent.getGrade(), is(student.getGrade()));
        assertThat(buildStudent.getAge(), is(Integer.parseInt(student.getAge())));
    }

}
