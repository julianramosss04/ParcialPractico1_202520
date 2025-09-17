package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.PlanetaEntity;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class PlanetaServiceTest {

    @Autowired private PlanetaService planetaService;

    private PlanetaEntity valido() {
        PlanetaEntity p = new PlanetaEntity();
        p.setNombre("Ramos II");       
        p.setPoblacion(1_000_000L);   
        p.setDiametroKm(12120.0);
        return p;
    }

    @Test
    void crear_ok() throws Exception {
        var res = planetaService.crearPlaneta(valido());
        assertNotNull(res.getId());
        assertTrue(res.getNombre().endsWith("II"));
    }

    @Test
    void crear_falla_nombreNoRomano() {
        var p = valido();
        p.setNombre("Ramos 2");  // no cumple I/II/III
        assertThrows(IllegalOperationException.class, () -> planetaService.crearPlaneta(p));
    }

    @Test
    void crear_falla_poblacionNoPositiva() {
        var p = valido();
        p.setPoblacion(0L);
        assertThrows(IllegalOperationException.class, () -> planetaService.crearPlaneta(p));
    }
}
