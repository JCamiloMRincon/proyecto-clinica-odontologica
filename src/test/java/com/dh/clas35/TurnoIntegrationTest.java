package com.dh.clas35;

import com.dh.clas35.entities.Domicilio;
import com.dh.clas35.entities.Odontologo;
import com.dh.clas35.entities.Paciente;
import com.dh.clas35.entities.Turno;
import com.dh.clas35.services.IOdontologoService;
import com.dh.clas35.services.IPacienteService;
import com.dh.clas35.services.ITurnoService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TurnoIntegrationTest {

    @Autowired
    private ITurnoService turnoService;
    @Autowired
    private IPacienteService pacienteService;
    @Autowired
    private IOdontologoService odontologoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void datosInicialesTurnoTest() {

        Domicilio domicilio =
                new Domicilio();
        Paciente paciente =
                pacienteService.guardarPaciente(new Paciente());
        Odontologo odontologo =
                odontologoService.guardarOdontologo(new Odontologo());

        assertNotNull(turnoService.guardarTurno(new Turno()));
    }

    @Test
    @Order(2)
    public void listarTurnosTest() throws Exception {
        MvcResult respuesta = mockMvc.perform(
                    MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(respuesta
                .getResponse()
                .getContentAsString()
                .isEmpty());
    }

}
