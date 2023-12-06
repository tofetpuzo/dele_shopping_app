package com.deleshopping.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deleshopping.entities.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learningjavaandroid.delemanventures.R;

import java.lang.reflect.Type;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    Product product;
    TextView productName, productDescription, productPrice;
    ImageView imageView;
    Context context;
    Button button_addToCart, remove_ItemInCart, btn_showCart;
    Map<Integer, Integer> cart;
    SharedPreferences sharedCartPreferences ;
    SharedPreferences.Editor sharedCartEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        imageView = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        button_addToCart =  findViewById(R.id.btn_addItem);
        remove_ItemInCart = findViewById(R.id.btn_removeItem);
        btn_showCart = findViewById(R.id.btn_showCart);
        Intent intent = getIntent();
        context = getApplicationContext();
        product = (Product) intent.getSerializableExtra("product");


        String product_id = "product_"+ String.valueOf(product.getProduct_id());
        Resources resources = context.getResources();

        int imageResourceId = resources.getIdentifier(product_id, "drawable", context.getPackageName());
        imageView.setImageResource(imageResourceId);

        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productPrice.setText(String.format("$%s", String.valueOf(product.getPrice())));

//        this helps us to carry the information of the cart where-ever move through the Activity class
        sharedCartPreferences = getSharedPreferences("dele_cart", Context.MODE_PRIVATE);
        sharedCartEditor  = sharedCartPreferences.edit();



        button_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sharedCart = sharedCartPreferences.getString("cart", "");
                Gson gson = new Gson();
                Type type = new TypeToken<Map<Integer, Integer>>(){}.getType();
//                Map<Product, Integer> type =  new HashMap<>();

                if (sharedCart != null){
                    cart = gson.fromJson(sharedCart, type);
                    cart = CartAdapter.addToCart(cart, product);
                    for (Map.Entry<Integer, Integer> item : cart.entrySet()){
                        System.out.println(cart);
                        System.out.println("product" + ":" + item.getKey()  + ", " + " quantity" + ":" + item.getValue());
                    }
                }

                else{
                    System.out.println(" just print something ");
                }
//                this is used for saving to the cart to allow it forwarded.
                Gson gsons = new Gson();
                String converted_cart_passed_to_next_activity = gsons.toJson(cart);
                sharedCartEditor.putString("cart", converted_cart_passed_to_next_activity);

//                sharedCartEditor.apply();
                sharedCartEditor.commit();
            }
        });

        remove_ItemInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sharedCart = sharedCartPreferences.getString("cart", null);
                Gson gson = new Gson();
                Type type = new TypeToken<Map<Integer, Integer>>(){}.getType();
                cart = gson.fromJson(sharedCart, type);
                cart = CartAdapter.deleteFromCart(cart, product);


                String converted_cart_through_activity = gson.toJson(cart);
                sharedCartEditor.putString("cart", converted_cart_through_activity);
                sharedCartEditor.apply();

            }
        });
        btn_showCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(DetailActivity.this, ShowCartActivity.class);
                Intent intent = new Intent(DetailActivity.this, ShowCartActivity.class);
                startActivity(intent);
            }

        });

    }

    //  This is the method in the action bar that needs to over-ride for action bar to show the arrow back
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}