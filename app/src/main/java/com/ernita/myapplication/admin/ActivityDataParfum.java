package com.ernita.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ernita.myapplication.R;
import com.ernita.myapplication.adapter.AdapterParfum;
import com.ernita.myapplication.model.ModelParfum;
import com.ernita.myapplication.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataParfum extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterParfum adapter;
    ListView list;

    ArrayList<ModelParfum> newsList = new ArrayList<ModelParfum>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_parfum);

        getSupportActionBar().setTitle("Data Parfum");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterParfum(ActivityDataParfum.this, newsList);
        list.setAdapter(adapter);
        getAllParfum();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataParfum.this, HomeAdminActivity.class);
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
                                Log.d("data Parfum = ", response.toString());
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
                                            Intent a = new Intent(ActivityDataParfum.this, EditParfumDanHapusActivity.class);
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
