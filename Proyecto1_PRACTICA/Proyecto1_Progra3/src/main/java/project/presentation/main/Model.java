package project.presentation.main;

import java.util.Observable;

public class Model extends Observable{

    @Override
    public void addObserver(java.util.Observer o) {
        super.addObserver(o);
        commit();
    }

    public Model() {

    }

    public void commit(){
        setChanged();
        notifyObservers(null);
    }

}
