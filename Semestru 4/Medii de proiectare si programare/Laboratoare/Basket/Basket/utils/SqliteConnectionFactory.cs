using Microsoft.Data.Sqlite;
using System;
using System.Collections.Generic;
using System.Data;
//using Mono.Data.Sqlite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.utils
{
	public class SqliteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection(IDictionary<string, string> props)
		{
			//Mono Sqlite Connection

			//String connectionString = "URI=file:/Users/Andrei/Desktop/Baschet/baschet.db,Version=3,Version=3";
			String connectionString = props["ConnectionString"];
			Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
			//Microsoft.Data.SqliteSQLitePCL.raw.SetProvider(new SQLitePCL.SQLite3Provider_e_sqlite3());
			return new SqliteConnection(connectionString);

			// Windows SQLite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
			//String connectionString = "Data Source=tasks.db;Version=3";
			//return new SQLiteConnection(connectionString);
		}
	}
}
