package es.madisa.www.scanapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.madisa.www.scanapp.clases.Inventario;
import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class ConsultarInventariosActivity extends AppCompatActivity {


    private Usuario userLogin;
    private ListView listaViewInventarios;
    private ArrayList<String> listaInformacion;
    private List<Inventario> listaInventarios;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_inventarios);
        conn = new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");
        listaViewInventarios=findViewById(R.id.listaInventarios);

        listaInventarios=Inventario.obtenerInventarios(conn);
        obtenerlista();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listaViewInventarios.setAdapter(adaptador);

        listaViewInventarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                Intent myIntent = new Intent(ConsultarInventariosActivity.this, InventarioActivity.class);
                myIntent.putExtra("userLogin",userLogin);
                myIntent.putExtra("id_inventario",String.valueOf(listaInventarios.get(pos).getId()));
                startActivity(myIntent);

            }
        });
    }


    private void obtenerlista() {
        listaInformacion=new ArrayList<String>();

        for (Inventario i : listaInventarios){
            listaInformacion.add(String.valueOf(i.getId())+" - "+i.getFecha());
        }


    }
}
