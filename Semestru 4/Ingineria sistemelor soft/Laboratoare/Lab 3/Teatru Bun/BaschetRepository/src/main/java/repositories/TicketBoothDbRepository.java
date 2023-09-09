package repositories;

import models.Entity;
import models.TicketBooth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class TicketBoothDbRepository implements TicketBoothRepository{

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public TicketBoothDbRepository(Properties props) {
        logger.info("Initializing TicketBoothDbRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public boolean save(TicketBooth ticketBooth) {

        logger.traceEntry("saving ticket_booth {} ", ticketBooth);
        Connection con = dbUtils.getConnection();

        /*
        Cu sau fara id?
         */
        try (PreparedStatement preparedStatement = con.prepareStatement("insert into TicketBooths (id, name, password, salt, hash) values (?, ?, ?, ?, ?)")){
            preparedStatement.setString(1, ticketBooth.getId());
            preparedStatement.setString(2, ticketBooth.getName());
            preparedStatement.setString(3, ticketBooth.getPassword());
            preparedStatement.setString(4, ticketBooth.getSalt());
            preparedStatement.setString(5, ticketBooth.getHash());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to save ticket_booth " + ticketBooth.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();

        return true;
    }

    @Override
    public boolean delete(Entity ide) {

        logger.traceEntry("deleting ticket_booth with id {}", ide.getId());
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("delete from TicketBooths where id=?")){
            preStmt.setString(1,ide.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB trying to delete ticket_booth " + ide.getId() + ": "  + ex);
            return false;
        }
        logger.traceExit();
        return true;
    }

    @Override
    public boolean update(Entity e, TicketBooth newE) {
        return false;
    }

    @Override
    public TicketBooth find(Entity ide) {

        logger.traceEntry("Entering find ticketBooth with id {}",  ide.getId());
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from TicketBooths where id=?")) {
            preparedStatement.setString(1,ide.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String hash = resultSet.getString("hash");

                    TicketBooth ticketBooth = new TicketBooth(id, name, password, salt, hash);

                    logger.traceExit(ticketBooth);
                    return ticketBooth;

                    //return true;
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to find ticket_booth " + ide.getId() + ": "  + ex);
        }

        return null;
    }

    @Override
    public ArrayList<TicketBooth> getAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        ArrayList<TicketBooth> ticketBooths = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from TicketBooths")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    String salt = resultSet.getString("salt");
                    String hash = resultSet.getString("hash");

                    TicketBooth ticketBooth = new TicketBooth(id, name, password, salt, hash);
                    ticketBooths.add(ticketBooth);
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB trying to get all ticket_booth " + "" + ": "  + ex);
        }
        logger.traceExit(ticketBooths);

        return ticketBooths;
    }
}
