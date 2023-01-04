package com.sg.garderie.controller;

import com.sg.garderie.model.ClassFood;
import com.sg.garderie.service.GarderieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClassFoodController {

    @Autowired
    private GarderieService service;

    @PostMapping("/class/foods")
    @ResponseStatus(HttpStatus.CREATED)
    public void addClassFoods(@RequestParam int classId, @RequestParam String foodsIds) {
        int[] ids = Arrays.stream(foodsIds.split(",")).mapToInt(id -> Integer.valueOf(id)).toArray() ;

        service.addClassFoods(classId, ids);

    }

    @GetMapping("/class/foods/{classId}")
    public List<ClassFood> getClassFoodsByClassId(@PathVariable int classId) {
        return service.getClassFoodsByClassId(classId);
    }

    @GetMapping("/class/foods")
    public List<ClassFood> getAllClassFoods() {
        return service.getAllClassesFoods();
    }

    @DeleteMapping("/class/foods/{classId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteClassFoodsByClassId(@PathVariable int classId) {
        service.deleteClassFoodsByClassId(classId);
    }


}