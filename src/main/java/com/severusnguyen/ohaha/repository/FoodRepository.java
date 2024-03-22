package com.severusnguyen.ohaha.repository;

import com.severusnguyen.ohaha.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

}
