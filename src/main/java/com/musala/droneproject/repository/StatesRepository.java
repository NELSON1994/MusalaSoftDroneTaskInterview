package com.musala.droneproject.repository;

import com.musala.droneproject.model.States;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatesRepository extends JpaRepository<States,Long> {
}
