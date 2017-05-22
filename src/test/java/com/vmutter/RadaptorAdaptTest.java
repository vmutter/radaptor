package com.vmutter;

import com.vmutter.pojo.Person;
import com.vmutter.result.PersonAdapt;
import com.vmutter.result.PersonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RadaptorAdaptTest {

    private Person person;

    @Before
    public void setup() {
        person = new com.vmutter.pojo.Person();
        person.setName("name");
        person.setLastName("lastName");
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

}