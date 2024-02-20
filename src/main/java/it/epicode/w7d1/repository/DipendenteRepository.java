package it.epicode.w7d1.repository;

import it.epicode.w7d1.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Integer>, PagingAndSortingRepository<Dipendente, Integer> {
    public Optional<Dipendente> findByUsername(String username);

    public Optional<Dipendente> deleteByUsername(String username);
}
