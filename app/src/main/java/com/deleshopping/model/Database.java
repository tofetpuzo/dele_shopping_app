package com.deleshopping.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.deleshopping.entities.Product;
import com.deleshopping.entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    List<Product> productList = new ArrayList<>();
    SQLiteDatabase database = getReadableDatabase();

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//         String stmt = "create table Users(username text primary key, password text, name text)";
//         sqLiteDatabase.execSQL(stmt);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean register(User user) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", user.getUsername());
            contentValues.put("password", user.getPassword());
            contentValues.put("name", user.getName());
            SQLiteDatabase sb = getWritableDatabase();
            sb.insert("users", null, contentValues);
            sb.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(User user) {
        database = getReadableDatabase();
        String[] args = new String[1];
        args[0] = user.getUsername();


        Cursor cursor = database.rawQuery("SELECT * from Users where username= ?", args);
        if (cursor.moveToFirst()) {
            String password = cursor.getString(2);
            if (user.getPassword().contentEquals(password)) ;
            return true;
        }
        cursor.close();
        database.close();
        return false;

    }


    public List<Product> getAllProducts() {
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from Product ", null);
        while (cursor.moveToNext()) {
            int product_id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            Date date_added = new Date(cursor.getLong(3) * 1000);
            Double price = cursor.getDouble(4);
            Double promo = cursor.getDouble(5);
            int category_id = cursor.getInt(6);
//            String image = cursor.getString(7);
            int sub_category_id = cursor.getInt(8);
            boolean shipping = (cursor.getInt(9) == 1);
            System.out.println(cursor.getInt(0));
            System.out.println(cursor.getString(2));
            Product product = new Product(product_id, name, description,
                    date_added, price, promo, category_id, sub_category_id, shipping);
            productList.add(product);
        }
        cursor.close();
        database.close();

        return productList;
    }

    public Product getProduct(int product_id) {
        List<Product> products = getAllProducts();
//        int product_id= cursor.getInt(0);
//        String description = cursor.getString(2);
//        Double price = cursor.getDouble(4);
//        String name = cursor.getString(1);
        for (Product product: products) {
            if(product.getProduct_id() == product_id){
                return product;
            }
        }
        return null;
    }
}