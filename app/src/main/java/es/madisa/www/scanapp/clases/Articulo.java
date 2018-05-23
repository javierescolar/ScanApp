package es.madisa.www.scanapp.clases;

import org.json.JSONArray;

import java.util.Arrays;

public class Articulo {

    private String id;
    private String nombre;
    private String[] referencias;
    private int unidades;
    private double precio;

    public Articulo(){}

    public Articulo(String id, String nombre, String[] referencias, int unidades, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.referencias = referencias;
        this.unidades = unidades;
        this.precio = precio;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() {return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String[] getReferencias() { return referencias; }

    public void setReferencias(String[] referencias) { this.referencias = referencias; }

    public int getUnidades() { return unidades; }

    public void setUnidades(int unidades) { this.unidades = unidades; }

    public double getPrecio() { return precio; }

    public void setPrecio(double precio) { this.precio = precio; }


    @Override
    public String toString() {
        return "Articulo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", referencias=" + Arrays.toString(referencias) +
                ", unidades=" + unidades +
                ", precio=" + precio +
                '}';
    }

    public String json(){
        return " { "+
                "'id':'"+id+"',"
                +"'nombre':'"+nombre+"',"
                +"'referencias':"+new JSONArray(Arrays.asList(referencias))+"',"
                +"'unidades':'"+String.valueOf(unidades)+"',"
                +"'precio':'"+String.valueOf(precio)+"'"
                +"}";
    }

}
