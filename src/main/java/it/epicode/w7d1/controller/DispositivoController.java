package it.epicode.w7d1.controller;

import com.cloudinary.Cloudinary;
import it.epicode.w7d1.enums.Disponibilita;
import it.epicode.w7d1.exception.BadRequestException;
import it.epicode.w7d1.exception.CustomResponse;
import it.epicode.w7d1.exception.NotFoundException;
import it.epicode.w7d1.model.Dispositivo;
import it.epicode.w7d1.request.DispositivoRequest;
import it.epicode.w7d1.service.DipendenteService;
import it.epicode.w7d1.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispositivo")
public class DispositivoController {
    
    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private DipendenteService dipendenteService;
    
    @Autowired
    private Cloudinary cloudinary;
    
    @GetMapping("")
    public ResponseEntity<CustomResponse> getAllDispositivi(Pageable pageable) {
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.findAllDipendenti(pageable), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getDispositivoById(@PathVariable int id){
        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.findDispositivoById(id), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<CustomResponse> saveDispositivo(@RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        if (bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.saveDispositivo(dispositivoRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateDispositivo(@PathVariable int id, @RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()) throw new BadRequestException(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString());
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.updateDispositivo(id, dispositivoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteDispositivo(@PathVariable int id) {
        try{
            dispositivoService.deleteDispositivo(id);
            return CustomResponse.emptyResponse("Dispositivo con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/set")
    public ResponseEntity<CustomResponse> setDipendente(@PathVariable int id,@RequestParam int id_dipendente){
        try {
            Dispositivo dispositivo = dispositivoService.setDipendente(id, id_dipendente);
            dispositivo.setDisponibilita(Disponibilita.ASSEGNATO);

            return CustomResponse.success(HttpStatus.OK.toString(), dispositivo, HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
