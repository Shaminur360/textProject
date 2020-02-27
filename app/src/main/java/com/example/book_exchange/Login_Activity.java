package com.example.book_exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Activity extends AppCompatActivity {
    private EditText email,password;
    private Button btn_login;
    private TextView link_regist;
    private ProgressBar loading;
    private static String URL_LOGIN="https://shaminur360.000webhostapp.com/android_register_login/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        loading=findViewById(R.id.loading);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btn_login=findViewById(R.id.btn_loading);
        link_regist=findViewById(R.id.link_regist);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();
                String mPass=password.getText().toString().trim();

                if(!mEmail.isEmpty()||!mPass.isEmpty()){
                   Login();
                }else {
                    email.setError("please Insert email");
                    password.setError("please Insert password");
                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,Register_Activity.class));
            }
        });
    }

    private void Login() {

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                            Toast.makeText( Login_Activity.this, "Successfully login", Toast.LENGTH_SHORT ).show();



                            btn_login.setVisibility( View.VISIBLE );
                            loading.setVisibility( View.GONE );




                            Intent intent = new Intent( Login_Activity.this,Six.class );
                            startActivity( intent );
                            finish();

                        }

                        else
                        {


                            btn_login.setVisibility( View.VISIBLE );
                            loading.setVisibility( View.GONE );

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login_Activity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("email",email.getText().toString().trim() );
                parms.put("password", password.getText().toString().trim());
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }

