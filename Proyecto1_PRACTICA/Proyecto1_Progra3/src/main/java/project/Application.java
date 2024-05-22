package project;

import project.presentation.sucursales.Controller;
import project.presentation.sucursales.Model;
import project.presentation.sucursales.View;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Application {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ex) {};

        Model sucursalesModel= new Model();
        View sucursalesView = new View();
        sucursalesController = new Controller(sucursalesView,sucursalesModel);

        project.presentation.empleados.Model empleadosModel= new project.presentation.empleados.Model();
        project.presentation.empleados.View empleadosView = new project.presentation.empleados.View();
        empleadosController = new project.presentation.empleados.Controller(empleadosView,empleadosModel);


        project.presentation.about.Model aboutModel = new project.presentation.about.Model();
        project.presentation.about.View aboutView = new project.presentation.about.View();
        aboutController = new project.presentation.about.Controller(aboutView,aboutModel);

        project.presentation.empleado.Model empleadoModel= new project.presentation.empleado.Model();
        project.presentation.empleado.View empleadoView = new project.presentation.empleado.View();
        empleadoController = new project.presentation.empleado.Controller(empleadoView,empleadoModel);


        project.presentation.sucursal.Model sucursalModel= new project.presentation.sucursal.Model();
        project.presentation.sucursal.View sucursalView = new project.presentation.sucursal.View();
        sucursalController = new project.presentation.sucursal.Controller(sucursalView,sucursalModel);

        project.presentation.main.Model mainModel= new project.presentation.main.Model();
        project.presentation.main.View mainView = new project.presentation.main.View();
        mainController = new project.presentation.main.Controller(mainView, mainModel);

        mainView.getPanel().add("Empleados",empleadosView.getPanel());
        mainView.getPanel().add("Sucursales",sucursalesView.getPanel());
        mainView.getPanel().add("Acerca de",aboutView.getPanel());

        window = new JFrame();
        window.setSize(400,300);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("SISE: Sistema de Sucursales y Empleados");
        window.setVisible(true);
        aboutController.show();
        mainController.show();
    }

    public static Controller sucursalesController;
    public static project.presentation.empleados.Controller empleadosController;

    public static project.presentation.empleado.Controller empleadoController;

    public static project.presentation.about.Controller aboutController;

    public static project.presentation.sucursal.Controller sucursalController;

    public static project.presentation.main.Controller mainController;

    public static JFrame window;

    public static  final int  MODO_AGREGAR=0;
    public static final int MODO_EDITAR=1;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

}

