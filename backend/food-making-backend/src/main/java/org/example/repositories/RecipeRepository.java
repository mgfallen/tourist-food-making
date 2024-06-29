package org.example.repositories;

import org.example.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query(value = "WITH target_products AS (" +
            "            SELECT product_id FROM products WHERE product_id IN (:excludedProducts)" +
            "            )" +
            "            SELECT r.*" +
            "            FROM recipes r" +
            "            WHERE NOT EXISTS (" +
            "                SELECT 1" +
            "                FROM target_products tp" +
            "                JOIN LATERAL jsonb_array_elements(r.products) AS j ON true" +
            "                WHERE (j.value->>'productId')::int = tp.product_id" +
            "            );", nativeQuery = true)
    List<Recipe> getRecipesWithExcludedProducts(@Param("excludedProducts") List<Integer> excluded_recipes);
}
