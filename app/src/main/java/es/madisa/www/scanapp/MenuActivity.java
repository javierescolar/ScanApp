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
    private Button btnCargarPruebas;
    private Button btnGestionUsuarios;
    private Button btnGestionArticulos;
    private Usuario userLogin;



    private ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //userLogin = getIntent().getStringExtra("userLogin");
        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");


        btnInventarios = findViewById(R.id.btnInventarios);
        btnCargarPruebas = findViewById(R.id.btnCargarPruebas);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);
        btnGestionArticulos = findViewById(R.id.btnGestionArticulos);
        conn=new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        inventarios();
        cargarDatosPruebas();
        gestionUsuarios();
        GestionArticulos();
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

    public void cargarDatosPruebas(){
        btnCargarPruebas.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                SQLiteDatabase db = conn.getWritableDatabase();

                try{
                    db.execSQL("DELETE FROM "+Utilidades.TABLA_EANS);
                    db.execSQL("DELETE FROM "+Utilidades.TABLA_ARTICULOS);
                    //cargamos articulos
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.ARTICULOS_CAMPO_CODARTICULO,"0001");
                    values.put(Utilidades.ARTICULOS_CAMPO_NOMBRE,"Pulsera");
                    db.insert(Utilidades.TABLA_ARTICULOS,Utilidades.ARTICULOS_CAMPO_ID,values);

                    values = new ContentValues();
                    values.put(Utilidades.ARTICULOS_CAMPO_CODARTICULO,"0002");
                    values.put(Utilidades.ARTICULOS_CAMPO_NOMBRE,"Reloj");
                    db.insert(Utilidades.TABLA_ARTICULOS,Utilidades.ARTICULOS_CAMPO_ID,values);
                    //cargamos enas

                    values = new ContentValues();
                    values.put(Utilidades.EANS_CAMPO_CODARTICULO,"0001");
                    values.put(Utilidades.EANS_CAMPO_EAN,"8412742808349");
                    db.insert(Utilidades.TABLA_EANS,Utilidades.EANS_CAMPO_ID,values);

                    values = new ContentValues();
                    values.put(Utilidades.EANS_CAMPO_CODARTICULO,"0001");
                    values.put(Utilidades.EANS_CAMPO_EAN,"8412742808999");
                    db.insert(Utilidades.TABLA_EANS,Utilidades.EANS_CAMPO_ID,values);

                    values.put(Utilidades.EANS_CAMPO_CODARTICULO,"0002");
                    values.put(Utilidades.EANS_CAMPO_EAN,"2500742808999");
                    db.insert(Utilidades.TABLA_EANS,Utilidades.EANS_CAMPO_ID,values);

                    Toast.makeText(getApplicationContext(),"Datos creado correctamente",Toast.LENGTH_LONG).show();
                } catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Error al generar datos de prueba",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                } finally {
                    db.close();
                }
                    }
        });

    }

    public void gestionUsuarios(){
        btnGestionUsuarios.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, GestionUsuariosActivity.class);
                startActivity(myIntent);
            }
        });
    }


    public void GestionArticulos(){
        btnGestionArticulos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent myIntent = new Intent(MenuActivity.this, ConsultarArticulosActivity.class);
                startActivity(myIntent);
            }
        });
    }


}
