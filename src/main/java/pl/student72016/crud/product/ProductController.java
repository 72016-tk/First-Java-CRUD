package pl.student72016.crud.product;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String list(@RequestParam(defaultValue = "") String search, Model model) {
        String normalizedSearch = search.trim();
        var products = normalizedSearch.isBlank()
                ? repository.findAll()
                : repository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrderByIdAsc(
                        normalizedSearch, normalizedSearch);
        model.addAttribute("products", products);
        model.addAttribute("search", normalizedSearch);
        return "products";
    }

    @GetMapping("/products/new")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add product");
        return "product-form";
    }

    @GetMapping("/products/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var product = repository.findById(id);
        if (product.isEmpty()) {
            return "redirect:/?error=not-found";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("pageTitle", "Edit product");
        return "product-form";
    }

    @PostMapping("/products/save")
    public String save(@Valid @ModelAttribute Product product,
                       BindingResult validation,
                       Model model) {
        if (validation.hasErrors()) {
            model.addAttribute("pageTitle", product.getId() == null ? "Add product" : "Edit product");
            return "product-form";
        }
        repository.save(product);
        return "redirect:/?saved";
    }

    @PostMapping("/products/{id}/delete")
    public String delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "redirect:/?deleted";
        }
        return "redirect:/?error=not-found";
    }
}
