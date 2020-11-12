package com.ernita.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ernita.myapplication.R;
import com.ernita.myapplication.model.ModelParfum;
import com.ernita.myapplication.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterParfum  extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelParfum> item;

    public AdapterParfum (Activity activity, List<ModelParfum> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_parfum, null);


        TextView NamaParfum = (TextView) convertView.findViewById(R.id.txtNamaParfum);
        TextView JenisParfum    = (TextView) convertView.findViewById(R.id.txtJenisParfum);
        TextView HargaParfum         = (TextView) convertView.findViewById(R.id.txtHargaParfum);
        TextView Ml         = (TextView) convertView.findViewById(R.id.txtMl);
        ImageView gambar         = (ImageView) convertView.findViewById(R.id.gambar);

        NamaParfum.setText(item.get(position).getNamaParfum());
        JenisParfum.setText(item.get(position).getJenisParfum());
        HargaParfum.setText("Rp." + item.get(position).getHargaParfum());
        Ml.setText(item.get(position).getMl());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }
}
