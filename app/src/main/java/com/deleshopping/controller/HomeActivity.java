package com.deleshopping.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.learningjavaandroid.delemanventures.R;
import com.deleshopping.entities.Product;
import com.deleshopping.model.Database;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Database database;
    RecyclerView rvProducts;
    ProductAdapter productAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList = new ArrayList<>();


//    product adapter that will be used to update product

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hello-beginning");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        database = new Database(getApplicationContext(), "DeleMan.db", null, 1);
        productList = database.getAllProducts();
        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setHasFixedSize(true);System.out.println("hello-middle");
        layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productList, rvProducts);
        rvProducts.setAdapter(productAdapter);
        System.out.println("hello-end");

        // with method findViewById()



        // Apply OnClickListener  to imageView to
        // switch from one activity to another

    }

}