package brianpelinku.u5w3d1.controllers;

import brianpelinku.u5w3d1.payloads.DipendenteLoginDTO;
import brianpelinku.u5w3d1.payloads.DipendenteLoginResoDTO;
import brianpelinku.u5w3d1.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public DipendenteLoginResoDTO login(@RequestBody DipendenteLoginDTO body){
        return new DipendenteLoginResoDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }
}
