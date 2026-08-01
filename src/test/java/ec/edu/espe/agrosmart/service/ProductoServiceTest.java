package ec.edu.espe.agrosmart.service;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.exception.ProductoNoEncontradoException;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class ProductoServiceTest {

    @Test
    void obtenerProductosComercializables_conTresValidosYDosInvalidos_debeEmitirSoloLosValidos() {
        
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        List<ProductoEntity> loteMixto = List.of(
            crearMockEntidad(101L, "Cacao Arriba", "Cacao", new BigDecimal("20.00"), List.of("a@a.ec")),
            crearMockEntidad(102L, "Cacao Premium", "Cacao", new BigDecimal("30.00"), List.of("b@a.ec")),
            crearMockEntidad(103L, "Cacao Bio", "Cacao", new BigDecimal("40.00"), List.of("c@a.ec")),
            crearMockEntidad(104L, "Cacao Fallido", "Cacao", BigDecimal.ZERO, List.of("d@a.ec")),
            crearMockEntidad(105L, "Cacao Sin Mail", "Cacao", new BigDecimal("10.00"), Collections.emptyList())
        );
        Mockito.when(repo.findAll()).thenReturn(loteMixto);
        ProductoService service = new ProductoService(repo, null);

     
        Flux<Producto> flujo = service.obtenerProductosComercializables();


        StepVerifier.create(flujo)
            .expectNextCount(3)
            .verifyComplete();
    }

    @Test
    void obtenerProductosComercializables_conTodosInvalidos_debeEmitirProductoGenerico() {
   
        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        List<ProductoEntity> loteInvalido = List.of(
            crearMockEntidad(201L, "Cacao Cero", "Cacao", BigDecimal.ZERO, List.of("x@x.ec")),
            crearMockEntidad(202L, "Cacao Vacio", "Cacao", new BigDecimal("11.00"), Collections.emptyList())
        );
        Mockito.when(repo.findAll()).thenReturn(loteInvalido);
        ProductoService service = new ProductoService(repo, null);

   
        Flux<Producto> flujo = service.obtenerProductosComercializables();

   
        StepVerifier.create(flujo)
            .expectNextMatches(item -> "PRODUCTO DE CACAO GENERICO".equals(item.getNombre()))
            .verifyComplete();
    }

    @Test
    void buscarPorId_conIdInexistente_debeLanzarProductoNoEncontradoException() {

        ProductoRepository repo = Mockito.mock(ProductoRepository.class);
        Mockito.when(repo.findById(888L)).thenReturn(Optional.empty());
        ProductoService service = new ProductoService(repo, null);


        StepVerifier.create(service.buscarPorId(888L))
            .expectError(ProductoNoEncontradoException.class)
            .verify();
    }

    private ProductoEntity crearMockEntidad(Long id, String nombre, String categoria, BigDecimal precio, List<String> correos) {
        ProductoEntity entity = Mockito.mock(ProductoEntity.class);
        Mockito.when(entity.getIdProducto()).thenReturn(id);
        Mockito.when(entity.getNombreProducto()).thenReturn(nombre);
        Mockito.when(entity.getCategoria()).thenReturn(categoria);
        Mockito.when(entity.getPrecioUsd()).thenReturn(precio);

        String correosCsv = (correos == null || correos.isEmpty()) ? "" : String.join(",", correos);
        Mockito.when(entity.getCorreosNotificacion()).thenReturn(correosCsv);

        return entity;
    }
}