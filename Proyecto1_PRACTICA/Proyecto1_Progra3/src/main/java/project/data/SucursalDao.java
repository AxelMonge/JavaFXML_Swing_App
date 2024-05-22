package project.data;

import project.logic.Sucursal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SucursalDao {
    Database db;

    public SucursalDao() {
        db = Database.instance();
    }

    public void create(Sucursal e) throws Exception {
        String sql = "insert into " +
                "Sucursal " +
                "(codigo, nombre, direccion, zonaje, coordX, coordY) " +
                "values(?,?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        stm.setString(2, e.getNombre());
        stm.setString(3, e.getDireccion());
        stm.setDouble(4, e.getZonaje());
        stm.setInt(5, e.getCoordX());
        stm.setInt(6, e.getCoordY());

        db.executeUpdate(stm);
    }

    public Sucursal read(String codigo) throws Exception {
        String sql = "select " +
                "* " +
                "from  Sucursal s " +
                "where s.codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "s");
        } else {
            throw new Exception("SUCURSAL NO EXISTE");
        }
    }

    public void update(Sucursal e) throws Exception {
        String sql = "update " +
                "Sucursal " +
                "set nombre=?, direccion=?, zonaje=?, coordX=?, coordY=?  " +
                "where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNombre());
        stm.setString(2, e.getDireccion());
        stm.setDouble(3, e.getZonaje());
        stm.setInt(4, e.getCoordX());
        stm.setInt(5, e.getCoordY());
        stm.setString(6, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("SUCURSAL NO EXISTE");
        }

    }

    public void delete(Sucursal e) throws Exception {
        String sql = "delete " +
                "from Sucursal " +
                "where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("SUCURSAL NO EXISTE");
        }
    }

    public List<Sucursal> findByReferencia(String referencia) throws Exception {
        List<Sucursal> resultado = new ArrayList<Sucursal>();
        String sql = "select * " +
                "from " +
                "sucursal s " +
                "where s.nombre like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + referencia + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            resultado.add(from(rs, "s"));
        }
        return resultado;
    }

    public Sucursal from(ResultSet rs, String alias) throws Exception {
        Sucursal e = new Sucursal();
        e.setCodigo(rs.getString(alias + ".codigo"));
        e.setNombre(rs.getString(alias + ".nombre"));
        e.setDireccion(rs.getString(alias + ".direccion"));
        e.setZonaje(rs.getDouble(alias + ".zonaje"));
        e.setCoordX(rs.getInt(alias + ".coordX"));
        e.setCoordY(rs.getInt(alias + ".coordY"));
        return e;
    }

}
