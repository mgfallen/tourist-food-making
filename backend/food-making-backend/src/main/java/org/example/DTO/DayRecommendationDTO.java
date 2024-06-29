package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DayRecommendationDTO {
    private Integer day;
    private RecommendationDTO recipes;
}
