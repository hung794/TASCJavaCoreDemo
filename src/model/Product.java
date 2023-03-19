package model;

import util.StringUtil;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private int productId;
    private String productName;
    private String manufacturerName;
    private String productLine;
    private double productPrice;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Product(int productId, String productName, String manufacturerName, String productLine, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.manufacturerName = manufacturerName;
        this.productLine = productLine;
        this.productPrice = productPrice;
    }

    public Product() {
    }

    @Override
    public String toString() {
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return StringUtil.center(String.valueOf(productId), 8) + "|"
                + StringUtil.center(productName, 33) + "|"
                + StringUtil.center(manufacturerName, 39) + "|"
                + StringUtil.center(productLine, 31) + "|"
                + StringUtil.center(currencyFormatter.format(productPrice), 16);
    }
}
