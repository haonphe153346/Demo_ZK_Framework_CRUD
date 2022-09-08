package model;

public class Products {

    private int product_id;
    private String product_name;
    private float product_price;
    private int product_quanlity;
    private String product_image;
    private int category_id;

    public Products() {
    }

    public Products(int product_id, String product_name, float product_price, int product_quanlity, String product_image, int category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_quanlity = product_quanlity;
        this.product_image = product_image;
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public float getProduct_price() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }

    public int getProduct_quanlity() {
        return product_quanlity;
    }

    public void setProduct_quanlity(int product_quanlity) {
        this.product_quanlity = product_quanlity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
