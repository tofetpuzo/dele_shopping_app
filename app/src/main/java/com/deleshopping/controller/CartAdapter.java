package com.deleshopping.controller;

import android.content.Context;

import com.deleshopping.entities.Product;
import com.deleshopping.model.Database;

import java.io.Serializable;
import java.util.Map;

public class CartAdapter implements Serializable {


//    public static Map<Product, Integer> cart = null;

    public static boolean checkItemExist(Map<Integer, Integer> cart, Product product){
        return cart.containsKey(product.getProduct_id());
    }

    public static Map<Integer, Integer>  addToCart(Map<Integer, Integer> cart, Product product) {

        if(!checkItemExist(cart, product)){
            cart.put(product.getProduct_id(), 1);
        }else{
            try {
                int currentValueInCart = Integer.parseInt(String.valueOf(cart.get(product.getProduct_id())));
                currentValueInCart += 1;
                System.out.println("before "+ currentValueInCart);
                cart.put(product.getProduct_id(), currentValueInCart);
                System.out.println("after "+ currentValueInCart);
            }catch (NullPointerException | NumberFormatException exception){
                exception.getMessage();
            }
        }
        for (Map.Entry<Integer , Integer> each_item_in_cart : cart.entrySet()){
            System.out.println(each_item_in_cart.getKey() + " ,"  + each_item_in_cart.getValue());
        }

        return cart;
    }

    public static Map<Integer, Integer>  deleteFromCart(Map<Integer, Integer> cart, Product product) {
        if (checkItemExist(cart, product)) {
            try {
                int currentValueInCart = Integer.parseInt(String.valueOf(cart.get(product.getProduct_id())));
                currentValueInCart -= 1;
                cart.put(product.getProduct_id(), currentValueInCart);
                if(currentValueInCart < 1){
                    cart.remove(product.getProduct_id());
                }//else{
//                    cart.put(product.getProduct_id(), currentValueInCart);
                //w}
            }catch (NullPointerException | NumberFormatException exception){
                exception.getMessage();
            }
        }
        //for (Map.Entry<Integer , Integer> each_item_in_cart : cart.entrySet()){
            //System.out.println(each_item_in_cart.getKey() + " ,"  + each_item_in_cart.getValue());
       // }
        return cart;
    }

    protected static double totalCart(Map<Integer, Integer> cart, Context context){
        double totalPrice = 0;
        for (Map.Entry<Integer, Integer> item : cart.entrySet()){
            int productId = item.getKey();

            Database database = new Database(context, "DeleMan.db", null, 1);
            Product product = database.getProduct(productId);
            totalPrice += product.getPrice() * item.getValue();

            // edit the single_product.xml to show the promo value
            // totalPrice += (item.getKey().getPromo() * item.getKey().getPrice()) * item.getValue();
        }
        return totalPrice;
    }
}
