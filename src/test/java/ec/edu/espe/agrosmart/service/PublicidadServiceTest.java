package ec.edu.espe.agrosmart.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;

class PublicidadServiceTest {

    @Test
    void generarPublicidad_cuandoElProveedorRespondeCorrectamente_debeEmitirTexto() {

        AgroSmartAIService iaMock = Mockito.mock(AgroSmartAIService.class);
        Mockito.when(iaMock.generarPublicidad(anyString(), anyString()))
               .thenReturn("Cacao ecuatoriano de calidad insuperable.");
        ProductoService service = new ProductoService(null, iaMock);


        StepVerifier.create(service.generarPublicidad("Cacao Fino", "Mercado Europeo"))
            .expectNext("Cacao ecuatoriano de calidad insuperable.")
            .verifyComplete();
    }

    @Test
    void generarPublicidad_cuandoElProveedorFalla_debeEmitirMensajeDeRespaldo() {

        AgroSmartAIService iaMock = Mockito.mock(AgroSmartAIService.class);
        Mockito.when(iaMock.generarPublicidad(anyString(), anyString()))
               .thenThrow(new RuntimeException("Error 500 API IA"));
        ProductoService service = new ProductoService(null, iaMock);


        StepVerifier.create(service.generarPublicidad("Cacao Fino", "Mercado Europeo"))
            .expectNextMatches(res -> res.contains("no disponible"))
            .verifyComplete();
    }
}