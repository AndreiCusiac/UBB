
namespace Basket
{
    partial class HomeForm
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
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.registeredTicketBooth = new System.Windows.Forms.Label();
            this.buttonRefresh = new System.Windows.Forms.Button();
            this.buttonLogout = new System.Windows.Forms.Button();
            this.buttonSortNormal = new System.Windows.Forms.Button();
            this.buttonSortBySeats = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.textBoxSeats = new System.Windows.Forms.TextBox();
            this.textBoxName = new System.Windows.Forms.TextBox();
            this.buttonSell = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.BackgroundColor = System.Drawing.SystemColors.ActiveCaption;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(106, 140);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(956, 211);
            this.dataGridView1.TabIndex = 8;
            // 
            // registeredTicketBooth
            // 
            this.registeredTicketBooth.AutoSize = true;
            this.registeredTicketBooth.Font = new System.Drawing.Font("Microsoft Sans Serif", 26.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.registeredTicketBooth.ForeColor = System.Drawing.Color.Transparent;
            this.registeredTicketBooth.Location = new System.Drawing.Point(410, 32);
            this.registeredTicketBooth.Name = "registeredTicketBooth";
            this.registeredTicketBooth.Size = new System.Drawing.Size(254, 39);
            this.registeredTicketBooth.TabIndex = 9;
            this.registeredTicketBooth.Text = "Registered as ";
            // 
            // buttonRefresh
            // 
            this.buttonRefresh.BackColor = System.Drawing.Color.OliveDrab;
            this.buttonRefresh.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttonRefresh.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonRefresh.ForeColor = System.Drawing.Color.Transparent;
            this.buttonRefresh.Location = new System.Drawing.Point(911, 21);
            this.buttonRefresh.Name = "buttonRefresh";
            this.buttonRefresh.Size = new System.Drawing.Size(83, 74);
            this.buttonRefresh.TabIndex = 10;
            this.buttonRefresh.Text = "Refresh";
            this.buttonRefresh.UseVisualStyleBackColor = false;
            this.buttonRefresh.Click += new System.EventHandler(this.buttonRefresh_Click);
            // 
            // buttonLogout
            // 
            this.buttonLogout.BackColor = System.Drawing.Color.Crimson;
            this.buttonLogout.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttonLogout.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonLogout.ForeColor = System.Drawing.Color.Transparent;
            this.buttonLogout.Location = new System.Drawing.Point(1042, 21);
            this.buttonLogout.Name = "buttonLogout";
            this.buttonLogout.Size = new System.Drawing.Size(83, 74);
            this.buttonLogout.TabIndex = 11;
            this.buttonLogout.Text = "Log out";
            this.buttonLogout.UseVisualStyleBackColor = false;
            this.buttonLogout.Click += new System.EventHandler(this.buttonLogout_Click);
            // 
            // buttonSortNormal
            // 
            this.buttonSortNormal.BackColor = System.Drawing.Color.BurlyWood;
            this.buttonSortNormal.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttonSortNormal.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonSortNormal.ForeColor = System.Drawing.SystemColors.ControlText;
            this.buttonSortNormal.Location = new System.Drawing.Point(811, 368);
            this.buttonSortNormal.Name = "buttonSortNormal";
            this.buttonSortNormal.Size = new System.Drawing.Size(106, 61);
            this.buttonSortNormal.TabIndex = 12;
            this.buttonSortNormal.Text = "Ordonează uzual";
            this.buttonSortNormal.UseVisualStyleBackColor = false;
            this.buttonSortNormal.Click += new System.EventHandler(this.buttonSortNormal_Click);
            // 
            // buttonSortBySeats
            // 
            this.buttonSortBySeats.BackColor = System.Drawing.Color.BurlyWood;
            this.buttonSortBySeats.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttonSortBySeats.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonSortBySeats.ForeColor = System.Drawing.SystemColors.ControlText;
            this.buttonSortBySeats.Location = new System.Drawing.Point(956, 368);
            this.buttonSortBySeats.Name = "buttonSortBySeats";
            this.buttonSortBySeats.Size = new System.Drawing.Size(106, 61);
            this.buttonSortBySeats.TabIndex = 13;
            this.buttonSortBySeats.Text = "Ordonează după locuri";
            this.buttonSortBySeats.UseVisualStyleBackColor = false;
            this.buttonSortBySeats.Click += new System.EventHandler(this.buttonSortBySeats_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 21.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.Transparent;
            this.label1.Location = new System.Drawing.Point(100, 94);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(399, 33);
            this.label1.TabIndex = 14;
            this.label1.Text = "Toate meciurile disponibile:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.ForeColor = System.Drawing.Color.Transparent;
            this.label3.Location = new System.Drawing.Point(108, 510);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(127, 29);
            this.label3.TabIndex = 18;
            this.label3.Text = "Nr. locuri:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.ForeColor = System.Drawing.Color.Transparent;
            this.label2.Location = new System.Drawing.Point(154, 448);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(89, 29);
            this.label2.TabIndex = 17;
            this.label2.Text = "Nume:";
            // 
            // textBoxSeats
            // 
            this.textBoxSeats.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxSeats.Location = new System.Drawing.Point(281, 504);
            this.textBoxSeats.Name = "textBoxSeats";
            this.textBoxSeats.Size = new System.Drawing.Size(173, 35);
            this.textBoxSeats.TabIndex = 16;
            // 
            // textBoxName
            // 
            this.textBoxName.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxName.Location = new System.Drawing.Point(281, 445);
            this.textBoxName.Name = "textBoxName";
            this.textBoxName.Size = new System.Drawing.Size(173, 35);
            this.textBoxName.TabIndex = 15;
            // 
            // buttonSell
            // 
            this.buttonSell.BackColor = System.Drawing.Color.Goldenrod;
            this.buttonSell.Cursor = System.Windows.Forms.Cursors.Hand;
            this.buttonSell.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonSell.ForeColor = System.Drawing.SystemColors.ControlText;
            this.buttonSell.Location = new System.Drawing.Point(320, 566);
            this.buttonSell.Name = "buttonSell";
            this.buttonSell.Size = new System.Drawing.Size(101, 43);
            this.buttonSell.TabIndex = 19;
            this.buttonSell.Text = "Vinde";
            this.buttonSell.UseVisualStyleBackColor = false;
            this.buttonSell.Click += new System.EventHandler(this.buttonSell_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 21.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.ForeColor = System.Drawing.Color.Transparent;
            this.label4.Location = new System.Drawing.Point(256, 396);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(224, 33);
            this.label4.TabIndex = 20;
            this.label4.Text = "Vânzare bilete:";
            // 
            // HomeForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.DarkSlateGray;
            this.ClientSize = new System.Drawing.Size(1176, 635);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.buttonSell);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.textBoxSeats);
            this.Controls.Add(this.textBoxName);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.buttonSortBySeats);
            this.Controls.Add(this.buttonSortNormal);
            this.Controls.Add(this.buttonLogout);
            this.Controls.Add(this.buttonRefresh);
            this.Controls.Add(this.registeredTicketBooth);
            this.Controls.Add(this.dataGridView1);
            this.Name = "HomeForm";
            this.Text = "HomeForm";
            this.Load += new System.EventHandler(this.HomeForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Label registeredTicketBooth;
        private System.Windows.Forms.Button buttonRefresh;
        private System.Windows.Forms.Button buttonLogout;
        private System.Windows.Forms.Button buttonSortNormal;
        private System.Windows.Forms.Button buttonSortBySeats;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox textBoxSeats;
        private System.Windows.Forms.TextBox textBoxName;
        private System.Windows.Forms.Button buttonSell;
        private System.Windows.Forms.Label label4;
    }
}