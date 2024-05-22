package project.presentation.empleado;

import project.Application;
import project.logic.Empleado;
import project.logic.Service;
import project.logic.Sucursal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) throws Exception {
        model.setCurrent(new Empleado());
        model.setSucursals(Service.instance().sucursalesSearch(""));

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void preAgregar(){
        model.setModo(Application.MODO_AGREGAR);
        model.setCurrent(new Empleado());
        view.setValidSucursalValue(0);
        model.commit();
        this.show();
    }

    JDialog dialog;
    public void show(){
        dialog = new JDialog(Application.window,"Empleado", true);
        dialog.setSize(500+340,220+360);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setContentPane(view.getPanel());
        Point location = Application.window.getLocation();
        dialog.setLocation( location.x+400,location.y+100);
        dialog.setVisible(true);
    }

    public void hide(){
        dialog.dispose();
    }

    public void show1(){
        Application.window.setContentPane(view.getPanel());
        Application.window.revalidate();
    }
    public void hide1(){
        Application.mainController.show();
    }

    public void guardar(Empleado e) throws Exception {
        switch (model.getModo()) {
            case Application.MODO_AGREGAR:
                Service.instance().empleadoAdd(e);
                model.setCurrent(new Empleado());
                break;
            case Application.MODO_EDITAR:
                Service.instance().empleadoUpdate(e);
                model.setCurrent(e);
                break;
        }
        Service.instance().store();
        Application.empleadosController.buscar("");
        Application.sucursalesController.buscar("");
        model.commit();
    }

    public void editar(Empleado e){
        model.setModo(Application.MODO_EDITAR);
        model.setCurrent(e);
        view.setValidSucursalValue(1);
        model.commit();
        this.show();
    }
    public void refresh() throws Exception {
        model.setSucursals(Service.instance().sucursalesSearch(""));
    }
    public int getSucursalIndex(String cod) throws Exception {
        List<Sucursal> lista=Service.instance().sucursalesSearch("");
        for(int i=0;i<lista.size();i++){
            if(lista.get(i).getCodigo().equals(cod)){
                return i;
            }
        }
        return -1;
    }
}