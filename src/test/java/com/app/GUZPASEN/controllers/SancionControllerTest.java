package com.app.GUZPASEN.controllers;

import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.serviceImpl.SancionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // ✔️ aquí está post(), put(), get(), delete()
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SancionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SancionServiceImpl sancionServiceImpl;

    @InjectMocks
    private SancionController sancionController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sancionController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private Sancion getMockSancion() {
        Sancion sancion = new Sancion();
        sancion.setId(1L);
        sancion.setFecha(LocalDate.of(2024, 5, 1));
        sancion.setTipoSancion(Sancion.TipoSancion.SIN_EXPULSION);
        sancion.setDuracion("3 días");

        Alumno alumno = new Alumno();
        alumno.setDni("12345678A");
        sancion.setAlumno(alumno);

        return sancion;
    }

    @Test
    void testCrearSancion_returnsCreatedSancion() throws Exception {
        Sancion sancion = getMockSancion();

        when(sancionServiceImpl.createSancion(any(Sancion.class))).thenReturn(sancion);

        mockMvc.perform(post("/guzpasen/conducta/sancion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sancion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.duracion").value("3 días"))
                .andExpect(jsonPath("$.tipoSancion").value("SIN_EXPULSION"));
    }

    @Test
    void testGetSancionById_returnsSancion() throws Exception {
        Sancion sancion = getMockSancion();

        when(sancionServiceImpl.getSancionById(1L)).thenReturn(sancion);

        mockMvc.perform(get("/guzpasen/conducta/sancion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.duracion").value("3 días"))
                .andExpect(jsonPath("$.tipoSancion").value("SIN_EXPULSION"));
    }

    @Test
    void testGetAllSanciones_returnsList() throws Exception {
        Sancion s1 = getMockSancion();
        Sancion s2 = new Sancion();
        s2.setId(2L);
        s2.setFecha(LocalDate.of(2024, 5, 3));
        s2.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_DENTRO);
        s2.setDuracion("2 horas");

        when(sancionServiceImpl.getAllSanciones()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/guzpasen/conducta/sancion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateSancion_returnsUpdatedSancion() throws Exception {
        Sancion sancion = getMockSancion();
        sancion.setDuracion("5 días");
        sancion.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_FUERA);

        when(sancionServiceImpl.updateSancion(eq(1L), any(Sancion.class))).thenReturn(sancion);

        mockMvc.perform(put("/guzpasen/conducta/sancion/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sancion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.duracion").value("5 días"))
                .andExpect(jsonPath("$.tipoSancion").value("CON_EXPULSION_FUERA"));
    }

    @Test
    void testDeleteSancion_returnsOk() throws Exception {
        doNothing().when(sancionServiceImpl).deleteSancion(1L);

        mockMvc.perform(delete("/guzpasen/conducta/sancion/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllSancionesByAlumno_returnsList() throws Exception {
        Sancion s1 = getMockSancion();

        when(sancionServiceImpl.getAllSancionesByAlumno("12345678A")).thenReturn(List.of(s1));

        mockMvc.perform(get("/guzpasen/conducta/sancion/alumno/12345678A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].duracion").value("3 días"))
                .andExpect(jsonPath("$[0].tipoSancion").value("SIN_EXPULSION"));
    }
}
