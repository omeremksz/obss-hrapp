package com.omer.hrapp.services;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.SpecialistRepository;
import com.omer.hrapp.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SpecialistDetailsService implements UserDetailsService {
    private SpecialistRepository specialistRepository;

    public SpecialistDetailsService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Specialist specialist = specialistRepository.findByUserName(username);
        return JwtUserDetails.create(specialist);
    }

}