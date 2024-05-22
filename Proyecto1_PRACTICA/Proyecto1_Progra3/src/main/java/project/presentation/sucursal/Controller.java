package project.presentation.sucursal;

import project.Application;
import project.logic.Sucursal;
import project.logic.Service;

import javax.swing.*;
import java.awt.*;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.setCurrent(new Sucursal());

        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void preAgregar(){
        model.setModo(Application.MODO_AGREGAR);
        model.setCurrent(new Sucursal());
        model.commit();
        this.show();
    }

    JDialog dialog;
    public void show(){
        dialog = new JDialog(Application.window,"Sucursal", true);
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

    public void guardar(Sucursal s) throws Exception {
        switch (model.getModo()) {
            case Application.MODO_AGREGAR:
                Service.instance().sucursalAdd(s);
                model.setCurrent(new Sucursal());
                break;
            case Application.MODO_EDITAR:
                Service.instance().sucursalUpdate(s);
                model.setCurrent(s);
                break;
        }
        Service.instance().store();
        Service.instance().refresh();
        Application.sucursalesController.buscar("");
        Application.empleadosController.buscar("");
        Application.empleadosController.refresh();
        Application.empleadoController.refresh();
        model.commit();
        this.hide();
    }

    public void editar(Sucursal s){
        model.setModo(Application.MODO_EDITAR);
        model.setCurrent(s);
        model.commit();
        this.show();
    }
}
