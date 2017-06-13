package com.korchbiz.fp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private String JSON_STR;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        id = i.getStringExtra(Config.POST_ID);
        listView = (ListView) findViewById(R.id.listPost);
        listView.setOnItemClickListener(this);
        getJSON();
    }
    public void showData() {
        JSONObject jso = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jso = new JSONObject(JSON_STR);
            JSONArray result = jso.getJSONArray(Config.TAG_JSON_ARRAY);

            for (int a = 0; a < result.length(); a++) {
                JSONObject j = result.getJSONObject(a);
                String nama = j.getString(Config.TAG_NAMA);
                String post = j.getString(Config.TAG_POST);

                HashMap<String, String> data = new HashMap<String, String>();
                data.put(Config.TAG_NAMA, nama);
                data.put(Config.TAG_POST, post);
                list.add(data);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        ListAdapter la = new SimpleAdapter(
                Home.this, list, R.layout.list_item,
                new String[]{Config.TAG_NAMA, Config.TAG_POST},
                new int[]{R.id.txtname, R.id.txtpost}
        );
        listView.setAdapter(la);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Home.this, "Fetching data...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STR = s;
                showData();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_SELECT);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(Home.this, Delete.class);
        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);
        String postID = map.get(Config.TAG_ID).toString();
        i.putExtra(Config.POST_ID, postID);
        startActivity(i);
    }

    private void delPost(final String abc) {
        class DelMember extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Home.this, "Deleting data...", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Home.this, "Deleted", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Config.URL_DELETE, abc);
                return s;
            }
        }
        DelMember dm = new DelMember();
        dm.execute();
    }

    private void askDelete(final String a){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure?");

        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        delPost(a);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // logic goes here //
                    }
                });
        AlertDialog ad = alertDialogBuilder.create();
        ad.show();
    }

    public void onDeleteButtonClick(View view) {
        View v = (View) view.getParent();
        EditText sparta = (EditText) v.findViewById(R.id.txtpost);
        String a = sparta.getText().toString();
        askDelete(a);
    }
}