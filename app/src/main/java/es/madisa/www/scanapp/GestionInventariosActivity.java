package es.madisa.www.scanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import es.madisa.www.scanapp.clases.Inventario;
import es.madisa.www.scanapp.clases.Usuario;

public class GestionInventariosActivity extends AppCompatActivity {

    private Button btnNuevoInventario,btnConsultarInventarios;
    private Usuario userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_inventarios);

        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");

        btnNuevoInventario=findViewById(R.id.btnNuevoInventario);
        btnConsultarInventarios=findViewById(R.id.btnConsultarInventarios);

        nuevoInventario();
        consultarInventarios();
    }


    public void nuevoInventario(){
        btnNuevoInventario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(GestionInventariosActivity.this, InventarioActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                myIntent.putExtra("id_inventario","nuevo");
                startActivity(myIntent);
            }
        });
    }

    public void consultarInventarios(){
        btnConsultarInventarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(GestionInventariosActivity.this, ConsultarInventariosActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                startActivity(myIntent);
            }
        });
    }
}
