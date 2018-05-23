package es.madisa.www.scanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Scanner;

import es.madisa.www.scanapp.clases.Articulo;
import es.madisa.www.scanapp.clases.Inventario;

public class InventarioActivity extends AppCompatActivity {

    private Scanner scan = null;
    private Button btnGuardar;
    private Inventario inventario;
    private EditText textReferencia;
    private EditText textUnidades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
        scan = new Scanner(System.in);

        textReferencia = findViewById(R.id.textReferecnia);
        textUnidades = findViewById(R.id.textUnidades);
        btnGuardar = findViewById(R.id.btnGuardar);
        inventario = new Inventario();
        //metodos
        guardarArticulo();
    }

    public void guardarArticulo(){
        btnGuardar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (!textReferencia.getText().toString().equals("")
                        && !textUnidades.getText().toString().equals("")) {

                    System.out.println("Guardo datos");
                    Articulo articulo = new Articulo();
                    articulo.setId(textReferencia.getText().toString());
                    articulo.setNombre("Prueba");
                    articulo.setReferencias(new String[]{textReferencia.getText().toString()});
                    articulo.setUnidades(Integer.valueOf(textUnidades.getText().toString()));
                    inventario.agregarArticulo(articulo);
                    Toast.makeText(InventarioActivity.this, "Referencia almacenada con éxito", Toast.LENGTH_SHORT).show();
                    System.out.println("MOSTRAMOS");
                    System.out.println();
                    inventario.mostrarArticulos();

                }
            }
        });
    }
    public void leerReferencia(){
        if(scan.hasNext()){
            String value = scan.nextLine();
            textReferencia.setText(value);
        }
    }




}
