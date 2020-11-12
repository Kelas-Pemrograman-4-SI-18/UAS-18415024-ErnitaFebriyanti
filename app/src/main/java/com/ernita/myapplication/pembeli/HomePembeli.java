package com.ernita.myapplication.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ernita.myapplication.R;
import com.ernita.myapplication.adapter.AdapterParfum;
import com.ernita.myapplication.admin.ActivityDataParfum;
import com.ernita.myapplication.admin.EditParfumDanHapusActivity;
import com.ernita.myapplication.admin.HomeAdminActivity;
import com.ernita.myapplication.model.ModelParfum;
import com.ernita.myapplication.server.BaseURL;
import com.ernita.myapplication.session.PrefSetting;
import com.ernita.myapplication.session.SessionManager;
import com.ernita.myapplication.users.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeli extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterParfum adapter;
    ListView list;

    ArrayList<ModelParfum> newsList = new ArrayList<ModelParfum>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;


    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharedPreferences();

        session = new SessionManager(HomePembeli.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Parfum");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeli.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        newsList.clear();
        adapter = new AdapterParfum(HomePembeli.this, newsList);
        list.setAdapter(adapter);
        getAllParfum();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(HomePembeli.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllParfum() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataParfum, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data buku = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelParfum parfum = new ModelParfum();
                                    final String _id = jsonObject.getString("_id");
                                    final String kodeParfum = jsonObject.getString("kodeParfum");
                                    final String namaParfum = jsonObject.getString("namaParfum");
                                    final String jenisParfum = jsonObject.getString("jenisParfum");
                                    final String hargaParfum = jsonObject.getString("hargaParfum");
                                    final String Ml = jsonObject.getString("Ml");
                                    final String gambar = jsonObject.getString("gambar");
                                    parfum.setKodeParfum(kodeParfum);
                                    parfum.setNamaParfum(namaParfum);
                                    parfum.setJenisParfum(jenisParfum);
                                    parfum.setHargaParfum(hargaParfum);
                                    parfum.setMl(Ml);
                                    parfum.setGambar(gambar);
                                    parfum.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(HomePembeli.this, DetailParfum.class);
                                            a.putExtra("kodeParfum", newsList.get(position).getKodeParfum());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaParfum", newsList.get(position).getNamaParfum());
                                            a.putExtra("jenisParfum", newsList.get(position).getJenisParfum());
                                            a.putExtra("hargaParfum", newsList.get(position).getHargaParfum());
                                            a.putExtra("Ml", newsList.get(position).getMl());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(parfum);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



}
