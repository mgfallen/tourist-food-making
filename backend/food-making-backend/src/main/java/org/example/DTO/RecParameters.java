package org.example.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecParameters {
    @JsonProperty("num_people")
    Integer numPeople;
    @JsonProperty("excluded_food")
    List<Integer> excludedFood;
    @JsonProperty("num_days")
    Integer numDays;
    @JsonProperty("budget")
    String budget;
    @JsonProperty("available_cookware")
    List<String> cookware;


}
