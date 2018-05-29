package es.madisa.www.scanapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class ConsultarListViewActivity extends AppCompatActivity {

    private ListView listViewUsuarios;
    private ArrayList<String> listaInformacion;
    private ArrayList<Usuario> listaUsuarios;

    ConexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_list_view);

        listViewUsuarios=findViewById(R.id.listViewUsuarios);

        conn = new ConexionSQLiteHelper(this,Utilidades.DB_NAME,null,1);
        consultarUsuariosLista();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewUsuarios.setAdapter(adaptador);

        listViewUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion="id: "+listaUsuarios.get(pos).getId()+"\n";
                informacion+="Nombre: "+listaUsuarios.get(pos).getNombre();

            }
        });

    }

    private void consultarUsuariosLista(){
        SQLiteDatabase db = conn.getReadableDatabase();

        Usuario usu = null;

        listaUsuarios=new ArrayList<Usuario>();
        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIOS,null);

        while(cursor.moveToNext()){
            usu=new Usuario();
            usu.setId(cursor.getInt(0));
            usu.setNombre(cursor.getString(1));
            listaUsuarios.add(usu);
        }

        obtenerlista();
    }

    private void obtenerlista() {
        listaInformacion=new ArrayList<String>();

        for (Usuario u : listaUsuarios){
            listaInformacion.add(String.valueOf(u.getId())+" - "+u.getNombre());
        }


    }


}
