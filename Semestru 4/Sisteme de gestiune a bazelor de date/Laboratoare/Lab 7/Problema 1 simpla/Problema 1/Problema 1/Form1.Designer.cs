
namespace Problema_1
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridViewFrancize = new System.Windows.Forms.DataGridView();
            this.labelFrancize = new System.Windows.Forms.Label();
            this.labelAngajati = new System.Windows.Forms.Label();
            this.dataGridViewAngajati = new System.Windows.Forms.DataGridView();
            this.stergeAngajat = new System.Windows.Forms.Button();
            this.adaugaAngajat = new System.Windows.Forms.Button();
            this.actualizeazaAngajat = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFrancize)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewAngajati)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewFrancize
            // 
            this.dataGridViewFrancize.BackgroundColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.dataGridViewFrancize.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewFrancize.Location = new System.Drawing.Point(46, 103);
            this.dataGridViewFrancize.Name = "dataGridViewFrancize";
            this.dataGridViewFrancize.Size = new System.Drawing.Size(477, 191);
            this.dataGridViewFrancize.TabIndex = 0;
            // 
            // labelFrancize
            // 
            this.labelFrancize.AutoSize = true;
            this.labelFrancize.BackColor = System.Drawing.Color.Gold;
            this.labelFrancize.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelFrancize.Location = new System.Drawing.Point(79, 78);
            this.labelFrancize.Name = "labelFrancize";
            this.labelFrancize.Size = new System.Drawing.Size(105, 20);
            this.labelFrancize.TabIndex = 1;
            this.labelFrancize.Text = "Tabel regat:";
            // 
            // labelAngajati
            // 
            this.labelAngajati.AutoSize = true;
            this.labelAngajati.BackColor = System.Drawing.Color.Gold;
            this.labelAngajati.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelAngajati.Location = new System.Drawing.Point(669, 78);
            this.labelAngajati.Name = "labelAngajati";
            this.labelAngajati.Size = new System.Drawing.Size(111, 20);
            this.labelAngajati.TabIndex = 3;
            this.labelAngajati.Text = "Tabel castel:";
            // 
            // dataGridViewAngajati
            // 
            this.dataGridViewAngajati.BackgroundColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.dataGridViewAngajati.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewAngajati.Location = new System.Drawing.Point(645, 103);
            this.dataGridViewAngajati.Name = "dataGridViewAngajati";
            this.dataGridViewAngajati.Size = new System.Drawing.Size(668, 191);
            this.dataGridViewAngajati.TabIndex = 2;
            // 
            // stergeAngajat
            // 
            this.stergeAngajat.BackColor = System.Drawing.Color.Peru;
            this.stergeAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.stergeAngajat.Location = new System.Drawing.Point(1090, 331);
            this.stergeAngajat.Name = "stergeAngajat";
            this.stergeAngajat.Size = new System.Drawing.Size(182, 55);
            this.stergeAngajat.TabIndex = 5;
            this.stergeAngajat.Text = "Șterge castel";
            this.stergeAngajat.UseVisualStyleBackColor = false;
            this.stergeAngajat.Click += new System.EventHandler(this.button1_Click);
            // 
            // adaugaAngajat
            // 
            this.adaugaAngajat.BackColor = System.Drawing.Color.Peru;
            this.adaugaAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.adaugaAngajat.Location = new System.Drawing.Point(83, 331);
            this.adaugaAngajat.Name = "adaugaAngajat";
            this.adaugaAngajat.Size = new System.Drawing.Size(182, 55);
            this.adaugaAngajat.TabIndex = 6;
            this.adaugaAngajat.Text = "Adaugă castel după regat";
            this.adaugaAngajat.UseVisualStyleBackColor = false;
            this.adaugaAngajat.Click += new System.EventHandler(this.adaugaAngajat_Click);
            // 
            // actualizeazaAngajat
            // 
            this.actualizeazaAngajat.BackColor = System.Drawing.Color.Peru;
            this.actualizeazaAngajat.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.actualizeazaAngajat.Location = new System.Drawing.Point(673, 331);
            this.actualizeazaAngajat.Name = "actualizeazaAngajat";
            this.actualizeazaAngajat.Size = new System.Drawing.Size(182, 55);
            this.actualizeazaAngajat.TabIndex = 7;
            this.actualizeazaAngajat.Text = "Actualizează castel";
            this.actualizeazaAngajat.UseVisualStyleBackColor = false;
            this.actualizeazaAngajat.Click += new System.EventHandler(this.actualizeazaAngajat_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ButtonFace;
            this.ClientSize = new System.Drawing.Size(1390, 557);
            this.Controls.Add(this.actualizeazaAngajat);
            this.Controls.Add(this.adaugaAngajat);
            this.Controls.Add(this.stergeAngajat);
            this.Controls.Add(this.labelAngajati);
            this.Controls.Add(this.dataGridViewAngajati);
            this.Controls.Add(this.labelFrancize);
            this.Controls.Add(this.dataGridViewFrancize);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewFrancize)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewAngajati)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewFrancize;
        private System.Windows.Forms.Label labelFrancize;
        private System.Windows.Forms.Label labelAngajati;
        private System.Windows.Forms.DataGridView dataGridViewAngajati;
        private System.Windows.Forms.Button stergeAngajat;
        private System.Windows.Forms.Button adaugaAngajat;
        private System.Windows.Forms.Button actualizeazaAngajat;
    }
}

