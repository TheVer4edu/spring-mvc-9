package com.example.springmvc9.models;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class Product {

    private final String id;
    private final String name;
    private final boolean bought;

    private static JSONArray products;
    private static final String path = "/home/thever4/Загрузки/spring-mvc-9/src/main/resources/products.json";

    static {
        try {
            products = (JSONArray) new JSONParser().parse(new FileReader(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product(String name, Boolean bought) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.bought = bought;
    }
    private Product(String id, String name, Boolean bought) {
        this.id = id;
        this.name = name;
        this.bought = bought;
    }

    public static Product getProduct(String id) {
        JSONObject jsonProduct = getJSONProduct(id);

        if (jsonProduct == null) {
            return null;
        }

        return new Product(
            (String) jsonProduct.get("id"),
            (String) jsonProduct.get("name"),
            (Boolean) jsonProduct.get("bought")
        );
    }

    public static String getJsonSerialized() {
        return products.toJSONString();
    }

    public static JSONObject getJSONProduct(String id) {
        for (Object product : products) {
            JSONObject jsonProduct = (JSONObject) product;

            if (Objects.equals(jsonProduct.get("id"), id)) {
                return jsonProduct;
            }
        }
        return null;
    }

    public static String getJSONProductSerialized(String id) {
        return getJSONProduct(id).toJSONString();
    }

    public static void removeProduct(String id) {
        JSONObject jsonProduct = Product.getJSONProduct(id);
        products.remove(jsonProduct);
        saveProducts();
    }

    public static String addProduct(String name, String bought) {
        Product product = new Product(name, Boolean.valueOf(bought));
        JSONObject jsonProduct = new JSONObject();
        jsonProduct.put("id",product.id);
        jsonProduct.put("name",product.name);
        jsonProduct.put("bought",product.bought);
        products.add(jsonProduct);
        saveProducts();
        return product.id;
    }

    private static void saveProducts() {
        try{
            FileWriter file = new FileWriter(path);
            file.write(products.toJSONString());
            file.flush();
            file.close();
            products = (JSONArray) new JSONParser().parse(new FileReader(path));
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


}
