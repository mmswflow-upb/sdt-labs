package com.lab2.app.Ex_2;

import java.util.Map;
import java.util.HashMap;
import com.google.gson.Gson;


public class PersonalDataAdapter implements PersonalInformation {
    private PersonalDataI wrapee;

    public PersonalDataAdapter(PersonalData wrapee) {
        this.wrapee = wrapee;
    }

    @Override
    public String getPersonalInformation(){

        Gson gson = new Gson();

        Map<String, Object> data = new HashMap<>();
        data.put("name", wrapee.getName());
        data.put("email", wrapee.getEmail());
        data.put("telephone", wrapee.getTelephone());
        data.put("bday", wrapee.getBDay().toString());

        return gson.toJson(data);
    }

    
}
