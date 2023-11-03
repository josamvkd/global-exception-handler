package com.josamtechie.api.controller;

import com.josamtechie.api.custom.exception.BusinessException;
import com.josamtechie.api.custom.exception.ControllerException;
import com.josamtechie.api.model.Product;
import com.josamtechie.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product){
    	try {
    		Product item = service.add(product);
            return new ResponseEntity<Product>(item, HttpStatus.CREATED);
    	}catch(BusinessException e) {
    		ControllerException ce =new ControllerException(e.getErrorCode(),e.getErrorMessage());
    		return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
    	}catch(Exception e) {
    		ControllerException ce =new ControllerException("611","Something went wron in controller layer");
    	    return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
    	}
    	
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(){
    	List<Product> productList = service.getProducts();
        return new ResponseEntity<List<Product>>(productList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
    	try {
    	Product product = service.getProductById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    	}catch(BusinessException e) {
    		ControllerException ce =new ControllerException(e.getErrorCode(),e.getErrorMessage());
    		return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
    	}catch(Exception e) {
    		ControllerException ce =new ControllerException("612","Something went wron in controller layer");
    	    return new ResponseEntity<ControllerException>(ce,HttpStatus.BAD_REQUEST);
    	}
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product productRequest){
        Product pdt = service.updateProduct(id,productRequest);
    	return new ResponseEntity<Product>(pdt, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public  ResponseEntity<Product> updateProductFields(@PathVariable int id, @RequestBody Map<String, Object> fields){
        Product item1 = service.updateProductFields(id, fields);
    	return new ResponseEntity<Product>(item1, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable int id){
    	String message = service.deleteProductById(id);
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

}
