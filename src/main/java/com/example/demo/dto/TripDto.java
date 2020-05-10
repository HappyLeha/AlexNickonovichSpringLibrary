package com.example.demo.dto;

import com.example.demo.entity.User;
import com.example.demo.util.CalendarDeserializer;
import com.example.demo.util.CalendarSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private int id;
    @JsonSerialize(using= CalendarSerializer.class)
    @JsonDeserialize(using= CalendarDeserializer.class)
    private Calendar dateTimeFrom;
    @JsonSerialize(using= CalendarSerializer.class)
    @JsonDeserialize(using= CalendarDeserializer.class)
    private Calendar dateTimeTo;
    private String from;
    private String to;
    private int countOfPlaces;
    private int currentCountOfPlaces;
    private String transport;
    private double cost;
    private String driver;
}
