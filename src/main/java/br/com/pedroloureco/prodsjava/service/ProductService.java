package br.com.pedroloureco.prodsjava.service;

import br.com.pedroloureco.prodsjava.domain.mapper.ProductMapper;
import br.com.pedroloureco.prodsjava.domain.product.Product;
import br.com.pedroloureco.prodsjava.domain.product.ProductInputDTO;
import br.com.pedroloureco.prodsjava.domain.product.ProductOutputDTO;
import br.com.pedroloureco.prodsjava.exception.ElementNotFoundException;
import br.com.pedroloureco.prodsjava.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductOutputDTO save(ProductInputDTO newProductDTO){
        Product newProduct = mapper.toEntity(newProductDTO);
        Product savedProduct = repository.save(newProduct);
        return mapper.toDTO(savedProduct);
    }

    public ProductOutputDTO findById(UUID id){
        Product product = repository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Product not found"));
        return mapper.toDTO(product);
    }

    public List<ProductOutputDTO> search(String name, String description){
        Product exampleProduct = new Product();
        exampleProduct.setName(name);
        exampleProduct.setDescription(description);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Product> example = Example.of(exampleProduct, matcher);

        return repository.findAll(example).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public void update(UUID id, ProductInputDTO updatedProductDTO){
        if(!repository.existsById(id)){
            throw new ElementNotFoundException("Can not updated an unsaved product");
        }

        Product updatedProduct = mapper.toEntity(updatedProductDTO);
        updatedProduct.setId(id);

        repository.save(updatedProduct);
    }

    public void delete(UUID id){
        if(!repository.existsById(id)){
            throw new ElementNotFoundException("Can not delete an unsaved product");
        }

        repository.deleteById(id);
    }
}
