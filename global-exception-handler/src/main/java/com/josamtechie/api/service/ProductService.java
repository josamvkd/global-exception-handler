package com.josamtechie.api.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.josamtechie.api.custom.exception.BusinessException;
import com.josamtechie.api.model.Product;
import com.josamtechie.api.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

	public Product add(Product product) {
		if (product.getName().isEmpty() || product.getName().length() == 0) {
			throw new BusinessException("601", "Please send a proper name");
		}
		try {
			Product item = repository.save(product);
			return item;
		} catch (IllegalArgumentException e) {
			throw new BusinessException("602", "given employee is null" + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("603", "Something went wrong in Service layer" + e.getMessage());
		}
	}

    public List<Product> getProducts() {
    	List<Product> productList = repository.findAll();
    	if(productList.isEmpty()) {
			throw new BusinessException("604","Product List is empty");
		}
    	try {
            return productList;
    	}catch (Exception e) {
    		throw new BusinessException("605","Something went wrong in Service layer while fetching the list"+e.getMessage());
		}
    }

    public Product getProductById(int id) {
    	try {
    		Product item1 = repository.findById(id).get();
            return item1;
    	}catch (IllegalArgumentException e) {
			throw new BusinessException("606", "given employee id is null" + e.getMessage());
		}catch (NoSuchElementException e) {
			throw new BusinessException("607", "given employee id does not exist in db" + e.getMessage());

		}
    	
    }

    public String deleteProductById(int id) {
        repository.deleteById(id);
        return "Product "+id+" Deleted successfully..";
    }

    public Product updateProduct(int id, Product productRequest) {
        Product existingProduct = repository.findById(id).get();
        existingProduct.setName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setProductType(productRequest.getProductType());
        return repository.save(existingProduct);
    }

    public Product updateProductFields(int id, Map<String, Object> fields) {
        Optional<Product> existingProduct = repository.findById(id);
        if(existingProduct.isPresent()){
            fields.forEach((key,value) ->{
                Field field = ReflectionUtils.findField(Product.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingProduct.get(),value);
            });
            return repository.save(existingProduct.get());
        }
        return null;
    }
}
