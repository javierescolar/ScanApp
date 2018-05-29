package es.madisa.www.scanapp.clases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class Tienda {

    private int id;
    private String cod_tienda;
    private String nombre;

    public Tienda() { }

    public Tienda(int id, String cod_tienda, String nombre) {
        this.id = id;
        this.cod_tienda = cod_tienda;
        this.nombre = nombre;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCod_tienda() { return cod_tienda; }

    public void setCod_tienda(String cod_tienda) { this.cod_tienda = cod_tienda; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "tienda:{" +
                "'id':" + id +
                ", 'cod_tienda':'" + cod_tienda + '\'' +
                ", 'nombre':'" + nombre + '\'' +
                '}';
    }

    public static Tienda obtenerTienda(ConexionSQLiteHelper conn, String cod_tienda) throws Exception{
        Tienda tda = new Tienda();
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {cod_tienda};
        String[] camposVisualizar = {
                Utilidades.TIENDAS_CAMPO_ID,
                Utilidades.TIENDAS_CAMPO_CODTIENDA,
                Utilidades.TIENDAS_CAMPO_NOMBRE
        };
        Cursor cursor = db.query(Utilidades.TABLA_TIENDAS, camposVisualizar, Utilidades.TIENDAS_CAMPO_CODTIENDA+"=?",parametros,null,null,null);
        cursor.moveToFirst();
        tda.setId(cursor.getInt(Utilidades.TIENDAS_IDX_CAMPO_ID));
        tda.setCod_tienda(cursor.getString(Utilidades.TIENDAS_IDX_CAMPO_CODTIENDA));
        tda.setNombre(cursor.getString(Utilidades.TIENDAS_IDX_CAMPO_NOMBRE));
        db.close();
        return tda;
    }
}
