package es.madisa.www.scanapp.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilidades {
    public static final String USERADMIN="rootadmin";
    public static final String PASSADMIN="SecretAdminPass";

    //base de datos
    public static final String DB_NAME="bd_scanapp";
//==========================================================
//==========================================================
    //constantes table USUARIOS
    public static final String TABLA_USUARIOS="usuarios";
    public static final String USUARIOS_CAMPO_ID="id";
    public static final int USUARIOS_IDX_CAMPO_ID=0;
    public static final String USUARIOS_CAMPO_CODUSUARIO="cod_usuario";
    public static final int USUARIOS_IDX_CAMPO_CODUSUARIO=1;
    public static final String USUARIOS_CAMPO_NOMBRE="nombre";
    public static final int USUARIOS_IDX_CAMPO_NOMBRE=2;
    public static final String USUARIOS_CAMPO_TIENDA="cod_tienda";
    public static final int USUARIOS_IDX_CAMPO_TIENDA=3;
    public static final String USUARIOS_CAMPO_PASS="password";
    public static final int USUARIOS_IDX_CAMPO_PASS=4;

    //Constantes tabla ARTICULOS
    public static final String TABLA_ARTICULOS="articulos";
    public static final String ARTICULOS_CAMPO_ID="id";
    public static final int ARTICULOS_IDX_CAMPO_ID=0;
    public static final String ARTICULOS_CAMPO_CODARTICULO="cod_articulo";
    public static final int ARTICULOS_IDX_CAMPO_CODARTICULO=1;
    public static final String ARTICULOS_CAMPO_NOMBRE="nombre";
    public static final int ARTICULOS_IDX_CAMPO_NOMBRE=2;

    //Constantes tabla EANS ARTICULOS
    public static final String TABLA_EANS="eans";
    public static final String EANS_CAMPO_ID="id";
    public static final int EANS_IDX_CAMPO_ID=0;
    public static final String EANS_CAMPO_CODARTICULO="cod_articulo";
    public static final int EANS_IDX_CAMPO_CODARTICULO=1;
    public static final String EANS_CAMPO_EAN="ean";
    public static final int EANS_IDX_CAMPO_NUMEAN=2;

    //Constantes tabla TIENDAS
    public static final String TABLA_TIENDAS="tiendas";
    public static final String TIENDAS_CAMPO_ID="id";
    public static final int TIENDAS_IDX_CAMPO_ID=0;
    public static final String TIENDAS_CAMPO_CODTIENDA="cod_tienda";
    public static final int TIENDAS_IDX_CAMPO_CODTIENDA=1;
    public static final String TIENDAS_CAMPO_NOMBRE="nombre";
    public static final int TIENDAS_IDX_CAMPO_NOMBRE=2;

    //Constantes tabla INVENTARIOS
    public static final String TABLA_INVENTARIOS="inventarios";
    public static final String INVENTARIOS_CAMPO_ID="id";
    public static final int INVENTARIOS_IDX_CAMPO_ID=0;
    public static final String INVENTARIOS_CAMPO_CODINVENTARIO="cod_inventario";
    public static final int INVENTARIOS_IDX_CAMPO_CODINVENTARIO=1;
    public static final String INVENTARIOS_CAMPO_FECHA="fecha";
    public static final int INVENTARIOS_IDX_CAMPO_FECHA=2;
    public static final String INVENTARIOS_CAMPO_CODUSUARIO="cod_usuario";
    public static final int INVENTARIOS_IDX_CAMPO_CODUSUARIO=3;
    public static final String INVENTARIOS_CAMPO_CODTIENDA="cod_tienda";
    public static final int INVENTARIOS_IDX_CAMPO_CODTIENDA=4;
    public static final String INVENTARIOS_CAMPO_ENVIADO="enviado";
    public static final int INVENTARIOS_IDX_CAMPO_ENVIADO=5;

//================================================================
//================================================================
//GENERAMOS LAS TABLAS N:M DE INVENTARIO ARTICULOS
//Constantes tabla INVENTARIO_ARTICULOS

    public static final String TABLA_INVENTARIO_ARTICULOS="inventario_articulos";
    public static final String INVENTARIO_ARTICULOS_CAMPO_ID="id";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_ID=0;
    public static final String INVENTARIO_ARTICULOS_CAMPO_IDINVENTARIO="id_inventario";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_IDINVENTARIO=1;
    public static final String INVENTARIO_ARTICULOS_CAMPO_CODINVENTARIO="cod_inventario";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_CODINVENTARIO=2;
    public static final String INVENTARIO_ARTICULOS_CAMPO_CODARTICULO="cod_articulo";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_CODARTICULO=3;
    public static final String INVENTARIO_ARTICULOS_CAMPO_UNIDADES="unidades";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_UNIDADES=4;
    public static final String INVENTARIO_ARTICULOS_CAMPO_FECHA="fecha";
    public static final int INVENTARIO_ARTICULOS_IDX_CAMPO_FECHA=5;

/*
    Es necesario diferenciar que en esta tabla necesitamos guardar el id del inventario que se esta realizando en el terminal
    y que solo una vez enviemos agsbase dicho inventario actualizaremos el cod_inventario
 */

//================================================================
//================================================================


    //creacion de tablas
    public static final String CREAR_TABLA_USUARIOS =
            "CREATE TABLE "+TABLA_USUARIOS+" ( "
                    +USUARIOS_CAMPO_ID+" INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +USUARIOS_CAMPO_CODUSUARIO+" TEXT, "
                    +USUARIOS_CAMPO_NOMBRE+" TEXT, "
                    +USUARIOS_CAMPO_TIENDA+" TEXT, "
                    +USUARIOS_CAMPO_PASS+" TEXT "
                    +")";

    public static final String CREAR_TABLA_ARTICULOS =
            "CREATE TABLE "+TABLA_ARTICULOS+" ( "
                    +ARTICULOS_CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +ARTICULOS_CAMPO_CODARTICULO+" TEXT, "
                    +ARTICULOS_CAMPO_NOMBRE+" TEXT"
                    +")";

    public static final String CREAR_TABLA_EANS =
            "CREATE TABLE "+TABLA_EANS+" ( "
                    +EANS_CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +EANS_CAMPO_CODARTICULO+" TEXT, "
                    +EANS_CAMPO_EAN+" TEXT"
                    +")";


    public static final String CREAR_TABLA_TIENDAS =
            "CREATE TABLE "+TABLA_TIENDAS+" ( "
                    +TIENDAS_CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +TIENDAS_CAMPO_CODTIENDA+" TEXT, "
                    +TIENDAS_CAMPO_NOMBRE+" TEXT"
                    +")";

    public static final String CREAR_TABLA_INVENTARIOS =
            "CREATE TABLE "+TABLA_INVENTARIOS+" ( "
                    +INVENTARIOS_CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +INVENTARIOS_CAMPO_CODINVENTARIO+" TEXT, "
                    +INVENTARIOS_CAMPO_FECHA+" TEXT, "
                    +INVENTARIOS_CAMPO_CODUSUARIO+" TEXT, "
                    +INVENTARIOS_CAMPO_CODTIENDA+" TEXT, "
                    +INVENTARIOS_CAMPO_ENVIADO+" INTEGER "
                    +")";

    public static final String CREAR_TABLA_INVENTARIO_ARTICULOS =
            "CREATE TABLE "+TABLA_INVENTARIO_ARTICULOS+" ( "
                    +INVENTARIO_ARTICULOS_CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                    +INVENTARIO_ARTICULOS_CAMPO_IDINVENTARIO+" INTEGER, "
                    +INVENTARIO_ARTICULOS_CAMPO_CODINVENTARIO+" TEXT, "
                    +INVENTARIO_ARTICULOS_CAMPO_CODARTICULO+" TEXT, "
                    +INVENTARIO_ARTICULOS_CAMPO_UNIDADES+" INTEGER, "
                    +INVENTARIO_ARTICULOS_CAMPO_FECHA+" TEXT "
                    +")";


    public static String fechaHoraNowString(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

        String strDate = sdf.format(date);

        sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        strDate = sdf.format(date);

        return strDate;
    }
}
