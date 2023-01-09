package com.sg.garderie.controller;

import com.sg.garderie.model.ClassTeacher;
import com.sg.garderie.model.Teacher;
import com.sg.garderie.service.GarderieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@RestController
public class ClassTeacherController {

    @Autowired
    private GarderieService service;

    @PostMapping("/class/teachers")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public void addClassTeachers(@RequestParam int classId, @RequestParam String teachersIds) {
        int[] ids = Arrays.stream(teachersIds.split(",")).mapToInt(id -> Integer.valueOf(id)).toArray() ;

        service.addClassTeachers(classId, ids);

    }

    @GetMapping("/class/teachers/{classId}")
    @CrossOrigin
    public List<Teacher> getClassTeachersByClassId(@PathVariable int classId) {
        return service.getClassTeachersByClassId(classId);
    }

    @GetMapping("/class/teachers")
    @CrossOrigin
    public List<ClassTeacher> getAllClassTeachers() {
        return service.getAllClassesTeachers();
    }

    @DeleteMapping("/class/teachers/{classId}")
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    public void deleteClassTeachersByClassId(@PathVariable int classId) {
        service.deleteClassTeachersByClassId(classId);
    }
}
