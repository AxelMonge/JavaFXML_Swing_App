package project.logic;

import project.data.*;

import project.data.Data;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {

    private static Service theInstance;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private SucursalDao sucursalDao;

    private EmpleadoDao empleadoDao;

    private Data data;

    private Service() {
        //data = new Data();
        try {
            data = XmlPersister.instance().load();
            sucursalDao= new SucursalDao();
            empleadoDao= new EmpleadoDao();
        } catch (Exception e) {
            data = new Data();
        }
    }

    public List<Empleado> empleadosSearch(String filtro) throws Exception{
        return empleadoDao.findByReferencia(filtro);
    }

    public Empleado empleadoGet(String cedula) throws Exception {
        Empleado result = empleadoDao.findByReferencia("").stream().filter(e -> e.getCedula().equals(cedula)).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Empleado no existe");
    }

    public void empleadoAdd(Empleado empleado) throws Exception {
        Empleado result = empleadoDao.findByReferencia("").stream().filter(e -> e.getCedula().equals(empleado.getCedula())).findFirst().orElse(null);
        if (result == null) empleadoDao.create(empleado);
        else throw new Exception("Empleado ya existe");
    }

    public void empleadoUpdate(Empleado empleado) throws Exception {
        try {
            empleadoDao.update(empleado);
        } catch (Exception e) {
            throw new Exception("Empleado no existe");
        }
    }

    public void empleadoDelete(Empleado empleado) throws Exception {
        Empleado result = empleadoDao.findByReferencia("").stream().filter(e -> e.getCedula().equals(empleado.getCedula())).findFirst().orElse(null);
        if (result != null) {empleadoDao.delete(result);}
        else {throw new Exception("El empleado no existe");}
    }

    public List<Sucursal> sucursalesSearch(String filtro) throws Exception{
        return sucursalDao.findByReferencia(filtro);
    }

    public Sucursal sucursalGet(String codigo) throws Exception{
        Sucursal result = sucursalDao.findByReferencia("").stream().filter(s -> s.getCodigo().equals(codigo)).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Sucursal no existe");
    }

    public void sucursalAdd(Sucursal sucursal) throws Exception{
        Sucursal result = sucursalDao.findByReferencia("").stream().filter(s -> s.getCodigo().equals(sucursal.getCodigo())).findFirst().orElse(null);
        if (result == null) sucursalDao.create(sucursal);
        else throw new Exception("Sucursal ya existe");
    }

    public void sucursalDelete(Sucursal sucursal) throws Exception{
        Sucursal result = sucursalDao.findByReferencia("").stream().filter(e->e.getCodigo().equals(sucursal.getCodigo())).findFirst().orElse(null);
        boolean validate=false;
        if(empleadoDao.findByReferencia("").size()==0){
            sucursalDao.delete(result);
        }else{
            for(int i=0;i<empleadoDao.findByReferencia("").size();i++){
                if(empleadoDao.findByReferencia("").get(i).getSucursal()==result){
                    validate=true;
                }
            }
            if(validate==true){
                throw new Exception("La sucursal no se puede borrar puesto que tiene empleados vinculados.");
            }else{
                sucursalDao.delete(result);
            }
        }
    }

    public void sucursalUpdate(Sucursal sucursal) throws Exception{
        try {
            sucursalDao.update(sucursal);
        } catch (Exception e) {
            throw new Exception("Sucursal no existe");
        }
    }

    public void store(){
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void refresh(){
        try{
            data= XmlPersister.instance().load();
        }
        catch(Exception e){
            data =  new Data();
        }
    }
}



