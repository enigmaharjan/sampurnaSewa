package Fragments;

import com.example.sampurnasewaagile.Validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {
    private Validation validate;
    private String email;
    private String password;


    @Before
    public void run() {
        validate = new Validation();
    }

    @Test
    public void is_email_empty() {
        String email="";
        assertEquals(true, validate.isEmpty(email));
    }

    @Test
    public void is_password_empty() {
        String password="";
        assertEquals(true, validate.isEmpty(password));
    }


    @Test
    public void is_email_valid() {
        String email="suman@gmail.com";
        assertEquals(true, validate.isValidEmail(email));

    }
}

