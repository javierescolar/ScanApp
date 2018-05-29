package es.madisa.www.scanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionUsuariosActivity extends AppCompatActivity {

    private Button btnInsertar,btnConsultarUsuarios,btnListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        btnInsertar=findViewById(R.id.btnInsertar);
        btnConsultarUsuarios=findViewById(R.id.btnConsultarUsuarios);
        btnListView=findViewById(R.id.btnListView);

        insertarUsuarios();
        consultarUsuarios();
        consultarUsuariosListView();
    }

    public void insertarUsuarios(){
        btnInsertar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(GestionUsuariosActivity.this, InsertarUsuarioActivity.class);
                startActivity(myIntent);
            }
        });
    }


    public void consultarUsuarios(){
        btnConsultarUsuarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(GestionUsuariosActivity.this, ConsultarUsuarioActivity.class);
                startActivity(myIntent);
            }
        });
    }

    public void consultarUsuariosListView(){
        btnListView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(GestionUsuariosActivity.this, ConsultarListViewActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
