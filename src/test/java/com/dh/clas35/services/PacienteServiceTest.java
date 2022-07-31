package com.dh.clas35.services;

import com.dh.clas35.entities.Domicilio;
import com.dh.clas35.entities.Paciente;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    IPacienteService pacienteService;

    @Test
    @Order(1)
    public void agregarPacienteTest() {
        Domicilio domicilioParaAgregar = new Domicilio(
                "Calle 3",
                43,
                "Bogota",
                "Bogota"
        );

        Paciente pacienteParaAgregar = new Paciente(
                "Baspineiro",
                "Rodolfo",
                "rodolfo@baspineiro.com",
                987654,
                LocalDate.of(2022,6,28),
                domicilioParaAgregar);

        pacienteService.guardarPaciente(pacienteParaAgregar);
        Optional<Paciente> pacienteBuscado =
                pacienteService.buscarPacientePorId(1L);

        assertFalse(pacienteBuscado.isEmpty());
    }

    @Test
    @Order(2)
    public void buscarPacientePorIdTest() {
        Long pacienteIdBuscado = 1L;

        Optional<Paciente> pacienteBuscado =
                pacienteService.buscarPacientePorId(pacienteIdBuscado);

        assertFalse(pacienteBuscado.isEmpty());
    }

    @Test
    @Order(3)
    public void listarPacientesTest() {
        List<Paciente> listaDePrueba =
                pacienteService.listarPacientes();

        assertTrue(listaDePrueba.size() > 0);
    }

    @Test
    @Order(4)
    public void actualizarPacienteTest() {
        Domicilio domicilioParaPacienteNuevo = new Domicilio(
                1L,
                "Calle 3",
                43,
                "Bogota",
                "Bogota"
        );

        Paciente pacienteConDatosNuevos = new Paciente(
                1L,
                "Rincon",
                "Juan",
                "juan@rincon.com",
                987654,
                LocalDate.of(2022,7,28),
                domicilioParaPacienteNuevo);

        pacienteService.actualizarPaciente(pacienteConDatosNuevos);
        Optional<Paciente> pacienteBuscado =
                pacienteService.buscarPacientePorId(1L);

        assertEquals("Rincon", pacienteBuscado.get().getApellido());
        assertEquals("Juan", pacienteBuscado.get().getNombre());
        assertEquals("juan@rincon.com", pacienteBuscado.get().getEmail());
        assertEquals(987654, pacienteBuscado.get().getDni());
        assertEquals(2022, pacienteBuscado.get().getFechaIngreso().getYear());
        assertEquals(7, pacienteBuscado.get().getFechaIngreso().getMonth().getValue());
        assertEquals(28, pacienteBuscado.get().getFechaIngreso().getDayOfMonth());
        assertEquals("Calle 3", pacienteBuscado.get().getDomicilio().getCalle());
        assertEquals(43, pacienteBuscado.get().getDomicilio().getNumero());
        assertEquals("Bogota", pacienteBuscado.get().getDomicilio().getLocalidad());
        assertEquals("Bogota", pacienteBuscado.get().getDomicilio().getProvincia());
    }

    @Test
    @Order(5)
    public void eliminarPacienteTest() {
        Long idBuscadoABorrar = 1L;

        pacienteService.eliminarPaciente(idBuscadoABorrar);
        Optional<Paciente> pacienteBuscado =
                pacienteService.buscarPacientePorId(idBuscadoABorrar);

        assertTrue(pacienteBuscado.isEmpty());
    }
}