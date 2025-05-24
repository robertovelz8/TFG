package com.app.GUZPASEN.controllers;

import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.serviceImpl.ParteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ParteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ParteServiceImpl parteServiceImpl;

    @InjectMocks
    private ParteController parteController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(parteController).build();
    }

    @Test
    void testCrearParte_returnsCreatedParte() throws Exception {
        Parte parte = new Parte();
        parte.setId(1L);
        parte.setMotivo("Motivo de prueba");

        Mockito.when(parteServiceImpl.createParte(any(Parte.class))).thenReturn(parte);

        mockMvc.perform(post("/guzpasen/conducta/parte")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.motivo").value("Motivo de prueba"));
    }

    @Test
    void testGetParteById_returnsParte() throws Exception {
        Parte parte = new Parte();
        parte.setId(1L);
        parte.setMotivo("Motivo");

        Mockito.when(parteServiceImpl.getParteById(1L)).thenReturn(parte);

        mockMvc.perform(get("/guzpasen/conducta/parte/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.motivo").value("Motivo"));
    }

    @Test
    void testGetAllPartes_returnsList() throws Exception {
        Parte parte1 = new Parte();
        parte1.setId(1L);
        parte1.setMotivo("Motivo 1");

        Parte parte2 = new Parte();
        parte2.setId(2L);
        parte2.setMotivo("Motivo 2");

        Mockito.when(parteServiceImpl.getAllPartes()).thenReturn(List.of(parte1, parte2));

        mockMvc.perform(get("/guzpasen/conducta/parte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateParte_returnsUpdatedParte() throws Exception {
        Parte parte = new Parte();
        parte.setId(1L);
        parte.setMotivo("Actualizado");

        Mockito.when(parteServiceImpl.updateParte(Mockito.eq(1L), any(Parte.class))).thenReturn(parte);

        mockMvc.perform(put("/guzpasen/conducta/parte/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.motivo").value("Actualizado"));
    }

    @Test
    void testDeleteParte_returnsOk() throws Exception {
        doNothing().when(parteServiceImpl).deleteParte(1L);

        mockMvc.perform(delete("/guzpasen/conducta/parte/1"))
                .andExpect(status().isOk());
    }
}

