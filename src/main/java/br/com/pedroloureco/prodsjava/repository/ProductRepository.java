package br.com.pedroloureco.prodsjava.repository;

import br.com.pedroloureco.prodsjava.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
