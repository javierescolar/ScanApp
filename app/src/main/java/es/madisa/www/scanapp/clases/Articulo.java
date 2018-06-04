package es.madisa.www.scanapp.clases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class Articulo {

    private int id;
    private String cod_articulo;
    private String nombre;
    private List<Ean> eans;
    private int unidades;
    private int id_inventario_articulos;



    public Articulo(){}

    public Articulo(int id, String cod_articulo, String nombre, List<Ean> eans, int unidades) {
        this.id = id;
        this.cod_articulo = cod_articulo;
        this.nombre = nombre;
        this.eans = eans;
        this.unidades = unidades;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCod_articulo() { return cod_articulo; }

    public void setCod_articulo(String cod_articulo) { this.cod_articulo = cod_articulo; }

    public String getNombre() {return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Ean> getEans() { return eans; }

    public void setEans(List<Ean> eans) { this.eans = eans; }

    public int getUnidades() { return unidades; }

    public void setUnidades(int unidades) { this.unidades = unidades; }

    public void agregarReferecia(Ean ean){ this.eans.add(ean); }

    public int getId_inventario_articulos() {
        return id_inventario_articulos;
    }

    public void setId_inventario_articulos(int id_inventario_articulos) {
        this.id_inventario_articulos = id_inventario_articulos;
    }

    public static Articulo obtenArticuloPorCodigo(ConexionSQLiteHelper conn, String cod_articulo) throws Exception{
        Articulo art = new Articulo();

        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {cod_articulo};
        String[] camposVisualizar = {
                Utilidades.ARTICULOS_CAMPO_ID,
                Utilidades.ARTICULOS_CAMPO_CODARTICULO,
                Utilidades.ARTICULOS_CAMPO_NOMBRE
        };
        Cursor cursor = db.query(Utilidades.TABLA_ARTICULOS, camposVisualizar, Utilidades.ARTICULOS_CAMPO_CODARTICULO+"=?",parametros,null,null,null);
        cursor.moveToFirst();
        art.setId(cursor.getInt(Utilidades.ARTICULOS_IDX_CAMPO_ID));
        art.setCod_articulo(cursor.getString(Utilidades.ARTICULOS_IDX_CAMPO_CODARTICULO));
        art.setNombre(cursor.getString(Utilidades.ARTICULOS_IDX_CAMPO_NOMBRE));
        List<Ean> eans = Ean.obtenerEansPorCodArticulo(conn,cod_articulo);
        art.setEans(eans);
        db.close();
 ;
        return art;
    }

    public static Articulo obtenerArticuloPorReferencia(ConexionSQLiteHelper conn, String referencia) throws Exception{
        Articulo art = new Articulo();
        Ean eanEncontrado = Ean.buscarEan(conn,referencia);
        art = Articulo.obtenArticuloPorCodigo(conn,eanEncontrado.getCod_articulo());
        return art;
    }

}
