using System;
using System.Configuration;
using System.Collections.Specialized;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Problema_1
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            NameValueCollection sAll;
            sAll = ConfigurationManager.AppSettings;
            string value = sAll["selectCommandParent"];
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            Form1 formToBeginWith = new Form1();
            formToBeginWith.setAppSettingsAttributes(sAll);

            Application.Run(formToBeginWith);
        }
    }
}
