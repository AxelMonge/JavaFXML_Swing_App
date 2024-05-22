package project.presentation.empleado;

import project.Application;
import project.logic.Empleado;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    private JPanel panel;
    private JTextField telFld;
    private JTextField nombreFld;
    private JTextField cedulaFld;
    private JButton guardarFld;
    private JButton cancelarFld;
    private JLabel nombreLbl;
    private JLabel cedulaLbl;
    private JLabel telLbl;
    private JLabel salBaseLbl;
    private JTextField salBaseFld;
    private JLabel mapaFld;
    private List<Point> pins;

    private int validSucursalValue;
    private int tooltipValidator;

    private int selectedSucursalIndex;

    public View() {
        validSucursalValue=0;
        guardarFld.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Empleado n = take();
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
        mapaFld.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Polygon rangePin;
                int detected=0;
                String detectedData="";
                for(int i=0;i<model.getSucursals().size();i++){
                    rangePin=new Polygon();
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()+14);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-7,model.getSucursals().get(i).getCoordY()+3);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-10,model.getSucursals().get(i).getCoordY()-5);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-8,model.getSucursals().get(i).getCoordY()-13);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()-14);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()+7,model.getSucursals().get(i).getCoordY()-10);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()+8,model.getSucursals().get(i).getCoordY()-1);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()+2,model.getSucursals().get(i).getCoordY()+8);
                    rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()+14);
                    if(rangePin.contains(new Point(e.getX(),e.getY()))==true) {
                        detected=1;
                        detectedData=model.getSucursals().get(i).getData();
                    }
                }
                if ((detected==1)&&(tooltipValidator==0)){
                    ToolTipManager.sharedInstance().setEnabled(true);
                    mapaFld.setToolTipText(detectedData);
                }else{
                    if(tooltipValidator==1){
                        ToolTipManager.sharedInstance().setEnabled(true);
                    }else {
                        ToolTipManager.sharedInstance().setEnabled(false);
                    }
                }
            }
        });
        mapaFld.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    Polygon rangePin;
                    int detected=0;
                    int detectedDataIndex=0;
                    for(int i=0;i<model.getSucursals().size();i++){
                        rangePin=new Polygon();
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()+14);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-7,model.getSucursals().get(i).getCoordY()+3);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-10,model.getSucursals().get(i).getCoordY()-5);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-8,model.getSucursals().get(i).getCoordY()-13);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()-14);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()+7,model.getSucursals().get(i).getCoordY()-10);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()+8,model.getSucursals().get(i).getCoordY()-1);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()+2,model.getSucursals().get(i).getCoordY()+8);
                        rangePin.addPoint(model.getSucursals().get(i).getCoordX()-1,model.getSucursals().get(i).getCoordY()+14);
                        if(rangePin.contains(new Point(e.getX(),e.getY()))==true) {
                            detected=1;
                            detectedDataIndex=i;
                        }
                    }
                    if (detected==1){
                        createMapWithSelectedPin(detectedDataIndex);
                        selectedSucursalIndex=detectedDataIndex;
                        validSucursalValue=1;
                    }else{
                        //createMap();
                        //validSucursalValue=0;
                    }
                }else{
                    //validSucursalValue=0;
                }
            }
        });
    }

    public int getValidSucursalValue() {
        return validSucursalValue;
    }

    public void setValidSucursalValue(int validSucursalValue) {
        this.validSucursalValue = validSucursalValue;
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
        Empleado current = model.getCurrent();
        this.cedulaFld.setEnabled(model.getModo() == Application.MODO_AGREGAR);
        this.cedulaFld.setText(current.getCedula());
        this.nombreFld.setText(current.getNombre());
        this.telFld.setText(current.getTelefono());
        this.salBaseFld.setText(String.valueOf(current.getSalarioBase()));
        if (this.model.getModo()==Application.MODO_AGREGAR){
            createMap();
        }else if(this.model.getModo()==Application.MODO_EDITAR){
            try {
                createMapWithSelectedPin(controller.getSucursalIndex(current.getSucursal().getCodigo()));
                validSucursalValue=1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.panel.validate();
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
        for(int i=0;i<model.getSucursals().size();i++) {
            pins.add(new Point(model.getSucursals().get(i).getCoordX()-16,model.getSucursals().get(i).getCoordY()-16));
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

    public Empleado take() {
        Empleado e = new Empleado();
        e.setCedula(cedulaFld.getText());
        e.setNombre(nombreFld.getText());
        e.setTelefono(telFld.getText());
        e.setSalarioBase(Double.parseDouble(salBaseFld.getText()));
        e.setSucursal(model.getSucursals().get(selectedSucursalIndex));
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
        if (nombreFld.getText().isEmpty()) {
            valid = false;
            nombreLbl.setBorder(Application.BORDER_ERROR);
            nombreLbl.setToolTipText("Id requerido");
        } else {
            nombreLbl.setBorder(null);
            nombreLbl.setToolTipText(null);
        }

        if (cedulaFld.getText().length() == 0) {
            valid = false;
            cedulaLbl.setBorder(Application.BORDER_ERROR);
            cedulaLbl.setToolTipText("Cédula requerida");
        } else {
            cedulaLbl.setBorder(null);
            cedulaLbl.setToolTipText(null);
        }
        if (telFld.getText().length() == 0) {
            valid = false;
            telLbl.setBorder(Application.BORDER_ERROR);
            telLbl.setToolTipText("Teléfono requerido");
        } else {
            telLbl.setBorder(null);
            telLbl.setToolTipText(null);
        }
        if (validSucursalValue==0) {
            valid = false;
            ToolTipManager.sharedInstance().setEnabled(true);
            mapaFld.setBorder(Application.BORDER_ERROR);
            mapaFld.setToolTipText("Localización válida requerida. Introduzca una haciendo click en uno de los pines dentro del mapa.");
            tooltipValidator=1;
        } else {
            ToolTipManager.sharedInstance().setEnabled(false);
            mapaFld.setBorder(null);
            mapaFld.setToolTipText(null);
            tooltipValidator=0;
        }
        if (isDouble(salBaseFld.getText())==false) {
            valid = false;
            salBaseLbl.setBorder(Application.BORDER_ERROR);
            salBaseLbl.setToolTipText("Salario base válido requerido (solo números)");
        } else {
            salBaseLbl.setBorder(null);
            salBaseLbl.setToolTipText(null);
        }

        if(valid==false){
            ToolTipManager.sharedInstance().setEnabled(true);
        }else{
            ToolTipManager.sharedInstance().setEnabled(false);
        }

        return valid;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
