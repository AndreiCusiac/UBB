package repositories;

import models.Entity;
import models.Game;
import models.Pozitie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

@Component
public class PozitieDbRepository implements PozitieRepository {

    private static final String bigName = "Pozitie";
    private static final String bigNames = "Pozities";
    private static final String smallName = "pozitie";

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public PozitieDbRepository(Properties props) {
        logger.info("Initializing " + bigName + "DbRepository with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public boolean save(Pozitie entityToBeSaved) {

        logger.traceEntry("saving "+ smallName +"{} ", entityToBeSaved);
        Connection con = dbUtils.getConnection();

            if (entityToBeSaved.getId() == null) {

            var all = getAll();
            int var = 0;

            for (var x : all) {
                if (Integer.parseInt(x.getId()) > var) {
                    var = Integer.parseInt(x.getId());
                }
            }

            var += 1;

            entityToBeSaved.setId(Integer.toString(var));
        }

        try (PreparedStatement preparedStatement = con.prepareStatement("insert into " + bigNames + " (id, pozitie1, pozitie2, pozitie3) values (?, ?, ?, ?)")){
            preparedStatement.setString(1, entityToBeSaved.getId());
            preparedStatement.setString(2, entityToBeSaved.getPozitie1());
            preparedStatement.setString(3, entityToBeSaved.getPozitie2());
            preparedStatement.setString(4, entityToBeSaved.getPozitie3());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save " + smallName + " " + entityToBeSaved.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting " + smallName + " with id {}",  ide.getId());
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("delete from " + bigNames + " where id=?")){
            preStmt.setString(1,ide.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB trying to delete " + smallName + " " + ide.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();
        return true;
    }

    @Override
    public boolean update(Entity e, Pozitie newE) {
        logger.traceEntry("updating " + smallName +" {} ", e);
        Connection con = dbUtils.getConnection();

        // PreparedStatement preparedStatement = con.prepareStatement("update Matches set (id, home_team, away_team, ticket_price, total_seats, available_seats) values (?, ?, ?, ?, ?, ?) where id=?")

        try (PreparedStatement preparedStatement = con.prepareStatement("update " + bigNames + " set id=?, pozitie1=?, pozitie2=?, pozitie3=? where id=?")){
            preparedStatement.setString(1, newE.getId());
            preparedStatement.setString(2, newE.getPozitie1());
            preparedStatement.setString(3, newE.getPozitie2());
            preparedStatement.setString(4, newE.getPozitie3());
            preparedStatement.setString(5, e.getId());

            int result = preparedStatement.executeUpdate();
            logger.trace("Updated {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to update " + smallName +" " + newE.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public Pozitie find(Entity ide) {

        logger.traceEntry("Entering find " + smallName + " with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + " where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String pozitie1 = resultSet.getString("pozitie1");
                    String pozitie2 = resultSet.getString("pozitie2");
                    String pozitie3 = resultSet.getString("pozitie3");

                    Pozitie entityToBeFound = new Pozitie(pozitie1, pozitie2, pozitie3);
                    entityToBeFound.setId(id);

                    logger.traceExit(entityToBeFound);
                    return entityToBeFound;

                    //return true;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to find " + smallName + " " + ide.getId() + ": "  + ex);
        }

        return null;
    }

    @Override
    public ArrayList<Pozitie> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Pozitie> entities = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + "")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String pozitie1 = resultSet.getString("pozitie1");
                    String pozitie2 = resultSet.getString("pozitie2");
                    String pozitie3 = resultSet.getString("pozitie3");

                    Pozitie entity = new Pozitie(pozitie1, pozitie2, pozitie3);
                    entity.setId(id);

                    entities.add(entity);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all " + smallName + ": "  + ex);
        }
        logger.traceExit(entities);

        return entities;
    }
//
//    @Override
//    public boolean updateAvailableSeats(Entity ide, Match matchToUpdate, Integer numberOfSeats) {
//
//        logger.traceEntry("Entering update " + smallName + " with id {} and seats to substract {}",  ide.getId(), numberOfSeats);
//        Connection con=dbUtils.getConnection();
//
//        try(PreparedStatement preStmt=con.prepareStatement("update Matches set available_seats=? where id=?")){
//
//            Integer newNumberOfSeats = matchToUpdate.getAvailableSeats() - numberOfSeats;
//
//            if (newNumberOfSeats < 0) {
//                throw new Exception("Not enough available seats!");
//            }
//
//            preStmt.setInt(1, newNumberOfSeats);
//            preStmt.setString(2, ide.getId());
//            preStmt.executeUpdate();
//
//            return true;
//
//        }catch(SQLException ex){
//            logger.error(ex);
//            System.out.println("Error trying to update " + smallName + " DB: "+ex);
//        } catch (Exception e) {
//            logger.error(e);
//            e.printStackTrace();
//        }
//
//        return false;
//    }
}
