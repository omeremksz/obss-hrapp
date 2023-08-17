package com.omer.hrapp.controllers;

import com.omer.hrapp.entities.Blacklist;
import com.omer.hrapp.requests.BlacklistCreateRequest;
import com.omer.hrapp.requests.BlacklistUpdateRequest;
import com.omer.hrapp.responses.BlacklistResponse;
import com.omer.hrapp.services.BlacklistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blacklists")
public class BlacklistController {

    private BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @GetMapping("/{blacklistId}")
    public Blacklist getBlacklistById(@PathVariable Long blacklistId) {
        return blacklistService.getBlacklistById(blacklistId);
    }

    @GetMapping
    public List<BlacklistResponse> getBlacklistBySpecialistId(@RequestParam Optional<Long> specialistId) {
        return blacklistService.getBlacklistBySpecialistId(specialistId);
    }

    @PostMapping Blacklist createNewBlacklist(@RequestBody BlacklistCreateRequest blacklistCreateRequest) {
        return blacklistService.createNewBlacklist(blacklistCreateRequest);
    }

    @PutMapping("/{blacklistId}")
    public Blacklist updateBlacklistById(@PathVariable Long blacklistId, @RequestBody BlacklistUpdateRequest blacklistUpdateRequest) {
        return blacklistService.updateBlacklistById(blacklistId, blacklistUpdateRequest);
    }

    @DeleteMapping("/{blacklistId}")
    public void deleteBlacklistById(@PathVariable Long blacklistId) {
        blacklistService.deleteBlacklistById(blacklistId);
    }

}
