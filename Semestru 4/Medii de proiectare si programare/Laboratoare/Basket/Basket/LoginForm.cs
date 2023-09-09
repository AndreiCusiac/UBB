using Basket.Business;
using Basket.services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Basket
{
    public partial class LoginForm : Form
    {
        private AppService appService { get; set; }

        string name;
        string password;

        internal AppService AppService { get => appService; set => appService = value; }

        public LoginForm()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.Text = "Log In";
        }

        private void buttonLogIn_Click(object sender, EventArgs e)
        {
            name = textBoxName.Text;
            password = textBoxPassword.Text;

            TicketBooth ticketBooth;

            if (appService.isAuthValid(name, password) == true)
            {

                ticketBooth = appService.getTicketBoothByName(name);
                appService.setCurrentTicketBooth(ticketBooth);

                //this.Close();

                startApp();
            } 
            else
            {
                MessageBox.Show("Atentie!\nNumele casei de bilete și/ sau parola nu sunt valide!");
            }
        }

        private void startApp()
        {
            this.Hide();

            HomeForm home = new HomeForm();
            home.AppService = appService;
            home.Text = "Baschet - " + appService.getCurrentTicketBooth().name;

            //Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);

            home.ShowDialog();
            this.Close();

            //home.Show();
        }
    }
}
