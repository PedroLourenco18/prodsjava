package br.com.pedroloureco.prodsjava.controller;

import br.com.pedroloureco.prodsjava.domain.product.ProductInputDTO;
import br.com.pedroloureco.prodsjava.domain.product.ProductOutputDTO;
import br.com.pedroloureco.prodsjava.domain.reponse.BasicResponse;
import br.com.pedroloureco.prodsjava.domain.reponse.DataResponse;
import br.com.pedroloureco.prodsjava.service.ProductService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataResponse<ProductOutputDTO>> save(@RequestBody @Valid ProductInputDTO product){
        ProductOutputDTO savedProduct = service.save(product);

        DataResponse<ProductOutputDTO> response = new DataResponse<>(
                false,
                "Product successfully created",
                savedProduct
        );

        URI location = URI.create("http://localhost:8080/product/" + savedProduct.getId());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<ProductOutputDTO>> findById(@PathVariable UUID id){
        ProductOutputDTO product = service.findById(id);

        DataResponse<ProductOutputDTO> response = new DataResponse<>(
                false,
                "Product found",
                product
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<ProductOutputDTO>>> search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description
    ){
        List<ProductOutputDTO> products = service.search(name, description);

        String message = products.size() + " products founds";

        DataResponse<List<ProductOutputDTO>> response = new DataResponse<>(
                false,
                message,
                products
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductInputDTO product){
        service.update(id, product);

        BasicResponse response = new BasicResponse(
                false,
                "Product updated"
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BasicResponse> delete(@PathVariable UUID id){
        service.delete(id);

        BasicResponse response = new BasicResponse(
                false,
                "Product deleted"
        );

        return ResponseEntity.ok(response);
    }
}
