package pl.student72016.crud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import pl.student72016.crud.product.Product;
import pl.student72016.crud.product.ProductRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
@AutoConfigureMockMvc
class FirstJavaCrudApplicationTests {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private MockMvc browser;

    @Test
    void createsReadsUpdatesAndDeletesProduct() {
        Product product = repository.save(new Product("Test item", "Tests", new BigDecimal("10.00"), 2));
        assertThat(repository.findById(product.getId())).isPresent();

        product.setQuantity(5);
        repository.save(product);
        assertThat(repository.findById(product.getId()).orElseThrow().getQuantity()).isEqualTo(5);

        repository.deleteById(product.getId());
        assertThat(repository.findById(product.getId())).isEmpty();
    }

    @Test
    void displaysProductList() throws Exception {
        browser.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Product inventory")));
    }

    @Test
    void savesValidProductFromForm() throws Exception {
        browser.perform(post("/products/save")
                        .param("name", "Keyboard")
                        .param("category", "Electronics")
                        .param("price", "199.99")
                        .param("quantity", "4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?saved"));

        assertThat(repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrderByIdAsc(
                "Keyboard", "Keyboard")).hasSize(1);
    }

    @Test
    void rejectsInvalidProduct() throws Exception {
        browser.perform(post("/products/save")
                        .param("name", "")
                        .param("category", "")
                        .param("price", "-1")
                        .param("quantity", "-2"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-form"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Name is required")));
    }
}
