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
    public partial class Form3 : Form
    {
        string connectionString;
        Form1 form1 = new Form1();

        string id_franciza;
        string nume;
        string prenume;
        int pozitie;
        int nr_camere;
        string id;

        public void setIdFranciza(string id)
        {
            id_franciza = id;
        }

        public void setAllDetails(string id_angajat, string num, string prenum, string poziti, string id_f)
        {
            id = id_angajat;
            nume = num;
            prenume = prenum;
            //pozitie = poziti;
            id_franciza = id_f;
        }

        public void setConnectionString(string connection)
        {
            connectionString = connection;
        }

        public void setForm(Form1 form)
        {
            form1 = form;
        }

        public Form3()
        {
            InitializeComponent();
        }

        private void Form3_Load(object sender, EventArgs e)
        {
            textBoxIdAngajat.Text = id;
            textBoxNumeAngajat.Text = nume;
            textBoxPrenumeAngajat.Text = prenume;
            //textBoxPozitieAngajat.Text = pozitie;
            textBoxFrancizaAngajat.Text = id_franciza;

            textBoxIdNouAngajat.Text = id;
            textBoxNumeNouAngajat.Text = nume;
            textBoxPrenumeNouAngajat.Text = prenume;
            //textBoxPozitieNouAngajat.Text = pozitie;
            textBoxFrancizaNouAngajat.Text = id_franciza;
        }

        private void actAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    id = textBoxIdAngajat.Text;
                    nume = textBoxNumeNouAngajat.Text;
                    prenume = textBoxPrenumeNouAngajat.Text;
                    //pozitie = textBoxPozitieNouAngajat.Text;
                    id_franciza = textBoxFrancizaNouAngajat.Text;

                    SqlCommand actualizeazaAngajatCommand = new SqlCommand("UPDATE Angajați SET " +
                            "nume=@nume, prenume=@prenume, poziție=@poziție, id_franciză=@id_franciză WHERE " +
                            "id_angajat=@id_angajat;", connection);

                    actualizeazaAngajatCommand.Parameters.AddWithValue("@id_angajat", id);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@nume", nume);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@prenume", prenume);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@poziție", pozitie);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@id_franciză", id_franciza);

                    int updateRowCount = actualizeazaAngajatCommand.ExecuteNonQuery();
                    string mesaj = "";

                    if (updateRowCount == 1)
                    {
                        mesaj = "A fost actualizat angajatul " + nume + " " + prenume + " cu ID-ul " + id + " în franciza " + id_franciza;
                    }
                    else
                    {
                        mesaj = "Au fost actualizați " + updateRowCount + " angajați";
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
