package es.madisa.www.scanapp.clases;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;

public class Fichero {

    private String archivo;
    private String extension;
    private Context contexto;


    public Fichero(Context contexto) {
        this.contexto = contexto;

    }

    public void leer(){
        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    contexto.openFileInput("ficheroSanApp.json")));


            while(fin.readLine()!=null){

                System.out.println(fin.readLine());
            }
            fin.close();

        }
        catch (Exception ex)
        {
            System.out.println("Ficheros - Error al leer fichero desde memoria interna");
        }
    }

    public void escribir(String texto, boolean agregar){

    }

    public void crearFichero(){

        JSONArray arrayjson = new JSONArray();
        JSONObject obj = new JSONObject();
        Articulo arti = new Articulo("0001","Pulsera", new String[]{"123","234","345","456"},0,0.0);



        try {

            obj.put("articulo",arti.json());
            System.out.println(obj);
        } catch (JSONException e) {
            System.out.println("Error crear json");
            e.printStackTrace();
        }

        try
        {
        OutputStreamWriter fout =
                new OutputStreamWriter(
                  contexto.openFileOutput("ficheroSanApp.json",Context.MODE_PRIVATE)
                );


            for(int i=0;i<10;i++){
                fout.write(obj.toString());
            }
            fout.write(obj.toString());
            fout.close();
            System.out.println("Fichero creado");
        }
        catch (Exception ex)
        {
            System.out.println("Ficheros - Error al escribir fichero a memoria interna");
        }

    }




}
