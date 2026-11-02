package com.squad.universityplacement.repository;

import com.university.placement.entity.PlacementStatus;
import com.university.placement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {

    boolean existsByRollNo(String rollNo);

    List<StudentEntity> findAllByOrderByRollNoAsc();

    long countByPlacementStatus(PlacementStatus placementStatus);
}
