package project.data;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import project.logic.Sucursal;
import project.logic.Empleado;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    private List<Sucursal> sucursales;
    private List<Empleado> empleados;

    public Data() {
        sucursales = new ArrayList<>();
        empleados = new ArrayList<>();

        /*sucursales.add(new Sucursal("111", "Sucursal de Heredia", "Heredia centro", 20.0, 50, 50));
        sucursales.add(new Sucursal("222", "Sucursal de Alajuela", "Alajuela centro", 30.0, 70, 70));
        sucursales.add(new Sucursal("333", "Sucursal de Limón", "Limón centro", 10.0, 10, 10));*/


    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
}
