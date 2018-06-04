package es.madisa.www.scanapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class MenuActivity extends AppCompatActivity {

    private Button btnInventarios;
    private Button btnConexionesGsBase;
    private Button btnGestionUsuarios;
    private Usuario userLogin;


    private ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //userLogin = getIntent().getStringExtra("userLogin");
        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");


        btnInventarios = findViewById(R.id.btnInventarios);
        btnConexionesGsBase = findViewById(R.id.btnConexionesGsBase);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);

        conn=new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        inventarios();
        gestionGsBase();
        gestionUsuarios();
    }


    public void inventarios(){
        btnInventarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, GestionInventariosActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                startActivity(myIntent);
            }
        });
    }

    public void gestionGsBase(){
        btnConexionesGsBase.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, GestionGsBaseActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                startActivity(myIntent);
                    }
        });

    }

    public void gestionUsuarios(){
        btnGestionUsuarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, GestionUsuariosActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                startActivity(myIntent);
            }
        });
    }

/*
    public void GestionArticulos(){
        btnGestionArticulos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, ConsultarArticulosActivity.class);
                startActivity(myIntent);
            }
        });
    }
*/
    @Override
    public void onBackPressed() {
        if(userLogin!=null){
            return;
        }
        super.onBackPressed();

    }
}
