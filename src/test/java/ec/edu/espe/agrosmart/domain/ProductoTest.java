package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void getters_cuandoSeConstruyeProducto_debenDevolverValoresCorrectos() {
        var correos = List.of("coordinacion@cacao.ec");
        var item = new Producto(15L, "Cacao Amazonia", "Cacao", new BigDecimal("85.00"), correos);

        assertAll("Verificación integral de getters",
            () -> assertEquals(15L, item.getId()),
            () -> assertEquals("Cacao Amazonia", item.getNombre()),
            () -> assertEquals("Cacao", item.getCategoria()),
            () -> assertEquals(new BigDecimal("85.00"), item.getPrecioUsd()),
            () -> assertEquals(correos, item.getCorreosNotificacion())
        );
    }

    @Test
    void getCorreosNotificacion_alMutarLaListaOriginal_noDebeAfectarAlProducto() {
        var correosBase = new ArrayList<String>();
        correosBase.add("envios@cacao.ec");
        var item = new Producto(2L, "Cacao Nacional", "Cacao", new BigDecimal("95.00"), correosBase);

        correosBase.add("externo@dominio.com");

        assertAll(
            () -> assertEquals(1, item.getCorreosNotificacion().size()),
            () -> assertNotSame(correosBase, item.getCorreosNotificacion())
        );
    }

    @Test
    void getCorreosNotificacion_alMutarLaListaDevuelta_debeLanzarExcepcion() {

        var item = new Producto(3L, "Cacao en Grano", "Cacao", new BigDecimal("40.00"), List.of("alerta@cacao.ec"));

        assertThrows(UnsupportedOperationException.class, () -> {
            item.getCorreosNotificacion().add("intruso@mail.com");
        });
    }
}