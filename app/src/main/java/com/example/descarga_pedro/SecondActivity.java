package com.example.descarga_pedro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<String> listaImagenes=getIntent().getExtras().getStringArrayList("list");

        ImageView Imagen1 = (ImageView) findViewById(R.id.imageView1);
        Picasso.get().load(listaImagenes.get(0)).into(Imagen1);

        ImageView Imagen2 = (ImageView) findViewById(R.id.imageView2);
        Picasso.get().load(listaImagenes.get(1)).into(Imagen2);

        ImageView Imagen3 = (ImageView) findViewById(R.id.imageView3);
        Picasso.get().load(listaImagenes.get(2)).into(Imagen3);

    }
}