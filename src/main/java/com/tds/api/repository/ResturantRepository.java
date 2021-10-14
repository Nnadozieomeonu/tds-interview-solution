package com.tds.api.repository;

import com.tds.api.entity.Resturant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResturantRepository extends JpaRepository<Resturant, Long> {
    Optional<Resturant> findByName(String string);
}
