package es.madisa.www.scanapp.clases;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Inventario {

    private static int key = 0;
    private String id;
    private Date fecha;
    private String tienda;
    private List<Articulo> articulos;

    public Inventario(){
        articulos = new ArrayList<Articulo>();
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public Date getFecha() { return fecha; }

    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getTienda() { return tienda; }

    public void setTienda(String tienda) { this.tienda = tienda; }

    public List<Articulo> getArticulos() { return articulos; }

    public void setArticulos(List<Articulo> articulos) { this.articulos = articulos; }

    public void agregarArticulo(Articulo articulo){
        int key = buscarArticulo(articulo.getId());
        if(key >= 0){
            this.articulos.remove(key);
        }
        this.articulos.add(articulo);
    }

    public int buscarArticulo(String codigo){

        int key = -1;
        for(int i=0;i<articulos.size();i++){
            if( articulos.get(i).getId().equals(codigo) ){
                key=i;
                return key;
            }
        }
        return key;
    }

    public Articulo traerArticulo(int key){
        return articulos.get(key);
    }

    public void mostrarArticulos(){
        for(Articulo art:articulos){
            System.out.println(art.toString());
        }
    }

}
