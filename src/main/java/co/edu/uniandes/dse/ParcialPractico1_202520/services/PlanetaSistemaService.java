package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.PlanetaRepository;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanetaSistemaService {

    @Autowired private PlanetaRepository planetaRepository;
    @Autowired private SistemaSolarRepository sistemaSolarRepository;

  
    @Transactional
    public PlanetaEntity asociarSistemaAPlaneta(Long planetaId, Long sistemaId) throws IllegalOperationException {
        log.info("Asociar (Planeta -> Sistema) planetaId={}, sistemaId={}", planetaId, sistemaId);

        PlanetaEntity planeta = planetaRepository.findById(planetaId).get();   // si no existe -> NoSuchElementException
        SistemaSolar sistema = sistemaSolarRepository.findById(sistemaId).get();

      
        if (planeta.getSistemaSolar() != null && !planeta.getSistemaSolar().getId().equals(sistemaId)) {
            throw new IllegalOperationException("El planeta ya pertenece a otro sistema solar.");
        }

        
        long poblacionActual = sistema.getPlanetas().stream()
                .map(p -> p.getPoblacion() == null ? 0L : p.getPoblacion())
                .reduce(0L, Long::sum);

        long poblacionTotalConNuevo = poblacionActual + (planeta.getPoblacion() == null ? 0L : planeta.getPoblacion());
        if (poblacionTotalConNuevo <= 0) {
            throw new IllegalOperationException("La población total no puede ser cero.");
        }

        double ratioActual = sistema.getNumeroStormtroopers() / (double) poblacionTotalConNuevo;
        if (ratioActual < sistema.getRatioMinimo()) {
            throw new IllegalOperationException("El ratio actual quedaría por debajo del mínimo del sistema.");
        }

       
        if (planeta.getSistemaSolar() == null) { 
            planeta.setSistemaSolar(sistema);
            sistema.getPlanetas().add(planeta);
        }

        sistemaSolarRepository.save(sistema);
        return planetaRepository.save(planeta);
    }
}
