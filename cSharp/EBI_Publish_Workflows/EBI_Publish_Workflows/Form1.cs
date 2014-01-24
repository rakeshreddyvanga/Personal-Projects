using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace EBI_Publish_Workflows
{
    public partial class formPublishWorkflows : Form
    {
        List<string> workflowNames = new List<string>();
        String Env = string.Empty;
        String newLine = Environment.NewLine;
        List<TreeNode> checkedNodes = new List<TreeNode>();
        bool done = false;

        public formPublishWorkflows()
        {
            InitializeComponent();
        }

        private void btnBrowse_Click(object sender, EventArgs e)
        {
            DialogResult browseBtnResult = opnfileSrcWrkflw.ShowDialog();
            if (browseBtnResult == DialogResult.OK)
            {
                String locations = System.String.Empty;
                String[] fileNames = opnfileSrcWrkflw.FileNames;
                foreach (String file in fileNames)
                {
                    if (!txtBoxWorkflowLoc.Text.Contains(file))
                    {
                        workflowNames.Add(file);
                        locations = locations + file + newLine;
                    }
                }
                txtBoxWorkflowLoc.Text += locations;
            }
        }

        private void btnBrwseClear_Click(object sender, EventArgs e)
        {
            txtBoxWorkflowLoc.Clear();
            if (workflowNames != null)
            {
                workflowNames.Clear();
            }
        }

        private void btnPublish_Click(object sender, EventArgs e)
        {
            int percent = workflowNames.Count * checkedNodes.Count, pdone = 0;
            lblPublishLoading.Visible = true;
            if (checkedNodes.Count == 0)
            {
                MessageBox.Show("Please select the library names");
                btnReset_Click(sender, e);
            }
            else if (workflowNames.Count == 0)
            {
                MessageBox.Show("Please select a workflow");
                btnReset_Click(sender, e);
            }
            else
            {

                foreach (TreeNode node in checkedNodes)
                {
                    try
                    {
                        NintexWorkFlowServices.NintexWorkflowWS objWFService = new NintexWorkFlowServices.NintexWorkflowWS();
                        objWFService.Url = "https://edss.iu.edu/sites/" + Env + "/BI-Centers/" + node.Parent.Text + "/_vti_bin/nintexworkflow/workflow.asmx";
                        //MessageBox.Show(objWFService.Url);
                        objWFService.Credentials = System.Net.CredentialCache.DefaultCredentials;
                        foreach (String file in workflowNames)
                        {

                            byte[] arrWorkflowFile = System.IO.File.ReadAllBytes(@file);
                            //MessageBox.Show(file);
                            //MessageBox.Show("libName :" + node.Text);

                            //Webservice Call
                             objWFService.PublishFromNWF(arrWorkflowFile, node.Text, "WF-" + node.Text, true);
                            pdone++;
                            lblPublishLoading.Text = (pdone * 100) / percent + "% Completed.";

                        }

                    }
                    catch (Exception exception)
                    {
                        MessageBox.Show("An error occured while trying to publish the workflows: " + exception.Message);
                    }
                  
                }
                MessageBox.Show("Done");
                btnReset_Click(sender, e);
                workflowNames.Clear();
                lblPublishLoading.Visible = false;
                lblMarked.Visible = false;
            }
        }

        //helper method to get checked radio button
        private void getCheckedButton()
        {
            foreach (Control rdbutton in grpbxEnv.Controls)
            {
                if (((RadioButton)rdbutton).Checked)
                {
                    Env = ((RadioButton)rdbutton).Text;
                    break;
                }
                    
            }
        }

        private void btnRetrieve_Click(object sender, EventArgs e)
        {
            getCheckedButton();
            treeView1.Nodes.Clear();
            btnRetrieve.Enabled = false;
            lblRetrieve.Visible = true;
            grpbxCollection.Visible = false;
            if (Env.Equals(String.Empty))
            {
                MessageBox.Show("Select a Environment");
                btnReset_Click(sender, e);
            }

                try
                {
                    /*Declare and initialize a variable for the Webs Web service.*/
                    WebsWebService.Webs webs_service = new WebsWebService.Webs();
                    SiteDataWebService.SiteData sitedata_service = new SiteDataWebService.SiteData();

                    /*Authenticate the current user by passing their default 
                    credentials to the Web service from the system credential 
                    cache. */
                    webs_service.Credentials =
                       System.Net.CredentialCache.DefaultCredentials;
                    sitedata_service.Credentials =
                        System.Net.CredentialCache.DefaultCredentials;

                    /*Set the Url property of the service for the path to a subsite. 
                    Not setting this property will return the items in the root Web site.*/
                    webs_service.Url =
                    "https://edss.iu.edu/sites/"+Env+"/BI-Centers/_vti_bin/Webs.asmx";


                    /*Declare an XmlNode object and initialize it with the XML 
                    response from the GetWebCollection method. */
                    System.Xml.XmlNode nodeWebCollection = webs_service.GetWebCollection();

                    /*Loop through XML response and parse out the value of the
                    Title attribute for each Web. */
                    foreach (System.Xml.XmlNode xmlnode in nodeWebCollection)
                    {
                        uint result = 1;
                        SiteDataWebService._sList[] lists = null;
                        String siteName = xmlnode.Attributes["Title"].Value;
                        
                        try
                        {
                            sitedata_service.Url = "https://edss.iu.edu/sites/" + Env + "/BI-Centers/" + siteName + "/_vti_bin/SiteData.asmx";
                            result = sitedata_service.GetListCollection(out lists);
                        }
                        catch (Exception exception)
                        {
                            MessageBox.Show("An error occured while retrieving list names: " + exception.Message);
                        }
                        if (result == 0)
                        {
                            
                            TreeNode[] arrayTreeNode = new TreeNode[lists.Length];
                            for (int i = 0; i < lists.Length; i++)
                            {
                                arrayTreeNode[i] = new TreeNode(lists[i].Title);
                                
                                
                            }
                            TreeNode treeNode = new TreeNode(siteName, arrayTreeNode);
                            treeView1.Nodes.Add(treeNode);
                        }
                        else
                            MessageBox.Show("Error in aite data get collection");
                        
                    }
                    
                }
                catch (Exception exception)
                {
                    MessageBox.Show("An error occured while getting site names: " + exception.Message);
                }

                grpbxCollection.Visible = true;
                btnRetrieve.Enabled = true;
                lblRetrieve.Visible = false;
                
                
        }

        private void formPublishWorkflows_Load(object sender, EventArgs e)
        {
            rdobtnDev.Checked = true;
            btnPublish.Enabled = false;
            
        }

        private void treeView1_AfterCheck(object sender, TreeViewEventArgs e)
        {
            if (done) return;
            done = true;
            try
            {
                checkNodes(e.Node, e.Node.Checked);
            }
            finally
            {
                done = false;
            }
        }

        /* Helper Method */
        private void checkNodes(TreeNode node, bool check)
        {
            foreach (TreeNode child in node.Nodes)
            {
                child.Checked = check;
                checkNodes(child, check);
            }
        }

        /* Helper Method */
        private List<TreeNode> CheckedNodes(TreeNodeCollection theNodes)
        {
            List<TreeNode> aResult = new List<TreeNode>();

            if (theNodes != null)
            {
                foreach (TreeNode aNode in theNodes)
                {
                    foreach (TreeNode cNode in aNode.Nodes)
                    {
                        if (cNode.Checked)
                        {
                            aResult.Add(cNode);
                        }
                    }
                }
            }

            return aResult;
        }

        private void btnMark_Click(object sender, EventArgs e)
        {
            checkedNodes.Clear();
            checkedNodes = CheckedNodes(treeView1.Nodes);
            if (!(checkedNodes.Count == 0))
            {
                lblMarked.Visible = true;
                btnPublish.Enabled = true;
            }
            else
            {
                MessageBox.Show("please select atleast one library/list");
            }
        }

        private void btntreeViewClear_Click(object sender, EventArgs e)
        {
            lblMarked.Visible = false;
            ClearNodes(treeView1.Nodes);
        }

        private void ClearNodes(TreeNodeCollection theNodes)
        {
            if (theNodes != null)
            {
                foreach (TreeNode aNode in theNodes)
                {
                    if (aNode.Checked)
                        aNode.Checked = false;
                    foreach (TreeNode cNode in aNode.Nodes)
                         if (cNode.Checked)
                            cNode.Checked = false;
                }
            }
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            System.Environment.Exit(0);
        }

        private void btnReset_Click(object sender, EventArgs e)
        {
            txtBoxWorkflowLoc.Clear();
            treeView1.Nodes.Clear();
            grpbxCollection.Visible = false;
            btnPublish.Enabled = false;
            rdobtnDev.Checked = true;
            lblPublishLoading.Visible = false;
            formPublishWorkflows_Load(sender, e);
        }
    }
}
