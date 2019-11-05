package com.example.sampurnasewaagile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UpdateProfileTest {
    private Validation validate;

    @Before
    public void run() {
        validate = new Validation();
    }

    @Test
    public void is_uptname_empty() {
        String uptname="";
        assertEquals(true, validate.isEmpty(uptname));
    }
    @Test
    public void is_uptuname_empty() {
        String uptuname="suman";
        assertEquals(true, validate.isEmpty(uptuname));
    }

    @Test
    public void is_uptphone_empty() {
        String number="";
        assertEquals(true, validate.isEmpty(number));
    }
    @Test
    public void is_username_empty() {
        String username="";
        assertEquals(true, validate.isEmpty(username));
    }

    @Test
    public void is_uptaddress_empty() {
        String address="shantinagar";
        assertEquals(true, validate.isEmpty(address));
    }


    @Test
    public void is_email_empty() {
        String email="";
        assertEquals(true, validate.isEmpty(email));
    }
    @Test
    public void is_uptpassword_empty() {
        String uptpassword="";
        assertEquals(true, validate.isEmpty(uptpassword));
    }

}