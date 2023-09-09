
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
            this.dataGridViewParent = new System.Windows.Forms.DataGridView();
            this.parentTableLabel = new System.Windows.Forms.Label();
            this.childTableLabel = new System.Windows.Forms.Label();
            this.dataGridViewChild = new System.Windows.Forms.DataGridView();
            this.mainDeleteButton = new System.Windows.Forms.Button();
            this.mainAddButton = new System.Windows.Forms.Button();
            this.mainModifyButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewParent
            // 
            this.dataGridViewParent.BackgroundColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.dataGridViewParent.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParent.Location = new System.Drawing.Point(46, 103);
            this.dataGridViewParent.Name = "dataGridViewParent";
            this.dataGridViewParent.Size = new System.Drawing.Size(477, 191);
            this.dataGridViewParent.TabIndex = 0;
            // 
            // parentTableLabel
            // 
            this.parentTableLabel.AutoSize = true;
            this.parentTableLabel.BackColor = System.Drawing.Color.Gold;
            this.parentTableLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.parentTableLabel.Location = new System.Drawing.Point(79, 78);
            this.parentTableLabel.Name = "parentTableLabel";
            this.parentTableLabel.Size = new System.Drawing.Size(0, 20);
            this.parentTableLabel.TabIndex = 1;
            // 
            // childTableLabel
            // 
            this.childTableLabel.AutoSize = true;
            this.childTableLabel.BackColor = System.Drawing.Color.Gold;
            this.childTableLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.childTableLabel.Location = new System.Drawing.Point(669, 78);
            this.childTableLabel.Name = "childTableLabel";
            this.childTableLabel.Size = new System.Drawing.Size(0, 20);
            this.childTableLabel.TabIndex = 3;
            // 
            // dataGridViewChild
            // 
            this.dataGridViewChild.BackgroundColor = System.Drawing.SystemColors.GradientActiveCaption;
            this.dataGridViewChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewChild.Location = new System.Drawing.Point(645, 103);
            this.dataGridViewChild.Name = "dataGridViewChild";
            this.dataGridViewChild.Size = new System.Drawing.Size(577, 191);
            this.dataGridViewChild.TabIndex = 2;
            // 
            // mainDeleteButton
            // 
            this.mainDeleteButton.BackColor = System.Drawing.Color.Peru;
            this.mainDeleteButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mainDeleteButton.Location = new System.Drawing.Point(979, 331);
            this.mainDeleteButton.Name = "mainDeleteButton";
            this.mainDeleteButton.Size = new System.Drawing.Size(182, 55);
            this.mainDeleteButton.TabIndex = 5;
            this.mainDeleteButton.UseVisualStyleBackColor = false;
            this.mainDeleteButton.Click += new System.EventHandler(this.button1_Click);
            // 
            // mainAddButton
            // 
            this.mainAddButton.BackColor = System.Drawing.Color.Peru;
            this.mainAddButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mainAddButton.Location = new System.Drawing.Point(83, 331);
            this.mainAddButton.Name = "mainAddButton";
            this.mainAddButton.Size = new System.Drawing.Size(182, 55);
            this.mainAddButton.TabIndex = 6;
            this.mainAddButton.UseVisualStyleBackColor = false;
            this.mainAddButton.Click += new System.EventHandler(this.adaugaAngajat_Click);
            // 
            // mainModifyButton
            // 
            this.mainModifyButton.BackColor = System.Drawing.Color.Peru;
            this.mainModifyButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.mainModifyButton.Location = new System.Drawing.Point(673, 331);
            this.mainModifyButton.Name = "mainModifyButton";
            this.mainModifyButton.Size = new System.Drawing.Size(182, 55);
            this.mainModifyButton.TabIndex = 7;
            this.mainModifyButton.UseVisualStyleBackColor = false;
            this.mainModifyButton.Click += new System.EventHandler(this.actualizeazaAngajat_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.OliveDrab;
            this.ClientSize = new System.Drawing.Size(1314, 557);
            this.Controls.Add(this.mainModifyButton);
            this.Controls.Add(this.mainAddButton);
            this.Controls.Add(this.mainDeleteButton);
            this.Controls.Add(this.childTableLabel);
            this.Controls.Add(this.dataGridViewChild);
            this.Controls.Add(this.parentTableLabel);
            this.Controls.Add(this.dataGridViewParent);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridViewParent;
        private System.Windows.Forms.Label parentTableLabel;
        private System.Windows.Forms.Label childTableLabel;
        private System.Windows.Forms.DataGridView dataGridViewChild;
        private System.Windows.Forms.Button mainDeleteButton;
        private System.Windows.Forms.Button mainAddButton;
        private System.Windows.Forms.Button mainModifyButton;
    }
}

