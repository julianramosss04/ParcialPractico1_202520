package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanetaService {

    @Autowired
    private PlanetaRepository planetaRepository;

    @Transactional
    public PlanetaEntity crearPlaneta(PlanetaEntity p) throws IllegalOperationException {
        log.info("Inicia creacion del Planeta");
        validar(p);
        PlanetaEntity creado = planetaRepository.save(p);
        log.info("Termina creacion del Planeta id={}", creado.getId());
        return creado;
    }

   
    private void validar(PlanetaEntity p) throws IllegalOperationException {
        if (p.getNombre() == null || 
           !(p.getNombre().endsWith(" I") || p.getNombre().endsWith(" II") || p.getNombre().endsWith(" III")
             || p.getNombre().endsWith("I") || p.getNombre().endsWith("II") || p.getNombre().endsWith("III"))) {
            throw new IllegalOperationException("El nombre del planeta debe terminar en numero romano ej: I, II o III.");
        }
        if (p.getPoblacion() == null || p.getPoblacion() <= 0) {
            throw new IllegalOperationException("La poblacion del planeta debe ser un numero positivo mayor que cero.");
        }
    }
}
