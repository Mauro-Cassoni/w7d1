package it.epicode.w7d1.model;


import it.epicode.w7d1.enums.Disponibilita;
import it.epicode.w7d1.enums.Tipologia;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private Disponibilita disponibilita;

    @Enumerated(EnumType.STRING)
    private Tipologia tipologia;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;


}
