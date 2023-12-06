package com.deleshopping.controller;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learningjavaandroid.delemanventures.R;
import com.deleshopping.entities.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

//    the session happening within the scope of the application
     Context context;
     List<Product> productList;
     RecyclerView rvProducts;
     ImageView imageView;


    public ProductAdapter(Context context, List<Product> productList, RecyclerView rvProducts) {
        this.context = context;
        this.productList = productList;
        this.rvProducts = rvProducts;
    }


//    when you click an item, event handlers
    final View.OnClickListener onClickListener = new ProductOnClickLister();
//    final View.OnClickListener imageOnClickListener = new ImageOnClickLister();


//   this displays the products from the database
        public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView productPrice;
        ImageView imageView;
//        this helps us see the view when it sees the product then the onbind method binds to this component
//     from the single_product this helps to display the items per information
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }


//    normally the recycleview adjusts itself to the size of the screen but the
//    the inflater helps us to keep populating the items to enable it scroll
    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.single_product, parent, false);
        view.setOnClickListener(onClickListener);
//        view.setOnClickListener(imageOnClickListener);
        // every time you have a new product create a new view holder more like refresh
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


//    it binds the content single_product to this page and iterates it
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        Resources resources = context.getResources();
        String product_id = "product_"+ String.valueOf(product.getProduct_id());
        System.out.println(product_id);

        //try {
//            int imageResourceId = resources.getIdentifier("nice", "drawable", context.getPackageName());
            int imageResourceId = resources.getIdentifier(product_id, "drawable", context.getPackageName());
            holder.imageView.setImageResource(imageResourceId);
        //}
//        catch(NullPointerException e) {
//            int imageResourceId = resources.getIdentifier("reindeer", "drawable", context.getPackageName());
//            holder.imageView.setImageResource(imageResourceId);
//        }
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$"+product.getPrice());
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }


//  this helps to handle the onclick it then helps determine what you want to append to
//    to the item
    private class ProductOnClickLister implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            This allows takes us to the product list of each item which contains description and price
            int itemPosition = rvProducts.getChildAdapterPosition(view);
//            the line below shows all the products that are going to the page
            Product item = productList.get(itemPosition);
            view.findViewById(R.id.productImage);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("product", item);
            context.startActivity(intent);
//            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }

//    private class ImageOnClickLister implements View.OnClickListener{
//        @Override
//        public void onClick(View view) {
//            view.findViewById(R.id.productImage);
//            Intent intent = new Intent(context, DetailActivity.class);
//            context.startActivity(intent);
//        }
//    }
}
