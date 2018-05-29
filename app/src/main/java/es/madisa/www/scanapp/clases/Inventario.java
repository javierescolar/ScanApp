package es.madisa.www.scanapp.clases;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class Inventario {

    private int id;
    private String cod_inventario;
    private String fecha;
    private List<Articulo> articulos;
    private Usuario usuario;
    private Tienda tienda;
    private boolean enviado;

    public Inventario() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCod_inventario() { return cod_inventario; }

    public void setCod_inventario(String cod_inventario) { this.cod_inventario = cod_inventario; }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public List<Articulo> getArticulos() { return articulos; }

    public void setArticulos(List<Articulo> articulos) { this.articulos = articulos; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Tienda getTienda() { return tienda; }

    public void setTienda(Tienda tienda) { this.tienda = tienda; }

    public boolean isEnviado() { return enviado; }

    public void setEnviado(boolean enviado) { this.enviado = enviado; }

    public void agregarArticulo(Articulo art){ this.articulos.add(art); }

    public static Inventario obtenerInventario(ConexionSQLiteHelper conn, int id_inventario) throws Exception{
        Inventario inv = new Inventario();
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {String.valueOf(id_inventario)};
        String[] camposVisualizar = {
                Utilidades.INVENTARIOS_CAMPO_ID,
                Utilidades.INVENTARIOS_CAMPO_CODINVENTARIO,
                Utilidades.INVENTARIOS_CAMPO_FECHA,
                Utilidades.INVENTARIOS_CAMPO_CODUSUARIO,
                Utilidades.INVENTARIOS_CAMPO_CODTIENDA,
                Utilidades.INVENTARIOS_CAMPO_ENVIADO
        };
        Cursor cursor = db.query(Utilidades.TABLA_INVENTARIOS, camposVisualizar, Utilidades.TIENDAS_CAMPO_ID+"=?",parametros,null,null,null);
        cursor.moveToFirst();

        inv.setId(cursor.getInt(Utilidades.INVENTARIO_ARTICULOS_IDX_CAMPO_ID));
        inv.setCod_inventario(cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_CODINVENTARIO));
        inv.setFecha(cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_FECHA));
        inv.setArticulos(new ArrayList<Articulo>());
        boolean enviado = (cursor.getInt(Utilidades.INVENTARIOS_IDX_CAMPO_ENVIADO) == 1)? true:false;
        inv.setEnviado(enviado);
        String cod_usuario = cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_CODUSUARIO);
        String cod_tda = cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_CODTIENDA);
        Usuario usu = Usuario.obtenerUsuario(conn,cod_usuario);
        inv.setUsuario(usu);
        Tienda tda = Tienda.obtenerTienda(conn,cod_tda);
        inv.setTienda(tda);

        return inv;
    }

    public static Inventario nuevoInventario(ConexionSQLiteHelper conn,Usuario usu) throws Exception{
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

        String strDate = sdf.format(date);

        sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        strDate = sdf.format(date);


        SQLiteDatabase db = conn.getWritableDatabase();
        Inventario inv = new Inventario();
        ContentValues values=new ContentValues();
        values.put(Utilidades.INVENTARIOS_CAMPO_FECHA,strDate);
        values.put(Utilidades.INVENTARIOS_CAMPO_CODUSUARIO,usu.getCod_usuario());
        values.put(Utilidades.INVENTARIOS_CAMPO_CODTIENDA,usu.getTienda());
        int idResultado = (int) db.insert(Utilidades.TABLA_INVENTARIOS,Utilidades.INVENTARIO_ARTICULOS_CAMPO_ID,values);
        inv.setId(idResultado);
        inv.setCod_inventario("");
        inv.setFecha(strDate);
        inv.setUsuario(usu);
        inv.setArticulos(new ArrayList<Articulo>());
        inv.setEnviado(false);
        //obtenemos los datos de la tienda
        Tienda tda = Tienda.obtenerTienda(conn, usu.getTienda());
        inv.setTienda(tda);
        db.close();
        return inv;
    }

    public static List<Inventario> obtenerInventarios(ConexionSQLiteHelper conn){
        List<Inventario> invs = new ArrayList();
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_INVENTARIOS,null);

        Inventario i = null;
        while(cursor.moveToNext()){
            i=new Inventario();
            i.setId(cursor.getInt(Utilidades.INVENTARIOS_IDX_CAMPO_ID));
            i.setFecha(cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_FECHA));
            invs.add(i);
        }
        return invs;
    }




}
