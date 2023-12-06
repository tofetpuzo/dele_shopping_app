package com.deleshopping.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.deleshopping.entities.User;
import com.google.gson.Gson;
import com.learningjavaandroid.delemanventures.R;
import com.deleshopping.model.Database;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
//    button
    TextView info;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        username = findViewById(R.id.id_email);
        password = findViewById(R.id.id_password);
        btn_login = findViewById(R.id.btn_sigin);
        info = findViewById(R.id.lbl_login_reg);


        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // explicit intent is moving from one point to another
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pword = password.getText().toString();
                Database database = new Database(getApplicationContext(), "DeleMan.db", null, 1);
                if (uname.length() == 0 || pword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(uname, pword);
                    if (database.login(user)) {
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                       // like cookies
                        SharedPreferences sharedPreferences = getSharedPreferences("dele", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user.getUsername());
                        editor.apply();

                        // For cart so we can retrieve it in all activities
                        SharedPreferences sharedCartPreferences = getSharedPreferences("dele_cart", Context.MODE_PRIVATE);
                        SharedPreferences.Editor sharedCartEditor  = sharedCartPreferences.edit();

                       // we had to use json to because it was not possible to serialize the object cart
                        Map<Integer, Integer> cart = new HashMap<>();
                        Gson gson = new Gson();
                        String gs_cart = gson.toJson(cart);

                        sharedCartEditor.putString("cart", gs_cart);
                        sharedCartEditor.apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}