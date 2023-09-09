package repositories;

import models.Entity;
import models.Match;

public interface MatchRepository extends IRepository<Match>{

    boolean updateAvailableSeats(Entity ide, Match matchToUpdate, Integer numberOfSeats);
}
