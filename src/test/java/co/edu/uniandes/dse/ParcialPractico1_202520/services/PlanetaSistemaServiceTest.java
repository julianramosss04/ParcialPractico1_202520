package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.RegionType;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class PlanetaSistemaServiceTest {

    @Autowired private PlanetaService planetaService;
    @Autowired private SistemaSolarService sistemaService;
    @Autowired private PlanetaSistemaService planetaSistemaService;

    private SistemaSolar sistemaA;
    private SistemaSolar sistemaB;
    private PlanetaEntity planeta;

    @BeforeEach
    void init() throws Exception {
       
        sistemaA = new SistemaSolar();
        sistemaA.setNombre("Julian System");
        sistemaA.setRegion(RegionType.MID_RIM);
        sistemaA.setRatioMinimo(0.3);
        sistemaA.setNumeroStormtroopers(1_000_000); 
        sistemaA = sistemaService.crearSistemaSolar(sistemaA);

        sistemaB = new SistemaSolar();
        sistemaB.setNombre("Ramos System");
        sistemaB.setRegion(RegionType.CORE);
        sistemaB.setRatioMinimo(0.25);
        sistemaB.setNumeroStormtroopers(800_000);
        sistemaB = sistemaService.crearSistemaSolar(sistemaB);

        planeta = new PlanetaEntity();
        planeta.setNombre("Julian I");
        planeta.setPoblacion(2_000_000L);   
        planeta.setDiametroKm(11800.0);
        planeta = planetaService.crearPlaneta(planeta);
    }

    @Test
    void asociar_ok_primeraVez() throws Exception {
        var asociado = planetaSistemaService.asociarSistemaAPlaneta(planeta.getId(), sistemaA.getId());
        assertNotNull(asociado.getSistemaSolar());
        assertEquals(sistemaA.getId(), asociado.getSistemaSolar().getId());
    }

    @Test
    void asociar_falla_planetaYaTieneSistemaDistinto() throws Exception {
       
        planetaSistemaService.asociarSistemaAPlaneta(planeta.getId(), sistemaA.getId());
        // Intentar asociar a otro sistema diferente debe fallar
        assertThrows(IllegalOperationException.class,
            () -> planetaSistemaService.asociarSistemaAPlaneta(planeta.getId(), sistemaB.getId()));
    }

    @Test
    void asociar_falla_idsInexistentes() {
        assertThrows(java.util.NoSuchElementException.class,
            () -> planetaSistemaService.asociarSistemaAPlaneta(999L, sistemaA.getId()));
        assertThrows(java.util.NoSuchElementException.class,
            () -> planetaSistemaService.asociarSistemaAPlaneta(planeta.getId(), 999L));
    }

    @Test
    void asociar_falla_ratioMinimo() throws Exception {
        // Planeta muy poblado para tumbar el ratio con sistemaA
        PlanetaEntity pGigante = new PlanetaEntity();
        pGigante.setNombre("Julian II");
        pGigante.setPoblacion(1_000_000_000_000L);
        pGigante.setDiametroKm(12000.0);
        PlanetaEntity giganteGuardado = planetaService.crearPlaneta(pGigante);

        assertThrows(IllegalOperationException.class,
            () -> planetaSistemaService.asociarSistemaAPlaneta(giganteGuardado.getId(), sistemaA.getId()));
    }
}
