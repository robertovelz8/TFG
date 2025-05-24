package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.repositories.ParteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ParteServiceImplTest {

    @InjectMocks
    private ParteServiceImpl parteService;

    @Mock
    private ParteRepository parteRepository;

    private Parte parte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parte = new Parte();
        parte.setId(1L);
        parte.setMotivo("Mal comportamiento");
        parte.setFecha(LocalDate.of(2024, 5, 24));
        parte.setHora(LocalTime.of(10, 30));
        parte.setDescripcion("Falta de respeto al profesor");
        parte.setLugar("Aula 2B");
    }

    @Test
    void createParte_shouldSetFechaYHoraSiNoEstanDefinidos() {
        Parte nuevoParte = new Parte();
        nuevoParte.setMotivo("Llegada tarde");

        when(parteRepository.save(any(Parte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Parte resultado = parteService.createParte(nuevoParte);

        assertNotNull(resultado.getFecha());
        assertNotNull(resultado.getHora());
        verify(parteRepository).save(any(Parte.class));
    }

    @Test
    void createParte_shouldUseProvidedFechaYHora() {
        when(parteRepository.save(parte)).thenReturn(parte);
        Parte resultado = parteService.createParte(parte);

        assertEquals(parte.getFecha(), resultado.getFecha());
        assertEquals(parte.getHora(), resultado.getHora());
    }

    @Test
    void getParteById_shouldReturnParte() {
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parte));

        Parte resultado = parteService.getParteById(1L);

        assertEquals(parte, resultado);
    }

    @Test
    void getParteById_shouldThrowIfNotFound() {
        when(parteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> parteService.getParteById(99L));
    }

    @Test
    void getAllPartes_shouldReturnList() {
        List<Parte> lista = Arrays.asList(parte, new Parte());
        when(parteRepository.findAll()).thenReturn(lista);

        List<Parte> resultado = parteService.getAllPartes();

        assertEquals(2, resultado.size());
    }

    @Test
    void updateParte_shouldUpdateOnlyNonNullFields() {
        Parte parteActualizada = new Parte();
        parteActualizada.setMotivo("Motivo actualizado");

        Parte parteExistente = new Parte();
        parteExistente.setId(1L);
        parteExistente.setMotivo("Original");
        parteExistente.setDescripcion("Descripción original");

        when(parteRepository.findById(1L)).thenReturn(Optional.of(parteExistente));
        when(parteRepository.save(any(Parte.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Parte resultado = parteService.updateParte(1L, parteActualizada);

        assertEquals("Motivo actualizado", resultado.getMotivo());
        assertEquals("Descripción original", resultado.getDescripcion());
    }

    @Test
    void updateParte_shouldThrowIfNotFound() {
        when(parteRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> parteService.updateParte(123L, parte));
    }

    @Test
    void deleteParte_shouldCallRepositoryDelete() {
        parteService.deleteParte(1L);

        verify(parteRepository).deleteById(1L);
    }

    @Test
    void deleteParte_shouldThrowIfIdIsNull() {
        assertThrows(ResourceNotFoundException.class, () -> parteService.deleteParte(null));
    }
}

