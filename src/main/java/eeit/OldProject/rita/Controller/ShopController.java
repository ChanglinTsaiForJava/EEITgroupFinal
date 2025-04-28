package eeit.OldProject.rita.Controller;

import eeit.OldProject.rita.Entity.Product;
import eeit.OldProject.rita.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shop") // 所有路徑都以 /api/shop 開頭
@RequiredArgsConstructor
public class ShopController {

    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    public List<Product> findAllProducts(Product product) {

        return productRepository.findAll();
    }
    public void updateProduct(Product product){
        productRepository.save(product);
    }
    public void deleteProduct(Product product){
        productRepository.delete(product);
    }



}
