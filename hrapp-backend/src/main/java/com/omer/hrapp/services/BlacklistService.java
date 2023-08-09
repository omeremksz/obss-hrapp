package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Blacklist;
import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.BlacklistRepository;
import com.omer.hrapp.requests.BlacklistCreateRequest;
import com.omer.hrapp.requests.BlacklistUpdateRequest;
import com.omer.hrapp.responses.BlacklistResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlacklistService {

    private BlacklistRepository blacklistRepository;

    private ApplicantService applicantService;

    private SpecialistService specialistService;

    private ApplicationService applicationService;

    public BlacklistService(BlacklistRepository blacklistRepository, ApplicantService applicantService, SpecialistService specialistService, ApplicationService applicationService) {
        this.blacklistRepository = blacklistRepository;
        this.applicantService = applicantService;
        this.specialistService = specialistService;
        this.applicationService = applicationService;
    }

    public Blacklist getBlacklistById(Long blacklistId) {
        return blacklistRepository.findById(blacklistId).orElse(null);
    }

    public List<BlacklistResponse> getBlacklistBySpecialistId(Optional<Long> specialistId) {
        List<Blacklist> blacklists;
        if (specialistId.isPresent()) {
            blacklists = blacklistRepository.findAllBySpecialistId(specialistId);
        } else
            blacklists = blacklistRepository.findAll();
        return blacklists.stream().map(b -> new BlacklistResponse(b)).collect(Collectors.toList());
    }

    public Blacklist createNewBlacklist(BlacklistCreateRequest blacklistCreateRequest) {
        Applicant applicant = applicantService.getApplicantById(blacklistCreateRequest.getApplicantId());
        Specialist specialist = specialistService.getSpecialistById(blacklistCreateRequest.getSpecialistId());
        if(applicant == null || specialist == null) {
            return null;
        }
        applicationService.deleteApplicationsByBlacklist(applicant.getId(), specialist.getId());
        Blacklist toSave = new Blacklist();
        toSave.setExplanation(blacklistCreateRequest.getExplanation());
        toSave.setBlackListedAt(LocalDateTime.now());
        toSave.setApplicant(applicant);
        toSave.setSpecialist(specialist);
        return blacklistRepository.save(toSave);
    }

    public Blacklist updateBlacklistById(Long blacklistId, BlacklistUpdateRequest blacklistUpdateRequest) {
        Optional<Blacklist> blacklist = blacklistRepository.findById(blacklistId);
        if(blacklist.isPresent()) {
            Blacklist toUpdate = blacklist.get();
            toUpdate.setExplanation(blacklistUpdateRequest.getExplanation());
            return blacklistRepository.save(toUpdate);
        } else
            return null;
    }

    public void deleteBlacklistById(Long blacklistId) {
        blacklistRepository.deleteById(blacklistId);
    }
}
