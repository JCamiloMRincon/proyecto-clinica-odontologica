package com.dh.clas35.services;

import com.dh.clas35.entities.Domicilio;
import com.dh.clas35.entities.Odontologo;
import com.dh.clas35.entities.Paciente;
import com.dh.clas35.entities.Turno;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class TurnoServiceTest {

    @Autowired
    ITurnoService turnoService;
    @Autowired
    IPacienteService pacienteService;
    @Autowired
    IOdontologoService odontologoService;

    public List<Object> crearPacientesYOdontologos() {
        Domicilio domicilioParaAgregarPacienteUno = new Domicilio(
                "Calle 3",
                43,
                "Bogota",
                "Bogota"
        );

        Domicilio domicilioParaAgregarPacienteDos = new Domicilio(
                "Calle 3",
                43,
                "Medellin",
                "Antioquia"
        );

        Paciente pacienteParaAgregarUno = new Paciente(
                "Baspineiro",
                "Rodolfo",
                "rodolfo@baspineiro.com",
                987654,
                LocalDate.of(2022,6,28),
                domicilioParaAgregarPacienteUno);

        Paciente pacienteParaAgregarDos = new Paciente(
                "Rincon",
                "Juan",
                "juan@rincon.com",
                987654,
                LocalDate.of(2022,7,28),
                domicilioParaAgregarPacienteDos);

        Odontologo odontologoParaAgregarUno = new Odontologo(
                "Giggio",
                "Topo",
                789012);

        Odontologo odontologoParaAgregarDos = new Odontologo(
                "Perez",
                "Raton",
                123456);

        List<Object> insumosParaTurnos = new ArrayList<>();

        insumosParaTurnos.add(pacienteParaAgregarUno);
        insumosParaTurnos.add(pacienteParaAgregarDos);
        insumosParaTurnos.add(odontologoParaAgregarUno);
        insumosParaTurnos.add(odontologoParaAgregarDos);

        return insumosParaTurnos;
    }

    @Test
    @Order(1)
    public void agregarTurnoTest() {
        List<Object> insumosParaTest = crearPacientesYOdontologos();
        Paciente pacienteParaAgregar =
                (Paciente) insumosParaTest.get(0);
        Odontologo odontologoParaAgregar =
                (Odontologo) insumosParaTest.get(2);

        pacienteService.guardarPaciente(pacienteParaAgregar);
        odontologoService.guardarOdontologo(odontologoParaAgregar);
        turnoService.guardarTurno(new Turno(pacienteParaAgregar,
                odontologoParaAgregar,
                LocalDate.of(2022,8,16))
        );

        Optional<Turno> turnoBuscado =
                turnoService.buscarTurnoPorId(1L);

        assertFalse(turnoBuscado.isEmpty());
    }

    @Test
    @Order(2)
    public void buscarTurnoPorIdTest() {
        Long turnoIdBuscado = 1L;

        Optional<Turno> turnoBuscado =
                turnoService.buscarTurnoPorId(turnoIdBuscado);

        assertFalse(turnoBuscado.isEmpty());
    }

    @Test
    @Order(3)
    public void listarTurnosTest() {
        List<Turno> listaDePrueba =
                turnoService.listarTurnos();

        assertTrue(listaDePrueba.size() > 0);
    }

    @Test
    @Order(4)
    public void actualizarTurnoTest() {
        List<Object> insumosParaTest = crearPacientesYOdontologos();
        Paciente pacienteConDatosNuevos =
                (Paciente) insumosParaTest.get(1);
        Odontologo odontologoConDatosNuevos =
                (Odontologo) insumosParaTest.get(3);

        pacienteService.guardarPaciente(pacienteConDatosNuevos);
        odontologoService.guardarOdontologo(odontologoConDatosNuevos);

        turnoService.actualizarTurno(new Turno(1L,
                pacienteConDatosNuevos,
                odontologoConDatosNuevos,
                LocalDate.of(2022,8,28)
        ));

        Optional<Turno> turnoBuscado =
                turnoService.buscarTurnoPorId(1L);

        assertEquals(3L, turnoBuscado.get().getPaciente().getId());
        assertEquals(3L, turnoBuscado.get().getOdontologo().getId());
        assertEquals(2022, turnoBuscado.get().getFecha().getYear());
        assertEquals(8, turnoBuscado.get().getFecha().getMonth().getValue());
        assertEquals(28, turnoBuscado.get().getFecha().getDayOfMonth());
    }

    @Test
    @Order(5)
    public void eliminarTurnoTest() {
        Long idBuscadoABorrar = 1L;

        turnoService.eliminarTurno(idBuscadoABorrar);
        Optional<Turno> turnoBuscado =
                turnoService.buscarTurnoPorId(idBuscadoABorrar);

        assertTrue(turnoBuscado.isEmpty());
    }
}