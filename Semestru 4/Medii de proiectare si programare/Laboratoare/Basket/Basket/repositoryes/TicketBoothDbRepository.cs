using Baschet.Business;
using Basket.Business;
using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.repositoryes
{
    public class TicketBoothDbRepository : TicketBoothRepository
    {
        private static readonly ILog log = LogManager.GetLogger("TicketBoothDbRepository");
        IDictionary<String, string> props;

        public TicketBoothDbRepository(IDictionary<String, string> props)
        {
            log.Info("Creating TicketBoothDbRepository ");
            this.props = props;
        }

        public bool save(TicketBooth ticketBooth)
        {
            var con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into TicketBooths values (@id, @name, @password, @salt, @hash)";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ticketBooth.id;
                comm.Parameters.Add(paramId);

                var paramHomeTeam = comm.CreateParameter();
                paramHomeTeam.ParameterName = "@name";
                paramHomeTeam.Value = ticketBooth.name;
                comm.Parameters.Add(paramHomeTeam);

                var paramAwayTeam = comm.CreateParameter();
                paramAwayTeam.ParameterName = "@password";
                paramAwayTeam.Value = ticketBooth.password;
                comm.Parameters.Add(paramAwayTeam);

                var ticketPrice = comm.CreateParameter();
                ticketPrice.ParameterName = "@salt";
                ticketPrice.Value = ticketBooth.salt;
                comm.Parameters.Add(ticketPrice);

                var totalSeats = comm.CreateParameter();
                totalSeats.ParameterName = "@hash";
                totalSeats.Value = ticketBooth.hash;
                comm.Parameters.Add(totalSeats);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    return false;
                /*throw new RepositoryException("No task added !");*/
            }

            return true;
        }

        public bool delete(Entity ide)
        {
            IDbConnection con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from TicketBooths where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ide.id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                if (dataR == 0)
                    return false;
                //throw new RepositoryException("No task deleted!");
            }

            return true;
        }

        public List<TicketBooth> getAll()
        {
            IDbConnection con = DBUtils.getConnection(props);
            List<TicketBooth> ticketBooths = new List<TicketBooth>();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText =
                    "select id, name, password, salt, hash from TicketBooths";

                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        String id = dataR.GetString(0);
                        String name = dataR.GetString(1);
                        String password = dataR.GetString(2);
                        String salt = dataR.GetString(3);
                        String hash = dataR.GetString(4);

                        TicketBooth ticketBooth = new TicketBooth(id, name, password, salt, hash);
                        ticketBooths.Add(ticketBooth);
                    }
                }
            }

            return ticketBooths;
        }

        public TicketBooth find(Entity ide)
        {
            log.InfoFormat("Entering find TicketBooths with value {0}", ide.id);
            IDbConnection con = DBUtils.getConnection(props);

            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select id, name, password, salt, hash from TicketBooths where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = ide.id;
                comm.Parameters.Add(paramId);

                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        String id = dataR.GetString(0);
                        String name = dataR.GetString(1);
                        String password = dataR.GetString(2);
                        String salt = dataR.GetString(3);
                        String hash = dataR.GetString(4);

                        TicketBooth ticketBooth = new TicketBooth(id, name, password, salt, hash);

                        log.InfoFormat("Exiting find with value {0}", ticketBooth);
                        return ticketBooth;
                        //return true;
                    }
                }
            }
            log.InfoFormat("Exiting find TicketBooths with value {0}", null);
            return null;
        }
    }
}
