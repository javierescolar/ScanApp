package es.madisa.www.scanapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class GestionGsBaseActivity extends AppCompatActivity {

    private ConexionSQLiteHelper conn;
    private Button btnActuArticulos;
    private Button btnActuUsuarios;
    private Usuario userLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_gs_base);
        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.DB_NAME,null,1);
        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");

        btnActuArticulos=findViewById(R.id.btnActuArticulos);
        btnActuUsuarios=findViewById(R.id.btnActuUsuarios);
        actualizarArticulos();
        actualizarUsuarios();
    }


    public void actualizarArticulos(){
        btnActuArticulos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                SQLiteDatabase db = conn.getWritableDatabase();

                try{
                    db.execSQL("DELETE FROM "+ Utilidades.TABLA_EANS);
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

                    Toast.makeText(getApplicationContext(),"Artículos actualizados correctamente",Toast.LENGTH_LONG).show();
                } catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Error al actualizar articulos",Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                } finally {
                    db.close();
                }
            }
        });
    }

    public void actualizarUsuarios(){

        btnActuUsuarios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SQLiteDatabase db = conn.getWritableDatabase();

                try {
                    db.execSQL("DELETE FROM " + Utilidades.TABLA_TIENDAS);
                    db.execSQL("DELETE FROM " + Utilidades.TABLA_USUARIOS);
                    //cargamos articulos
                    ContentValues values = new ContentValues();
                    values.put(Utilidades.USUARIOS_CAMPO_CODUSUARIO, "jescolar");
                    values.put(Utilidades.USUARIOS_CAMPO_NOMBRE, "Javier Escolar");
                    values.put(Utilidades.USUARIOS_CAMPO_PASS, "123");
                    values.put(Utilidades.USUARIOS_CAMPO_TIENDA, "001");
                    db.insert(Utilidades.TABLA_USUARIOS, Utilidades.USUARIOS_CAMPO_ID, values);

                    values.put(Utilidades.USUARIOS_CAMPO_CODUSUARIO, "jescolar2");
                    values.put(Utilidades.USUARIOS_CAMPO_NOMBRE, "Javier Escolar 2");
                    values.put(Utilidades.USUARIOS_CAMPO_PASS, "1234");
                    values.put(Utilidades.USUARIOS_CAMPO_TIENDA, "001");
                    db.insert(Utilidades.TABLA_USUARIOS, Utilidades.USUARIOS_CAMPO_ID, values);

                    values = new ContentValues();
                    values.put(Utilidades.TIENDAS_CAMPO_CODTIENDA, "001");
                    values.put(Utilidades.TIENDAS_CAMPO_NOMBRE, "Alicante");
                    db.insert(Utilidades.TABLA_TIENDAS, Utilidades.TIENDAS_CAMPO_ID, values);
                    //cargamos enas
                    Toast.makeText(getApplicationContext(), "Datos creado correctamente", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error al generar datos de prueba", Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                } finally {
                    db.close();
                }
            }
        });
    }
}

