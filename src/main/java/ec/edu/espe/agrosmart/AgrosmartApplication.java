package ec.edu.espe.agrosmart;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class AgrosmartApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgrosmartApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(ProductoRepository repository) {
        return args -> {
            // Siembra idempotente (solo si la tabla está vacía)
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                    // 3 Válidos (precio > 0 y correos no vacíos)
                    new ProductoEntity("Cacao Fino de Aroma", new BigDecimal("15.50"), 100, "Cacao", "export@cacao.ec,ventas@cacao.ec"),
                    new ProductoEntity("Cacao CCN-51", new BigDecimal("12.00"), 200, "Cacao", "info@cacao.ec"),
                    new ProductoEntity("Cacao Orgánico en Grano", new BigDecimal("18.00"), 150, "Cacao", "bio@cacao.ec"),
                    
                    // 1 Inválido (precio = 0)
                    new ProductoEntity("Cacao Muestra Promocional", new BigDecimal("0.00"), 50, "Cacao", "muestra@cacao.ec"),
                    
                    // 1 Inválido (lista de correos vacía)
                    new ProductoEntity("Cacao Licor Crudo", new BigDecimal("25.00"), 80, "Cacao", "")
                ));
            }
        };
    }
}