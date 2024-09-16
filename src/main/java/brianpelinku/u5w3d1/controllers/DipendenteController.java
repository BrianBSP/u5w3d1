package brianpelinku.u5w3d1.controllers;

import brianpelinku.u5w3d1.entities.Dipendente;
import brianpelinku.u5w3d1.exceptions.BadRequestException;
import brianpelinku.u5w3d1.payloads.NewDipendenteDTO;
import brianpelinku.u5w3d1.payloads.NewDipendenteRespDTO;
import brianpelinku.u5w3d1.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    // POST --> creo un nuovo record --- +body
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDipendenteRespDTO createDipendente(@RequestBody @Validated NewDipendenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            String messages = validation
                    .getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Segnalazione Errori nel Payload. " + messages);
        } else {
            return new NewDipendenteRespDTO(this.dipendenteService.saveDipendente(body).getId());
        }
    }

    // GET All
    @GetMapping
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.dipendenteService.findAll(page, size, sortBy);
    }

    // GET byId
    @GetMapping("/{dipendenteId}")
    public NewDipendenteRespDTO findDipendenteById(@PathVariable int dipendenteId) {
        return new NewDipendenteRespDTO(this.dipendenteService.findById(dipendenteId).getId());
    }

    // PUT   +body
    @PutMapping("/{dipendenteId}")
    public NewDipendenteRespDTO findByIdAndUpdate(@PathVariable int dipendenteId, @RequestBody NewDipendenteDTO newDipendente) {
        return new NewDipendenteRespDTO(this.dipendenteService.findByIdAndUpdate(dipendenteId, newDipendente).dipendenteId());
    }

    // DELETE
    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int dipendenteId) {
        dipendenteService.findByIdAndDelete(dipendenteId);
    }

    // UPLOAD IMMAGINE
    @PostMapping("/{dipendenteId}/avatar")
    public Dipendente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable int dipendenteId) throws IOException {
        try {
            return this.dipendenteService.uploadImagine(img, dipendenteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
