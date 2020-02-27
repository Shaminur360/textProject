package com.example.book_exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {
    private EditText nameEt, emailEt, passwordEt, c_passwodEt;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "https://shaminur360.000webhostapp.com/android_register_login/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);

        loading = findViewById(R.id.loading);
        nameEt = findViewById(R.id.name);
        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        c_passwodEt = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regist();

            }
        });
    }

    private void Regist() {


        final String name = this.nameEt.getText().toString().trim();
        final String email = this.emailEt.getText().toString().trim();
        final String password = this.passwordEt.getText().toString().trim();
        final String c_passwod = this.c_passwodEt.getText().toString().trim();

        if (name.isEmpty()) {
            nameEt.requestFocus();
            nameEt.setError("Enter Your Name");
        }else{
        if (email.isEmpty()) {
            emailEt.requestFocus();
            emailEt.setError("Enter Your Email");
        }else{
        if (password.isEmpty()) {
            passwordEt.requestFocus();
            passwordEt.setError("Enter Password");
        }else {
        if (passwordEt.getText().toString().length()<6) {
            passwordEt.requestFocus();
            passwordEt.setError("Password minimum 6 charecters");
        }else {
        if (!password.equals(c_passwod)){
            c_passwodEt.setError("PassWord not match");
        }else {
            loading.setVisibility(View.VISIBLE);
            btn_regist.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(Register_Activity.this, "register success!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
                                startActivity(intent);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Register_Activity.this, "register error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register_Activity.this, "register error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        }
    }}}}}
}
