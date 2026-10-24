package pl.student72016.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.student72016.crud.product.Product;
import pl.student72016.crud.product.ProductRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class FirstJavaCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstJavaCrudApplication.class, args);
    }

    @Bean
    CommandLineRunner loadExampleProducts(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Product("Coffee", "Drinks", new BigDecimal("12.50"), 20));
                repository.save(new Product("Notebook", "Stationery", new BigDecimal("8.99"), 35));
                repository.save(new Product("Headphones", "Electronics", new BigDecimal("129.00"), 8));
            }
        };
    }
}
