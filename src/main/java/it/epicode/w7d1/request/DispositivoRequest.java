package it.epicode.w7d1.request;

import it.epicode.w7d1.enums.Disponibilita;
import it.epicode.w7d1.enums.Tipologia;
import it.epicode.w7d1.model.Dipendente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DispositivoRequest {

    @NotNull(message = "disponibilita obblogatoria")
    private Disponibilita disponibilita;

    @NotNull(message = "tipologia obblogatoria")
    private Tipologia tipologia;

    private Dipendente dipendente;

}
