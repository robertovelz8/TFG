package com.app.GUZPASEN.controllers;

import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.serviceImpl.AlumnoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AlumnoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlumnoServiceImpl alumnoServiceImpl;

    @InjectMocks
    private AlumnoController alumnoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(alumnoController).build();
    }

    @Test
    void testCreateAlumno_returnsCreatedAlumno() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setNombre("Juan");

        Mockito.when(alumnoServiceImpl.createAlumno(any(Alumno.class))).thenReturn(alumno);

        mockMvc.perform(post("/guzpasen/conducta/alumno")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dni").value("12345678A"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void testGetAlumnoById_returnsAlumno() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setNombre("María");

        Mockito.when(alumnoServiceImpl.getAlumnoById("12345678A")).thenReturn(alumno);

        mockMvc.perform(get("/guzpasen/conducta/alumno/12345678A"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dni").value("12345678A"))
                .andExpect(jsonPath("$.nombre").value("María"));
    }

    @Test
    void testGetAllAlumnos_returnsList() throws Exception {
        Alumno a1 = new Alumno();
        a1.setDni("12345678A");
        a1.setNombre("Pepe");

        Alumno a2 = new Alumno();
        a2.setDni("87654321B");
        a2.setNombre("Laura");

        Mockito.when(alumnoServiceImpl.getAllAlumnos()).thenReturn(List.of(a1, a2));

        mockMvc.perform(get("/guzpasen/conducta/alumno"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].dni").value("12345678A"))
                .andExpect(jsonPath("$[1].dni").value("87654321B"));
    }

    @Test
    void testUpdateAlumno_returnsUpdatedAlumno() throws Exception {
        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        alumno.setNombre("Nombre actualizado");

        Mockito.when(alumnoServiceImpl.updateAlumno(Mockito.eq("12345678A"), any(Alumno.class)))
                .thenReturn(alumno);

        mockMvc.perform(put("/guzpasen/conducta/alumno/12345678A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alumno)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.dni").value("12345678A"))
                .andExpect(jsonPath("$.nombre").value("Nombre actualizado"));
    }

    @Test
    void testDeleteAlumno_returnsOk() throws Exception {
        doNothing().when(alumnoServiceImpl).deleteAlumno("12345678A");

        mockMvc.perform(delete("/guzpasen/conducta/alumno/12345678A"))
                .andExpect(status().isOk());
    }
}
