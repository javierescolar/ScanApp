package es.madisa.www.scanapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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
    private Button btnComprobar,btnVerArticulosInventario;
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
        btnVerArticulosInventario = findViewById(R.id.btnVerArticulosInventario);
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
        verArticulo();

    }

    public void guardarArticulo(){
        btnComprobar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if (!textReferencia.getText().equals("")) {
                    try {
                        obtenerArticuloLeido(textReferencia.getText().toString(),"cod_articulo");
                    } catch (Exception e) {
                        try{
                            obtenerArticuloLeido(textReferencia.getText().toString(),"referencia");
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(),"Artículo no encontrado para la referencia: "+textReferencia.getText().toString(),Toast.LENGTH_LONG).show();
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void leerReferencia(){
        try{
            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
                    try {
                        inventario.agregarArticulo(conn,articuloEscaneado);
                        Toast.makeText(getApplicationContext(), "Articulo agregado correctamente", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error al agregar articulo al inventario", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
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

            AlertDialog b = alert.create();
            b.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            b.show();
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Referencia no existe",Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }


    }

    private void limpiarDatos() {
        textReferencia.setText("");
    }


    public Articulo obtenerArticuloLeido(String referencia,String flat) throws Exception{
        Articulo art = null;
            if(flat.equals("cod_articulo")){
                art=Articulo.obtenArticuloPorCodigo(conn,referencia);
            } else if(flat.equals("referencia")){
                art=Articulo.obtenerArticuloPorReferencia(conn,referencia);
            }
            articuloEscaneado = art;
            leerReferencia();

        return art;
    }


    public void inicializarDatos(){
        textTienda.setText(inventario.getTienda().getCod_tienda() +" - "+inventario.getTienda().getNombre());
        textUsuario.setText(userLogin.getNombre());
        textFecha.setText(inventario.getFecha());
        textTitulo.setText("Inventario #"+inventario.getId());
    }


    public void verArticulo(){
        btnVerArticulosInventario.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(InventarioActivity.this);
                builderSingle.setIcon(R.drawable.ic_remove_red_eye_black_24dp);
                builderSingle.setTitle("Articulo | Unidades");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(InventarioActivity.this, android.R.layout.select_dialog_item);
                int i = 0;
                for (Articulo art : inventario.getArticulos()){
                    arrayAdapter.add(String.valueOf(i)+"-"+art.getNombre()+" | "+art.getUnidades());
                    i+=1;
                }

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        String seleccion = arrayAdapter.getItem(which);
                        String[] arraySeleccion = seleccion.split("\\|");
                        String[] idxNombre = arraySeleccion[0].split("-");
                        //obtenemos el indice y el nombre
                        final int idx = Integer.valueOf(idxNombre[0]);
                        String strName = idxNombre[1].trim();
                        final String unidades = arraySeleccion[1].trim();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(InventarioActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("¿Qué operación desea hacer?");

                        final EditText textUnidades = new EditText(getApplicationContext());
                        textUnidades.setInputType(InputType.TYPE_CLASS_NUMBER);
                        textUnidades.setText(unidades);
                        textUnidades.setTextColor(Color.BLACK);
                        builderInner.setView(textUnidades);

                        builderInner.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                int nuevaUnidades = Integer.valueOf(textUnidades.getText().toString());
                                try {
                                    inventario.actualizaUnidadesArticulo(conn,idx,nuevaUnidades);
                                    Toast.makeText(getApplicationContext(),"Unidades actualizadas",Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),"Error al actualizar unidades",Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        });
                        builderInner.setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                try {
                                    inventario.eliminaArticulo(conn,idx);
                                    Toast.makeText(getApplicationContext(),"Articulo borrado",Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(),"Error al borrar artículo",Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        });
                        builderInner.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                });
                builderSingle.show();
            }
        });
    }







}
