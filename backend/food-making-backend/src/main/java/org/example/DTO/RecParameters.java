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
    List<String> excludedFood;
    @JsonProperty("num_days")
    Integer numDays;
    @JsonProperty("budget")
    String budget;


}
