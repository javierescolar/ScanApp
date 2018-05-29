package es.madisa.www.scanapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.UniversalTimeScale;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.madisa.www.scanapp.clases.Articulo;
import es.madisa.www.scanapp.clases.Ean;
import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class ConsultarArticulosActivity extends AppCompatActivity {

    private ListView listViewArticulos;
    private ArrayList<String> listaInformacion;
    private ArrayList<Articulo> listaArticulos;

    ConexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_articulos);

        listViewArticulos=findViewById(R.id.listViewArticulos);

        conn = new ConexionSQLiteHelper(this, Utilidades.DB_NAME,null,1);
        consultarUsuariosLista();

        ArrayAdapter adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewArticulos.setAdapter(adaptador);

        listViewArticulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion="id: "+listaArticulos.get(pos).getId()+"\n";
                informacion+="Cod Articulo: "+listaArticulos.get(pos).getCod_articulo()+"\n";
                informacion+="Nombre: "+listaArticulos.get(pos).getNombre();
            }
        });
    }

    private void consultarUsuariosLista(){
        SQLiteDatabase db = conn.getReadableDatabase();

        Articulo art = null;
        Ean ean = null;
        listaArticulos=new ArrayList<Articulo>();
        Cursor cursorArticulos=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_ARTICULOS,null);

        while(cursorArticulos.moveToNext()){
            art=new Articulo();
            List<Ean> eansArti = new ArrayList<>();
            art.setId(cursorArticulos.getInt(Utilidades.ARTICULOS_IDX_CAMPO_ID));
            art.setCod_articulo(cursorArticulos.getString(Utilidades.ARTICULOS_IDX_CAMPO_CODARTICULO));
            art.setNombre(cursorArticulos.getString(Utilidades.ARTICULOS_IDX_CAMPO_NOMBRE));
            //Obtenemos los eans del articulo
            Cursor cursorEans =db.rawQuery("SELECT * FROM "+Utilidades.TABLA_EANS,null);
            while(cursorEans.moveToNext()){
                ean=new Ean();
                ean.setId(cursorEans.getInt(Utilidades.EANS_IDX_CAMPO_ID));
                ean.setCod_articulo(cursorEans.getString(Utilidades.EANS_IDX_CAMPO_CODARTICULO));
                ean.setNum_ean(cursorEans.getString(Utilidades.EANS_IDX_CAMPO_NUMEAN));
                eansArti.add(ean);
            }
            art.setEans(eansArti);
            listaArticulos.add(art);
        }

        obtenerlista();
    }

    private void obtenerlista() {
        listaInformacion=new ArrayList<String>();

        for (Articulo a : listaArticulos){
            listaInformacion.add(String.valueOf(a.getId())+" - "+a.getNombre());
        }


    }
}
