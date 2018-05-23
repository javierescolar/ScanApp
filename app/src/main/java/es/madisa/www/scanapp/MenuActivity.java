package es.madisa.www.scanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.madisa.www.scanapp.clases.Fichero;

public class MenuActivity extends AppCompatActivity {

    private Button btnInventarios;
    private Button btnCargarFichero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnInventarios = findViewById(R.id.btnInventarios);
        btnCargarFichero = findViewById(R.id.btnCargarFichero);
        inventarios();
        cargarFichero();
    }


    public void inventarios(){
        btnInventarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, InventarioActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void cargarFichero(){
        btnCargarFichero.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
               Fichero f = new Fichero(getApplicationContext());
               f.crearFichero();
               f.leer();
            }
        });
    }


}
