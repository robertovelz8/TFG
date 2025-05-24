package com.app.GUZPASEN.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.app.GUZPASEN.controllers.NotificacionController;
import com.app.GUZPASEN.exceptions.ResourceNotFoundException;
import com.app.GUZPASEN.models.Alumno;
import com.app.GUZPASEN.models.Parte;
import com.app.GUZPASEN.models.Sancion;
import com.app.GUZPASEN.repositories.AlumnoRepository;
import com.app.GUZPASEN.repositories.ParteRepository;
import com.app.GUZPASEN.repositories.SancionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SancionServiceImplTest {

    @Mock
    private SancionRepository sancionRepository;

    @Mock
    private ParteRepository parteRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private NotificacionController notificacionController;

    @InjectMocks
    private SancionServiceImpl sancionService;

    private Alumno alumno;
    private Parte parte;
    private Sancion sancion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        alumno = new Alumno();
        alumno.setDni("12345678A");

        parte = new Parte();
        parte.setId(1L);

        sancion = new Sancion();
        sancion.setDuracion("3 días");
        sancion.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_DENTRO);
        sancion.setAlumno(alumno);
        sancion.setParte(parte);
    }

    @Test
    void testCreateSancion_conExpulsionYFechaNula_enviaNotificacion() {
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parte));
        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));
        when(sancionRepository.save(any(Sancion.class))).thenAnswer(i -> i.getArgument(0));

        Sancion result = sancionService.createSancion(sancion);

        assertNotNull(result);
        assertEquals("3 días", result.getDuracion());
        assertNotNull(result.getFecha());
        verify(notificacionController).notificarExpulsionTutorLegal("12345678A");
    }

    @Test
    void testCreateSancion_sinExpulsion_noEnviaNotificacion() {
        sancion.setTipoSancion(Sancion.TipoSancion.SIN_EXPULSION);
        when(parteRepository.findById(1L)).thenReturn(Optional.of(parte));
        when(alumnoRepository.findById("12345678A")).thenReturn(Optional.of(alumno));
        when(sancionRepository.save(any(Sancion.class))).thenAnswer(i -> i.getArgument(0));

        Sancion result = sancionService.createSancion(sancion);

        verify(notificacionController, never()).notificarExpulsionTutorLegal(anyString());
    }

    @Test
    void testGetSancionById_existente_devuelveSancion() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancion));

        Sancion result = sancionService.getSancionById(1L);

        assertEquals(sancion, result);
    }

    @Test
    void testGetSancionById_noExistente_lanzaExcepcion() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sancionService.getSancionById(1L));
    }

    @Test
    void testGetAllSanciones_devuelveLista() {
        when(sancionRepository.findAll()).thenReturn(Arrays.asList(sancion));

        List<Sancion> resultado = sancionService.getAllSanciones();

        assertEquals(1, resultado.size());
        assertEquals(sancion, resultado.get(0));
    }

    @Test
    void testUpdateSancion_modificaCamposCorrectos() {
        when(sancionRepository.findById(1L)).thenReturn(Optional.of(sancion));
        when(sancionRepository.save(any(Sancion.class))).thenAnswer(i -> i.getArgument(0));

        Sancion nuevosDatos = new Sancion();
        nuevosDatos.setDuracion("5 días");
        nuevosDatos.setTipoSancion(Sancion.TipoSancion.CON_EXPULSION_FUERA);

        Sancion actualizada = sancionService.updateSancion(1L, nuevosDatos);

        assertEquals("5 días", actualizada.getDuracion());
        assertEquals(Sancion.TipoSancion.CON_EXPULSION_FUERA, actualizada.getTipoSancion());
    }

    @Test
    void testDeleteSancion_idValido_elimina() {
        sancionService.deleteSancion(1L);
        verify(sancionRepository).deleteById(1L);
    }

    @Test
    void testDeleteSancion_idNulo_lanzaExcepcion() {
        assertThrows(ResourceNotFoundException.class, () -> sancionService.deleteSancion(null));
    }

    @Test
    void testGetAllSancionesByAlumno_devuelveLista() {
        when(sancionRepository.findByAlumnoDni("12345678A")).thenReturn(Arrays.asList(sancion));

        List<Sancion> resultado = sancionService.getAllSancionesByAlumno("12345678A");

        assertEquals(1, resultado.size());
        assertEquals("3 días", resultado.get(0).getDuracion());
    }
}

