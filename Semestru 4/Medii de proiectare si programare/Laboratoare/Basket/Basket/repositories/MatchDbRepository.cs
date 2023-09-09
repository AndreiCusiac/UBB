using Baschet.models;
using System;
//using System.Collections;
using System.Collections.Generic;
using System.Data;
using log4net;
using System.Text;

namespace Baschet.repositories
{
    public class MatchDbRepository : IRepository<Match>
    {
        private static readonly ILog log = LogManager.GetLogger("MatchDbRepository");
        IDictionary<String, string> props;

        public MatchDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating MatchDbRepository ");
            this.props = props;
        }

        public bool save(Match match)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "insert into Matches  values (@id, @home_team, @away_team, @ticket_price, @total_seats, @available_seats)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = match.id;
                comm.Parameters.Add(paramId);

                var paramHomeTeam = comm.CreateParameter();
                paramHomeTeam.ParameterName = "@home_team";
                paramHomeTeam.Value = match.homeTeam;
                comm.Parameters.Add(paramHomeTeam);

                var paramAwayTeam = comm.CreateParameter();
                paramAwayTeam.ParameterName = "@away_team";
                paramAwayTeam.Value = match.awayTeam;
                comm.Parameters.Add(paramAwayTeam);

                var ticketPrice = comm.CreateParameter();
                ticketPrice.ParameterName = "@ticket_price";
                ticketPrice.Value = match.ticketPrice;
                comm.Parameters.Add(ticketPrice);

                var totalSeats = comm.CreateParameter();
                totalSeats.ParameterName = "@total_seats";
                totalSeats.Value = match.totalSeats;
                comm.Parameters.Add(totalSeats);

                var availableSeats = comm.CreateParameter();
                availableSeats.ParameterName = "@available_seats";
                availableSeats.Value = match.availableSeats;
                comm.Parameters.Add(availableSeats);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    return false;
                /*throw new RepositoryException("No task added !");*/
            }

            return true;
        }

        public bool delete(Match match)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Matches where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = match.id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    return false;
                //throw new RepositoryException("No task deleted!");
            }

            return true;
        }

        public List<Match> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            List<Match> matches = new List<Match>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "select id, home_team, away_team, ticket_price, total_seats, available_seats from Matches";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        String id = dataR.GetString(0);
                        String homeTeam = dataR.GetString(1);
                        String awayTeam = dataR.GetString(2);
                        float ticketPrice = dataR.GetFloat(3);
                        int totalSeats = dataR.GetInt32(4);
                        int availableSeats = dataR.GetInt32(5);

                        Match match = new Match(id, homeTeam, awayTeam, ticketPrice, totalSeats, availableSeats);
                        matches.Add(match);
                    }
                }
            }

            return matches;
        }
    }
}
