
namespace Problema_1
{
    partial class Form2
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
            this.labelDetalii = new System.Windows.Forms.Label();
            this.addNewButton = new System.Windows.Forms.Button();
            this.abortButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // labelDetalii
            // 
            this.labelDetalii.AutoSize = true;
            this.labelDetalii.BackColor = System.Drawing.Color.Transparent;
            this.labelDetalii.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelDetalii.ForeColor = System.Drawing.Color.Transparent;
            this.labelDetalii.Location = new System.Drawing.Point(83, 19);
            this.labelDetalii.Name = "labelDetalii";
            this.labelDetalii.Size = new System.Drawing.Size(0, 37);
            this.labelDetalii.TabIndex = 11;
            // 
            // addNewButton
            // 
            this.addNewButton.BackColor = System.Drawing.Color.YellowGreen;
            this.addNewButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.addNewButton.Location = new System.Drawing.Point(51, 470);
            this.addNewButton.Name = "addNewButton";
            this.addNewButton.Size = new System.Drawing.Size(204, 55);
            this.addNewButton.TabIndex = 12;
            this.addNewButton.UseVisualStyleBackColor = false;
            this.addNewButton.Click += new System.EventHandler(this.addAngajat_Click);
            // 
            // abortButton
            // 
            this.abortButton.BackColor = System.Drawing.Color.OrangeRed;
            this.abortButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.abortButton.Location = new System.Drawing.Point(307, 470);
            this.abortButton.Name = "abortButton";
            this.abortButton.Size = new System.Drawing.Size(137, 55);
            this.abortButton.TabIndex = 13;
            this.abortButton.UseVisualStyleBackColor = false;
            this.abortButton.Click += new System.EventHandler(this.abortButton_Click);
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.OliveDrab;
            this.ClientSize = new System.Drawing.Size(514, 570);
            this.Controls.Add(this.abortButton);
            this.Controls.Add(this.addNewButton);
            this.Controls.Add(this.labelDetalii);
            this.Name = "Form2";
            this.Text = "Form2";
            this.Load += new System.EventHandler(this.Form2_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.Label labelDetalii;
        private System.Windows.Forms.Button addNewButton;
        private System.Windows.Forms.Button abortButton;
    }
}