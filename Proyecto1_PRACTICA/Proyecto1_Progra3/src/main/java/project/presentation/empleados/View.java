package project.presentation.empleados;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JButton borrarFld;
    private JButton reporteFld;
    private JTextField nombreFld;
    private JButton buscarFld;
    private JButton agregarFld;
    private JTable empleadosFld;


    public View() {
        buscarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.buscar(nombreFld.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        agregarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.preAgregar();
            }
        });
        empleadosFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = empleadosFld.getSelectedRow();
                    controller.editar(row);
                }
            }
        });
        borrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=empleadosFld.getSelectedRow();
                if(row!=-1){
                    controller.borrar(row);
                }
            }
        });
        reporteFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.imprimir();
                    if (Desktop.isDesktopSupported()) {
                        File myFile = new File("empleados.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                } catch (Exception ex) { }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object parametros) {
        int[] cols = {TableModel.CEDULA, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.SALARIOBASE,TableModel.SUCURSAL,TableModel.ZONAJE,TableModel.SALARIOTOTAL};
        empleadosFld.setModel(new TableModel(cols, model.getEmpleados()));
        empleadosFld.setRowHeight(30);
        this.panel.revalidate();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

