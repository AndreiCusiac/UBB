using System;
using System.Collections.Generic;
using System.Data;
using Baschet.models;
using log4net;

namespace Baschet.repositories
{
    public class TicketDbRepository : IRepository<Ticket>
    {
        private static readonly ILog log = LogManager.GetLogger("TicketDbRepository");
        IDictionary<String, string> props;

        public TicketDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TicketDbRepository ");
            this.props = props;
        }

        public bool save(Ticket ticket)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Tickets  values (@id, @id_match, @name, @seats)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ticket.id;
                comm.Parameters.Add(paramId);

                var paramHomeTeam = comm.CreateParameter();
                paramHomeTeam.ParameterName = "@id_match";
                paramHomeTeam.Value = ticket.idMatch;
                comm.Parameters.Add(paramHomeTeam);

                var paramAwayTeam = comm.CreateParameter();
                paramAwayTeam.ParameterName = "@name";
                paramAwayTeam.Value = ticket.name;
                comm.Parameters.Add(paramAwayTeam);

                var ticketPrice = comm.CreateParameter();
                ticketPrice.ParameterName = "@seats";
                ticketPrice.Value = ticket.seats;
                comm.Parameters.Add(ticketPrice);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    return false;
                /*throw new RepositoryException("No task added !");*/
            }

            return true;
        }

        public bool delete(Ticket ticket)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from Tickets where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ticket.id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    return false;
                //throw new RepositoryException("No task deleted!");
            }

            return true;
        }

        public List<Ticket> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            List<Ticket> tickets = new List<Ticket>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "select id, name, password, salt, hash from TicketBooths";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        String id = dataR.GetString(0);
                        String idMatch = dataR.GetString(1);
                        String name = dataR.GetString(2);
                        int seats = dataR.GetInt32(3);

                        Ticket ticket = new Ticket(id, name, name, seats);
                        tickets.Add(ticket);
                    }
                }
            }

            return tickets;
        }
    }
}