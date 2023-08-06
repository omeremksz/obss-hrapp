package com.omer.hrapp.services;

import com.omer.hrapp.entities.Job;
import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.SpecialistRepository;
import com.omer.hrapp.requests.SpecialistCreateRequest;
import com.omer.hrapp.requests.SpecialistUpdateRequest;
import com.omer.hrapp.responses.JobResponse;
import com.omer.hrapp.responses.SpecialistResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialistService {

    SpecialistRepository specialistRepository;
    JobService jobService;

    public SpecialistService(SpecialistRepository specialistRepository, @Lazy JobService jobService) {
        this.specialistRepository = specialistRepository;
        this.jobService = jobService;
    }

    public List<Specialist> getAllSpecialists() {
        return specialistRepository.findAll();
    }

    public Specialist getSpecialistById(Long specialistId) {
        return specialistRepository.findById(specialistId).orElse(null);
    }

    public Specialist getSpecialistByUserName(String userName){
        return specialistRepository.findByUserName(userName);
    }

    public SpecialistResponse getSpecialistByIdWithJob(Long specialistId) {
        Specialist specialist = specialistRepository.findById(specialistId).orElse(null);
        List<JobResponse> jobs = jobService.getAllJobsBySpecialistId(Optional.of(specialistId));
        return new SpecialistResponse(specialist, jobs);
    }

    public Specialist createNewSpecialist(SpecialistCreateRequest specialistCreateRequest) {
        Specialist toSave = new Specialist();
        toSave.setId(specialistCreateRequest.getId());
        toSave.setFirstName(specialistCreateRequest.getFirst_name());
        toSave.setLastName(specialistCreateRequest.getLast_name());
        toSave.setEmail(specialistCreateRequest.getEmail());
        return specialistRepository.save(toSave);
    }

    public Specialist updateSpecialist(Long specialistId, SpecialistUpdateRequest specialistUpdateRequest) {
        Optional<Specialist> specialist = specialistRepository.findById(specialistId);
        if(specialist.isPresent()){
            Specialist toUpdate = specialist.get();
            toUpdate.setFirstName(specialistUpdateRequest.getFirst_name());
            toUpdate.setLastName(specialistUpdateRequest.getLast_name());
            toUpdate.setEmail(specialistUpdateRequest.getEmail());
            return specialistRepository.save(toUpdate);
        } else
            return null;
    }

    public void deleteSpecialistById(Long specialistId) {
        specialistRepository.deleteById(specialistId);
    }
}
