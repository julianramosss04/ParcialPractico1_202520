package co.edu.uniandes.dse.ParcialPractico1_202520.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import co.edu.uniandes.dse.ParcialPractico1_202520.entities.RegionType;
import co.edu.uniandes.dse.ParcialPractico1_202520.entities.SistemaSolar;
import co.edu.uniandes.dse.ParcialPractico1_202520.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class SistemaSolarServiceTest {

    @Autowired private SistemaSolarService sistemaSolarService;

    private SistemaSolar valido() {
        SistemaSolar s = new SistemaSolar();
        s.setNombre("Julian System");
        s.setRegion(RegionType.CORE);
        s.setRatioMinimo(0.3);
        s.setNumeroStormtroopers(10_000);
        return s;
    }

    @Test
    void crear_ok() throws Exception {
        var res = sistemaSolarService.crearSistemaSolar(valido());
        assertNotNull(res.getId());
        assertEquals("Julian System", res.getNombre());
    }

    @Test
    void crear_falla_nombreLargo() {
        var s = valido();
        s.setNombre("Nombre.excesivamente.largo.para.reprobar.este.test"); // >= 31
        assertThrows(IllegalOperationException.class, () -> sistemaSolarService.crearSistemaSolar(s));
    }

    @Test
    void crear_falla_ratioFueraDeRango() {
        var s1 = valido();
        s1.setRatioMinimo(0.1);
        assertThrows(IllegalOperationException.class, () -> sistemaSolarService.crearSistemaSolar(s1));

        var s2 = valido();
        s2.setRatioMinimo(0.7);
        assertThrows(IllegalOperationException.class, () -> sistemaSolarService.crearSistemaSolar(s2));
    }

    @Test
    void crear_falla_stormtroopersInsuficientes() {
        var s = valido();
        s.setNumeroStormtroopers(1000);
        assertThrows(IllegalOperationException.class, () -> sistemaSolarService.crearSistemaSolar(s));
    }

    @Test
    void crear_falla_regionNull() {
        var s = valido();
        s.setRegion(null);
        assertThrows(IllegalOperationException.class, () -> sistemaSolarService.crearSistemaSolar(s));
    }
}
