package org.example.support;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class ProductsList implements Serializable {
    private Long productId;
    private Double amount;

}
