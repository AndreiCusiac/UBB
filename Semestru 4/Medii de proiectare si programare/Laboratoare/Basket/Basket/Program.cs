using Basket.repositoryes;
using Basket.services;
using log4net.Config;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Basket
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            XmlConfigurator.Configure(new System.IO.FileInfo("Init Log4net"));
            //Console.WriteLine("Configuration Settings for baschetDB {0}", GetConnectionStringByName("baschetDB"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("baschetDB"));

            MatchDbRepository matchDbRepository = new MatchDbRepository(props);
            MatchService matchService = new MatchService(matchDbRepository);

            TicketBoothDbRepository ticketBoothDbRepository = new TicketBoothDbRepository(props);
            TicketBoothService ticketBoothService = new TicketBoothService(ticketBoothDbRepository);

            TicketDbRepository ticketDbRepository = new TicketDbRepository(props);
            TicketService ticketService = new TicketService(ticketDbRepository);

            AppService appService = new AppService(matchService, ticketBoothService, ticketService);

            LoginForm login = new LoginForm();
            login.AppService = appService;
            login.Text = "Log In";

            Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(login);
        }

        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}
