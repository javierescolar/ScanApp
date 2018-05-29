package es.madisa.www.scanapp.clases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class Ean {

    private int id;
    private String cod_articulo;
    private String num_ean;

    public Ean() { }

    public Ean(int id, String cod_articulo, String num_ean) {
        this.id = id;
        this.cod_articulo = cod_articulo;
        this.num_ean = num_ean;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getCod_articulo() { return cod_articulo; }

    public void setCod_articulo(String cod_articulo) { this.cod_articulo = cod_articulo; }

    public String getNum_ean() { return num_ean; }

    public void setNum_ean(String num_ean) { this.num_ean = num_ean; }

    public static List<Ean> obtenerEansPorCodArticulo(ConexionSQLiteHelper conn, String cod_articulo) throws Exception{
        List<Ean> eans = new ArrayList<>();
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_EANS +" WHERE "+Utilidades.EANS_CAMPO_CODARTICULO+" = "+cod_articulo ,null);

        Ean ean = null;
        while(cursor.moveToNext()){
            ean=new Ean();
            ean.setId(cursor.getInt(Utilidades.EANS_IDX_CAMPO_ID));
            ean.setCod_articulo(cursor.getString(Utilidades.EANS_IDX_CAMPO_CODARTICULO));
            ean.setNum_ean(cursor.getString(Utilidades.EANS_IDX_CAMPO_NUMEAN));
            eans.add(ean);
        }
        return eans;
    }


}
