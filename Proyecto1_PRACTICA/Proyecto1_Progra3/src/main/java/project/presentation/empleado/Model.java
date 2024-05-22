package project.presentation.empleado;

import project.logic.Empleado;
import project.logic.Sucursal;

import java.util.List;

public class Model extends java.util.Observable{
    Empleado current;
    List<Sucursal> sucursals;
    int modo;

    public Model() {
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public Empleado getCurrent() {
        return current;
    }

    public void setCurrent(Empleado current) {
        this.current = current;
    }

    public List<Sucursal> getSucursals() {
        return sucursals;
    }

    public void setSucursals(List<Sucursal> sucursals) {
        this.sucursals = sucursals;
    }

    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        this.commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }
}
