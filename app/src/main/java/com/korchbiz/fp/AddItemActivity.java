package com.korchbiz.fp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AddItemActivity extends AppCompatActivity {

    EditText editTextItem;
    Button buttonSave,buttonCancel,btnHome;
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setupView();
        if(editMode()){
            setData();
        }
    }
    private void setData() {
        editTextItem.setText(getIntent().getStringExtra("post"));
    }

    private boolean editMode() {
        return getIntent().getStringExtra("post")!=null;
    }
    private void setupView() {
        editTextItem = (EditText) findViewById(R.id.editText3);
        buttonCancel = (Button) findViewById(R.id.button2);
        buttonSave = (Button) findViewById(R.id.button3);
        txtUser = (TextView) findViewById(R.id.txtUsername);
        btnHome = (Button) findViewById(R.id.btnHome);
        Bundle b = getIntent().getExtras();
        String nama = b.getString("abc");

        txtUser.setText(nama);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPosting();
                Toast.makeText(AddItemActivity.this, "Berhasil.", Toast.LENGTH_SHORT).show();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItemActivity.this, Home.class));
            }
        });
    }

    private void addPosting(){
        final String nama = txtUser.getText().toString();
        final String post = editTextItem.getText().toString();

        class AddPosting extends AsyncTask<Void, Void, String>{

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> paramek = new HashMap<>();
                paramek.put(Config.KEY_NAMA,nama);
                paramek.put(Config.KEY_POST,post);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Config.URL_INSERT, paramek);
                return res;
            }
        }
        AddPosting ap = new AddPosting();
        ap.execute();
    }
}