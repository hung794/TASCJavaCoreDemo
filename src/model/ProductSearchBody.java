package model;

public class ProductSearchBody {
    public String name;
    private String manufacturer;
    private String line;
    private String startPrice;
    private String endPrice;

    public ProductSearchBody(String name, String manufacturer, String line, String startPrice, String endPrice) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.line = line;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }
}
