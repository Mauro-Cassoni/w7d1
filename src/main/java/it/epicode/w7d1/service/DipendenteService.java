package it.epicode.w7d1.service;

import it.epicode.w7d1.exception.NotFoundException;
import it.epicode.w7d1.model.Dipendente;
import it.epicode.w7d1.repository.DipendenteRepository;
import it.epicode.w7d1.request.DipendenteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Page<Dipendente> findAllDipendenti(Pageable pageable){

        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente findDipendenteById(int id) throws NotFoundException {
        return dipendenteRepository.findById(id).orElseThrow(()->new NotFoundException("Dipendente con id= " + id + " non trovato"));
    }

    public Dipendente saveDipendente(DipendenteRequest dipendenteRequest){
        Dipendente a = new Dipendente();
        a.setNome(dipendenteRequest.getNome());
        a.setCognome(dipendenteRequest.getCognome());
        a.setEmail(dipendenteRequest.getEmail());
        a.setUsername(dipendenteRequest.getUsername());
        a.setPassword(dipendenteRequest.getPassword());


        return dipendenteRepository.save(a);
    }

    public Dipendente updateDipendente(int id, DipendenteRequest dipendenteRequest) throws NotFoundException{
        Dipendente a = new Dipendente();
        a.setNome(dipendenteRequest.getNome());
        a.setCognome(dipendenteRequest.getCognome());
        a.setEmail(dipendenteRequest.getEmail());
        a.setUsername(dipendenteRequest.getUsername());
        a.setPassword(dipendenteRequest.getPassword());


        return dipendenteRepository.save(a);
    }

    public void deleteDipendente(int id) throws NotFoundException{
        Dipendente a = findDipendenteById(id);
        dipendenteRepository.delete(a);
    }

    public Dipendente uploadLogo(int id, String url) throws NotFoundException{
        Dipendente a = findDipendenteById(id);
        a.setLogo(url);
        return dipendenteRepository.save(a);
    }

    public Dipendente getDipendenteByUsername(String username){
        return dipendenteRepository.findByUsername(username).orElseThrow(()->new NotFoundException("Username non trovato"));
    }


}
