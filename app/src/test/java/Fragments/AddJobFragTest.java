package Fragments;

import com.example.sampurnasewaagile.Validation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AddJobFragTest {
    private Validation validate;

    @Before
    public void run() {
        validate = new Validation();
    }

    @Test
    public void is_jname_empty(){
        String jname = "Plumber";
        assertEquals(true, validate.isEmpty(jname));
    }

    @Test
    public void is_jdetail_empty(){
        String jdetail= "I want a plumber in my house on sunday";
        assertEquals(true, validate.isEmpty(jdetail));
    }

    @Test
    public void is_mincharge_empty(){
        String mincharge="";
        assertEquals(true, validate.isEmpty(mincharge));
    }

    @Test
    public void is_addjob_empty(){
        String addjob="";
        assertEquals(true, validate.isEmpty(addjob));
    }



}