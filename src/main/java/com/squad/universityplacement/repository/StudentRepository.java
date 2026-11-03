package com.squad.universityplacement.repository;

import com.squad.universityplacement.entity.PlacementStatus;
import com.squad.universityplacement.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, String> {

    boolean existsByRollNo(String rollNo);

    List<StudentEntity> findAllByOrderByRollNoAsc();

    long countByPlacementStatus(PlacementStatus placementStatus);
}
