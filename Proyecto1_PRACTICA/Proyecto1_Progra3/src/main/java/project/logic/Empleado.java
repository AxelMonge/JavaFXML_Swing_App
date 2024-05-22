package project.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Empleado {

    String cedula;
    String nombre;

    String telefono;
    double salarioBase;

    double salarioTotal;

    @XmlIDREF
    Sucursal sucursal;


    public Empleado(String cedula, String nombre, String telefono, double salarioBase, Sucursal sucursal) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono= telefono;
        this.salarioBase=salarioBase;
        this.sucursal=sucursal;
        this.salarioTotal=calculaSalarioTotal(this.sucursal.getZonaje());

    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Empleado() { this("","","",0,new Sucursal());}

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public double getSalarioTotal() {
        return salarioTotal;
    }

    public void setSalarioTotal(double salarioTotal) {
        this.salarioTotal = salarioTotal;
    }


    public double calculaSalarioTotal(double e){
        double total;
        total = getSalarioBase()+(getSalarioBase()*(e/100));
        return total;
    }
}


