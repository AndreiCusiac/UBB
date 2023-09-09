using System;
using System.Configuration;
using System.Collections.Specialized;
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
        //string connectionString = @"Server=DESKTOP-QOGGLUB\SQLEXPRESS;Initial Catalog=Farma;Integrated Security=true;";
        string connectionString;
        DataSet dataSet = new DataSet();
        SqlDataAdapter adapterParent = new SqlDataAdapter();
        SqlDataAdapter adapterChild = new SqlDataAdapter();

        BindingSource bindingSourceParent = new BindingSource();
        BindingSource bindingSourceChild = new BindingSource();

        NameValueCollection sAll { get; set; }

        string selectCommandParent;
        string selectCommandChild;
        string parentTableName;
        string childTableName;

        string foreignKeyColumn;
        string foreignKeyRelationName;

        public Form1()
        {
            InitializeComponent();
        }

        public void setAppSettingsAttributes(NameValueCollection allAttributes)
        {
            sAll = allAttributes;
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            init_button_label();
            connectionString = sAll["connectionString"];
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    selectCommandParent = sAll["selectCommandParent"];
                    selectCommandChild = sAll["selectCommandChild"];

                    parentTableName = sAll["parentTableName"];
                    childTableName = sAll["childTableName"];

                    foreignKeyColumn = sAll["foreignKeyColumn"];
                    foreignKeyRelationName = sAll["foreignKeyRelationName"];

                    adapterParent.SelectCommand = new SqlCommand(selectCommandParent, connection);
                    adapterChild.SelectCommand = new SqlCommand(selectCommandChild, connection);

                    adapterParent.Fill(dataSet, parentTableName);
                    adapterChild.Fill(dataSet, childTableName);

                    // dataGridViewFrancize.DataSource = dataSet.Tables["Francize"];

                    DataColumn pk = dataSet.Tables[parentTableName].Columns[foreignKeyColumn];
                    DataColumn fk = dataSet.Tables[childTableName].Columns[foreignKeyColumn];

                    DataRelation relation = new DataRelation(foreignKeyRelationName, pk, fk, true);

                    dataSet.Relations.Add(relation);

                    bindingSourceParent.DataSource = dataSet.Tables[parentTableName];
                    dataGridViewParent.DataSource = bindingSourceParent;

                    bindingSourceChild.DataSource = bindingSourceParent;
                    bindingSourceChild.DataMember = foreignKeyRelationName;

                    dataGridViewChild.DataSource = bindingSourceChild;

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

        private void init_button_label()
        {
            parentTableLabel.Text = sAll["parentTableLabel"];
            childTableLabel.Text = sAll["childTableLabel"];

            mainAddButton.Text = sAll["mainAddButton"];
            mainModifyButton.Text = sAll["mainModifyButton"];
            mainDeleteButton.Text = sAll["mainDeleteButton"];
        }

        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    string deleteByAttributeOfChild = sAll["deleteByAttributeOfChild"];
                    string deleteCommandOfChildEntityByAttribute1 = sAll["deleteCommandOfChildEntityByAttribute1"];
                    string deleteChildEntityParameter = sAll["deleteChildEntityParameter"];

                    DataRowView current = (DataRowView)bindingSourceChild.Current;
                    string idByWhichToDelete = current[deleteByAttributeOfChild].ToString();
                    //string nume = current["nume"].ToString();
                    //string prenume = current["prenume"].ToString();

                    SqlCommand stergeAngajatDupaID = new SqlCommand(deleteCommandOfChildEntityByAttribute1, connection);
                    stergeAngajatDupaID.Parameters.AddWithValue(deleteChildEntityParameter, idByWhichToDelete);

                    int deleteRowCount = stergeAngajatDupaID.ExecuteNonQuery();
                    string mesaj;

                    if (deleteRowCount == 1)
                    {
                        mesaj = sAll["deleteConfirmationMessage"];
                        //mesaj = "S-a șters angajatul " + nume + " " + prenume + " cu ID-ul " + id_angajat;
                        //mesaj = "Succes!";
                    }
                    else
                    {
                        //mesaj = "S-au șters " + deleteRowCount + " angajați";
                        mesaj = "Ceva nu a mers bine la ștergere!";
                    }

                    //updateData(sender, e);
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
                DataRowView current = (DataRowView)bindingSourceParent.Current;

                string insertChildToParentByAttribute1 = sAll["insertChildToParentByAttribute1"];

                string valueOfParentAttribute1 = current[insertChildToParentByAttribute1].ToString();

                Form2 formAdaugare = new Form2();
                formAdaugare.setForeignKeyColumn(valueOfParentAttribute1);
                formAdaugare.setConnectionString(connectionString);
                formAdaugare.setForm(this);
                formAdaugare.setAppSettingsAttributes(sAll);
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
                DataRowView current = (DataRowView)bindingSourceChild.Current;
                List<String> allDetails = new List<string>();
                int noOfDetails = Int32.Parse(sAll["noOfAttributesChild"]);

                allDetails.Add(sAll["noOfAttributesChild"]);

                for (int i = 1; i <= noOfDetails; i++)
                {
                    string currentAttribute = sAll["attributeChild" + i.ToString()];
                    allDetails.Add(current[currentAttribute].ToString());
                }

                /*
                string id_angajat = current["id_angajat"].ToString();
                string nume = current["nume"].ToString();
                string prenume = current["prenume"].ToString();
                string pozitie = current["poziție"].ToString();
                string id_franciză = current["id_franciză"].ToString();
                */

                Form3 formActualizare = new Form3();
                //formActualizare.setAllDetails(id_angajat, nume, prenume, pozitie, id_franciză);
                formActualizare.setAllDetailsByList(allDetails, noOfDetails);
                formActualizare.setConnectionString(connectionString);
                formActualizare.setForm(this);
                formActualizare.setAppSettingsAttributes(sAll);
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
                    adapterChild.SelectCommand = new SqlCommand("SELECT * FROM Francize", connection);

                    DataColumn pk = dataSet.Tables["Francize"].Columns["id_franciză"];
                    DataColumn fk = dataSet.Tables["Angajați"].Columns["id_franciză"];

                    DataRelation relation = new DataRelation("fk_Francize_Angajați", pk, fk, true);

                    dataSet.Relations.RemoveAt(0);

                    dataSet.Tables["Francize"].Clear();

                    dataSet.Relations.Add(relation);

                    adapterParent.Fill(dataSet, "Francize");
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
                    adapterChild.SelectCommand = new SqlCommand("SELECT * FROM Angajați", connection);

                    DataColumn pk = dataSet.Tables["Francize"].Columns["id_franciză"];
                    DataColumn fk = dataSet.Tables["Angajați"].Columns["id_franciză"];

                    DataRelation relation = new DataRelation("fk_Francize_Angajați", pk, fk, true);

                    dataSet.Relations.RemoveAt(0);

                    dataSet.Tables["Angajați"].Clear();

                    dataSet.Relations.Add(relation);

                    adapterParent.Fill(dataSet, "Angajați");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

    }
}
