namespace EBI_Publish_Workflows
{
    partial class formPublishWorkflows
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(formPublishWorkflows));
            this.grpBoxSource = new System.Windows.Forms.GroupBox();
            this.grpbxTarget = new System.Windows.Forms.GroupBox();
            this.opnfileSrcWrkflw = new System.Windows.Forms.OpenFileDialog();
            this.lblWorkflowLoc = new System.Windows.Forms.Label();
            this.btnBrowse = new System.Windows.Forms.Button();
            this.txtBoxWorkflowLoc = new System.Windows.Forms.TextBox();
            this.btnBrwseClear = new System.Windows.Forms.Button();
            this.btnExit = new System.Windows.Forms.Button();
            this.btnPublish = new System.Windows.Forms.Button();
            this.btnRetrieve = new System.Windows.Forms.Button();
            this.grpbxCollection = new System.Windows.Forms.GroupBox();
            this.lblLibNames = new System.Windows.Forms.Label();
            this.treeView1 = new System.Windows.Forms.TreeView();
            this.btnMark = new System.Windows.Forms.Button();
            this.btntreeViewClear = new System.Windows.Forms.Button();
            this.grpbxEnv = new System.Windows.Forms.GroupBox();
            this.rdobtnDev = new System.Windows.Forms.RadioButton();
            this.rdobtnTst = new System.Windows.Forms.RadioButton();
            this.rdobtnPrd = new System.Windows.Forms.RadioButton();
            this.lblMarked = new System.Windows.Forms.Label();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.lblRetrieve = new System.Windows.Forms.Label();
            this.btnReset = new System.Windows.Forms.Button();
            this.lblPublishLoading = new System.Windows.Forms.Label();
            this.grpBoxSource.SuspendLayout();
            this.grpbxTarget.SuspendLayout();
            this.grpbxCollection.SuspendLayout();
            this.grpbxEnv.SuspendLayout();
            this.SuspendLayout();
            // 
            // grpBoxSource
            // 
            this.grpBoxSource.Controls.Add(this.btnBrwseClear);
            this.grpBoxSource.Controls.Add(this.lblWorkflowLoc);
            this.grpBoxSource.Controls.Add(this.btnBrowse);
            this.grpBoxSource.Controls.Add(this.txtBoxWorkflowLoc);
            this.grpBoxSource.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.grpBoxSource.Location = new System.Drawing.Point(34, 27);
            this.grpBoxSource.Name = "grpBoxSource";
            this.grpBoxSource.Size = new System.Drawing.Size(393, 318);
            this.grpBoxSource.TabIndex = 0;
            this.grpBoxSource.TabStop = false;
            this.grpBoxSource.Text = "Source";
            // 
            // grpbxTarget
            // 
            this.grpbxTarget.Controls.Add(this.lblRetrieve);
            this.grpbxTarget.Controls.Add(this.grpbxEnv);
            this.grpbxTarget.Controls.Add(this.grpbxCollection);
            this.grpbxTarget.Controls.Add(this.btnRetrieve);
            this.grpbxTarget.Location = new System.Drawing.Point(477, 27);
            this.grpbxTarget.Name = "grpbxTarget";
            this.grpbxTarget.Size = new System.Drawing.Size(482, 472);
            this.grpbxTarget.TabIndex = 1;
            this.grpbxTarget.TabStop = false;
            this.grpbxTarget.Text = "Target";
            // 
            // opnfileSrcWrkflw
            // 
            this.opnfileSrcWrkflw.AddExtension = false;
            this.opnfileSrcWrkflw.FileName = " ";
            this.opnfileSrcWrkflw.Filter = "Nintex Workflows|*.nwf|All Files|*.*";
            this.opnfileSrcWrkflw.Multiselect = true;
            this.opnfileSrcWrkflw.ShowHelp = true;
            this.opnfileSrcWrkflw.Title = "Browse the Documents";
            // 
            // lblWorkflowLoc
            // 
            this.lblWorkflowLoc.AutoSize = true;
            this.lblWorkflowLoc.Location = new System.Drawing.Point(6, 30);
            this.lblWorkflowLoc.Name = "lblWorkflowLoc";
            this.lblWorkflowLoc.Size = new System.Drawing.Size(104, 13);
            this.lblWorkflowLoc.TabIndex = 5;
            this.lblWorkflowLoc.Text = "Workflows Location:";
            // 
            // btnBrowse
            // 
            this.btnBrowse.Location = new System.Drawing.Point(305, 282);
            this.btnBrowse.Name = "btnBrowse";
            this.btnBrowse.Size = new System.Drawing.Size(82, 23);
            this.btnBrowse.TabIndex = 4;
            this.btnBrowse.Text = "Browse...";
            this.btnBrowse.UseVisualStyleBackColor = true;
            this.btnBrowse.Click += new System.EventHandler(this.btnBrowse_Click);
            // 
            // txtBoxWorkflowLoc
            // 
            this.txtBoxWorkflowLoc.Location = new System.Drawing.Point(5, 49);
            this.txtBoxWorkflowLoc.Multiline = true;
            this.txtBoxWorkflowLoc.Name = "txtBoxWorkflowLoc";
            this.txtBoxWorkflowLoc.ReadOnly = true;
            this.txtBoxWorkflowLoc.Size = new System.Drawing.Size(382, 219);
            this.txtBoxWorkflowLoc.TabIndex = 3;
            // 
            // btnBrwseClear
            // 
            this.btnBrwseClear.Location = new System.Drawing.Point(5, 282);
            this.btnBrwseClear.Name = "btnBrwseClear";
            this.btnBrwseClear.Size = new System.Drawing.Size(69, 23);
            this.btnBrwseClear.TabIndex = 6;
            this.btnBrwseClear.Text = "Clear";
            this.btnBrwseClear.UseVisualStyleBackColor = true;
            this.btnBrwseClear.Click += new System.EventHandler(this.btnBrwseClear_Click);
            // 
            // btnExit
            // 
            this.btnExit.Location = new System.Drawing.Point(346, 392);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(75, 23);
            this.btnExit.TabIndex = 8;
            this.btnExit.Text = "Exit";
            this.btnExit.UseVisualStyleBackColor = true;
            this.btnExit.Click += new System.EventHandler(this.btnExit_Click);
            // 
            // btnPublish
            // 
            this.btnPublish.Enabled = false;
            this.btnPublish.Location = new System.Drawing.Point(39, 392);
            this.btnPublish.Name = "btnPublish";
            this.btnPublish.Size = new System.Drawing.Size(151, 54);
            this.btnPublish.TabIndex = 7;
            this.btnPublish.Text = "Publish Workflows";
            this.btnPublish.UseVisualStyleBackColor = true;
            this.btnPublish.Click += new System.EventHandler(this.btnPublish_Click);
            // 
            // btnRetrieve
            // 
            this.btnRetrieve.Location = new System.Drawing.Point(21, 77);
            this.btnRetrieve.Name = "btnRetrieve";
            this.btnRetrieve.Size = new System.Drawing.Size(99, 23);
            this.btnRetrieve.TabIndex = 8;
            this.btnRetrieve.Text = "Retrieve";
            this.btnRetrieve.UseVisualStyleBackColor = true;
            this.btnRetrieve.Click += new System.EventHandler(this.btnRetrieve_Click);
            // 
            // grpbxCollection
            // 
            this.grpbxCollection.Controls.Add(this.lblMarked);
            this.grpbxCollection.Controls.Add(this.btntreeViewClear);
            this.grpbxCollection.Controls.Add(this.btnMark);
            this.grpbxCollection.Controls.Add(this.treeView1);
            this.grpbxCollection.Controls.Add(this.lblLibNames);
            this.grpbxCollection.Location = new System.Drawing.Point(21, 106);
            this.grpbxCollection.Name = "grpbxCollection";
            this.grpbxCollection.Size = new System.Drawing.Size(445, 351);
            this.grpbxCollection.TabIndex = 9;
            this.grpbxCollection.TabStop = false;
            this.grpbxCollection.Text = "Site Collection";
            this.grpbxCollection.Visible = false;
            // 
            // lblLibNames
            // 
            this.lblLibNames.AutoSize = true;
            this.lblLibNames.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLibNames.Location = new System.Drawing.Point(23, 18);
            this.lblLibNames.Name = "lblLibNames";
            this.lblLibNames.Size = new System.Drawing.Size(179, 13);
            this.lblLibNames.TabIndex = 0;
            this.lblLibNames.Text = "Select the Library/List Names:";
            // 
            // treeView1
            // 
            this.treeView1.CheckBoxes = true;
            this.treeView1.Location = new System.Drawing.Point(40, 39);
            this.treeView1.Name = "treeView1";
            this.treeView1.Size = new System.Drawing.Size(282, 306);
            this.treeView1.TabIndex = 1;
            this.treeView1.AfterCheck += new System.Windows.Forms.TreeViewEventHandler(this.treeView1_AfterCheck);
            // 
            // btnMark
            // 
            this.btnMark.Location = new System.Drawing.Point(328, 39);
            this.btnMark.Name = "btnMark";
            this.btnMark.Size = new System.Drawing.Size(101, 23);
            this.btnMark.TabIndex = 2;
            this.btnMark.Text = "Mark as Selected";
            this.btnMark.UseVisualStyleBackColor = true;
            this.btnMark.Click += new System.EventHandler(this.btnMark_Click);
            // 
            // btntreeViewClear
            // 
            this.btntreeViewClear.Location = new System.Drawing.Point(328, 103);
            this.btntreeViewClear.Name = "btntreeViewClear";
            this.btntreeViewClear.Size = new System.Drawing.Size(101, 23);
            this.btntreeViewClear.TabIndex = 3;
            this.btntreeViewClear.Text = "Clear Selection";
            this.btntreeViewClear.UseVisualStyleBackColor = true;
            this.btntreeViewClear.Click += new System.EventHandler(this.btntreeViewClear_Click);
            // 
            // grpbxEnv
            // 
            this.grpbxEnv.Controls.Add(this.rdobtnPrd);
            this.grpbxEnv.Controls.Add(this.rdobtnTst);
            this.grpbxEnv.Controls.Add(this.rdobtnDev);
            this.grpbxEnv.Location = new System.Drawing.Point(21, 30);
            this.grpbxEnv.Name = "grpbxEnv";
            this.grpbxEnv.Size = new System.Drawing.Size(445, 41);
            this.grpbxEnv.TabIndex = 10;
            this.grpbxEnv.TabStop = false;
            this.grpbxEnv.Text = "Select Target Environment";
            // 
            // rdobtnDev
            // 
            this.rdobtnDev.AutoSize = true;
            this.rdobtnDev.Location = new System.Drawing.Point(40, 19);
            this.rdobtnDev.Name = "rdobtnDev";
            this.rdobtnDev.Size = new System.Drawing.Size(43, 17);
            this.rdobtnDev.TabIndex = 0;
            this.rdobtnDev.Text = "dev";
            this.rdobtnDev.UseVisualStyleBackColor = true;
            // 
            // rdobtnTst
            // 
            this.rdobtnTst.AutoSize = true;
            this.rdobtnTst.Location = new System.Drawing.Point(205, 18);
            this.rdobtnTst.Name = "rdobtnTst";
            this.rdobtnTst.Size = new System.Drawing.Size(36, 17);
            this.rdobtnTst.TabIndex = 1;
            this.rdobtnTst.Text = "tst";
            this.rdobtnTst.UseVisualStyleBackColor = true;
            // 
            // rdobtnPrd
            // 
            this.rdobtnPrd.AutoSize = true;
            this.rdobtnPrd.Location = new System.Drawing.Point(360, 18);
            this.rdobtnPrd.Name = "rdobtnPrd";
            this.rdobtnPrd.Size = new System.Drawing.Size(40, 17);
            this.rdobtnPrd.TabIndex = 2;
            this.rdobtnPrd.Text = "prd";
            this.rdobtnPrd.UseVisualStyleBackColor = true;
            // 
            // lblMarked
            // 
            this.lblMarked.AutoSize = true;
            this.lblMarked.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblMarked.ForeColor = System.Drawing.Color.Green;
            this.lblMarked.Location = new System.Drawing.Point(333, 71);
            this.lblMarked.Name = "lblMarked";
            this.lblMarked.Size = new System.Drawing.Size(59, 15);
            this.lblMarked.TabIndex = 4;
            this.lblMarked.Text = "Marked!";
            this.lblMarked.Visible = false;
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "ajax-loader.gif");
            // 
            // lblRetrieve
            // 
            this.lblRetrieve.AutoSize = true;
            this.lblRetrieve.Location = new System.Drawing.Point(142, 86);
            this.lblRetrieve.Name = "lblRetrieve";
            this.lblRetrieve.Size = new System.Drawing.Size(54, 13);
            this.lblRetrieve.TabIndex = 11;
            this.lblRetrieve.Text = "Loading...";
            this.lblRetrieve.Visible = false;
            // 
            // btnReset
            // 
            this.btnReset.Location = new System.Drawing.Point(346, 433);
            this.btnReset.Name = "btnReset";
            this.btnReset.Size = new System.Drawing.Size(75, 23);
            this.btnReset.TabIndex = 13;
            this.btnReset.Text = "Reset";
            this.btnReset.UseVisualStyleBackColor = true;
            this.btnReset.Click += new System.EventHandler(this.btnReset_Click);
            // 
            // lblPublishLoading
            // 
            this.lblPublishLoading.AutoSize = true;
            this.lblPublishLoading.Location = new System.Drawing.Point(210, 433);
            this.lblPublishLoading.Name = "lblPublishLoading";
            this.lblPublishLoading.Size = new System.Drawing.Size(54, 13);
            this.lblPublishLoading.TabIndex = 12;
            this.lblPublishLoading.Text = "Loading...";
            this.lblPublishLoading.Visible = false;
            // 
            // formPublishWorkflows
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(984, 502);
            this.Controls.Add(this.btnReset);
            this.Controls.Add(this.lblPublishLoading);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.btnPublish);
            this.Controls.Add(this.grpbxTarget);
            this.Controls.Add(this.grpBoxSource);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "formPublishWorkflows";
            this.Text = "Publish Workflows";
            this.Load += new System.EventHandler(this.formPublishWorkflows_Load);
            this.grpBoxSource.ResumeLayout(false);
            this.grpBoxSource.PerformLayout();
            this.grpbxTarget.ResumeLayout(false);
            this.grpbxTarget.PerformLayout();
            this.grpbxCollection.ResumeLayout(false);
            this.grpbxCollection.PerformLayout();
            this.grpbxEnv.ResumeLayout(false);
            this.grpbxEnv.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.GroupBox grpBoxSource;
        private System.Windows.Forms.Label lblWorkflowLoc;
        private System.Windows.Forms.Button btnBrowse;
        private System.Windows.Forms.TextBox txtBoxWorkflowLoc;
        private System.Windows.Forms.GroupBox grpbxTarget;
        private System.Windows.Forms.OpenFileDialog opnfileSrcWrkflw;
        private System.Windows.Forms.Button btnBrwseClear;
        private System.Windows.Forms.Button btnExit;
        private System.Windows.Forms.Button btnPublish;
        private System.Windows.Forms.GroupBox grpbxCollection;
        private System.Windows.Forms.TreeView treeView1;
        private System.Windows.Forms.Label lblLibNames;
        private System.Windows.Forms.Button btnRetrieve;
        private System.Windows.Forms.Button btntreeViewClear;
        private System.Windows.Forms.Button btnMark;
        private System.Windows.Forms.GroupBox grpbxEnv;
        private System.Windows.Forms.RadioButton rdobtnPrd;
        private System.Windows.Forms.RadioButton rdobtnTst;
        private System.Windows.Forms.RadioButton rdobtnDev;
        private System.Windows.Forms.Label lblMarked;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.Label lblRetrieve;
        private System.Windows.Forms.Button btnReset;
        private System.Windows.Forms.Label lblPublishLoading;
    }
}

