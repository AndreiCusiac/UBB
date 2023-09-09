package repositories;

import models.Entity;
import models.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class TicketDbRepository implements TicketRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TicketDbRepository(Properties props) {
        logger.info("Initializing TicketDbRepository with properties: {} ", props);
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public boolean save(Ticket ticket) {

        logger.traceEntry("saving ticket{} ", ticket);
        Connection con = dbUtils.getConnection();

        /*
        Cu sau fara id?
         */
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into Tickets (id, id_match, name, seats) values (?, ?, ?, ?)")) {
            preparedStatement.setString(1, ticket.getId());
            preparedStatement.setString(2, ticket.getIdMatch());
            preparedStatement.setString(3, ticket.getName());
            preparedStatement.setInt(4, ticket.getSeats());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save ticket " + ticket.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting ticket with id {}", ide.getId());
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("delete from Tickets where id=?")) {
            preStmt.setString(1, ide.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to delete ticket " + ide.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();
        return true;
    }

    @Override
    public boolean update(Entity e, Ticket newE) {
        return false;
    }

    @Override
    public Ticket find(Entity ide) {

        logger.traceEntry("Entering find ticket with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Tickets where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String idMatch = resultSet.getString("id_match");
                    String name = resultSet.getString("name");
                    Integer seats = resultSet.getInt("seats");

                    Ticket ticket = new Ticket(id, idMatch, name, seats);

                    logger.traceExit(ticket);
                    return ticket;

                    //return true;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to find ticket " + ide.getId() + ": "  + ex);
        }

        return null;
    }

    @Override
    public ArrayList<Ticket> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from Tickets")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String idMatch = resultSet.getString("id_match");
                    String name = resultSet.getString("name");
                    Integer seats = resultSet.getInt("seats");

                    Ticket ticket = new Ticket(id, idMatch, name, seats);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all ticket " + "" + ": "  + ex);
        }
        logger.traceExit(tickets);

        return tickets;
    }
}