package com.example.demo.dataInitializer;

import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Electronics"));
            categoryRepository.save(new Category("Books"));
            categoryRepository.save(new Category("Clothing"));
            categoryRepository.save(new Category("Home Appliances"));

            System.out.println("Categorias de prueba creadas");
        }


    }
}
