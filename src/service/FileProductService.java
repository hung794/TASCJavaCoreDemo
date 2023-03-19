package service;

import model.Product;
import model.ProductSearchBody;
import util.StringUtil;

import java.io.*;
import java.util.StringJoiner;

public class FileProductService {
    RandomAccessFile scanner;
    RandomAccessFile scannerId;
    private final String path = "src/file/product.txt";
    private static Integer id;

    public FileProductService() throws IOException {
        scanner = new RandomAccessFile(path, "rw");
        String idPath = "src/file/id.txt";
        scannerId = new RandomAccessFile(idPath, "rw");
        String s;
        while ((s = scannerId.readLine()) != null) {
            id = Integer.parseInt(s);
        }
    }

    public void getAllProduct() throws IOException {
        String productStr;
        while ((productStr = scanner.readLine()) != null) {
            String[] array = productStr.split("-");
            Product product = new Product();
            product.setProductId(Integer.parseInt(array[0].trim()));
            product.setProductName(array[1].trim());
            product.setManufacturerName(array[2].trim());
            product.setProductLine(array[3].trim());
            product.setProductPrice(Double.parseDouble(array[4].trim()));
            System.out.println(product);
        }
        scanner.seek(0);
    }

    public boolean addProduct(String productStr) {
        try {
            scanner.seek(scanner.length());
            scanner.writeBytes(id + " - " + productStr + "\n");
            scanner.seek(0);
            id += 1;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean updateProduct(String id, String productStr) {
        try {
            String product;
            StringBuilder stringBuilder = new StringBuilder();
            while ((product = scanner.readLine()) != null) {
                String productId = StringUtil.getProductId(product);
                if (productId.equals(id)) {
                    String[] oldProductPart = product.split("-");
                    String[] newProductPart = productStr.split("-");
                    StringJoiner stringJoiner = new StringJoiner(" - ");
                    for (int i = 0; i < newProductPart.length; i++) {
                        if (newProductPart[i].trim().equals("null")) {
                            stringJoiner.add(oldProductPart[i].trim());
                        } else stringJoiner.add(newProductPart[i].trim());
                    }
                    stringBuilder.append(stringJoiner).append("\n");
                } else {
                    stringBuilder.append(product).append("\n");
                }
            }
            deleteFile();
            scanner.seek(0);
            scanner.writeBytes(stringBuilder.toString());
            scanner.seek(0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean deleteProduct(String id) {
        try {
            String product;
            StringBuilder stringBuilder = new StringBuilder();
            while ((product = scanner.readLine()) != null) {
                String productId = StringUtil.getProductId(product);
                if (!productId.equals(id)) {
                    stringBuilder.append(product).append("\n");
                }
            }
            deleteFile();
            scanner.seek(0);
            scanner.writeBytes(stringBuilder.toString());
            scanner.seek(0);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void searchProduct(ProductSearchBody searchBody) throws IOException {
        String productStr;
        int count = 0;
        while ((productStr = scanner.readLine()) != null) {
            String[] productPart = productStr.split("-");
            if (!searchBody.getName().isEmpty()) {
                if (!productPart[1].contains(searchBody.getName())) {
                    continue;
                }
            }
            if (!searchBody.getManufacturer().isEmpty()) {
                if (!productPart[2].contains(searchBody.getManufacturer())) {
                    continue;
                }
            }
            if (!searchBody.getLine().isEmpty()) {
                if (!productPart[3].contains(searchBody.getLine())) {
                    continue;
                }
            }
            if (!searchBody.getStartPrice().isEmpty()) {
                if (Double.parseDouble(productPart[4]) < Double.parseDouble(searchBody.getStartPrice())) {
                    continue;
                }
            }
            if (!searchBody.getEndPrice().isEmpty()) {
                if (Double.parseDouble(productPart[4]) > Double.parseDouble(searchBody.getEndPrice())) {
                    continue;
                }
            }
            Product product = new Product();
            product.setProductId(Integer.parseInt(productPart[0].trim()));
            product.setProductName(productPart[1].trim());
            product.setManufacturerName(productPart[2].trim());
            product.setProductLine(productPart[3].trim());
            product.setProductPrice(Double.parseDouble(productPart[4].trim()));
            System.out.println(product);
            count += 1;
        }
        if (count == 0) {
            System.out.println(StringUtil.center("Not found product!", 132));
        }
    }

    public String getProductDetail(String id) {
        String result = null;
        String product;
        try {
            while ((product = scanner.readLine()) != null) {
                String productId = StringUtil.getProductId(product);
                if (productId.equals(id)) {
                    result = product;
                    break;
                }
            }
            scanner.seek(0);
        } catch (IOException e) {
            return null;
        }
        return result;
    }

    public void closeFileReader() {
        try {
            scanner.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFile() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(path);
        writer.print("");
        writer.close();
    }

    public void saveId() {
        try {
            scannerId.seek(0);
            scannerId.writeBytes(String.valueOf(id));
            scannerId.seek(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}