package com.ernita.myapplication.pembeli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.ernita.myapplication.R;
import com.ernita.myapplication.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailParfum extends AppCompatActivity {

    EditText edtkodeParfum, edtnamaParfum, edtjenisParfum, edtMl, edthargaParfum;
    ImageView imgGambarParfum;

    String strkodeParfum, strnamaParfum, strjenisParfum, strMl, strhargaParfum, strGambar, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_parfum);

        edtkodeParfum = (EditText) findViewById(R.id.edtKodeParfum);
        edtnamaParfum = (EditText) findViewById(R.id.edtNamaParfum);
        edtjenisParfum = (EditText) findViewById(R.id.edtJenisParfum);
        edtMl = (EditText) findViewById(R.id.edtMl);
        edthargaParfum = (EditText) findViewById(R.id.edthargaParfum);

        imgGambarParfum = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strkodeParfum = i.getStringExtra("kodeParfum");
        strnamaParfum = i.getStringExtra("namaParfum");
        strjenisParfum = i.getStringExtra("jenisParfum");
        strMl = i.getStringExtra("Ml");
        strhargaParfum = i.getStringExtra("hargaParfum");
        strGambar = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtkodeParfum.setText(strkodeParfum);
        edtnamaParfum.setText(strnamaParfum);
        edtjenisParfum.setText(strjenisParfum);
        edtMl.setText(strMl);
        edthargaParfum.setText(strhargaParfum);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambar)
                .into(imgGambarParfum);
    }
}
