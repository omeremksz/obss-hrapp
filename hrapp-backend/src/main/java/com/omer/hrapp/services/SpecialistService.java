package com.omer.hrapp.services;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.SpecialistRepository;
import com.omer.hrapp.requests.SpecialistCreateRequest;
import com.omer.hrapp.requests.SpecialistUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService {

    SpecialistRepository specialistRepository;

    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    public Specialist getSpecialistById(Long specialistId) {
        return specialistRepository.findById(specialistId).orElse(null);
    }

    public Specialist createNewSpecialist(SpecialistCreateRequest specialistCreateRequest) {
        Specialist toSave = new Specialist();
        toSave.setId(specialistCreateRequest.getId());
        toSave.setFirst_name(specialistCreateRequest.getFirst_name());
        toSave.setLast_name(specialistCreateRequest.getLast_name());
        toSave.setEmail(specialistCreateRequest.getEmail());
        return specialistRepository.save(toSave);
    }

    public Specialist updateSpecialist(Long specialistId, SpecialistUpdateRequest specialistUpdateRequest) {
        Optional<Specialist> specialist = specialistRepository.findById(specialistId);
        if(specialist.isPresent()){
            Specialist toUpdate = specialist.get();
            toUpdate.setFirst_name(specialistUpdateRequest.getFirst_name());
            toUpdate.setLast_name(specialistUpdateRequest.getLast_name());
            toUpdate.setEmail(specialistUpdateRequest.getEmail());
            return specialistRepository.save(toUpdate);
        } else
            return null;
    }

    public void deleteSpecialistById(Long specialistId) {
        specialistRepository.deleteById(specialistId);
    }
}
