package it.epicode.w7d1.service;

import it.epicode.w7d1.enums.Disponibilita;
import it.epicode.w7d1.exception.NotFoundException;
import it.epicode.w7d1.model.Dipendente;
import it.epicode.w7d1.model.Dispositivo;
import it.epicode.w7d1.repository.DispositivoRepository;
import it.epicode.w7d1.request.DispositivoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DipendenteService dipendenteService;

    public Page<Dispositivo> findAllDipendenti(Pageable pageable){

        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findDispositivoById(int id) throws NotFoundException {
        return dispositivoRepository.findById(id).orElseThrow(()->new NotFoundException("Dispositivo con id= " + id + " non trovato"));
    }

    public Dispositivo saveDispositivo(DispositivoRequest dispositivoRequest){
        Dispositivo a = new Dispositivo();
        a.setDisponibilita(dispositivoRequest.getDisponibilita());
        a.setTipologia(dispositivoRequest.getTipologia());

        return dispositivoRepository.save(a);
    }

    public Dispositivo updateDispositivo(int id, DispositivoRequest dispositivoRequest) throws NotFoundException{
        Dispositivo a = findDispositivoById(id);

        if (a.getDisponibilita() == Disponibilita.ASSEGNATO){
            throw new RuntimeException("questo dispositivo è già assegnato ad un altro utente");
        } if (a.getDisponibilita() == Disponibilita.IN_MANUTENZIONE){
            throw new RuntimeException("questo dispositivo è in manutenzione");
        }

        a.setDipendente(dispositivoRequest.getDipendente());
        a.setDisponibilita(dispositivoRequest.getDisponibilita());
        a.setTipologia(dispositivoRequest.getTipologia());

        return dispositivoRepository.save(a);
    }

    public void deleteDispositivo(int id) throws NotFoundException{
        Dispositivo a = findDispositivoById(id);
        dispositivoRepository.delete(a);
    }

    public Dispositivo setDipendente(int id_dispositivo, int id_dipendente) throws NotFoundException{
        Dispositivo dispositivo = findDispositivoById(id_dispositivo);
        Dipendente dipendente = dipendenteService.findDipendenteById(id_dipendente);
        dispositivo.setDipendente(dipendente);

        return dispositivoRepository.save(dispositivo);
    }
    
    
}
