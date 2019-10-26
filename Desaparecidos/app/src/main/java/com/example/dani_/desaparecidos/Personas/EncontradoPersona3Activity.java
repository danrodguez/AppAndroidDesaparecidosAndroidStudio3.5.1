package com.example.dani_.desaparecidos.Personas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dani_.desaparecidos.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.dani_.desaparecidos.R.id.imageView;
import static com.example.dani_.desaparecidos.R.id.imageView2;

public class EncontradoPersona3Activity extends AppCompatActivity {

    String TiposFisicos;
    String Peligrosos;
    String Nombre;
    String Apellido;
    String Edad;
    String Genero;
    String Piel;
    String Altura;
    String Peso;
    String ColorCabello;
    String ColorOjos;
    String FechaDesaparicion;
    String tipos;
    String peligrosos;
    EditText Provincia;
    Spinner TipoFisico;
    Spinner Peligroso;
    Button BotonImagen;
    ImageView Imagen;
    Button Registrar;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_encontrado3);


        Intent mIntent = getIntent();
        Nombre = mIntent.getStringExtra("Nombre");
        Apellido = mIntent.getStringExtra("Apellido");
        Edad = mIntent.getStringExtra("Edad");
        Genero = mIntent.getStringExtra("Genero");
        Piel = mIntent.getStringExtra("Piel");
        Altura = mIntent.getStringExtra("Altura");
        Peso = mIntent.getStringExtra("Peso");
        ColorCabello = mIntent.getStringExtra("ColorCabello");
        ColorOjos = mIntent.getStringExtra("ColorOjos");
        FechaDesaparicion = mIntent.getStringExtra("FechaDesaparicion");


        Provincia = (EditText) findViewById(R.id.Provincia);
        TipoFisico = (Spinner) findViewById(R.id.TipoFisico);
        Peligroso = (Spinner) findViewById(R.id.Peligroso);
        BotonImagen = (Button)findViewById(R.id.Img);
        Imagen = (ImageView)findViewById(imageView2);
        Registrar = (Button) findViewById(R.id.Siguiente);

        tipos = TipoFisico.getSelectedItem().toString();
        peligrosos= Peligroso.getSelectedItem().toString();


        BotonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        EncontradoPersona3Activity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {

               Intent miIntent = new Intent(EncontradoPersona3Activity.this, PersonasMapsActivity.class);


            miIntent.putExtra("Altura",Altura);
            miIntent.putExtra("Peso",Peso);
            miIntent.putExtra("ColorCabello",ColorCabello);
            miIntent.putExtra("ColorOjos",ColorOjos);
            miIntent.putExtra("FechaDesaparicion",FechaDesaparicion);
            miIntent.putExtra("Nombre",Nombre);
            miIntent.putExtra("Apellido",Apellido);
            miIntent.putExtra("Edad",Edad);
            miIntent.putExtra("Genero",Genero);
            miIntent.putExtra("Piel",Piel);
            miIntent.putExtra("TipoFisico",tipos);
            miIntent.putExtra("Peligroso",peligrosos);
            miIntent.putExtra("Provincia",Provincia.getText().toString());
            miIntent.putExtra("Imagen",imageViewToByte(Imagen));

           startActivity(miIntent);


            }


        });
    }



    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Imagen.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
