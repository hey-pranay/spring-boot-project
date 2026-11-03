package com.university.placement.service;

import com.university.placement.exception.DuplicateResourceException;
import com.university.placement.exception.EligibilityException;
import com.university.placement.exception.ResourceNotFoundException;
import com.university.placement.exception.ValidationException;
import com.university.placement.entity.PlacementStatus;
import com.university.placement.entity.StudentEntity;
import com.university.placement.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/** FR-1 (student half): onboarding and maintenance of student profiles. */
@Service
public class StudentService {

    private static final BigDecimal MAX_CGPA = new BigDecimal("10.00");

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /** Validates and registers a new, UNPLACED student. */
    @Transactional
    public StudentEntity registerStudent(String rollNo, String name, BigDecimal cgpa, int activeBacklogs) {
        String normalizedRoll = normalizeRollNo(rollNo);
        if (normalizedRoll.length() > 30) {
            throw new ValidationException("Roll number must be at most 30 characters.");
        }
        if (name == null || name.isBlank()) {
            throw new ValidationException("Student name is required.");
        }
        if (name.trim().length() > 100) {
            throw new ValidationException("Student name must be at most 100 characters.");
        }
        if (cgpa == null || cgpa.signum() < 0 || cgpa.compareTo(MAX_CGPA) > 0) {
            throw new ValidationException("CGPA must be between 0.00 and 10.00.");
        }
        if (cgpa.scale() > 2) {
            throw new ValidationException("CGPA can have at most 2 decimal places.");
        }
        if (activeBacklogs < 0) {
            throw new ValidationException("Active backlogs cannot be negative.");
        }
        if (studentRepository.existsByRollNo(normalizedRoll)) {
            throw new DuplicateResourceException("A student with roll number " + normalizedRoll + " already exists.");
        }

        return studentRepository.save(new StudentEntity(normalizedRoll, name.trim(), cgpa.setScale(2),
                activeBacklogs, PlacementStatus.UNPLACED));
    }

    @Transactional(readOnly = true)
    public StudentEntity getStudent(String rollNo) {
        String normalizedRoll = normalizeRollNo(rollNo);
        return studentRepository.findById(normalizedRoll)
                .orElseThrow(() -> new ResourceNotFoundException("No student found with roll number " + normalizedRoll + "."));
    }

    @Transactional(readOnly = true)
    public List<StudentEntity> listStudents() {
        return studentRepository.findAllByOrderByRollNoAsc();
    }

    /** A student who is still UNPLACED may withdraw from the placement process. */
    @Transactional
    public StudentEntity optOut(String rollNo) {
        StudentEntity student = getStudent(rollNo);
        if (student.getPlacementStatus() == PlacementStatus.PLACED) {
            throw new EligibilityException(student.getRollNo() + " is already PLACED and cannot opt out.");
        }
        if (student.getPlacementStatus() == PlacementStatus.OPTED_OUT) {
            throw new EligibilityException(student.getRollNo() + " has already opted out.");
        }
        student.setPlacementStatus(PlacementStatus.OPTED_OUT);
        return student;
    }

    static String normalizeRollNo(String rollNo) {
        if (rollNo == null || rollNo.isBlank()) {
            throw new ValidationException("Roll number is required.");
        }
        return rollNo.trim().toUpperCase();
    }
}
