using Simple.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace LPOA.Sqlite
{
    public class DALBase
    {
        private static readonly string DatabasePath = Path.Combine(
             Path.GetDirectoryName(Assembly.GetExecutingAssembly().CodeBase.Substring(8)),
             "mydb.db");
        //private static readonly string SqlConnString = "Data Source=.;Initial Catalog=INPC;Persist Security Info=True;User ID=sa;Password=sa";
        /// <summary>
        /// DB
        /// </summary>
        public static dynamic DAL
        {
            get { return Database.Opener.OpenConnection("Data Source=" + DatabasePath); }
        }
    }
}
