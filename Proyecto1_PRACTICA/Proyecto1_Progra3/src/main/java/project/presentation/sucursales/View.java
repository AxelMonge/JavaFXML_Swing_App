package project.presentation.sucursales;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer{
    private JPanel panel;
    private JTextField nombreFld;
    private JButton buscarFld;
    private JButton agregarFld;
    private JButton borrarFld;
    private JButton reporteFld;
    private JTable sucursalesFld;
    private JLabel mapaFld;

    private List<Point> pins;

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
        borrarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=sucursalesFld.getSelectedRow();
                if(row!=-1){
                    try {
                        controller.borrar(row);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "No se pudo borrar esta sucursal, puesto que tiene empleados vinculados.","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        reporteFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.imprimir();
                    if (Desktop.isDesktopSupported()) {
                        File myFile = new File("sucursales.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                } catch (Exception ex) { }
            }
        });
        sucursalesFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    createMapWithSelectedPin(sucursalesFld.getSelectedRow());
                }
                if (e.getClickCount() == 2) {
                    int row = sucursalesFld.getSelectedRow();
                    controller.editar(row);
                }
            }
        });
        mapaFld.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Polygon rangePin;
                int detected=0;
                String detectedData="";
                for(int i=0;i<model.getSucursales().size();i++){
                    rangePin=new Polygon();
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-1,model.getSucursales().get(i).getCoordY()+14);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-7,model.getSucursales().get(i).getCoordY()+3);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-10,model.getSucursales().get(i).getCoordY()-5);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-8,model.getSucursales().get(i).getCoordY()-13);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-1,model.getSucursales().get(i).getCoordY()-14);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()+7,model.getSucursales().get(i).getCoordY()-10);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()+8,model.getSucursales().get(i).getCoordY()-1);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()+2,model.getSucursales().get(i).getCoordY()+8);
                    rangePin.addPoint(model.getSucursales().get(i).getCoordX()-1,model.getSucursales().get(i).getCoordY()+14);
                    if(rangePin.contains(new Point(e.getX(),e.getY()))==true) {
                        detected=1;
                        detectedData=model.getSucursales().get(i).getData();
                    }
                }
                if (detected==1){
                    ToolTipManager.sharedInstance().setEnabled(true);
                    mapaFld.setToolTipText(detectedData);
                }else{
                    ToolTipManager.sharedInstance().setEnabled(false);
                }
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
        int[] cols = {TableModel.CODIGO, TableModel.NOMBRE,TableModel.DIRECCION,TableModel.ZONAJE};
        sucursalesFld.setModel(new TableModel(cols, model.getSucursales()));
        sucursalesFld.setRowHeight(30);
        createMap();
        this.panel.revalidate();
    }

    public void createMap(){
        try {
            pins= new ArrayList<Point>();
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/Sucursal.png"));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            for(int i=0;i<pins.size();i++){
                g.drawImage(pin,(int)(pins.get(i).getX()), (int)(pins.get(i).getY()),32,32,null);
            }

            mapaFld.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaFld.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public void createPins(){
        for(int i=0;i<model.getSucursales().size();i++) {
            pins.add(new Point(model.getSucursales().get(i).getCoordX(),model.getSucursales().get(i).getCoordY()));
        }
    }

    public BufferedImage getMap() {
        try {
            pins = new ArrayList<Point>();
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564, 521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/Sucursal.png"));
            pin.getScaledInstance(32, 32, Image.SCALE_SMOOTH);

            BufferedImage result = new BufferedImage(564, 521, BufferedImage.TYPE_INT_BGR);
            Graphics g = result.getGraphics();
            g.drawImage(mapa, 0, 0, 564, 521, null);

            for (int i = 0; i < pins.size(); i++) {
                g.drawImage(pin, (int) (pins.get(i).getX()), (int) (pins.get(i).getY()), 32, 32, null);
            }

            return result;
        } catch (IOException e) {
            return null;
        }
    }

    public void createMapWithSelectedPin(int index){
        try {
            pins= new ArrayList<Point>();
            Image mapa;
            mapa = ImageIO.read(getClass().getResourceAsStream("../icons/mapa.jpg"));
            mapa.getScaledInstance(564,521, Image.SCALE_SMOOTH);

            this.createPins();

            Image pin;
            pin = ImageIO.read(getClass().getResourceAsStream("../icons/Sucursal.png"));
            pin.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            Image pinSelected;
            pinSelected = ImageIO.read(getClass().getResourceAsStream("../icons/SucursalSel.png"));
            pinSelected.getScaledInstance(32,32, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(564,521,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(mapa,0,0,564,521,null);

            for(int i=0;i<pins.size();i++){
                if(i==index){
                    g.drawImage(pinSelected,(int)(pins.get(i).getX()), (int)(pins.get(i).getY()),32,32,null);
                }else{
                    g.drawImage(pin,(int)(pins.get(i).getX()), (int)(pins.get(i).getY()),32,32,null);
                }
            }

            mapaFld.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            mapaFld.setText("Ha ocurrido un error con la vista base.");
        }
    }
}
