package com.coviam.YoStore.Product.entity;

import java.util.List;

public class Products {
    private String SKU;
    private double mrp;
    private String brand;
    private String imgurl;
    private List<ProductMerchants> merchants;
    private int quantity;
    private String category;
    private ProductDetails productDetails;
    private String productName;

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMerchants(List<ProductMerchants> merchants) {
        this.merchants = merchants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}