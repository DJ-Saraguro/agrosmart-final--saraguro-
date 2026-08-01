package ec.edu.espe.agrosmart.mapper;

import ec.edu.espe.agrosmart.domain.Producto;
import ec.edu.espe.agrosmart.entity.ProductoEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProductoMapper {

    private ProductoMapper() {
    }

    public static Producto toDominio(ProductoEntity entity) {
        if (entity == null) {
            return null;
        }

        List<String> correos = parseCorreos(entity.getCorreosNotificacion());

        return new Producto(
                entity.getIdProducto(),
                entity.getNombreProducto(),
                entity.getCategoria(),
                entity.getPrecioUsd(),
                correos
        );
    }

    private static List<String> parseCorreos(String correosStr) {
        if (correosStr == null || correosStr.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(correosStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}