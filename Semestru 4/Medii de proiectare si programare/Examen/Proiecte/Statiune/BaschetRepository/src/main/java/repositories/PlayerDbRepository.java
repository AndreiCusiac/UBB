package repositories;

import models.Entity;
import models.Match;
import models.Player;
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
public class PlayerDbRepository implements PlayerRepository {

    private static final String bigName = "Player";
    private static final String bigNames = "Players";
    private static final String smallName = "player";

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public PlayerDbRepository(Properties props) {
        logger.info("Initializing " + bigName + "DbRepository with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public boolean save(Player entityToBeSaved) {

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

        try (PreparedStatement preparedStatement = con.prepareStatement("insert into " + bigNames + " (id, name) values (?, ?)")){
            preparedStatement.setString(1, entityToBeSaved.getId());
            preparedStatement.setString(2, entityToBeSaved.getName());

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
    public boolean update(Entity e, Player newE) {
        logger.traceEntry("updating " + smallName +" {} ", e);
        Connection con = dbUtils.getConnection();

        // PreparedStatement preparedStatement = con.prepareStatement("update Matches set (id, home_team, away_team, ticket_price, total_seats, available_seats) values (?, ?, ?, ?, ?, ?) where id=?")

        try (PreparedStatement preparedStatement = con.prepareStatement("update " + bigNames + " set id=?, name=? where id=?")){
            preparedStatement.setString(1, newE.getId());
            preparedStatement.setString(2, newE.getName());
            preparedStatement.setString(3, e.getId());

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
    public Player find(Entity ide) {

        logger.traceEntry("Entering find " + smallName + " with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + " where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");

                    Player entityToBeFound = new Player(name);
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
    public ArrayList<Player> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Player> entities = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + "")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");

                    Player entity = new Player(name);
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
