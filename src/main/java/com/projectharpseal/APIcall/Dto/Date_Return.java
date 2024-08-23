package com.projectharpseal.APIcall.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Date_Return{


    private String year;
    private String month;
    private String day;
    public Date_Return(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}