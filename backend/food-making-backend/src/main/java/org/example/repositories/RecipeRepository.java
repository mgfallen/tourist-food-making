package org.example.repositories;

import org.example.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "WITH target_products AS " +
            "(SELECT id FROM products WHERE name IN ?1)" +
            " SELECT r.* FROM recipes r WHERE NOT EXISTS " +
            "(SELECT 1    FROM target_products tp, jsonb_each_text(r.products) as j(productId, amount) " +
            "WHERE j.productId::int = tp.id);", nativeQuery = true)

    public List<Recipe> getRecipesWithExcludedProducts(List<String> excluded_recipes);
}
