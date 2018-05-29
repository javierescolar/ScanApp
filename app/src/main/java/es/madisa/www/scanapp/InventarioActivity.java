package es.madisa.www.scanapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Scanner;

import es.madisa.www.scanapp.clases.Articulo;
import es.madisa.www.scanapp.clases.Inventario;
import es.madisa.www.scanapp.clases.Usuario;
import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;


public class InventarioActivity extends AppCompatActivity {

    private Scanner scan = null;
    private Button btnComprobar;
    private Inventario inventario;
    private EditText textReferencia;
    private Usuario userLogin;
    private ConexionSQLiteHelper conn;
    private TextView textTienda,textUsuario,textFecha,textTitulo;
    private Articulo articuloEscaneado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.DB_NAME,null,1);

        userLogin = (Usuario) getIntent().getParcelableExtra("userLogin");
        String id_inventario = getIntent().getStringExtra("id_inventario");

        textReferencia = findViewById(R.id.textReferecnia);
        btnComprobar = findViewById(R.id.btnComprobar);
        textTienda = findViewById(R.id.textTienda);
        textUsuario = findViewById(R.id.textUsuario);
        textFecha = findViewById(R.id.textFecha);
        textTitulo = findViewById(R.id.textTitulo);

        //Aqui comprobamos si es un inventario nuevoo si debemos obtener otro
        if (id_inventario.equals("nuevo")){
            try {
                inventario = Inventario.nuevoInventario(conn,userLogin);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Error al crear nuevo inventario :\n"+e.toString(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            try{
                int id = Integer.valueOf(id_inventario);
                inventario = Inventario.obtenerInventario(conn,id);
            }  catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error al obtener inventario :\n"+e.toString(),Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

        }

        //metodos
        inicializarDatos();
        guardarArticulo();


    }

    public void guardarArticulo(){
        btnComprobar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (!textReferencia.getText().equals("")) {
                    try {
                        obtenerArticuloLeido(textReferencia.getText().toString());
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),"Artículo no encontrado para la referencia: "+textReferencia.getText().toString(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void leerReferencia(){
        try{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Referencia leída: "+textReferencia.getText().toString());
            alert.setMessage(articuloEscaneado.getNombre());
            // Set an EditText view to get user input
            final EditText textUnidades = new EditText(this);
            textUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
            alert.setView(textUnidades);

            alert.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String unidades = textUnidades.getText().toString();

                    articuloEscaneado.setUnidades(Integer.valueOf(unidades));

                    Toast.makeText(getApplicationContext(), "Agregamos "+unidades+" unidad/es al inventario", Toast.LENGTH_LONG).show();

                    limpiarDatos();
                    return;
                }
            });

            alert.setNegativeButton("Cancelar",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            return;
                        }
                    });

            alert.create().show();
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Referencia no existe",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }


    }

    private void limpiarDatos() {
        textReferencia.setText("");
    }


    public Articulo obtenerArticuloLeido(String referencia) throws Exception{
        Articulo art = null;
        try{
            art=Articulo.obtenArticuloPorCodigo(conn,referencia);
            if(art==null){

            }
            articuloEscaneado = art;
            leerReferencia();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Artículo no encontrado para la referencia: "+referencia,Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return art;
    }


    public void inicializarDatos(){
        textTienda.setText(inventario.getTienda().getCod_tienda() +" - "+inventario.getTienda().getNombre());
        textUsuario.setText(userLogin.getNombre());
        textFecha.setText(inventario.getFecha());
        textTitulo.setText("Inventario #"+inventario.getId());
    }




}
