package com.example.springmvc9.controllers;


import com.example.springmvc9.models.Product;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class RestApiController {

    @GetMapping("/")
    private String getProducts() {
        return Product.getJsonSerialized();
    }

    @GetMapping("/{id}")
    private ResponseEntity<String> getProduct(@PathVariable("id") String id) {
        Product product = Product.getProduct(id);

        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Product.getJSONProductSerialized(id), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<String> addProduct(@RequestBody JSONObject jsonProduct) {

        String name = (String) jsonProduct.get("name");
        String bought = (String) jsonProduct.get("bought");

        String id = Product.addProduct(name, bought);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping
    private ResponseEntity<String> updateProduct(@RequestBody JSONObject jsonProduct) {

        System.out.println(jsonProduct);

        String id = (String) jsonProduct.get("id");
        String name = (String) jsonProduct.get("name");
        String bought = (String) jsonProduct.get("bought");

        Product.removeProduct(id);
        Product.addProduct(name, bought);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteProduct(@PathVariable("id") String id) {
        Product.removeProduct(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
