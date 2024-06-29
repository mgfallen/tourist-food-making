package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {
    private Long orderID;
    private List<DayRecommendationDTO> recommendations;
}