package com.softwareplant.sw.models.swApi;

import lombok.Data;

import java.util.List;

@Data
public class Planets{
    private int count;
    private String next;
    private String previous;
    private List<Planet> results;
}
