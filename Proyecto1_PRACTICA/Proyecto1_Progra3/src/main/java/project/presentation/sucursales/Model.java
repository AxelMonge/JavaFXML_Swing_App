package project.presentation.sucursales;

import project.logic.Sucursal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<Sucursal> sucursales;

    public Model() {
        this.setSucursales(new ArrayList<Sucursal>());
    }

    public void setSucursales(List<Sucursal> sucursales){
        this.sucursales = sucursales;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }
}
