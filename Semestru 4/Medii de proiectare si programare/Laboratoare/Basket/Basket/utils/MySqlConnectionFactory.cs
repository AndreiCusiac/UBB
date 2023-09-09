using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.utils
{
	/*public class MySqlConnectionFactory:ConnectionFactory
	{
		public override IDbConnection createConnection(IDictionary<string,string> props)
		{
			//MySql Connection
			//String connectionString = "Database=mpp;" +
			//							"Data Source=localhost;" +
			//							"User id=test;" +
			//							"Password=passtest;";
			String connectionString = props["ConnectionString"];
			Console.WriteLine("MySql ---se deschide o conexiune la  ... {0}", connectionString);
			
			return new MySqlConnection(connectionString);

	
		}
	}*/

}
