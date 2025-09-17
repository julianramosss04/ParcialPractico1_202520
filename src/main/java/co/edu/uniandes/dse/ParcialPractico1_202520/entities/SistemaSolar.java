package co.edu.uniandes.dse.ParcialPractico1_202520.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class SistemaSolar extends BaseEntity {

    private String nombre;

    @Enumerated(EnumType.STRING)
    private RegionType region;  

    private Double ratioMinimo;
    private Integer numeroStormtroopers;

    
    // Un sistema solar tiene muchos planetas

    @PodamExclude
    @OneToMany(mappedBy = "sistemaSolar")
    private List<PlanetaEntity> planetas = new ArrayList<>();
}
