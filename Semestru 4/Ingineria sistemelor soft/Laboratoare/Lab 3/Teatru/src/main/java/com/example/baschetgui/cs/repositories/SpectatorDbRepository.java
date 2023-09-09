package com.example.baschetgui.cs.repositories;

import com.example.baschetgui.cs.models.Entity;
import com.example.baschetgui.cs.models.Regizor;
import com.example.baschetgui.cs.models.Spectator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class SpectatorDbRepository implements SpectatorRepository{

    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public SpectatorDbRepository(Properties props) {
        logger.info("Initializing SpectatorDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public boolean save(Spectator spectator) {

        logger.traceEntry("saving spectator {} ", spectator);
        Connection con = dbUtils.getConnection();

        /*
        Cu sau fara id?
         */
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Spectators (id, name, password) values (?, ?, ?)")){
            preparedStatement.setString(1, spectator.getId());
            preparedStatement.setString(2, spectator.getName());
            preparedStatement.setString(3, spectator.getPassword());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save spectator " + spectator.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting spectator with id {}", ide.getId());
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("delete from Spectators where id=?")){
            preStmt.setString(1,ide.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB trying to delete spectator " + ide.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();
        return true;
    }

    @Override
    public Spectator find(Entity ide) {

        logger.traceEntry("Entering find spectator with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Spectators where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");

                    Spectator spectator = new Spectator(id, name, password);

                    logger.traceExit(spectator);
                    return spectator;

                    //return true;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to find spectator " + ide.getId() + ": "  + ex);
        }

        return null;
    }

    @Override
    public boolean update(Entity e, Spectator newE) {
        return false;
    }

    @Override
    public ArrayList<Spectator> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Spectator> spectators = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Spectators")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");

                    Spectator spectator = new Spectator(id, name, password);
                    spectators.add(spectator);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all spectators " + "" + ": "  + ex);
        }
        logger.traceExit(spectators);

        return spectators;
    }
}
