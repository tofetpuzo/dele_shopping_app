package com.deleshopping.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.learningjavaandroid.delemanventures.R;

import java.lang.reflect.Type;
import java.util.Map;

public class ShowCartActivity extends AppCompatActivity {

    Map<Integer, Integer> cartProductList;
    RecyclerView rvShowCartProducts;
    ShowCartAdapter showCartAdapter;
    CartAdapter cartAdapter;
    RecyclerView.LayoutManager layoutManager;
    SharedPreferences sharedCartPreferences ;
    SharedPreferences.Editor sharedCartEditor;
    static TextView txtViewTotalPrice;

//    ImageView imageViewProductImage;
//    TextView txtViewProductDescription, textViewProductName,
//            txtQuantityInput, txtViewProductPrice;

//    Button btn_IncreaseCartItemQuantity, btn_ReduceCartItemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        txtViewTotalPrice = findViewById(R.id.txtViewtotalPrice);
        rvShowCartProducts =  findViewById(R.id.recyclerViewShowCart);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // showing the back button in action bar
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

//      this helps us to carry the information of the cart where-ever move through the Activity class
        sharedCartPreferences = getSharedPreferences("dele_cart", Context.MODE_PRIVATE);
        sharedCartEditor = sharedCartPreferences.edit();

        String sharedCart = sharedCartPreferences.getString("cart", null);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer, Integer>>() {
        }.getType();
        cartProductList = gson.fromJson(sharedCart, type);



//        create a recycler view -> using layout manager
        layoutManager = new LinearLayoutManager(this);

        rvShowCartProducts.setLayoutManager(layoutManager);

        showCartAdapter = new ShowCartAdapter(this, cartProductList, rvShowCartProducts);

        cartTotalCartRefreshButton();
//        txtViewTotalPrice.setText("$" + showCartAdapter.totalCart(cartProductList, getApplicationContext()));
        rvShowCartProducts.setAdapter(showCartAdapter);

    }

    public void cartTotalCartRefreshButton(){
        txtViewTotalPrice.setText("$" + ShowCartAdapter.ViewHolder.totalCart(cartProductList, getApplicationContext()));
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
//We will go for the single_cart_product-list page then the Show cart Adapter will implement

// the recycler view of the cart show-activity