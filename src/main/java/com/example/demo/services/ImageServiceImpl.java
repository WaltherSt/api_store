package com.example.demo.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.example.demo.models.Image;
import com.example.demo.models.Product;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.services.interfaces.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ImageServiceImpl implements ImageService {

    @Value("${AZURE_STORAGE_BLOB_CONNECTION_STRING}")
    private String connectionString;

    @Value("${AZURE_STORAGE_BLOB_CONTAINER_NAME}")
    private String containerName;

    private final ImageRepository imageRepository;

    private BlobContainerClient containerClient;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Inicializa el BlobContainerClient después de que las propiedades hayan sido inyectadas
    private void initContainerClient() {
        if (containerClient == null) {
            containerClient = new BlobContainerClientBuilder()
                    .connectionString(connectionString)
                    .containerName(containerName)
                    .buildClient();
        }
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteImage(Long id) {
        // Implementar lógica para eliminar imagen si es necesario
    }

    @Override
    public String saveImage(MultipartFile file, Long productId) {
        try {
            // Inicializar el cliente de contenedor
            initContainerClient();

            // Crear contenedor si no existe
            containerClient.createIfNotExists();

            // Obtener la extensión del archivo (por ejemplo, .jpg, .png, etc.)
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            // Validar la extensión del archivo (asegurándonos de que sea una imagen)
            assert extension != null;
            if (!isValidImageExtension(extension)) {
                return "El tipo de archivo no es válido. Solo se permiten imágenes.";
            }

            // Generar un nombre único para el archivo, manteniendo la extensión original
            String generatedFileName = UUID.randomUUID().toString() + "." + extension;

            BlobClient blobClient = containerClient.getBlobClient(generatedFileName);

            // Subir el archivo
            blobClient.upload(file.getInputStream(), file.getSize(), true); // true para sobrescribir si existe

            // Asociar la imagen a un producto
            Image image = new Image();
            image.setUrl(generatedFileName);  // Guardamos solo el nombre del archivo generado
            Product product = new Product();
            product.setId(productId);
            image.setProduct(product);

            // Guardar el nombre de la imagen con su extension  en la base de datos
            imageRepository.save(image);

            return "Imagen guardada con éxito";

        } catch (IOException e) {
            return "Error al guardar la imagen: " + e.getMessage();
        }
    }

    // Método para validar las extensiones de archivo de imagen
    private boolean isValidImageExtension(String extension) {
        List<String> validExtensions = List.of("jpg", "jpeg", "png", "gif", "bmp", "tiff");
        return validExtensions.contains(extension.toLowerCase());
    }
}
