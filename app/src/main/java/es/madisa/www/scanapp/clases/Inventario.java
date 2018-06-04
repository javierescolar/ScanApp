package es.madisa.www.scanapp.clases;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public void agregarArticulo(ConexionSQLiteHelper conn,Articulo art) throws Exception{
        this.articulos.add(art);
        this.guardaArticuloBD(conn,art);
    }

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
        //inv.setArticulos(new ArrayList<Articulo>());
        boolean enviado = (cursor.getInt(Utilidades.INVENTARIOS_IDX_CAMPO_ENVIADO) == 1)? true:false;
        inv.setEnviado(enviado);
        String cod_usuario = cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_CODUSUARIO);
        String cod_tda = cursor.getString(Utilidades.INVENTARIOS_IDX_CAMPO_CODTIENDA);
        Usuario usu = Usuario.obtenerUsuario(conn,cod_usuario);
        inv.setUsuario(usu);
        Tienda tda = Tienda.obtenerTienda(conn,cod_tda);
        inv.setTienda(tda);
        inv.recuperaArticulosInventarioBD(conn);
        return inv;
    }



    public static Inventario nuevoInventario(ConexionSQLiteHelper conn,Usuario usu) throws Exception{


        String strDate = Utilidades.fechaHoraNowString();
        SQLiteDatabase db = conn.getWritableDatabase();
        Inventario inv = new Inventario();
        ContentValues values=new ContentValues();
        values.put(Utilidades.INVENTARIOS_CAMPO_FECHA,strDate);
        values.put(Utilidades.INVENTARIOS_CAMPO_CODUSUARIO,usu.getCod_usuario());
        values.put(Utilidades.INVENTARIOS_CAMPO_CODTIENDA,usu.getTienda());
        int idResultado = (int) db.insert(Utilidades.TABLA_INVENTARIOS,Utilidades.INVENTARIOS_CAMPO_ID,values);
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


    private void guardaArticuloBD(ConexionSQLiteHelper conn,Articulo art) throws Exception{
        String strDate = Utilidades.fechaHoraNowString();

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Utilidades.INVENTARIO_ARTICULOS_CAMPO_IDINVENTARIO,this.id);
        values.put(Utilidades.INVENTARIO_ARTICULOS_CAMPO_CODARTICULO,art.getCod_articulo());
        values.put(Utilidades.INVENTARIO_ARTICULOS_CAMPO_UNIDADES,art.getUnidades());
        values.put(Utilidades.INVENTARIO_ARTICULOS_CAMPO_FECHA,strDate);
        int idResultado = (int) db.insert(Utilidades.TABLA_INVENTARIO_ARTICULOS,Utilidades.INVENTARIO_ARTICULOS_CAMPO_ID,values);
        art.setId_inventario_articulos(idResultado);
        db.close();
    }

    private void recuperaArticulosInventarioBD(ConexionSQLiteHelper conn) throws Exception{
        List<Articulo> artis = new ArrayList<>();

        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {String.valueOf(this.id)};
        String[] camposVisualizar = {
                Utilidades.INVENTARIO_ARTICULOS_CAMPO_ID,
                Utilidades.INVENTARIO_ARTICULOS_CAMPO_CODARTICULO,
                Utilidades.INVENTARIO_ARTICULOS_CAMPO_UNIDADES
        };
        Cursor cursor = db.query(Utilidades.TABLA_INVENTARIO_ARTICULOS, camposVisualizar, Utilidades.INVENTARIO_ARTICULOS_CAMPO_IDINVENTARIO+"=?",parametros,null,null,null);
        Articulo articulo = null;
        while(cursor.moveToNext()){
            int id_inventario_articulos=cursor.getInt(0);
            String cod_articulo= cursor.getString(1);
            int unidades = cursor.getInt(2);
            articulo = Articulo.obtenArticuloPorCodigo(conn,cod_articulo);
            articulo.setUnidades(unidades);
            articulo.setId_inventario_articulos(id_inventario_articulos);
            artis.add(articulo);
        }

        this.articulos= artis;


    }

    public void actualizaUnidadesArticulo(ConexionSQLiteHelper conn, int idx_articulo,int new_unidades) throws Exception{

        String id_inv_art =  String.valueOf(articulos.get(idx_articulo).getId_inventario_articulos());


        //actualizamos la tabla para persistirdatos
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {id_inv_art};
        ContentValues values = new ContentValues();
        values.put(Utilidades.INVENTARIO_ARTICULOS_CAMPO_UNIDADES,String.valueOf(new_unidades));
        String where = Utilidades.INVENTARIO_ARTICULOS_CAMPO_ID+"=?";
        db.update(Utilidades.TABLA_INVENTARIO_ARTICULOS,values,where,parametros);
        db.close();
        //actualizamos el objeto
        articulos.get(idx_articulo).setUnidades(new_unidades);

    }

    public void eliminaArticulo(ConexionSQLiteHelper conn, int idx_articulo) throws Exception{


        String id_inv_art =  String.valueOf(articulos.get(idx_articulo).getId_inventario_articulos());
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {id_inv_art};
        db.delete(Utilidades.TABLA_INVENTARIO_ARTICULOS,Utilidades.INVENTARIO_ARTICULOS_CAMPO_ID+"=?",parametros);
        db.close();
        //eliminamos el objeto
        articulos.remove(idx_articulo);
    }




}
