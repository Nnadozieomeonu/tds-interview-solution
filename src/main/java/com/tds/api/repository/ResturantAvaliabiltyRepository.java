package com.tds.api.repository;

import com.tds.api.entity.ResturantAvaliabilty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResturantAvaliabiltyRepository extends JpaRepository<ResturantAvaliabilty, Long> {
    List<ResturantAvaliabilty> findByDay(String day);
}
