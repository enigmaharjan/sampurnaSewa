package com.example.sampurnasewaagile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    private Validation validate;

    @Before
    public void run() {
        validate = new Validation();
    }

    @Test
    public void is_jobtype_empty(){
        String jobtype = "Plumber";
        assertEquals(true, validate.isEmpty(jobtype));
    }

    @Test
    public void is_jdate_empty(){
        String jdate= "";
        assertEquals(true, validate.isEmpty(jdate));
    }

    @Test
    public void is_jtime_empty(){
        String jtime="";
        assertEquals(true, validate.isEmpty(jtime));
    }

    @Test
    public void is_problem_empty(){
        String problem="I have a problem";
        assertEquals(true, validate.isEmpty(problem));
    }



}