import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class UI_IP extends JFrame {

	private JPanel contentPane;
	private JTextField txtboxNoofPeople;
	private JTextField txtboxNoofJobs;
	private JTextArea txtareaMatrix;
	private ButtonGroup rdbtngroup;
	private JRadioButton rdbtnMinimise;
	private JRadioButton rdbtnMaximise;
	private JButton btnYes;
	private JButton btnStart;
	private JButton btnBrowse;
	private JButton btnReset;
	private int totalLinesTextArea;
	//private JScrollPane txtareascroll;
	
	Hungarian obj = new Hungarian();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI_IP frame = new UI_IP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI_IP() {
		setTitle("HUNGARIAN ALGORITHM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Initialization of the Panel */
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 491, 404);
		contentPane.add(panel);
		panel.setLayout(null);
		
		/*Label to display to enter People  */
		JLabel lblEnterTheNumber = new JLabel("Enter the number of People:");
		lblEnterTheNumber.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblEnterTheNumber.setBounds(23, 49, 142, 14);
		panel.add(lblEnterTheNumber);
		
		/*Text Box to take input from user for no of people */
		txtboxNoofPeople = new JTextField();		
		txtboxNoofPeople.setBounds(175, 46, 37, 20);
		panel.add(txtboxNoofPeople);
		txtboxNoofPeople.setColumns(10);
		
		/*Label to display to enter jobs */
		JLabel lblEnterTheNumber_1 = new JLabel("Enter the number of Jobs:");
		lblEnterTheNumber_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblEnterTheNumber_1.setBounds(244, 49, 142, 14);
		panel.add(lblEnterTheNumber_1);
		
		/*Text Box to take input from user for no of Jobs */
		txtboxNoofJobs = new JTextField();		
		txtboxNoofJobs.setColumns(10);
		txtboxNoofJobs.setBounds(390, 46, 37, 20);
		panel.add(txtboxNoofJobs);
		
		JLabel lblEnterTheCost = new JLabel("Enter the cost matrix elements(like matrix)  or");
		lblEnterTheCost.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblEnterTheCost.setBounds(23, 74, 240, 27);
		panel.add(lblEnterTheCost);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInput.setBounds(23, 23, 46, 14);
		panel.add(lblInput);
		
		JLabel lblSelectOperation = new JLabel("Select the operation");
		lblSelectOperation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectOperation.setBounds(23, 311, 123, 14);
		panel.add(lblSelectOperation);
		
		txtareaMatrix = new JTextArea("");		
		txtareaMatrix.setBounds(33, 120, 410, 180);
    	txtareaMatrix.setRows(12);
		txtareaMatrix.setColumns(12);
		txtareaMatrix.setLineWrap(true);
		totalLinesTextArea = 0;
		panel.add(txtareaMatrix);
		
		rdbtngroup = new ButtonGroup();
		
    	rdbtnMinimise = new JRadioButton("Minimise");
		rdbtnMinimise.setBounds(155, 307, 87, 23);
		rdbtnMinimise.setSelected(true);
		rdbtngroup.add(rdbtnMinimise);
		panel.add(rdbtnMinimise);
		
		rdbtnMaximise = new JRadioButton("Maximise");
		rdbtnMaximise.setBounds(247, 307, 101, 23);
		rdbtngroup.add(rdbtnMaximise);
		panel.add(rdbtnMaximise);
		
		JLabel lblConstraints = new JLabel("  Any Constraints?");
		lblConstraints.setBounds(23, 336, 123, 20);
		panel.add(lblConstraints);
		
		btnYes = new JButton("Yes");
		btnYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 int vJobs = -1 , vPeople = -1 , vTextArea = -1 ;
				  vPeople = validatetxtNoofPeople();
				  vJobs = validatetxtNoofJobs();
				  vTextArea = validateTxtareaMatrix();
				  if (vPeople == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of people!!","False",JOptionPane.ERROR_MESSAGE);
				  }
				  else if (vPeople == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for people");	
				  }
				  else if (vJobs == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of jobs!!","False",JOptionPane.ERROR_MESSAGE);														
				  }
				  else if (vJobs == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for jobs");
				  }
				  else if(vTextArea == 1)
				  {		
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the matrix!!","False",JOptionPane.ERROR_MESSAGE);	
				  }
				  else if(vTextArea == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid matrix");								
				  }
				  else if(vPeople == 3 && vJobs == 3 && validateTxtareaMatrix() == 3)
				  {
					  takeRestriction();
				  }
			}
		});
		btnYes.setBounds(184, 335, 58, 23);
		panel.add(btnYes);
		
		btnStart = new JButton("Perform Assignment");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int vJobs = -1 , vPeople = -1 , vTextArea = -1 ;
				  vPeople = validatetxtNoofPeople();
				  vJobs = validatetxtNoofJobs();
				  vTextArea = validateTxtareaMatrix();
				  if (vPeople == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of people!!","False",JOptionPane.ERROR_MESSAGE);				
				  }
				  else if (vPeople == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for people");
				  }
				  else  if (vJobs == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of jobs!!","False",JOptionPane.ERROR_MESSAGE);								
				  }
				  else if (vJobs == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for jobs");
				  }
				  else if(vTextArea == 1)
				  {		
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the matrix!!","False",JOptionPane.ERROR_MESSAGE);	
				  }
				  else if(vTextArea == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid matrix");							
				  }
				  else if(vPeople == 3 && vJobs == 3 &&  validateTxtareaMatrix() == 3)
				  {
					  performAssignment();
				  }
			}
		});
		
		
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStart.setBounds(132, 367, 186, 23);
		panel.add(btnStart);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				  int vJobs = -1 , vPeople = -1 ;
				  vPeople = validatetxtNoofPeople();
				  vJobs = validatetxtNoofJobs();
				  txtareaMatrix.setText(null);
				  if (vPeople == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of people!!","False",JOptionPane.ERROR_MESSAGE);				
				  }
				  else if (vPeople == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for people");
				  }
				  else if (vJobs == 1)
				  {
					  JOptionPane.showMessageDialog(getContentPane(),"Please enter the number of jobs!!","False",JOptionPane.ERROR_MESSAGE);								
				  }
				  else if (vJobs == 2)
				  {
					  JOptionPane.showMessageDialog(null, "Please enter a valid Number for jobs");
				  }
				  else if(vPeople == -1)
				  {		
					  vPeople = validatetxtNoofPeople();	
				  }
				  else if(vJobs == -1)
				  {		
					  vJobs = validatetxtNoofJobs();
				  }
				  else if(vPeople == 3 && vJobs == 3)
				  {
					  Chooser frame = new Chooser();
					  read_config_file(frame.fileName);
				  }	
			}	
		});
		btnBrowse.setBounds(273, 74, 101, 23);
		panel.add(btnBrowse);
		
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				resetFunctionality();
			}
		});
		btnReset.setBounds(377, 336, 89, 41);
		panel.add(btnReset);
	}
	
	/**
	 * Method to validate the input textbox which takes in the number of jobs
	 * @return an integer value based on the result of the validation
	 * 1-if input is empty
	 * 2-if input is non-empty but is invalid
	 * 3-if input is valid
	 */
	public int validatetxtNoofJobs()
	{
		String noOfJobs=null;
		Pattern p;
	    Matcher m ;
		if(txtboxNoofJobs.getText().toString().isEmpty())
		{
			return 1;		
		}	
		else
		 {
			noOfJobs=txtboxNoofJobs.getText().toString();
			
			p = Pattern.compile("[^0-9]");
			m = p.matcher(noOfJobs);
			
			if (m.find())
	        {
	        	
	        	txtboxNoofJobs.setText("");
	        	return 2;
	        	
	        }
		 }
		return 3;
	}
	
	/**
	 * Method to validate the input textbox which takes in the number of people
	 * @return an integer value based on the result of the validation
	 * 1-if input is empty
	 * 2-if input is non-empty but is invalid
	 * 3-if input is valid
	 */
	public int validatetxtNoofPeople()
	{
		String noOfPeople=null;
		Pattern p;
	    Matcher m ;
		if(txtboxNoofPeople.getText().toString().isEmpty())
		{		
			return 1;	
		}	
		else
		 {
			noOfPeople=txtboxNoofPeople.getText().toString();
			
			p = Pattern.compile("[^0-9]");
			m = p.matcher(noOfPeople);
			
			if (m.find())
	        {
				txtboxNoofPeople.setText("");
				return 2;	
	        }
		 }
		return 3;
	}
	
	/**
	 * Method to validate the input textarea which takes the input cost matrix
	 * @return an integer value based on the result of the validation
	 * 1-if input is empty
	 * 3-if input is entered
	 */
	public int validateTxtareaMatrix()
	{	
		if(txtareaMatrix.getText().toString().isEmpty())
		{					
			return 1;		
		}	
		return 3;
	}
	
	/**
	 * Method to validate the input textarea which takes the input cost matrix after the data is entered
	 * This validation is performed just before performing the assignment (perform assignment)
	 * @return String[] if data entered if in correct format
	 * else returns null
	 */
	public  String[] validateTextAreaInput()
	{
		String temp ;
		String [] inputTxtArea = txtareaMatrix.getText().trim().split("\\n");
		totalLinesTextArea = txtareaMatrix.getLineCount();		
		int len =0;
		try
		{
			for( len = 0 ; len < totalLinesTextArea; len++)
			{
				temp = inputTxtArea[len] ;
			}
			totalLinesTextArea = Integer.parseInt(txtboxNoofPeople.getText());
		}
		catch(Exception e)
		{
			totalLinesTextArea = len;
		}
	    try
	    {
	    	for (int i=0; i < totalLinesTextArea; i++)
	    	{
	    		String line = inputTxtArea[i];	    	
	    		line = line.trim();
	    		line =line.concat(" ");
	    		if(validateFileInputMatrixFormCheck(line) == 0)
	    		{
			        line = line.trim();
			        inputTxtArea[i] = line;
			    }
	    		else
	    		{
		    	   inputTxtArea = null;
		    	   JOptionPane.showMessageDialog(getContentPane(),"Please enter numbers only in matrix form onto the text area accordingly to jobs and people","False",JOptionPane.ERROR_MESSAGE);
		    	   break;
		       }
	    	}
	    }
	    catch (Exception e)
	    {
	    	JOptionPane.showMessageDialog(getContentPane(),"Data is not in correct format as like a matrix.If you have done copy paste please check the extra lines that may have formed","False",JOptionPane.ERROR_MESSAGE);
	    }
		return inputTxtArea;
	}
	
	/**
	 * Method to validate the content of the file browsed for input.
	 * The file must contain a sequence of numbers each separated by a space
	 * @return 0 if the file content is valid else it returns 1 
	 */
	public int validateFileInput(String line)
	{
		Pattern p;
	    Matcher m ; 
	    int n=Integer.parseInt(txtboxNoofJobs.getText()) *  Integer.parseInt(txtboxNoofPeople.getText());
		p = Pattern.compile("^(([0-9]+)[\\p{Z}\\s]){"+ n +"}$");
		m = p.matcher(line);				
		if (m.find())
        {
        	return 0;
        }
		return 1;
	}
	
	/**
	 * Method to validate the restrictions entered by the user
	 * The input must contain two numbers separated by a space
	 * @return 0 if the file content is valid else it returns 1 
	 */
	public int validateFormat(String restrictionInput)
	{				
		Pattern p;
		Matcher m;					
		//check for required pattern 
		p = Pattern.compile("^([0-9]+)[\\p{Z}\\s]([0-9]+)$");
		m = p.matcher(restrictionInput);
		//something other than the required format is found					
		if (m.find())
        {
        	return 0;		        	
        }
		else
		{		
			return 1;
		}
	}
	
	/**
	 * Method to take restrictions and call the validateFormat function in order to validate them
	 * If the user does not give any input the function prompts the user to give restrictions
	 * If the restriction is given, the validateFormat function is called in order to validate the restriction
	 * If the restriction entered is valid, the validateFormat function returns 0 and the user is prompted for the next 
	 * restriction
	 * If the restriction entered is invalid, the validateFormat function returns 1 and the user is prompted to enter the 
	 * restriction in the right format
	 */ 
	public void takeRestriction()
	{		
		try
		{
			int optionPane= JOptionPane.YES_OPTION;
			for(int i=0;i<Integer.parseInt(txtboxNoofPeople.getText());i++)
			{
				for(int j=0;j<Integer.parseInt(txtboxNoofJobs.getText());j++)
				{
					obj.restriction[i][j] =0;
				}
			}	
			do
			{
				 String restrictionInput = JOptionPane.showInputDialog("Enter the person and the job he cannot perform separated by spaces:");
				 if(restrictionInput.toString().isEmpty() == true)
				 {
					 JOptionPane.showMessageDialog(getContentPane(),"Please enter the restriction","False",JOptionPane.ERROR_MESSAGE);
				 }
				 else if (restrictionInput.equals(null) == true)
				 {
					 break;
				 }
				 else if (validateFormat(restrictionInput) == 1 )
				 {
					 JOptionPane.showMessageDialog(getContentPane(),"Please enter the restriction in the specified format","False",JOptionPane.ERROR_MESSAGE);
				 }
				 else
				 {
					 String[] restrictionArr=restrictionInput.split(" ");
					 int restrictionJobs= Integer.parseInt(restrictionArr[1]);
					 int restrictionPeople= Integer.parseInt(restrictionArr[0]);
					 obj.restriction[restrictionPeople-1][restrictionJobs-1]=1;				 
					 optionPane = JOptionPane.showConfirmDialog(null,
					 					    "Do you want to add another constraint?",
					 					    "Confirm Add",
					 					    JOptionPane.YES_NO_OPTION);		
				}
			 }while(optionPane==JOptionPane.YES_OPTION);
		}
		//When user clicks on cancel button while entering restrictions
		catch (NullPointerException e1)
		{
			//JOptionPane.showMessageDialog(null, "You have cancelled to enter the restrictions");
		}			
	}
	
	/**
	 *Resets all the fields used to take input 
	 */
	public void resetFunctionality()
	{
		txtareaMatrix.setText(null);		
		obj = new Hungarian();
		txtboxNoofJobs.setText(null);
		txtboxNoofPeople.setText(null);	
		rdbtngroup.clearSelection();
	    rdbtnMinimise.setSelected(true);	
	}
	
	/**
	 * This class generates file browse window and gets the path for the selected file 
	 */
	class Chooser extends JFrame 
	{
		JFileChooser chooser;
		String fileName;

		public Chooser()
		{
			chooser = new JFileChooser();
			int r = chooser.showOpenDialog(new JFrame());
			if (r == JFileChooser.APPROVE_OPTION)
			{
				fileName = chooser.getSelectedFile().getPath();
			}
		}
	}
	
	/**
	 *  Reads the file browsed and parses it and enters it into costMatrix and into the textarea 
	 */
	public void read_config_file(String fileName)
	{
		BufferedReader buf;
		try
		{
			buf = new BufferedReader(new FileReader(fileName));
			String line = null;					
    		while((line=buf.readLine())!=null)
    		{
	    		line = line.trim();
	    		line = line.concat(" ");
	    		if (validateFileInput(line)== 0)
	    		{		
	    			line = line.trim();
	    			String[] row = line.split(" ");		    			
					int k=0;
					for(int i=0;i<Integer.parseInt(txtboxNoofPeople.getText());i++)
					{
						for(int j=0;j<Integer.parseInt(txtboxNoofJobs.getText());j++)
						{
							obj.cost[i][j]=Integer.parseInt(row[k++]);						
							txtareaMatrix.append(Integer.toString((obj.cost[i][j]))+" ");												
						}
						txtareaMatrix.append("\n");
					}	   
	    		}
	    		else
	    		{
	    			JOptionPane.showMessageDialog(null, "The file doesnot contain numbers and spaces as specified. Please check the file format");
	    		} 
    		}
		}
		catch (FileNotFoundException e1)
		{
			JOptionPane.showMessageDialog(null, "File is not found");
		} 
		catch (IOException e1)
		{
			JOptionPane.showMessageDialog(null, "Input output Exception");
		}
		catch (NullPointerException e1)
		{
			JOptionPane.showMessageDialog(null, "Please browse for a file or enter a matrix");
		}
		catch (ArrayIndexOutOfBoundsException e1)
		{
			JOptionPane.showMessageDialog(null, "The format entered in the file is not correct.");
		}
	}
	
	/**
	 * Checks whether the data in the file browsed is of correct form. It should be of format (number space number)  
	 * this function is called in validateTextAreaInput for every line of input entered in the textarea
	 * Hence the number of elements in each line are equal to the number of jobs
	 * @return 0 if input is valid else returns 1
	 */
	public int validateFileInputMatrixFormCheck(String line)
	{
		Pattern p;
	    Matcher m ;	  
	    int n=Integer.parseInt(txtboxNoofJobs.getText());
		p = Pattern.compile("^(([0-9]+)[\\p{Z}\\s]){"+n+"}$");		
		m = p.matcher(line);				
		if (m.find())
        {
        	return 0;
        }
		else
		{
			return 1;
		}
	}
	
	/**
	 * Performs assignment when all the validations are done
	 * The number of people and number of jobs are assigned to the respective fields of the Hungarian object
	 * The cost matrix field of the Hungarian object is also populated with the data obtained from the text area or the input file
	 */
	public void performAssignment()
	{	
		int length =0;
		obj.noOfJobs = Integer.parseInt(txtboxNoofJobs.getText());
		obj.noOfPeople = Integer.parseInt(txtboxNoofPeople.getText());
		String[] actualData = new String[obj.noOfJobs*obj.noOfPeople];
		String[] inputTxtArea = new String[obj.noOfJobs*obj.noOfPeople];
	
		int flag=0;
		if(obj.noOfPeople>=obj.noOfJobs)
		{
			obj.n=obj.noOfPeople;
			flag=1;
		}
		else
		{
			obj.n=obj.noOfJobs;
			flag=2;
		}
		
		//When data is entered through text area
		inputTxtArea = validateTextAreaInput();
		if(inputTxtArea != null)
		{		
			String[] data = null;					
			for (int i =0; i<totalLinesTextArea;i++)
			{
				data = inputTxtArea[i].split(" ");
				for(int k=0; k < data.length ;k++)
				{	
					try
					{
						actualData[length] = data[k];
						length++;
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
						resetFunctionality();
					}
				}
			}
			length =0;
			try
			{
			for(int i=0;i<obj.noOfPeople;i++)
			{
				for(int j=0;j<obj.noOfJobs;j++)
				{
					obj.cost[i][j]=Integer.parseInt(actualData[length]);
					length++;
				}
			}
			if(flag==1)
			{
				for(int j=obj.noOfJobs;j<obj.n;j++)
				{
					for(int i=0;i<obj.n;i++)
					{
						obj.cost[i][j]=0;
					}
				}
			}
			else if(flag==2)
			{
				for(int i=obj.noOfPeople;i<obj.n;i++)
				{
					for(int j=0;j<obj.n;j++)
					{
						obj.cost[i][j]=0;
					}
				}
			}				
			obj.duplicate();			
			if (rdbtnMaximise.isSelected() == true)
			{					
				obj.maximize();
			}
			obj.addRestrictions();
			obj.hungarian();
			}
			catch(NumberFormatException  e)
			{
				JOptionPane.showMessageDialog(getContentPane(),"Please enter correct number of elements accordingly","False",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
