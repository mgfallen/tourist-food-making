package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class ProductsList implements Serializable {
    private Long productId;
    private Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductsList that = (ProductsList) o;

        return productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(productId);
    }

}
