using Basket.Business;
using Basket.services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Basket
{
    public partial class HomeForm : Form
    {
        DataSet ds = new DataSet();
        SqlDataAdapter adapter = new SqlDataAdapter();

        int redColumn = 0;

        private AppService appService { get; set; }

        internal AppService AppService { get => appService; set => appService = value; }

        public HomeForm()
        {
            InitializeComponent();
        }

        private void HomeForm_Load(object sender, EventArgs e)
        {
            //dataGridView1.Columns(redColumn).DefaultCellStyle.ForeColor = Color.Red;

            registeredTicketBooth.Text = "Registered as " + appService.getCurrentTicketBooth().name;

            dataGridView1.DataSource = appService.getAllMatches();
            redColumn = dataGridView1.Columns.Count - 2;

            dataGridView1.Columns[redColumn].DefaultCellStyle.ForeColor = Color.Red;

        }

        private void buttonRefresh_Click(object sender, EventArgs e)
        {
            dataGridView1.DataSource = appService.getAllMatches();
           // MessageBox.Show("Număr de coloane: " + redColumn.ToString());
            dataGridView1.Columns[redColumn].DefaultCellStyle.ForeColor = Color.Red;

        }

        private void buttonLogout_Click(object sender, EventArgs e)
        {
            this.Hide();

            LoginForm login = new LoginForm();
            login.Text = "Log In";
            login.AppService = appService;

            //Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);
            login.ShowDialog();

            this.Close();
        }

        private void buttonSortNormal_Click(object sender, EventArgs e)
        {
            dataGridView1.DataSource = appService.getAllMatches();
            dataGridView1.Columns[redColumn].DefaultCellStyle.ForeColor = Color.Red;

        }

        private void buttonSortBySeats_Click(object sender, EventArgs e)
        {
            dataGridView1.DataSource = appService.getMatchesSortedByAvailableSeatsDesc();
            dataGridView1.Columns[redColumn].DefaultCellStyle.ForeColor = Color.Red;

        }

        private void buttonSell_Click(object sender, EventArgs e)
        {

            Match currentMatch = null;

            try
            {
                currentMatch = dataGridView1.SelectedRows[0].DataBoundItem as Match;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Eroare");
                return;
            }

            if (textBoxName.Text == "" || textBoxSeats.Text == "")
            {
                MessageBox.Show("Numele și numărul de locuri nu pot fi nule!", "Eroare");
                return;
            }

            string name = textBoxName.Text;
            int seats;

            try
            {
                seats = Int32.Parse(textBoxSeats.Text);
            }
            catch (FormatException ex)
            {
                MessageBox.Show(ex.Message, "Eroare");
                return;
            }

            if (currentMatch != null)
            {
                if (currentMatch.availableSeats <= 0)
                {
                    MessageBox.Show("Nu există locuri disponibile pentru meciul selectat!", "Eroare");
                    return;
                }

                if (seats > currentMatch.availableSeats)
                {
                    MessageBox.Show("Nu există locuri suficiente pentru meciul selectat!", "Eroare");
                    return;
                }

                appService.sellTicket(currentMatch, name, seats);
            }
            else
            {
                MessageBox.Show("Nu există un meci selectat!", "Eroare");
                return;
            }

            MessageBox.Show(name + " a achiziționat " + seats + " bilete la meciul " + currentMatch.id + ".", "Status vânzare");

        }
    }
}
