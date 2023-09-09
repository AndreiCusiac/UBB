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
    public partial class Form3 : Form
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

        int leftOfNewTextBoxes = 596;
        int topOfNewTextBoxes = 146;
        int currentTopOfNew = 146;

        int leftOfLabels = 58;
        int currentTopOfLabels = 148;

        /*

        string id_franciza;
        string nume;
        string prenume;
        string pozitie;
        string id;

        */

        NameValueCollection sAll;

        public void setForeignKeyColumn(string id)
        {
            //id_franciza = id;
        }

        public void setAllDetailsByList(List<String> allD, int noOfD)
        {
            allDetails = allD;
            noOfDetails = noOfD;
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

        public void createRequiredTextBoxes ()
        {
            // creating old attributes text boxes

            allTextBoxesOld.Add(null);

            for (int i=1; i <= noOfDetails; i++)
            {
                TextBox textBox = new TextBox();
                this.Controls.Add(textBox);

                textBox.ReadOnly = true;
                textBox.Enabled = false;
                textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                textBox.Size = new System.Drawing.Size(212, 29);
                textBox.Location = new System.Drawing.Point(leftOfOldTextBoxes, currentTopOfOld);
                textBox.Name = "oldAttributeChild" + i.ToString();

                allTextBoxesOld.Add(textBox);

                currentTopOfOld += 60;
            }

            // creating new attributes text boxes

            allTextBoxesNew.Add(null);

            for (int i = 1; i <= noOfDetails; i++)
            {
                TextBox textBox = new TextBox();
                this.Controls.Add(textBox);

                if (i == 1)
                {
                    textBox.ReadOnly = true;
                    textBox.Enabled = false;
                }
                else
                {
                    textBox.ReadOnly = false;
                    textBox.Enabled = true;
                }
                
                textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
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
                label.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
                label.Text = sAll["attributeChildN" + i.ToString()];
                label.Location = new System.Drawing.Point(leftOfLabels, currentTopOfLabels);


                allLabels.Add(label);

                currentTopOfLabels += 60;
            }

            /*
            // 
            // textBoxPozitieAngajat
            // 
            this.textBoxPozitieAngajat.Enabled = false;
            this.textBoxPozitieAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxPozitieAngajat.Location = new System.Drawing.Point(242, 360);
            this.textBoxPozitieAngajat.Name = "textBoxPozitieAngajat";
            this.textBoxPozitieAngajat.ReadOnly = true;
            this.textBoxPozitieAngajat.Size = new System.Drawing.Size(212, 29);
            this.textBoxPozitieAngajat.TabIndex = 20;
            // 
            // labelPrenumeAngajat
            // 
            this.labelPrenumeAngajat.AutoSize = true;
            this.labelPrenumeAngajat.BackColor = System.Drawing.Color.Gold;
            this.labelPrenumeAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelPrenumeAngajat.Location = new System.Drawing.Point(58, 297);
            this.labelPrenumeAngajat.Name = "labelPrenumeAngajat";
            this.labelPrenumeAngajat.Size = new System.Drawing.Size(112, 25);
            this.labelPrenumeAngajat.TabIndex = 19;
            this.labelPrenumeAngajat.Text = "Prenume:";*/


        }

        public Form3()
        {
            InitializeComponent();
        }

        private void Form3_Load(object sender, EventArgs e)
        {
            init_button_label();
            createRequiredTextBoxes();

            for (int i=1; i <= noOfDetails; i++)
            {
                allTextBoxesOld[i].Text = allDetails[i];
                allTextBoxesNew[i].Text = allDetails[i];
            }

            /*
            textBoxIdAngajat.Text = id;
            textBoxNumeAngajat.Text = nume;
            textBoxPrenumeAngajat.Text = prenume;
            textBoxPozitieAngajat.Text = pozitie;
            textBoxFrancizaAngajat.Text = id_franciza;

            textBoxIdNouAngajat.Text = id;
            textBoxNumeNouAngajat.Text = nume;
            textBoxPrenumeNouAngajat.Text = prenume;
            textBoxPozitieNouAngajat.Text = pozitie;
            textBoxFrancizaNouAngajat.Text = id_franciza;
            */
        }

        private void init_button_label()
        {
            labelDetalii.Text = sAll["updateChildLabel"];
            modifyButton.Text = sAll["modifyButton"];
            abortButton.Text = sAll["abortButton"];
        }

        private void actAngajat_Click(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    connection.Open();

                    /*
                    id = textBoxIdAngajat.Text;
                    nume = textBoxNumeNouAngajat.Text;
                    prenume = textBoxPrenumeNouAngajat.Text;
                    pozitie = textBoxPozitieNouAngajat.Text;
                    id_franciza = textBoxFrancizaNouAngajat.Text;
                    */

                    SqlCommand actualizeazaAngajatCommand = new SqlCommand(sAll["updateCommandOfChildEntity"], connection);

                    for (int i = 1; i <= noOfDetails; i++)
                    {
                        actualizeazaAngajatCommand.Parameters.AddWithValue(sAll["updateChildEntityParameter" + i.ToString()], allTextBoxesNew[i].Text);
                    }

                    /*
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@id_angajat", id);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@nume", nume);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@prenume", prenume);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@poziție", pozitie);
                    actualizeazaAngajatCommand.Parameters.AddWithValue("@id_franciză", id_franciza);*/

                    int updateRowCount = actualizeazaAngajatCommand.ExecuteNonQuery();
                    string mesaj = "";

                    if (updateRowCount == 1)
                    {
                        mesaj = sAll["updateConfirmationMessage"];
                        //mesaj = "A fost actualizat angajatul " + nume + " " + prenume + " cu ID-ul " + id + " în franciza " + id_franciza;
                        //mesaj = "Succes!";
                    }
                    else
                    {
                        //mesaj = "Au fost actualizați " + updateRowCount + " angajați";
                        mesaj = "Ceva nu a mers bine la actualizare!";
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
