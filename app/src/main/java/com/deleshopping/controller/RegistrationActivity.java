package com.deleshopping.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.learningjavaandroid.delemanventures.R;
import com.deleshopping.entities.User;
import com.deleshopping.model.Database;

public class RegistrationActivity extends AppCompatActivity {
    TextView reg_text_view;
    EditText registration_name , registration_email;
    EditText password, confirm_password;
    Button button_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        registration_name = findViewById(R.id.id_registration_name);
        registration_email = findViewById(R.id.id_registration_email);
        password = findViewById(R.id.id_registration_password);
        confirm_password = findViewById(R.id.id_registration_confirm_password);
        button_signup = findViewById(R.id.btn_signup);

        reg_text_view =  findViewById(R.id.lbl_login_reg);
        reg_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = registration_email.getText().toString();
                String pword = password.getText().toString();
                String name = registration_name.getText().toString();
                String con_password = confirm_password.getText().toString();
                Database database = new Database(getApplicationContext(), "DeleMan.db", null, 1);
                if(uname.length() == 0 || pword.length() == 0  || con_password.length() == 0){
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();

                }
                else if (pword.contentEquals(con_password)){

                    User user = new User(uname, pword, name);
                    if(database.register(user)){
                        Toast.makeText(getApplicationContext(), "account created", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error creating account see Admin", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}