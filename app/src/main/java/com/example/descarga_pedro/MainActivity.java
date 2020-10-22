package com.example.descarga_pedro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static int contador = 0;
    private Button botonGuardar, botonDescargar, botonPermisos, botonPass;
    private int REQUEST_CODE = 69;
    private boolean checkDescarga = true;

    String link1 = "https://www.youtube.com/watch?v=_Qb2zNfIIBY";
    String link2 = "https://www.youtube.com/watch?v=PQvFtSJEXu8";
    String link3 = "https://www.youtube.com/watch?v=kJQP7kiw5Fk";

    String imagen1 = "https://i.imgur.com/B9A7TM7.jpeg";
    String imagen2 = "https://i.ytimg.com/vi/GRxw7ttFczE/maxresdefault.jpg";
    String imagen3 = "https://i.imgur.com/1DSSRov.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonGuardar = (Button) findViewById(R.id.botonGuardar);
        botonDescargar = (Button) findViewById(R.id.botonDescargar);
        botonPermisos = (Button) findViewById(R.id.botonPermisos);
        botonPass = (Button) findViewById(R.id.botonPass);

        //Boton que descarga las imagenes y las muestra en un ImageView
        botonDescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadStorageAllowed()) {
                    if (checkDescarga) {

                        checkDescarga = false;
                        final ImageView ImagenIv = (ImageView) findViewById(R.id.Imagenes);

                        Timer timer;
                        timer = new Timer();

                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        contador++;

                                        if (contador == 1) {
                                            Picasso.get().load(imagen1).into(ImagenIv);
                                            ImageView imageView = (ImageView) findViewById(R.id.Imagenes);
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    Uri uri = Uri.parse(link1);
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                }
                                            });
                                        }

                                        if (contador == 2) {
                                            Picasso.get().load(imagen2).into(ImagenIv);
                                            ImageView imageView = (ImageView) findViewById(R.id.Imagenes);
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    Uri uri = Uri.parse(link2);
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                }
                                            });
                                        }

                                        if (contador == 3) {
                                            Picasso.get().load(imagen3).into(ImagenIv);
                                            ImageView imageView = (ImageView) findViewById(R.id.Imagenes);
                                            imageView.setOnClickListener(new View.OnClickListener() {
                                                public void onClick(View v) {
                                                    Uri uri = Uri.parse(link3);
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                }
                                            });
                                        }

                                        if (contador == 3) {
                                            contador = 0;
                                        }

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {

                                            @Override
                                            public void run() {

                                            }
                                        }, 5000);

                                    }
                                });
                            }
                        }, 0, 5000);

                        Toast.makeText(getApplicationContext(), "Descarga completada", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Las imagenes ya fueron descargadas", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no estan habilitados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton que guarda las imagenes
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadStorageAllowed()) {
                    if (!checkDescarga) {

                        if (contador == 1) {
                            saveImage("https://i.imgur.com/B9A7TM7.jpeg");
                            Toast.makeText(getApplicationContext(), "Se descargo la imagen1", Toast.LENGTH_SHORT).show();
                        }

                        if (contador == 2) {
                            saveImage("https://i.ytimg.com/vi/GRxw7ttFczE/maxresdefault.jpg");
                            Toast.makeText(getApplicationContext(), "Se descargo la imagen2", Toast.LENGTH_SHORT).show();
                        }

                        if (contador == 0) {
                            saveImage("https://i.imgur.com/1DSSRov.jpg");
                            Toast.makeText(getApplicationContext(), "Se descargo la imagen3", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Primero descarga las imagenes", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Los permisos no estan habilitados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton que habilita los permisos de Escritura y habilita el resto de botones
        botonPermisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadStorageAllowed()) {
                    Toast.makeText(MainActivity.this, "Ya tienes permisos", Toast.LENGTH_LONG).show();
                    return;
                }

                requestStoragePermission();
            }
        });

        botonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReadStorageAllowed()){

                    ArrayList<String> listaImagenes = new ArrayList<String>();

                    listaImagenes.add(imagen1);
                    listaImagenes.add(imagen2);
                    listaImagenes.add(imagen3);

                    Toast.makeText(MainActivity.this, "Viajando a la 2º Actividad", Toast.LENGTH_LONG).show();
                    Intent miIntent = new Intent(getApplicationContext(), SecondActivity.class);

                    miIntent.putStringArrayListExtra("list", listaImagenes);
                    startActivity(miIntent);
                }
            }
        });

    }

    //Metodo que guarda la imagen en el directorio Pictures
    public void saveImage(String image) {
        Picasso.get().load(image).into(new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                try {

                    File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }

                    FileOutputStream fileOutputStream = new FileOutputStream(new File(directory, new Date().toString().concat(" Imagen_").concat(String.valueOf(contador)).concat(".jpg")));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                }

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    //Metodo que comprueba el estado del permiso si fue denegado devolvera false y viceversa
    private boolean isReadStorageAllowed() {

        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }

    //Metodo que pide los permisos
    private void requestStoragePermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
    }

    //Metodo que es llamado cuando el usuario pulsa Aceptar o Denegar
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permisos, @NonNull int[] grantResults) {

        //Comprueba si los permisos fueron concedidos
        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permiso concedido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
            }
        }
    }

}
