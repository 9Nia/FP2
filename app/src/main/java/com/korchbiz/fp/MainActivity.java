package com.korchbiz.fp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUser, txtPass;
    Button btnLog;
    private String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUser = (EditText) findViewById(R.id.txtUsername);
        txtPass = (EditText) findViewById(R.id.txtPassword);
        btnLog = (Button) findViewById(R.id.btnLogin);

        btnLog.setOnClickListener(this);
    }

    private void auth(){
        user = txtUser.getText().toString();
        pass = txtPass.getText().toString();
        if (user.equals("maji")&&pass.equals("vira")){
            Toast.makeText(this, "Welcome " + user, Toast.LENGTH_SHORT).show();

        }
        else if (user.equals("aji")&&pass.equals("jikawe")) {
            Toast.makeText(this, "Welcome " + user, Toast.LENGTH_SHORT).show();
        }
        else if (user.equals("budi")&&pass.equals("crown")) {
            Toast.makeText(this, "Welcome " + user, Toast.LENGTH_SHORT).show();
        }
        else if (user.equals("rosyid")&&pass.equals("9nia")) {
            Toast.makeText(this, "Welcome " + user, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v==btnLog){
            auth();
            Intent i = new Intent(MainActivity.this, AddItemActivity.class);
            i.putExtra("abc", txtUser.getText().toString());
            startActivity(i);
        }
    }
}
