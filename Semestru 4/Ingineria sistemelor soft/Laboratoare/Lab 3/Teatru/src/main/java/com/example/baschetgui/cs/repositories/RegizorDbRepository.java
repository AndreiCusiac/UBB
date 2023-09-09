package com.example.baschetgui.cs.repositories;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Regizor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RegizorDbRepository implements RegizorRepository{

    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public RegizorDbRepository(Properties props) {
        logger.info("Initializing RegizorDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public boolean save(Regizor regizor) {

        logger.traceEntry("saving regizor {} ", regizor);
        Connection con = dbUtils.getConnection();

        /*
        Cu sau fara id?
         */
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Regizors (id, name, password) values (?, ?, ?)")){
            preparedStatement.setString(1, regizor.getId());
            preparedStatement.setString(2, regizor.getName());
            preparedStatement.setString(3, regizor.getPassword());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save regizor " + regizor.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting regizor with id {}", ide.getId());
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("delete from Regizors where id=?")){
            preStmt.setString(1,ide.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB trying to delete regizor " + ide.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();
        return true;
    }

    @Override
    public Regizor find(Entity ide) {

        logger.traceEntry("Entering find regizor with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Regizors where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");

                    Regizor regizor = new Regizor(id, name, password);

                    logger.traceExit(regizor);
                    return regizor;

                    //return true;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to find regizor " + ide.getId() + ": "  + ex);
        }

        return null;
    }

    @Override
    public boolean update(Entity e, Regizor newE) {
        return false;
    }

    @Override
    public ArrayList<Regizor> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Regizor> regizors = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Regizors")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");

                    Regizor regizor = new Regizor(id, name, password);
                    regizors.add(regizor);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all regizors " + "" + ": "  + ex);
        }
        logger.traceExit(regizors);

        return regizors;
    }
}
