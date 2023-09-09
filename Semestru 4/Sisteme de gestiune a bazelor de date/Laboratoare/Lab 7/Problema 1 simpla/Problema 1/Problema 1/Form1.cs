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
    public partial class Form1 : Form
    {
        string connectionString = @"Server=DESKTOP-QOGGLUB\SQLEXPRESS;Initial Catalog=P52022;Integrated Security=true;";
        DataSet dataSet = new DataSet();
        SqlDataAdapter adapterRegate = new SqlDataAdapter();
        SqlDataAdapter adapterCastele = new SqlDataAdapter();

        BindingSource bindingSourceRegate = new BindingSource();
        BindingSource bindingSourceCastele = new BindingSource();


        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    adapterRegate.SelectCommand = new SqlCommand("SELECT * FROM Regate", connection);
                    adapterCastele.SelectCommand = new SqlCommand("SELECT * FROM Castele", connection);

                    adapterRegate.Fill(dataSet, "Regate");
                    adapterCastele.Fill(dataSet, "Castele");

                    // dataGridViewFrancize.DataSource = dataSet.Tables["Francize"];

                    DataColumn pk = dataSet.Tables["Regate"].Columns["cod_r"];
                    DataColumn fk = dataSet.Tables["Castele"].Columns["cod_r"];

                    DataRelation relation = new DataRelation("fk_Regate_Castele", pk, fk, true);

                    dataSet.Relations.Add(relation);

                    bindingSourceRegate.DataSource = dataSet.Tables["Regate"];
                    dataGridViewFrancize.DataSource = bindingSourceRegate;

                    bindingSourceCastele.DataSource = bindingSourceRegate;
                    bindingSourceCastele.DataMember = "fk_Regate_Castele";

                    dataGridViewAngajati.DataSource = bindingSourceCastele;

                    // textBox1.DataBindings.Add("Text", bsParent, "Nume", true);

                    //textBox1.DataBindings.Add("Text", bindingSourceAngajați, "prenume", true);

                    // MessageBox.Show(dataGridViewAngajati.SelectedCells.ToString());


                    // dataGridViewAngajati.SelectedCells = 
                }
            } 
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: ", ex.Message);
            }
        }
            
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    DataRowView current = (DataRowView)bindingSourceCastele.Current;
                    string cod_c = current["cod_c"].ToString();
                    string nume = current["nume_c"].ToString();

                    SqlCommand stergeAngajatDupaID = new SqlCommand("DELETE FROM Castele WHERE cod_c = @cod_c; ", connection);
                    stergeAngajatDupaID.Parameters.AddWithValue("@cod_c", cod_c);

                    int deleteRowCount = stergeAngajatDupaID.ExecuteNonQuery();
                    string mesaj;

                    if (deleteRowCount == 1)
                    {
                        mesaj = "S-a șters castelul " + nume + " cu ID-ul " + cod_c;
                    }
                    else
                    {
                        mesaj = "S-au șters " + deleteRowCount + " angajați";
                    }

                    updateData(sender, e);
                    MessageBox.Show(mesaj);
                }
            } 
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
        }

        private void adaugaAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                DataRowView current = (DataRowView)bindingSourceRegate.Current;

                string cod_r = current["cod_r"].ToString();

                Form2 formAdaugare = new Form2();
                formAdaugare.setCodRegat(cod_r);
                formAdaugare.setConnectionString(connectionString);
                formAdaugare.setForm(this);
                formAdaugare.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            
        }

        private void actualizeazaAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                DataRowView current = (DataRowView)bindingSourceCastele.Current;

                string cod_c = current["cod_c"].ToString();
                string nume = current["nume_c"].ToString();
                string dimensiune = current["dimensiune"].ToString();
                int valoare = int.Parse(current["valoare_estimata"].ToString());
                int nr_camere = int.Parse(current["nr_camere"].ToString());
                string id_franciză = current["cod_r"].ToString();

                Form3 formActualizare = new Form3();
                //formActualizare.setAllDetails(cod_c, nume, dimensiune, valoare, nr_camere, id_franciză);
                formActualizare.setConnectionString(connectionString);
                formActualizare.setForm(this);
                formActualizare.Show();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        public void updateData(object sender, EventArgs e)
        {
            //refreshAngajati(sender, e);
            //refreshFrancize(sender, e);
            
            /*
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    adapterFrancize.SelectCommand = new SqlCommand("SELECT * FROM Francize", connection);
                    adapterAngajați.SelectCommand = new SqlCommand("SELECT * FROM Angajați", connection);

                    adapterFrancize.Fill(dataSet, "Francize");
                    adapterAngajați.Fill(dataSet, "Angajați");

                    // dataGridViewFrancize.DataSource = dataSet.Tables["Francize"];

                    DataColumn pk = dataSet.Tables["Francize"].Columns["id_franciză"];
                    DataColumn fk = dataSet.Tables["Angajați"].Columns["id_franciză"];

                    DataRelation relation = new DataRelation("fk_Francize_Angajați", pk, fk, true);

                    dataSet.Relations.Add(relation);

                    bindingSourceFrancize.DataSource = dataSet.Tables["Francize"];
                    dataGridViewFrancize.DataSource = bindingSourceFrancize;

                    bindingSourceAngajați.DataSource = bindingSourceFrancize;
                    bindingSourceAngajați.DataMember = "fk_Francize_Angajați";

                    dataGridViewAngajati.DataSource = bindingSourceAngajați;

                    // textBox1.DataBindings.Add("Text", bsParent, "Nume", true);

                    textBox1.DataBindings.Add("Text", bindingSourceAngajați, "prenume", true);

                    // MessageBox.Show(dataGridViewAngajati.SelectedCells.ToString());


                    // dataGridViewAngajati.SelectedCells = 
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare: ", ex.Message);
            }

            */
        }

        private void refreshFrancize(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    adapterCastele.SelectCommand = new SqlCommand("SELECT * FROM Regate", connection);

                    DataColumn pk = dataSet.Tables["Regate"].Columns["cod_r"];
                    DataColumn fk = dataSet.Tables["Castele"].Columns["cod_r"];

                    DataRelation relation = new DataRelation("fk_Regate_Castele", pk, fk, true);

                    dataSet.Relations.RemoveAt(0);

                    dataSet.Tables["Castele"].Clear();

                    dataSet.Relations.Add(relation);

                    adapterRegate.Fill(dataSet, "Castele");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void refreshAngajati(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    adapterCastele.SelectCommand = new SqlCommand("SELECT * FROM Castele", connection);

                    DataColumn pk = dataSet.Tables["Regate"].Columns["id_regat"];
                    DataColumn fk = dataSet.Tables["Castele"].Columns["id_regat"];

                    DataRelation relation = new DataRelation("fk_Regate_Castele", pk, fk, true);

                    dataSet.Relations.RemoveAt(0);

                    dataSet.Tables["Castele"].Clear();

                    dataSet.Relations.Add(relation);

                    adapterRegate.Fill(dataSet, "Castele");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

    }
}
