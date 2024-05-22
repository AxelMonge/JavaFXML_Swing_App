package project.presentation.about;

import project.Application;
import project.presentation.about.Model;
import project.presentation.about.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    project.presentation.about.Model model;
    project.presentation.about.View view;

    public Controller(View view, Model model) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        view.setController(this);
    }

    public void show(){
        view.createLogo();
    }
}
