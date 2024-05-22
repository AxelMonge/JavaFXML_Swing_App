package project.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Sucursal {
    @XmlID
    String codigo;
    String nombre;
    String direccion;
    Double zonaje;
    int coordX;
    int coordY;

    public Sucursal(String codigo, String nombre, String direccion, Double zonaje, int coordX, int coordY) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.zonaje = zonaje;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Sucursal() {
        this("","","",0.0,0,0);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getZonaje() {
        return zonaje;
    }

    public void setZonaje(Double zonaje) {
        this.zonaje = zonaje;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public String getData() {
        return nombre+", Código: "+codigo+", "+"Dirección: "+direccion+", "+"Zonaje: "+zonaje+".";
    }

}
