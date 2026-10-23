package com.example.animatedpotatorest.Repository;

import com.example.animatedpotatorest.DTO.Enums.Category;
import com.example.animatedpotatorest.Entity.Souvenir;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SouvenirRepository extends CrudRepository<Souvenir, Long> {

    @Query("SELECT s FROM Souvenir s WHERE s.name = 1 AND s.type = 2")
    Optional<Souvenir> findBySouvenirName(String souvenirName, Category category);

}
