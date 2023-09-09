using System;
using System.Collections.Generic;
using System.Collections.Specialized;
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

        List<String> allDetails = new List<string>();
        List<TextBox> allTextBoxesOld = new List<TextBox>();
        List<TextBox> allTextBoxesNew = new List<TextBox>();

        List<Label> allLabels = new List<Label>();
        int noOfDetails;

        int textBoxOldCount = 1;
        int textBoxNewCount = 1;

        int leftOfOldTextBoxes = 242;
        int topOfOldTextBoxes = 146;
        int currentTopOfOld = 146;

        int leftOfNewTextBoxes = 251;
        int topOfNewTextBoxes = 146;
        int currentTopOfNew = 93;

        int leftOfLabels = 46;
        int currentTopOfLabels = 95;

        /*

        string id_franciza;
        string nume;
        string prenume;
        string pozitie;
        string id;

        */

        string foreignKeyColumnValue;

        NameValueCollection sAll;

        public void setForeignKeyColumn(string id)
        {
            foreignKeyColumnValue = id;
        }

        public void setConnectionString(string connection)
        {
            connectionString = connection;
        }

        public void setForm(Form1 form)
        {
            form1 = form;
        }

        public void setAppSettingsAttributes(NameValueCollection allAttributes)
        {
            sAll = allAttributes;
        }

        public Form2()
        {
            InitializeComponent();
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            labelDetalii.Text = sAll["newChildLabel"];
            noOfDetails = Int32.Parse(sAll["noOfAttributesChild"]);

            init_button_label();
            createRequiredTextBoxes();

            allTextBoxesNew[noOfDetails].Text = foreignKeyColumnValue;
        }

        private void createRequiredTextBoxes()
        {
            // creating new attributes text boxes

            allTextBoxesNew.Add(null);

            for (int i = 1; i <= noOfDetails; i++)
            {
                TextBox textBox = new TextBox();
                this.Controls.Add(textBox);

                if (i == noOfDetails)
                {
                    textBox.ReadOnly = true;
                    textBox.Enabled = false;
                }
                else
                {
                    textBox.ReadOnly = false;
                    textBox.Enabled = true;
                }

                textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0))); ;
                textBox.Size = new System.Drawing.Size(212, 29);
                textBox.Location = new System.Drawing.Point(leftOfNewTextBoxes, currentTopOfNew);
                textBox.Name = "newAttributeChild" + i.ToString();

                allTextBoxesNew.Add(textBox);

                currentTopOfNew += 60;
            }

            // creating new labels

            allLabels.Add(null);

            for (int i = 1; i <= noOfDetails; i++)
            {
                Label label = new Label();
                this.Controls.Add(label);

                label.AutoSize = true;
                label.BackColor = System.Drawing.Color.Gold;
                label.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));;
                label.Text = sAll["attributeChildN" + i.ToString()];
                label.Location = new System.Drawing.Point(leftOfLabels, currentTopOfLabels);


                allLabels.Add(label);

                currentTopOfLabels += 60;
            }

            /*
            this.textBoxNumeAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxNumeAngajat.Location = new System.Drawing.Point(251, 168);
            this.textBoxNumeAngajat.Name = "textBoxNumeAngajat";
            this.textBoxNumeAngajat.Size = new System.Drawing.Size(212, 29);
            this.textBoxNumeAngajat.TabIndex = 3;
            // 
            // labelNew3
            // 
            this.labelNew3.AutoSize = true;
            this.labelNew3.BackColor = System.Drawing.Color.Gold;
            this.labelNew3.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelNew3.Location = new System.Drawing.Point(46, 244);
            this.labelNew3.Name = "labelNew3";
            this.labelNew3.Size = new System.Drawing.Size(112, 25);
            this.labelNew3.TabIndex = 6;
            this.labelNew3.Text = "Prenume:";
            */
        }

        private void init_button_label()
        {
            labelDetalii.Text = sAll["newChildLabel"];
            addNewButton.Text = sAll["addNewButton"];
            abortButton.Text = sAll["abortButton"];
        }

        private void addAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    /*
                    id = textBoxIdAngajat.Text;
                    nume = textBoxNumeAngajat.Text;
                    prenume = textBoxPreumeAngajat.Text;
                    pozitie = textBoxPozitieAngajat.Text;
                    */

                    SqlCommand adaugaAngajatCommand = new SqlCommand(sAll["insertCommandOfChildEntity"], connection);

                    /*for (int i = 1; i <= noOfDetails; i++)
                    {
                        adaugaAngajatCommand.Parameters.AddWithValue(sAll["insertChildEntityParameter" + i.ToString()], allTextBoxesNew[i].Text);
                    }*/

                    for (int i = 2; i <= noOfDetails; i++)
                    {
                        adaugaAngajatCommand.Parameters.AddWithValue(sAll["insertChildEntityParameter" + i.ToString()], allTextBoxesNew[i].Text);
                    }

                    /*
                    adaugaAngajatCommand.Parameters.AddWithValue("@id_angajat", id);
                    adaugaAngajatCommand.Parameters.AddWithValue("@nume", nume);
                    adaugaAngajatCommand.Parameters.AddWithValue("@prenume", prenume);
                    adaugaAngajatCommand.Parameters.AddWithValue("@poziție", pozitie);
                    adaugaAngajatCommand.Parameters.AddWithValue("@id_franciză", id_franciza);
                    */

                    int insertRowCount = adaugaAngajatCommand.ExecuteNonQuery();
                    string mesaj = "";

                    if (insertRowCount == 1)
                    {
                        mesaj = sAll["insertConfirmationMessage"];
                        //mesaj = "A fost adăugat angajatul " + nume + " " + prenume + " cu ID-ul " + id + " în franciza " + id_franciza;
                    }
                    else
                    {
                        mesaj = "Ceva nu a mers bine la adăugare!";
                        //mesaj = "Au fost adăugați " + insertRowCount + " angajați";
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
