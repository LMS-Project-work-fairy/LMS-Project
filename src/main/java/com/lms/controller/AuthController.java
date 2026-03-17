package com.lms.controller;

import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;

public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    public boolean registerProfessor(ProfessorDTO professor) {
        return service.insertProfessor(professor);
    }

    public void registerProfessor() {
    }
}
