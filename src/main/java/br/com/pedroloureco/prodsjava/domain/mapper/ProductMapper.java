package br.com.pedroloureco.prodsjava.domain.mapper;

import br.com.pedroloureco.prodsjava.domain.product.Product;
import br.com.pedroloureco.prodsjava.domain.product.ProductInputDTO;
import br.com.pedroloureco.prodsjava.domain.product.ProductOutputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductInputDTO dto);
    ProductOutputDTO toDTO(Product product);
}
