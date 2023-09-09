package repositories;

import models.Entity;
import models.Match;
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
public class MatchDbRepository implements MatchRepository {

    private static final String bigName = "Match";
    private static final String bigNames = "Matches";
    private static final String smallName = "match";

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public MatchDbRepository(Properties props) {
        logger.info("Initializing " + bigName + "DbRepository with properties: {} ",props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public boolean save(Match match) {

        logger.traceEntry("saving "+ smallName +"{} ", match);
        Connection con = dbUtils.getConnection();

            if (match.getId() == null) {

            var all = getAll();
            int var = 0;

            for (var x : all) {
                if (Integer.parseInt(x.getId()) > var) {
                    var = Integer.parseInt(x.getId());
                }
            }

            var += 1;

            match.setId(Integer.toString(var));
        }

        try (PreparedStatement preparedStatement = con.prepareStatement("insert into " + bigNames + " (id, home_team, away_team, ticket_price, total_seats, available_seats) values (?, ?, ?, ?, ?, ?)")){
            preparedStatement.setString(1, match.getId());
            preparedStatement.setString(2, match.getHomeTeam());
            preparedStatement.setString(3, match.getAwayTeam());
            preparedStatement.setDouble(4, match.getTicketPrice());
            preparedStatement.setInt(5, match.getTotalSeats());
            preparedStatement.setInt(6, match.getAvailableSeats());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save " + smallName + " " + match.getId() + ": "  + ex);
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
    public boolean update(Entity e, Match newE) {
        logger.traceEntry("updating " + smallName +" {} ", e);
        Connection con = dbUtils.getConnection();

        // PreparedStatement preparedStatement = con.prepareStatement("update Matches set (id, home_team, away_team, ticket_price, total_seats, available_seats) values (?, ?, ?, ?, ?, ?) where id=?")

        try (PreparedStatement preparedStatement = con.prepareStatement("update " + bigNames + " set id=?, home_team=?, away_team=?, ticket_price=?, total_seats=?, available_seats=? where id=?")){
            preparedStatement.setString(1, newE.getId());
            preparedStatement.setString(2, newE.getHomeTeam());
            preparedStatement.setString(3, newE.getAwayTeam());
            preparedStatement.setDouble(4, newE.getTicketPrice());
            preparedStatement.setInt(5, newE.getTotalSeats());
            preparedStatement.setInt(6, newE.getAvailableSeats());
            preparedStatement.setString(7, e.getId());

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
    public Match find(Entity ide) {

        logger.traceEntry("Entering find " + smallName + " with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + " where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String homeTeam = resultSet.getString("home_team");
                    String awayTeam = resultSet.getString("away_team");
                    Double ticketPrice = resultSet.getDouble("ticket_price");
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer availableSeats = resultSet.getInt("available_seats");

                    Match match = new Match(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);

                    logger.traceExit(match);
                    return match;

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
    public ArrayList<Match> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Match> matches = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from " + bigNames + "")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String homeTeam = resultSet.getString("home_team");
                    String awayTeam = resultSet.getString("away_team");
                    Double ticketPrice = resultSet.getDouble("ticket_price");
                    Integer totalSeats = resultSet.getInt("total_seats");
                    Integer availableSeats = resultSet.getInt("available_seats");

                    Match match = new Match(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);

                    if (availableSeats == 0) {
                        match.setSoldOut("sold out");
                    }

                    matches.add(match);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all " + smallName + ": "  + ex);
        }
        logger.traceExit(matches);

        return matches;
    }

    @Override
    public boolean updateAvailableSeats(Entity ide, Match matchToUpdate, Integer numberOfSeats) {

        logger.traceEntry("Entering update " + smallName + " with id {} and seats to substract {}",  ide.getId(), numberOfSeats);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("update Matches set available_seats=? where id=?")){

            Integer newNumberOfSeats = matchToUpdate.getAvailableSeats() - numberOfSeats;

            if (newNumberOfSeats < 0) {
                throw new Exception("Not enough available seats!");
            }

            preStmt.setInt(1, newNumberOfSeats);
            preStmt.setString(2, ide.getId());
            preStmt.executeUpdate();

            return true;

        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error trying to update " + smallName + " DB: "+ex);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        return false;
    }
}
