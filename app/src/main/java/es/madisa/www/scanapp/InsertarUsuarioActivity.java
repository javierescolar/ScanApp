package es.madisa.www.scanapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class InsertarUsuarioActivity extends AppCompatActivity {


    private EditText textId,textName;
    private Button btnGuardarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_usuario);

        textId = findViewById(R.id.textId);
        textName = findViewById(R.id.textName);
        btnGuardarUsuario=findViewById(R.id.btnGuardarUsuario);

        registrarUsuario();
    }


    public void registrarUsuario(){
        btnGuardarUsuario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ConexionSQLiteHelper conn= new ConexionSQLiteHelper(getApplicationContext(),Utilidades.DB_NAME,null,1);
                SQLiteDatabase db = conn.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put(Utilidades.USUARIOS_CAMPO_ID,textId.getText().toString());
                values.put(Utilidades.USUARIOS_CAMPO_NOMBRE,textName.getText().toString());
                Long idResultado = db.insert(Utilidades.TABLA_USUARIOS,Utilidades.USUARIOS_CAMPO_ID,values);
                db.close();
                Toast.makeText(getApplicationContext(),"Id registrado: "+idResultado,Toast.LENGTH_LONG).show();
            }
        });
    }
}
