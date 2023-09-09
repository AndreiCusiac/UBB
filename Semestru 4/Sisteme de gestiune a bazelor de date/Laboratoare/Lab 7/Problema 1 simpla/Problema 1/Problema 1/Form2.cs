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

namespace Problema_1
{
    public partial class Form2 : Form
    {
        string connectionString;
        Form1 form1 = new Form1();

        string cod_r;
        string nume_c;
        string dimensiune;
        int valoare_estimata;
        int nr_camere;
        string cod_c;

        public void setCodRegat(string id)
        {
            cod_r = id;
        }

        public void setConnectionString(string connection)
        {
            connectionString = connection;
        }

        public void setForm(Form1 form)
        {
            form1 = form;
        }

        public Form2()
        {
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            textBoxCodRegat.Text = cod_r;
        }

        private void addAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    cod_c = textBoxCodCastel.Text;
                    nume_c = textBoxNumeCastel.Text;
                    dimensiune = textBoxDimensiune.Text;
                    //pozitie = textBoxValoare.Text;
                    valoare_estimata = int.Parse(textBoxValoare.Text);
                    nr_camere = int.Parse(textBoxNrCamere.Text);

                    SqlCommand adaugaCastelCommand = new SqlCommand("INSERT INTO Castele VALUES " +
                            "(@nume_c, @dimensiune, @valoare_estimata, @nr_camere , @cod_r);", connection);

                    //adaugaAngajatCommand.Parameters.AddWithValue("@id_angajat", id);
                    adaugaCastelCommand.Parameters.AddWithValue("@nume_c", nume_c);
                    adaugaCastelCommand.Parameters.AddWithValue("@dimensiune", dimensiune);
                    adaugaCastelCommand.Parameters.AddWithValue("@valoare_estimata", valoare_estimata);
                    adaugaCastelCommand.Parameters.AddWithValue("@nr_camere", nr_camere);
                    adaugaCastelCommand.Parameters.AddWithValue("@cod_r", cod_r);

                    int insertRowCount = adaugaCastelCommand.ExecuteNonQuery();
                    string mesaj = "";

                    if (insertRowCount == 1)
                    {
                        mesaj = "A fost adăugat castelul " + nume_c + " în regatul " + cod_r;
                    }
                    else
                    {
                        mesaj = "Au fost adăugați " + insertRowCount + " castele";
                    }

                    form1.updateData(sender, e);
                    MessageBox.Show(mesaj);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            this.Close();
        }

        private void abortButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
