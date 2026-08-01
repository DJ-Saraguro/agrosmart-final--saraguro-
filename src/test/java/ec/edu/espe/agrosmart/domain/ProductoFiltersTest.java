package ec.edu.espe.agrosmart.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProductoFiltersTest {

    @Test
    void isValid_conProductoValido_debeRetornarTrue() {
        var valido = new Producto(10L, "CACAO EXPORT", "Cacao", new BigDecimal("25.00"), List.of("ok@cacao.ec"));

        assertTrue(ProductoFilters.IS_VALID.test(valido));
    }

    @Test
    void isValid_conPrecioCeroOInvalido_debeRetornarFalse() {
        var sinPrecio = new Producto(11L, "CACAO MUESTRA", "Cacao", BigDecimal.ZERO, List.of("ok@cacao.ec"));

        assertFalse(ProductoFilters.IS_VALID.test(sinPrecio));
    }

    @Test
    void isValid_conListaDeCorreosVacia_debeRetornarFalse() {
        var sinCorreo = new Producto(12L, "CACAO LOCAL", "Cacao", new BigDecimal("18.50"), Collections.emptyList());

        assertFalse(ProductoFilters.IS_VALID.test(sinCorreo));
    }
}