package project.presentation.sucursal;

import project.logic.Sucursal;
import project.Application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer{
    private JPanel panel;
    private JLabel mapaFld;
    private JTextField codigoFld;
    private JTextField nombreFld;
    private JTextField zonajeFld;
    private JButton agregarFld;
    private JButton cancelarFld;
    private JTextField direccionFld;
    private JLabel codigoLbl;
    private JLabel nombreLbl;
    private JLabel direccionLbl;
    private JLabel zonajeLbl;
    int pinLocationXFld;
    int pinLocationYFld;
    Polygon mapHitbox;

    public View() {
        agregarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Sucursal n = take();
                    try {
                        controller.guardar(n);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        cancelarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.hide();
            }
        });
        mapaFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    createCustomMap(e.getX(),e.getY());
                }
            }
        });
        mapHitbox=new Polygon();
        mapHitbox.addPoint(32,-11);
        mapHitbox.addPoint(49 ,-16);
        mapHitbox.addPoint(148,33);
        mapHitbox.addPoint(190 ,8);
        mapHitbox.addPoint(246,30);
        mapHitbox.addPoint(280 ,65);
        mapHitbox.addPoint(300,63);
        mapHitbox.addPoint(359 ,55);
        mapHitbox.addPoint(545,274);
        mapHitbox.addPoint(498 ,262);
        mapHitbox.addPoint(483,352);
        mapHitbox.addPoint(511 ,365);
        mapHitbox.addPoint(475,495);
        mapHitbox.addPoint(449 ,470);
        mapHitbox.addPoint(438,432);
        mapHitbox.addPoint(404 ,462);
        mapHitbox.addPoint(349,420);
        mapHitbox.addPoint(348 ,337);
        mapHitbox.addPoint(295,302);
        mapHitbox.addPoint(219 ,265);
        mapHitbox.addPoint(202,266);
        mapHitbox.addPoint(153 ,185);
        mapHitbox.addPoint(101,253);
        mapHitbox.addPoint(18 ,193);
        mapHitbox.addPoint(-16,140);
        mapHitbox.addPoint(-9 ,104);
        mapHitbox.addPoint(-5,36);
        mapHitbox.addPoint(32 ,-11);
        mapHitbox.addPoint(185 ,237);
        mapHitbox.addPoint(183,200);
        mapHitbox.addPoint(99 ,151);
        mapHitbox.addPoint(84,165);
        mapHitbox.addPoint(141 ,211);
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
        Sucursal current = model.getCurrent();
        this.codigoFld.setEnabled(model.getModo() == Application.MODO_AGREGAR);
        this.codigoFld.setText(current.getCodigo());
        nombreFld.setText(current.getNombre());
        direccionFld.setText(current.getDireccion());
        zonajeFld.setText(current.getZonaje().toString());

        if(current.getCoordX()==0&&current.getCoordY()==0) {
            this.createDefaultMap();
        }
        else{
            this.createCustomMap(current.getCoordX(), current.getCoordY());
        }

        this.panel.validate();
    }

    public void createDefaultMap(){
        try {
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            pinLocationXFld=0;
            pinLocationYFld=0;

            mapaFld.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaFld.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public void createCustomMap(int X,int Y){
        try {
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/SucursalSel.png"));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            g.drawImage(pin,X-16,Y-16,32,32,null);
            pinLocationXFld=X-16;
            pinLocationYFld=Y-16;

            mapaFld.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaFld.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public Sucursal take() {
        Sucursal e = new Sucursal();
        e.setCodigo(codigoFld.getText());
        e.setNombre(nombreFld.getText());
        e.setDireccion(direccionFld.getText());
        e.setZonaje(Double.parseDouble(zonajeFld.getText()));
        e.setCoordX(pinLocationXFld);
        e.setCoordY(pinLocationYFld);
        return e;
    }

    public static boolean isDouble(String text){
        double v;
        try {
            v=Double.parseDouble(text);
            return true;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean validate() {
        boolean valid = true;
        if (codigoFld.getText().isEmpty()) {
            valid = false;
            codigoLbl.setBorder(Application.BORDER_ERROR);
            codigoLbl.setToolTipText("Código requerido.");
        } else {
            codigoLbl.setBorder(null);
            codigoLbl.setToolTipText(null);
        }

        if (nombreFld.getText().length() == 0) {
            valid = false;
            nombreLbl.setBorder(Application.BORDER_ERROR);
            nombreLbl.setToolTipText("Nombre requerido.");
        } else {
            nombreLbl.setBorder(null);
            nombreLbl.setToolTipText(null);
        }

        if (direccionFld.getText().length() == 0) {
            valid = false;
            direccionLbl.setBorder(Application.BORDER_ERROR);
            direccionLbl.setToolTipText("Dirección requerida.");
        } else {
            direccionLbl.setBorder(null);
            direccionLbl.setToolTipText(null);
        }

        if (zonajeFld.getText().length() == 0) {
            valid = false;
            zonajeLbl.setBorder(Application.BORDER_ERROR);
            zonajeLbl.setToolTipText("Zonaje requerido.");
        } else {
            zonajeLbl.setBorder(null);
            zonajeLbl.setToolTipText(null);
        }
        if ((pinLocationYFld==0&&pinLocationXFld==0)||(mapHitbox.contains(pinLocationXFld,pinLocationYFld)==false)) {
            valid = false;
            mapaFld.setBorder(Application.BORDER_ERROR);
            ToolTipManager.sharedInstance().setEnabled(true);
            mapaFld.setToolTipText("Localización válida requerida. Introduzca una haciendo click dentro del mapa.");
        } else {
            ToolTipManager.sharedInstance().setEnabled(false);
            mapaFld.setBorder(null);
            mapaFld.setToolTipText(null);
        }
        if (isDouble(zonajeFld.getText())==false) {
            valid = false;
            zonajeLbl.setBorder(Application.BORDER_ERROR);
            zonajeLbl.setToolTipText("Zonaje base válido requerido (solo números)");
        } else {
            zonajeLbl.setBorder(null);
            zonajeLbl.setToolTipText(null);
        }
        if(valid==false){
            ToolTipManager.sharedInstance().setEnabled(true);
        }else{
            ToolTipManager.sharedInstance().setEnabled(false);
        }
        return valid;
    }
}
