package com.musala.droneproject.repository;

import com.musala.droneproject.model.Models;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelsRepository extends JpaRepository<Models, Long> {
    Models findByModelName(String name);
}
