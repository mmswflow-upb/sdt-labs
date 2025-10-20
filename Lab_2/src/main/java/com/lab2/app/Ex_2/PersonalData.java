package com.lab2.app.Ex_2;

import java.time.LocalDate;



public class PersonalData implements PersonalDataI {
    
    private String name;
    private String email;
    private String telephone;
    private LocalDate bday;

    public PersonalData(String name, String email, LocalDate bday, String telephone){
        this.name = name;
        this.email = email;
        this.bday = bday;
        this.telephone = telephone;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public LocalDate getBDay(){
        return this.bday;
    }
    @Override
    public String getEmail(){
        return this.email;
    }
    @Override
    public String getTelephone(){
        return this.telephone;
    }
}
