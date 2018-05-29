package es.madisa.www.scanapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.madisa.www.scanapp.utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper{


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_ARTICULOS);
        db.execSQL(Utilidades.CREAR_TABLA_EANS);
        db.execSQL(Utilidades.CREAR_TABLA_TIENDAS);
        db.execSQL(Utilidades.CREAR_TABLA_INVENTARIOS);
        db.execSQL(Utilidades.CREAR_TABLA_INVENTARIO_ARTICULOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_TIENDAS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_EANS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_ARTICULOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_INVENTARIOS);
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_INVENTARIO_ARTICULOS);
        onCreate(db);
    }
}
