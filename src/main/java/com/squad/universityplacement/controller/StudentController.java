package com.squad.universityplacement.controller;

import com.squad.universityplacement.api.StudentRequest;
import com.squad.universityplacement.entity.StudentEntity;
import com.squad.universityplacement.service.StudentService;

import jakarta.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentEntity registerStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.registerStudent(request.getRollNo(), request.getName(),
                request.getCgpa(), request.getActiveBacklogs());
    }

    @GetMapping
    public List<StudentEntity> listStudents() {
        return studentService.listStudents();
    }

    @GetMapping("/{rollNo}")
    public StudentEntity getStudent(@PathVariable String rollNo) {
        return studentService.getStudent(rollNo);
    }

    @PostMapping("/{rollNo}/opt-out")
    public StudentEntity optOut(@PathVariable String rollNo) {
        return studentService.optOut(rollNo);
    }
}