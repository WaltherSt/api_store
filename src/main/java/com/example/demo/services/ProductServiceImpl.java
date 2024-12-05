package com.example.demo.services;

import com.example.demo.dtos.ProductRequest;
import com.example.demo.dtos.product.ProductResponseDTO;
import com.example.demo.mappers.product.ProductMappers;
import com.example.demo.models.Category;
import com.example.demo.models.Image;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.interfaces.CategoryService;
import com.example.demo.services.interfaces.ImageService;
import com.example.demo.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;

    private final ProductMappers productMappers= new ProductMappers();


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService,
                              CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
    }
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        List<ProductResponseDTO> productResponseDTOs = new ArrayList<>();
            products.forEach(product-> {
                ProductResponseDTO productResponseDTO = productMappers.productToProductResponseDTO(product);
                productResponseDTOs.add(productResponseDTO);
            });
            return productResponseDTOs;
    }

    @Override
    public ResponseEntity<ProductResponseDTO> getProductById(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.map(value -> ResponseEntity.ok(productMappers.productToProductResponseDtoWithComments(value))).orElseGet(
                () -> ResponseEntity.notFound().build());
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * Este método crea una nueva instancia de un producto, asigna su categoría,
     * establece sus atributos (nombre, descripción, precio, stock) y asocia imágenes
     * que se suben a Cloudinary. Finalmente, guarda el producto en la base de datos junto
     * con las imágenes asociadas.
     *
     * @param productRequest Contiene los datos del producto a guardar, incluyendo el ID de la categoría
     *                       y las imágenes asociadas.
     * @return El producto guardado, incluyendo sus imágenes asociadas.
     * @throws RuntimeException Si no se encuentra la categoría o si ocurre un error durante la subida de imágenes.
     */
    @Override
    public Product saveProduct(ProductRequest productRequest) {
        Product product = new Product();

        // Obtiene la categoría asociada al producto utilizando el ID proporcionado
        Optional<Category> category = categoryService.getCategoryById(productRequest.getCategory());
        if (category.isPresent()) {
            product.setCategory(category.get());
        } else {
            throw new RuntimeException("Category not found");
        }

        // Asigna los atributos del producto
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(new BigDecimal(productRequest.getPrice()));
        product.setStock(productRequest.getStock());

        // Itera sobre las imágenes proporcionadas y sube cada imagen
        productRequest.getImages().forEach(image -> {
            try {
                // Subir la imagen a Cloudinary
                String url = this.cloudinaryService.upload(image);

                // Crea la instancia de Image y asocia la URL de la imagen y el producto
                Image i = new Image();
                i.setUrl(url);
                i.setProduct(product); // Asocia la imagen al producto

                // Agrega la imagen a la lista de imágenes del producto
                product.getImages().add(i);

            } catch (IOException e) {
                throw new RuntimeException(e); // Si ocurre un error, se lanza una excepción
            }
        });

        // Guarda el producto en la base de datos junto con las imágenes asociadas
        return this.productRepository.save(
                product);  // Aquí se guardan tanto el producto como las imágenes por el cascade
    }


    @Override
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        // Buscar el producto existente por ID
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        // Actualizar los campos básicos
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setCategory(productDetails.getCategory());

        return Optional.of(productRepository.save(existingProduct));
    }
    @Override
    public void deleteProduct(Long id) {
       this.productRepository.deleteById(id);
    }
}
