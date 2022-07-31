package com.dh.clas35.services;

import com.dh.clas35.entities.Odontologo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class OdontologoServiceTest {

    @Autowired
    IOdontologoService odontologoService;

    @Test
    @Order(1)
    public void agregarOdontologoTest() {
        Odontologo odontologoParaAgregar = new Odontologo(
                "Giggio",
                "Topo",
                789012);

        odontologoService.guardarOdontologo(odontologoParaAgregar);
        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(1L);

        assertFalse(odontologoBuscado.isEmpty());
    }

    @Test
    @Order(2)
    public void buscarOdontologoPorIdTest() {
        Long odontologoIdBuscado = 1L;

        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(odontologoIdBuscado);

        assertFalse(odontologoBuscado.isEmpty());
    }

    @Test
    @Order(3)
    public void listarOdontologosTest() {
        List<Odontologo> listaDePrueba =
                odontologoService.listarOdontologos();

        assertTrue(listaDePrueba.size() > 0);
    }

    @Test
    @Order(4)
    public void actualizarOdontologoTest() {
        Odontologo odontologoConDatosNuevos = new Odontologo(
                1L,
                "Perez",
                "Raton",
                123456);

        odontologoService.actualizarOdontologo(odontologoConDatosNuevos);
        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(1L);

        assertEquals("Perez", odontologoBuscado.get().getApellido());
        assertEquals("Raton", odontologoBuscado.get().getNombre());
        assertEquals(123456, odontologoBuscado.get().getMatricula());
    }

    @Test
    @Order(5)
    public void eliminarOdontologoTest() {
        Long idBuscadoABorrar = 1L;

        odontologoService.eliminarOdontologo(idBuscadoABorrar);
        Optional<Odontologo> odontologoBuscado =
                odontologoService.buscarOdontologoPorId(idBuscadoABorrar);

        assertTrue(odontologoBuscado.isEmpty());
    }
}