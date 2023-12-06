
package com.deleshopping.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Product implements Serializable {
    private int product_id;
    private String name;
    private String description;
    private Date date_added;
    private Double price;
    private Double promo;
    private int category_id;
//    private String image;
    private int sub_category_id;
    private boolean shipping;

    public Product(int product_id, String name, String description, Date date_added,
                   Double price, Double promo, int category_id,
                   int sub_category_id, boolean shipping) {
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.date_added = date_added;
        this.price = price;
        this.promo = promo;
        this.category_id = category_id;
//        this.image = image;
        this.sub_category_id = sub_category_id;
        this.shipping = shipping;
    }




    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPromo() {
        return promo;
    }

    public void setPromo(Double promo) {
        this.promo = promo;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

//    public String getImage() {
//        return image;
//    }

//    public void setImage(String image) {
//        this.image = image;
//    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }


}