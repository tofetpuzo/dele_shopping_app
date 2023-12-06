package com.deleshopping.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.learningjavaandroid.delemanventures.R;
import com.deleshopping.entities.Product;
import com.deleshopping.model.Database;


import java.util.Map;

public class ShowCartAdapter extends RecyclerView.Adapter<ShowCartAdapter.ViewHolder> {

    //    the session happening within the scope of the application
    Context context;
    Map<Integer, Integer> cartProductList;
    RecyclerView rvProducts;
    SharedPreferences sharedCartPreferences ;
    SharedPreferences.Editor sharedCartEditor;
    TextView txtViewTotalPrice;



    public ShowCartAdapter(Context context, Map<Integer, Integer> cartProductList, RecyclerView rvProducts) {
        this.context = context;
        this.cartProductList = cartProductList;
        this.rvProducts = rvProducts;
//        this.txtViewTotalPrice  = findViewById(R.id.txtViewtotalPrice);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProductImage;
        TextView txtViewProductDescription, textViewProductName,
                  txtQuantityInput, txtViewProductPrice;
        Button btn_IncreaseCartItemQuantity, btn_ReduceCartItemQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.txtViewProductName);
            txtViewProductDescription = itemView.findViewById(R.id.txtViewProductDescription);
            txtViewProductPrice = itemView.findViewById(R.id.txtViewProductPrice);
            imageViewProductImage = itemView.findViewById(R.id.imageViewProductImage);
            btn_IncreaseCartItemQuantity = itemView.findViewById(R.id.btn_IncreaseCartItemQuantity);
            btn_ReduceCartItemQuantity = itemView.findViewById(R.id.btn_ReduceCartItemQuantity);
            txtQuantityInput = itemView.findViewById(R.id.txtQuantityInput);
        }
        public static double totalCart(Map<Integer, Integer> cart, Context context){
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

    @NonNull
    @Override
    public ShowCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_cart_productlist, parent, false);

        //view.setOnClickListener(onClickListener);
//        view.setOnClickListener(imageOnClickListener);

        // every time you have a new product create a new view holder more like refresh
        ShowCartAdapter.ViewHolder viewHolder = new ShowCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCartAdapter.ViewHolder holder, int position) {
        int i = 0;
        Database database = new Database(context, "DeleMan.db", null, 1);
        for (Map.Entry<Integer, Integer> entry : cartProductList.entrySet()) {
            if(position == i){
                Integer key = entry.getKey();
                Integer value = entry.getValue();
                Product product = database.getProduct(key);
                System.out.println(value);
                Resources resources = context.getResources();
                String product_id = "product_"+ key;
                int imageResourceId = resources.getIdentifier(product_id, "drawable", context.getPackageName());
                holder.imageViewProductImage.setImageResource(imageResourceId);
                holder.txtQuantityInput.setText(String.valueOf(value));
                holder.textViewProductName.setText(product.getName());
                holder.txtViewProductDescription.setText(product.getDescription());
                holder.txtViewProductPrice.setText("$" + product.getPrice());

                System.out.println(product_id);

//                The implementation of
                holder.btn_IncreaseCartItemQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         updateCartForButton(2, product, holder);
//
                    }
                });
                holder.btn_ReduceCartItemQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view){

                        updateCartForButton(1, product, holder);
                        //cartProductList.remove(holder.getAdapterPosition());
                        //rvProducts.removeViewAt(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), cartProductList.size());
                    }
                });
                break;
            }
            i++;
        }
//        txtViewTotalPrice.setText(("$" + CartAdapter.totalCart(cartProductList, context)));
    }
    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public void updateCartForButton(int switchCaseNumber, Product product, @NonNull ShowCartAdapter.ViewHolder holder){
        sharedCartPreferences = context.getSharedPreferences("dele_cart", Context.MODE_PRIVATE);
        sharedCartEditor = sharedCartPreferences.edit();
        switch (switchCaseNumber){
            case 1:
                cartProductList = CartAdapter.deleteFromCart(cartProductList, product);
                break;
            case 2:
                cartProductList = CartAdapter.addToCart(cartProductList, product);
                break;
        }
//        productValue -> quantity of the items in that particular position {product:2,  quantity:2}
//        we used the product id to get the item from our cartlist
        Integer productValue = cartProductList.get(product.getProduct_id());

        if(productValue != null){
            Integer value = productValue.intValue();
            holder.txtQuantityInput.setText(String.valueOf(value));
            Gson gsons = new Gson();
            String converted_cart_passed_to_next_activity = gsons.toJson(cartProductList);
            //ShowCartAdapter.ViewHolder.totalCart(cartProductList, context);
            ShowCartActivity.txtViewTotalPrice.setText("$" + totalCart(cartProductList, context));
            sharedCartEditor.putString("cart", converted_cart_passed_to_next_activity);
            sharedCartEditor.commit();
        }
    }

    protected double totalCart(Map<Integer, Integer> cart, Context context){
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
