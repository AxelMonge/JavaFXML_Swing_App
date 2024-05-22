package project.presentation.about;

import project.presentation.about.Controller;
import project.presentation.about.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private JLabel logoFld;
    private JPanel panel;

    public void createLogo(){
        try {
            Image logo;
            logo = ImageIO.read(getClass().getResourceAsStream("../icons/aboutLogo.jpg"));
            logo.getScaledInstance(800,600, Image.SCALE_SMOOTH);

            BufferedImage result=new BufferedImage(800,600,BufferedImage.TYPE_INT_BGR);
            Graphics g=result.getGraphics();
            g.drawImage(logo,0,0,800,600,null);

            logoFld.setIcon(new ImageIcon(result));
        } catch (IOException e) {
            logoFld.setText("Ha ocurrido un error con la vista base.");
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    project.presentation.about.Controller controller;
    project.presentation.about.Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable updatedModel, Object parametros) {

    }

}
