package es.madisa.www.scanapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class ConsultarUsuarioActivity extends AppCompatActivity {


    private EditText textBuscar,textName;
    private Button btnBuscar,btnActualizar,btnEliminar;
    private ConexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        textBuscar=findViewById(R.id.textBuscar);
        textName=findViewById(R.id.textName);
        btnBuscar= (Button) findViewById(R.id.btnBuscar);
        btnActualizar= (Button) findViewById(R.id.btnActualizar);
        btnEliminar= (Button) findViewById(R.id.btnEliminar);
        conn= new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        consultarUsuario();
        actualizarUsuario();
        eliminarUsuario();
    }


    public void consultarUsuario(){
        btnBuscar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                SQLiteDatabase db = conn.getReadableDatabase();
                String[] parametros = {textBuscar.getText().toString()};
                String[] camposVisualizar = {Utilidades.USUARIOS_CAMPO_NOMBRE};

                try{
                    Cursor cursor = db.query(Utilidades.TABLA_USUARIOS, camposVisualizar, Utilidades.USUARIOS_CAMPO_ID+"=?",parametros,null,null,null);
                    cursor.moveToFirst();

                    textName.setText(cursor.getString(0));
                    db.close();
                } catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"El usuario no existe",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }

            }
        });
    }

    public void actualizarUsuario(){
        btnActualizar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                SQLiteDatabase db = conn.getWritableDatabase();
                String[] parametros = {textBuscar.getText().toString()};
                ContentValues values = new ContentValues();
                values.put(Utilidades.USUARIOS_CAMPO_NOMBRE,textName.getText().toString());
                db.update(Utilidades.TABLA_USUARIOS,values,Utilidades.USUARIOS_CAMPO_ID+"=?",parametros);
                Toast.makeText(getApplicationContext(),"Usuario actualizado",Toast.LENGTH_SHORT).show();
                db.close();


            }
        });
    }

    public void eliminarUsuario(){
        btnEliminar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                SQLiteDatabase db = conn.getWritableDatabase();
                String[] parametros = {textBuscar.getText().toString()};
                db.delete(Utilidades.TABLA_USUARIOS,Utilidades.USUARIOS_CAMPO_ID+"=?",parametros);
                Toast.makeText(getApplicationContext(),"Usuario eliminado",Toast.LENGTH_SHORT).show();
                db.close();

            }
        });
    }

    public void limpiarCampos(){
        textBuscar.setText("");
        textName.setText("");
    }


}
