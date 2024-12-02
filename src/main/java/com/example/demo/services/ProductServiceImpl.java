package com.example.demo.services;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;
import com.example.demo.models.Product;
import com.example.demo.projections.ProductoProjection;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    // Parámetros para la conexión al contenedor de Azure Blob Storage
    @Value("${AZURE_STORAGE_BLOB_CONNECTION_STRING}")
    private String connectionString;

    @Value("${AZURE_STORAGE_BLOB_CONTAINER_NAME}")
    private String containerName;

    @Value("${AZURE_STORAGE_ACCOUNT_NAME}")
    private String accountName;

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,ImageRepository imageRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        List<Product> products = this.productRepository.findAll();

        // Conectar al contenedor de Azure Blob Storage
        BlobContainerClient containerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();

        // Generar los valores SAS para el contenedor
        BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(
                OffsetDateTime.now().plusHours(1), // Tiempo de expiración
                BlobSasPermission.parse("r") // Permiso de lectura
        ).setProtocol(SasProtocol.HTTPS_ONLY);

        // Generar el SAS
        String sasUrl = containerClient.generateSas(sasValues);

        // URL base para acceder al contenedor
        String baseUrl = String.format("https://%s.blob.core.windows.net/%s/", accountName, containerName);

        // Asignar el SAS a las imágenes de los productos
        products.forEach(product -> {
            product.getImages().forEach(image -> {
                // Construir la URL completa para cada imagen con SAS
                String imageUrl = baseUrl + image.getUrl() + "?" + sasUrl;
                image.setUrl(imageUrl);
            });
        });

        return products;
    }


    @Override
    public Optional<Product> getProductById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return this.productRepository.save(product);
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
