package repositories;

import models.Entity;
import models.Ticket;

public interface TicketRepository extends IRepository<Ticket> {

    boolean delete(Entity ide);
}
