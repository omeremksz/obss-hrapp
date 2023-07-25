package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.requests.SpecialistCreateRequest;
import com.omer.hrapp.requests.SpecialistUpdateRequest;
import com.omer.hrapp.services.SpecialistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialists")
public class SpecialistController {
    SpecialistService specialistService;

    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping
    public List<Specialist> getAllSpecialists(){
        return specialistService.getAllSpecialists();
    }

    @GetMapping("/{specialistId}")
    public Specialist getSpecialistById(@PathVariable Long specialistId){
        return specialistService.getSpecialistById(specialistId);
    }

    @PostMapping
    public Specialist createNewSpecialist(@RequestBody SpecialistCreateRequest specialistCreateRequest){
        return specialistService.createNewSpecialist(specialistCreateRequest);
    }

    @PutMapping("/{specialistId}")
    public Specialist updateSpecialist(@PathVariable Long specialistId, @RequestBody SpecialistUpdateRequest specialistUpdateRequest){
        return specialistService.updateSpecialist(specialistId, specialistUpdateRequest);
    }

    @DeleteMapping("/{specialistId}")
    public void deleteSpecialistById(@PathVariable Long specialistId){
        specialistService.deleteSpecialistById(specialistId);
    }
}
