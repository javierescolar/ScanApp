package es.madisa.www.scanapp.clases;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import es.madisa.www.scanapp.sqlite.ConexionSQLiteHelper;
import es.madisa.www.scanapp.utilidades.Utilidades;

public class Usuario implements Parcelable {

    private int id;
    private String cod_usuario;
    private String nombre;
    private String tienda;

    public Usuario(){}

    public Usuario(int id, String cod_usuario, String nombre, String tienda) {
        this.id = id;
        this.cod_usuario = cod_usuario;
        this.nombre = nombre;
        this.tienda = tienda;
    }

    public Usuario(Parcel in) {
        this.id = in.readInt();
        this.cod_usuario = in.readString();
        this.nombre = in.readString();
        this.tienda = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCod_usuario() { return cod_usuario; }

    public void setCod_usuario(String cod_usuario) { this.cod_usuario = cod_usuario; }

    public String getTienda() { return tienda; }

    public void setTienda(String tienda) { this.tienda = tienda; }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", cod_usuario='" + cod_usuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tienda='" + tienda + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new Parcelable.Creator<Usuario>(){
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(cod_usuario);
        parcel.writeString(nombre);
        parcel.writeString(tienda);

    }

    public static Usuario obtenerUsuario(ConexionSQLiteHelper conn, String cod_usuario) throws Exception{
        Usuario usu = new Usuario();
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] parametros = {cod_usuario};
        String[] camposVisualizar = {
                Utilidades.USUARIOS_CAMPO_ID,
                Utilidades.USUARIOS_CAMPO_CODUSUARIO,
                Utilidades.USUARIOS_CAMPO_NOMBRE,
                Utilidades.USUARIOS_CAMPO_TIENDA
        };
        Cursor cursor = db.query(Utilidades.TABLA_USUARIOS, camposVisualizar, Utilidades.USUARIOS_CAMPO_CODUSUARIO+"=?",parametros,null,null,null);
        cursor.moveToFirst();
        usu.setId(cursor.getInt(Utilidades.USUARIOS_IDX_CAMPO_ID));
        usu.setCod_usuario(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_CODUSUARIO));
        usu.setNombre(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_NOMBRE));
        usu.setTienda(cursor.getString(Utilidades.USUARIOS_IDX_CAMPO_TIENDA));
        return usu;
    }
}
