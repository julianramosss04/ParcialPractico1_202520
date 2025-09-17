package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.ParcialPractico1_202520.repositories.SistemaSolarRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SistemaSolarService {

    @Autowired
    private SistemaSolarRepository sistemaSolarRepository;

    @Transactional
    public SistemaSolar crearSistemaSolar(SistemaSolar s) throws IllegalOperationException {
        log.info("Inicia la creación del Sistema Solar");
        validar(s);
        SistemaSolar creado = sistemaSolarRepository.save(s);
        log.info("Termina la creación del Sistema Solar id={}", creado.getId());
        return creado;
    }

    private void validar(SistemaSolar s) throws IllegalOperationException {
        if (s.getNombre() == null || s.getNombre().length() >= 31)
            throw new IllegalOperationException("El nombre debe tener menos de 31 caracteres por favor");

        if (s.getRatioMinimo() == null || s.getRatioMinimo() < 0.2 || s.getRatioMinimo() > 0.6)
            throw new IllegalOperationException("El ratio debe estar entre 0.2 y 0.6");

        if (s.getNumeroStormtroopers() == null || s.getNumeroStormtroopers() <= 1000)
            throw new IllegalOperationException("Stormtroopers deben ser mayor a 1000 unidades");

        if (s.getRegion() == null)
            throw new IllegalOperationException("La región es obligatoria");
    }
}
