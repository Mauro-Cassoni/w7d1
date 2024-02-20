package it.epicode.w7d1.controller;

import it.epicode.w7d1.exception.BadRequestException;
import it.epicode.w7d1.exception.LoginFaultException;
import it.epicode.w7d1.model.Dipendente;
import it.epicode.w7d1.request.DipendenteRequest;
import it.epicode.w7d1.request.LoginRequest;
import it.epicode.w7d1.security.JwtTools;
import it.epicode.w7d1.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public Dipendente register(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return dipendenteService.saveDipendente(dipendenteRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginRequest loginRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        Dipendente dipendente = dipendenteService.getDipendenteByUsername(loginRequest.getUsername());

        if(passwordEncoder.matches(loginRequest.getPassword(), dipendente.getPassword())){
            return jwtTools.createToken(dipendente);
        }
        else{
            throw new LoginFaultException("username/password errate");
        }

    }
}
