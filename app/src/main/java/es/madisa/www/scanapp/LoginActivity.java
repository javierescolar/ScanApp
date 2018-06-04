package es.madisa.www.scanapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class LoginActivity extends AppCompatActivity {

    private EditText textUsuario,textPassword;
    private Button btnLogin;
    private ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsuario=findViewById(R.id.textUsuario);
        textPassword=findViewById(R.id.textPassword);
        btnLogin=findViewById(R.id.btnLogin);

        conn = new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        creaUsuarioAdmin();
        login();
    }

    public void login(){
        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(textUsuario.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Usuario obligatorio",Toast.LENGTH_SHORT).show();

                }

                else if(textPassword.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"Contraseña obligatoria",Toast.LENGTH_SHORT).show();

                }
                else {


                    SQLiteDatabase db = conn.getReadableDatabase();


                    String[] camposVisualizar = {
                            Utilidades.USUARIOS_CAMPO_ID,
                            Utilidades.USUARIOS_CAMPO_CODUSUARIO,
                            Utilidades.USUARIOS_CAMPO_NOMBRE,
                            Utilidades.USUARIOS_CAMPO_TIENDA
                    };
                    String where = Utilidades.USUARIOS_CAMPO_CODUSUARIO + "= ?" + " AND " + Utilidades.USUARIOS_CAMPO_PASS + "= ?";
                    String[] parametros = {textUsuario.getText().toString(), textPassword.getText().toString()};


                    try {
                        Cursor cursor = db.query(Utilidades.TABLA_USUARIOS, camposVisualizar, where, parametros, null, null, null);
                        cursor.moveToFirst();
                        Usuario usuario = new Usuario();

                        usuario.setId(cursor.getInt(Utilidades.USUARIOS_IDX_CAMPO_ID));
                        usuario.setCod_usuario(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_CODUSUARIO));
                        usuario.setNombre(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_NOMBRE));
                        usuario.setTienda(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_TIENDA));


                        try {

                            Intent myIntent = new Intent(LoginActivity.this, MenuActivity.class);
                            myIntent.putExtra("userLogin",usuario);
                            startActivity(myIntent);
                        } catch (Exception ex){
                            Toast.makeText(getApplicationContext(), "Error al cargar sesión", Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                        limpiarCampos();
                    } finally {
                        db.close();
                    }


                }

            }
        });
    }

    public void limpiarCampos(){
        textUsuario.setText("");
        textPassword.setText("");
    }

    public void creaUsuarioAdmin(){
        SQLiteDatabase db = conn.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM "+Utilidades.TABLA_TIENDAS);
            db.execSQL("DELETE FROM "+Utilidades.TABLA_USUARIOS);
            //cargamos articulos
            ContentValues values = new ContentValues();
            values.put(Utilidades.USUARIOS_CAMPO_CODUSUARIO,"admin");
            values.put(Utilidades.USUARIOS_CAMPO_NOMBRE,"admin");
            values.put(Utilidades.USUARIOS_CAMPO_PASS,"123");
            values.put(Utilidades.USUARIOS_CAMPO_TIENDA,"000");
            db.insert(Utilidades.TABLA_USUARIOS,Utilidades.USUARIOS_CAMPO_ID,values);

            values = new ContentValues();
            values.put(Utilidades.TIENDAS_CAMPO_CODTIENDA,"000");
            values.put(Utilidades.TIENDAS_CAMPO_NOMBRE,"Central");
            db.insert(Utilidades.TABLA_TIENDAS,Utilidades.TIENDAS_CAMPO_ID,values);
            //cargamos enas
            Toast.makeText(getApplicationContext(),"Datos creado correctamente",Toast.LENGTH_LONG).show();
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Error al generar datos de prueba",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }
}
