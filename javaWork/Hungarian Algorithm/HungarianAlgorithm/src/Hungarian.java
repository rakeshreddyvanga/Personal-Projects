import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Hungarian 
{
	private int max_size = 100;
	public int noOfPeople;
	public int noOfJobs;
	public int n;
	private int min=10000;
	public int[][] cost = new int[max_size][max_size];
	public int[][] restriction = new int[max_size][max_size];	
	private int[][] temp = new int[max_size][max_size];
	private int[] columnLabel = new int[max_size];
	private int[] rowLabel = new int[max_size];
	private int assignCounter = 0;
	private int rowTickCounter = 0 ;
	private int columnTickCounter = 0 ;
	private int[] rowTick = new int[max_size];
	private int[] columnTick = new int[max_size];

	private int[][] matrix = new int[max_size][max_size];
	private int[][] path = new int[max_size][max_size];
	
	private int[] columnLabelMultiple = new int[max_size];
	private int[] rowLabelMultiple = new int[max_size];
	private int[] rowLabelFinalValues = new int[max_size];
	
	private int path_row_0;
	private int path_col_0;
	private int pathCount=0;
	
	private int step;
	private boolean yes=false;
	
	/**
	 *  Adds restrictions to the elements of the matrix 
	 */
	public void addRestrictions()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{				
				if(restriction[i][j] == 1 )
				{
					cost[i][j] = 1000000000;
				}
			}
		}
	}
	
	/**
	 *  Copies the cost matrix to the temp matrix 
	 */
	public void duplicate()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				temp[i][j] = cost[i][j];
			}
		}
	}
	
	/**
	 * Finds the maximum element in the cost matrix and subtracts each and every element from the maximum element and assigns it 
	 * back to the cost matrix
	 */
	public void maximize()
	{
		int i,j,max=cost[0][0];
		
		for(i=0;i<n;i++)
		{
			for(j=0;j<n;j++)
			{
				if(cost[i][j]>max)
				{
					max=cost[i][j];
				}
			}
		}
		
		for(i=0;i<n;i++)
		{
			for(j=0;j<n;j++)
			{		
				cost[i][j]=max-cost[i][j];
			}
		}
		
	}
	
	/**
	 * Finds the minimum element in the matrix from ticked rows and non ticked columns
	 */
	public void minimum()
	{
		for(int i=0;i<n;i++)
		{
			if(rowTick[i]==8888)
			{
				for(int j=0;j<n;j++)
				{
					if(columnTick[j]==0)
					{
						if(cost[i][j]<min)
						{
							min=cost[i][j];
						}
					}
				}
			}
		}
	}
	
	/**
	 * Performs initial operations on the cost matrix
	 * - finds the minimum element in each row
	 * - subtracts the minimum element in every row from the entire row
	 * - finds the minimum element in each column 
	 * - subtracts the minimum element in every column from the entire column
	 */
	public void initial()
	{ 
		int i,j; 
		int tempValue=0;
		for(i=0; i<n; i++) 
		{ 
			for(j=0; j<n; j++)
			 {
				if(j==0)
				{
					tempValue = cost[i][j];
				}
				else
				{
					if(tempValue> cost[i][j])
					{
						tempValue = cost[i][j];
					}		
				}				
			}
			for (j=0; j<n;j++)
			{
				cost[i][j] = cost[i][j]- tempValue;
			}			
		}
		
		for(j=0;j<n;j++)
		{
			for (i=0;i<n;i++)
			{
				if(i==0)
				{
					tempValue = cost[i][j];
				}
				else
				{
				    if(tempValue> cost[i][j])
				    {
				    	tempValue = cost[i][j];
				    }
				}
			}
			for(i=0;i<n;i++)
			{
				cost[i][j] = cost[i][j] - tempValue;
			}
		}	
	}
	
	/**
	 * Changes the cost matrix according to the following conditions using the minimum element obtained after ticking
	 * - Subtracts the minimum element from the cost matrix element if its row is ticked and column is non ticked
	 * - Adds the minimum element to the cost matrix element if its row and column are ticked
	 * - Leaves the element of the cost matrix unchanged  if its row and column are not ticked
	 */
	public void change()
	{
		for(int i=0;i<n;i++)
		{
			if(rowTick[i]==8888)
			{
				for(int j=0;j<n;j++)
				{
					if(columnTick[j]==0)
					{
						cost[i][j]-=min;
					}
				}
			}
			else
			{
				for(int j=0;j<n;j++)
				{
					if(columnTick[j]==8888)
					{
						cost[i][j]+=min;
					}
				}
			}
		}
	}
	
	/**
	 * Counts the number of zeroes present in each row
	 * @return a single dimensional integer array containing the number of zeroes in each row
	 */
	public int[] countRowZeroes()
	{
		int[] zeros = new int[n];
		for(int i=0;i<n;i++)
		{
			zeros[i]=0;
			for(int j=0;j<n;j++)
			{
				if(cost[i][j]==0)
				{
					zeros[i]++;
				}
			}
		}
		return zeros;
	}
	
	/**
	 * Counts the number of zeroes present in each column
	 * @return a single dimensional integer array containing the number of zeroes in each column
	 */
	public int[] countColumnZeroes()
	{
		int[] zeros = new int[n];
		for(int j=0;j<n;j++)
		{
			zeros[j]=0;
			for(int i=0;i<n;i++)
			{
				if(cost[i][j]==0)
				{
					zeros[j]++;
				}
			}
		}
		return zeros;
	}
	
	/**
	 * Performs assignment of jobs to people based on the following rules
	 * - if the number of zeroes in a row is one and its columnLabel shows no assignment assign it
	 * - if the number of zeroes in a column is one and its columnLabel and rowLabel show no assignment assign it
	 * - if there are zeroes whose columnLabel and rowLabel show no assignment assign them
	 */
	public void assignment()
	{
		int[] zerosRow = new int[n];
		int[] zerosColumn = new int[n];
		assignCounter=0;
		
		//obtains the number of zeroes in each row
		zerosRow = countRowZeroes();
		
		//Initialize all the row labels and column labels to 9999
		for(int k=0;k<n;k++)
		{
			rowLabel[k]=9999;
			columnLabel[k]=9999;
		}
		
		for(int i=0;i<n;i++)
		{
			if(zerosRow[i]==1)
			{
				for(int j=0;j<n;j++)
				{
					if(cost[i][j]==0 && columnLabel[j]==9999)
					{
						rowLabel[i]=j;
						columnLabel[j]=i;
						assignCounter++;
					}
				}
			}
		}
		
		//obtains the number of zeroes in each column
		zerosColumn = countColumnZeroes();		
		
		for(int j=0;j<n;j++)
		{
			if(zerosColumn[j]==1)
			{
				for(int i=0;i<n;i++)
				{
					if(cost[i][j]==0 && columnLabel[j]==9999 && rowLabel[i]==9999)
					{
						rowLabel[i]=j;
						columnLabel[j]=i;
						assignCounter++;
					}
				}
			}
		} 
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(cost[i][j]==0 && rowLabel[i]==9999 && columnLabel[j]==9999)
				{
					rowLabel[i]=j;
					columnLabel[j]=i;
					assignCounter++;
				}
			}
		}
	}
	
	/**
	 * Ticking is performed based on the following rules
	 * - Tick all unassigned rows
	 * - In each ticked row, if there are zeroes present, tick the corresponding columns
	 * - For each ticked column, if there are any assignments present, tick the corresponding row
	 * - Perform the second and the third steps until no further ticking is possible 
	 */
	public void ticking()
	{
		for(int i=0; i<n ; i++)
		{
			if(rowLabel[i] == 9999)
			{
				rowTick[i] = 1;
				rowTickCounter++;
				columnTick[i]=0;
			}
			else
			{
				rowTick[i]=0;
				columnTick[i]=0;
			}
		}		
		int i=0;
		while(rowTickCounter!=0)
		{			
			if(rowTick[i]==1)
			{
				for(int j=0;j<n;j++)
				{
					if(cost[i][j]==0 && columnTick[j]==0)
					{
						columnTick[j]=1;
						columnTickCounter++;
					}
				}			
				while(columnTickCounter!=0)
				{	
					for(int j=0;j<n;j++)
					{
						if(columnTick[j]==1 && columnLabel[j]!=9999)
						{			
							int t = columnLabel[j];
							columnTick[j] = 8888;
							columnTickCounter--;
							if(rowTick[t]==0)
							{
								rowTick[t]=1;
								rowTickCounter++;
							} 
						}
						else if (columnTick[j]== 1 && columnLabel[j] == 9999)
						{		
							columnTick[j] = 8888;
							columnTickCounter--;
						}
					}
					int l =0;
					l++;
					if(l==10)
					{
						break;
					}
				}
				rowTick[i]= 8888;
				rowTickCounter--;
			}
			i++;			
			if(i>=n)
			{
				i=0;
			}
			
		}
	}
	/**
	 * Checks if all rows are un-ticked and if all columns are ticked
	 * @return true if above condition is satisfied else false
	 */
	public boolean allTickuntick()
	{
		int count1=0, count2=0;
	
		for(int i=0;i<n;i++)
		{
			if(rowTick[i]!=8888)
			{
				count1++;
			}
		}
		
		for(int j=0;j<n;j++)
		{
			if(columnTick[j]==8888)
			{
				count2++;
			}
		}
	
		if(count1==n || count2==n)
		{
			yes=true;
		}
		
		return yes;
	}
	
	/**
	 * Calls each and every step of the Hungarian Algorithm 
	 */
	public void hungarian()
	{
		duplicate();
		initial();
		assignment();
		while(assignCounter!=n)
		{
			ticking();
			boolean yes=allTickuntick();
			if(yes)
			{
				step=1;
				alternateFunction();
				break;
			}
			minimum();
			change();
			assignment();
		}
		if(yes)
		{
			finalDisplay();
		}
		else
		{
			step=1;
			alternateFunction();
			int flag1=0;
			for(int i=0;i<n;i++)
			{
				if(rowLabel[i]!=rowLabelFinalValues[i])
				{
					flag1=1;
					break;
				}
			}
			if(flag1==1)
			{
				finalDisplay2();
			}
			else
			{
				finalDisplay1();
			}
		}
	}

	/**
	 * Runs when we cannot find unticked columns and ticked rows or when there are multiple solutions
	 */
	public void alternateFunction()
	{
		start();
		runAlgorithm();
	}
	
	/**
	 * Runs steps required to run algorithm to solve multiple solutions 
	 */
	public void runAlgorithm()
	{
		boolean done = false;
        while (!done)
        {
            switch (step)
            {
                case 1:
                    step=stepOne(step);
                    break;
                case 2:
                    step=stepTwo(step);
                    break;
                case 3:
                    step=stepThree(step);
                    break;
                case 4:
                    step=stepFour(step);
                    break;
                case 5:
                    step=stepFive(step);
                    break;
                case 6:
                    step=stepSix(step);
                    break;
                case 7:
                    stepSeven(step);
                    done = true;
                    break;
            }
        }
	}
	
	/**
	 * Intializes cost matrix, row labels, column labels and mask matrix
	 */
	public void start()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				cost[i][j] = temp[i][j];
				matrix[i][j]=0;
			}
		}
	
		for(int k=0;k<n;k++)
		{
			rowLabelMultiple[k]=0;
			columnLabelMultiple[k]=0;
		}			
	}
	
	/**
	 * - finds the smallest element in each row
	 * - subtracts the smallest element in every row from the entire row
	 * - @param step number
	 * @return step number
	 */
	public int stepOne(int step)
	{ 
		int i,j; 
		int min;
		for(i=0; i<n; i++) 
		{ 
			min=cost[i][0];
			for(j=0; j<n; j++)
			 {
				if(cost[i][j]<min)
				{
					min = cost[i][j];
				}						
			}
			for (j=0; j<n;j++)
			{
				cost[i][j] = cost[i][j]- min;
			}			
		}
		step=2;
		return step;
	}
	
	/**
	 * Find a zero in the resulting matrix.  If there is no zero in its rows or columns then go to step 3 
	 * @param step number
	 * @return step number
	 */    
	public int stepTwo(int step)
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(cost[i][j]==0 && rowLabelMultiple[i]==0 && columnLabelMultiple[j]==0)
				{
					matrix[i][j]=1;
					rowLabelMultiple[i]=1;
					columnLabelMultiple[j]=1;
				}
			}
		}
		
		for(int k=0;k<n;k++)
		{
			rowLabelMultiple[k]=0;
			columnLabelMultiple[k]=0;
		}
		step=3;
		return step;
	}
	
	/**
	 * Cover each column containing a starred zero.  If all columns are covered, then the 1s in the mask matrix represent the
	 * solution else go to step 4 
	 * @param step number
	 * @return step number
	 */
	public int stepThree(int step)
	{
		int columnCount;
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(matrix[i][j]==1)
				{
					columnLabelMultiple[j]=1;
				}
			}
		}
		
		columnCount=0;
		for(int j=0;j<n;j++)
		{
			if(columnLabelMultiple[j]==1)
			{
				columnCount++;
			}
		}
		
		if(columnCount>=n)
		{
			step=7;
		}
		else
		{
			step=4;
		}		
		
		return step;
	}
	
	/**
	 * Finds the zeros in rows and cols
	 * @param rows
	 * @param cols
	 * @return rows and cols
 	 */
	public int[] findZeros(int rows, int cols)
	{
		int r=0;
		int c;
		boolean over;
		
		rows=-1;
		cols=-1;
		over=false;
		while(!over)
		{
			c=0;
			while(true)
			{
				if(cost[r][c]==0 && rowLabelMultiple[r]==0 && columnLabelMultiple[c]==0)
				{
					rows=r;
					cols=c;
					over=true;
				}
				c+=1;
				if(c>=n || over)
				{
					break;
				}
			}
			r+=1;
			if(r >= n)
			{
				over=true;				
			}
		}
		int[] a=new int[2];
		a[0]=rows;
		a[1]=cols;
		return a;
	}
	
	public boolean starRows(int rows)
	{
		boolean temp=false;
		for(int c=0;c<n;c++)
		{
			if(matrix[rows][c]==1)
			{
				temp=true;
			}
		}
		return temp;
	}
	
	public int getStarInRow(int rows, int cols)
	{
		cols=-1;
		for(int c=0;c<n;c++)
		{
			if(matrix[rows][c]==1)
			{
				cols=c;
			}
		}
		return cols;
	}
	
	/**
	 * Find a non labeled zero and mark it.  If there is no assigned zero
	 * in the row containing this marked zero, Go to Step 5.  Otherwise, 
     * label this row and label the column containing the assigned zero. 
     * Continue in this manner until there are no unlabeled zeros left. 
     * Save the smallest unlabeled value and Go to Step 6 
	 * @param step number
	 * @return step number
	 */
    public int stepFour(int step)
	{
		int rows=-1;
		int cols=-1;
		boolean over;
		over=false;
		
		while(!over)
		{
			int[] a=findZeros(rows,cols);
			rows=a[0];
			cols=a[1];
			
			if(rows==-1)
			{
				over=true;
				step=6;
			}
			else
			{
				matrix[rows][cols]=2;
				if(starRows(rows))
				{
					cols=getStarInRow(rows,cols);
					rowLabelMultiple[rows]=1;
					columnLabelMultiple[cols]=0;
				}
				else
				{
					over=true;
					step=5;
					path_row_0=rows;
					path_col_0=cols;
				}
			}
		}
		return step;
	}
	
	public int getStarInCol(int column,int rows)
	{
		rows=-1;
		for(int i=0;i<n;i++)
		{
			if(matrix[i][column]==1)
			{
				rows=i;
			}
		}
		return rows;
	}
	
	public int getPrimeInRow(int rows,int cols)
	{
		for(int i=0;i<n;i++)
		{
			if(matrix[rows][i]==2)
			{
				cols=i;
			}
		}
		return cols;
	}
	
	public void augmentPath()
	{
		for(int i=0;i<pathCount;i++)
		{
			if(matrix[path[i][0]][path[i][1]]==1)
			{
				matrix[path[i][0]][path[i][1]]=0;
			}
			else
			{
				matrix[path[i][0]][path[i][1]]=1;
			}
		}
	}
	
	public void clearLabels()
	{
		for(int k=0;k<n;k++)
		{
			rowLabelMultiple[k]=0;
			columnLabelMultiple[k]=0;
		}
	}
	
	public void removePrimes()
	{
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(matrix[i][j]==2)
				{
					matrix[i][j]=0;
				}
			}
		}
	}
	
	/**
	 *We construct an augmenting path with labeled and assigned zeros to find the perfect matching
	 * @param step number
	 * @return step number
	 */
    public int stepFive(int step)
	{
		boolean over;
		int rows1=-1;
		int cols1=-1;
		
		pathCount=1;
		path[pathCount-1][0]=path_row_0;
		path[pathCount-1][1]=path_col_0;
		over=false;
		
		while(!over)
		{
			rows1=getStarInCol(path[pathCount-1][1],rows1);
			if(rows1>-1)
			{
				pathCount++;
				path[pathCount-1][0]=rows1;
				path[pathCount-1][1]=path[pathCount-2][1];
			}
			else
			{
				over=true;
			}
			if(!over)
			{
				cols1=getPrimeInRow(path[pathCount-1][0],cols1);
				pathCount++;
				path[pathCount-1][0]=path[pathCount-2][0];
				path[pathCount-1][1]=cols1;
			}
		}
		
		augmentPath();
		clearLabels();
		removePrimes();
		step=3;
		
		return step;
	}
	
    public int findMin(int minval)
	{
		for (int r = 0; r < n; r++)
		{
            for (int c = 0; c < n; c++)
            {
                if (rowLabelMultiple[r] == 0 && columnLabelMultiple[c] == 0)
                {
                    if (minval > cost[r][c])
                    {
                        minval = cost[r][c];
                    }
                }
            }
		}
		return minval;
	}
	
    /**
     * Adds the minimum value to elements where the row is labeled and subtract the minimum value from
     * elements where the column is un labeled
     * @param step
     * @return
     */
	public int stepSix(int step)
	{
		int minval = 10000;
	    minval=findMin(minval);
	    for (int r = 0; r < n; r++)
	    {
	        for (int c = 0; c < n; c++)
	        {
	            if (rowLabelMultiple[r] == 1)
	                cost[r][ c] += minval;
	            if (columnLabelMultiple[c] == 0)
	                cost[r][ c] -= minval;
	        }
	    }
	    step=4;
	    
	    return step;
	}
	
	/**
	 * Makes the number of assignments equal to the number of persons and jobs and also gets the job assignment
	 * for all people  
	 */
	public void stepSeven(int step)
	{
		assignCounter=n;
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				if(matrix[i][j]==1)
				{
					rowLabelFinalValues[i]=j;
				}
			}
		}
	}
	
	/**
	 * Creates a table and assigns the rows and columns of the table
	 * @param t- an object of type JTable
	 * @param size-the number of rows in the table
	 */
	public void testTable(JTable t,final int size) 
	{
			t.setModel(new AbstractTableModel()
			{
	        	String[] columnNames={" "," "};
	            public int getRowCount() { return size; }
	            public int getColumnCount() { return 2; }
	            
	            public String getColumnName(int column)
	            {
	                return this.columnNames[column];
	            }
	            
	            public void setColumnIdentifiers(Object[] newIdentifiers)
	            {
	            	
	            }
	            
	            public Object getValueAt(int row, int col)
	            {
		            Object value="";
		            if(row==0 && col==0)
					{
						value="Person";
					}
					else if(row==0 && col==1)
					{
						value="Job";
					}
					else if(col==0)
					{
						value=row;
					}
					else if(col==1)
					{
		    		   	value=rowLabel[row-1]+1;	
					}		
		            return value;
	            }
            
	            public boolean isCellEditable(int row, int col) { return false; }
	            public void setValueAt(Object value, int row, int col) {    }
        });
    }

	/**
	 * Displays the table with the set of people and jobs assigned to each person for each optimal solution
	 * Also displays the total cost of the optimal solution
	 */
	public void finalDisplay()
	{
		String s1=null;
		if(!yes)
		{
			int sum=0;
			int j;
			System.out.println("Final Display algo1");
			for(int i=0;i<n;i++)
			{
				j=rowLabel[i];			
				System.out.println(j);
				sum+=temp[i][j];	
			}
			s1="Total Cost is: "+sum;
		}
		else
		{
			int sum=0;
			System.out.println("Final Display algo2");
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					if(matrix[i][j]==1)
					{
						sum+=temp[i][j];
						rowLabel[i]=j;
						System.out.println(rowLabel[i]);
					}
				}
			}
			s1="Total Cost is: "+sum;
		}
		
		JTable t = new JTable();
        testTable(t,noOfPeople+1);
        JScrollPane scrollPane = new JScrollPane(t); 
        JOptionPane.showMessageDialog(null,scrollPane, s1, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Displays the table with the set of people and jobs assigned to each person for each optimal solution
	 * Also displays the total cost of the optimal solution
	 */
	public void finalDisplay1()
	{
		String s1=null;
		if(!yes)
		{
			int sum=0;
			int j;
			System.out.println("Final Display1 algo1");
			for(int i=0;i<n;i++)
			{
				j=rowLabel[i];			
				sum+=temp[i][j];	
				System.out.println(j);
			}
			s1="Total Cost is: "+sum;
		}
		else
		{
			int sum=0;
			System.out.println("Final Display algo2");
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					if(matrix[i][j]==1)
					{
						sum+=temp[i][j];
						rowLabel[i]=j;
						System.out.println(rowLabel[i]);
					}
				}
			}
			s1="Total Cost is: "+sum;
		}
		
		JTable t = new JTable();
        testTable(t,noOfPeople+1);
        JScrollPane scrollPane = new JScrollPane(t); 
        JOptionPane.showMessageDialog(null,scrollPane, s1, JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Constructs the table for multiple solutions
	 */
	public void testTableMultiple(JTable t,final int size) 
	{
			t.setModel(new AbstractTableModel()
			{
	        	String[] columnNames={" "," "};
	            public int getRowCount() { return size; }
	            public int getColumnCount() { return 2; }
	            
	            public String getColumnName(int column)
	            {
	                return this.columnNames[column];
	            }
	            
	            public void setColumnIdentifiers(Object[] newIdentifiers)
	            {
	            	
	            }
	            
	            public Object getValueAt(int row, int col)
	            {
		            Object value="";
		            if(row==0 && col==0)
					{
						value="Person";
					}
					else if(row==0 && col==1)
					{
						value="Job";
					}
					else if(col==0 && row<=n)
					{
						value=row;
					}
					else if(col==1 && row<=n)
					{
		    		   	value=rowLabel[row-1]+1;	
					}	
					else if(col==0 && row==(n+1))
					{
						value="Person";
					}
					else if(col==1 && row==(n+1))
					{
						value="Job";
					}
					else if(col==0)
					{
						value=row-n-1;
					}
					else if(col==1)
					{
						value=rowLabelFinalValues[row-n-2]+1;
					}
		            return value;
	            }
            
	            public boolean isCellEditable(int row, int col) { return false; }
	            public void setValueAt(Object value, int row, int col) {    }
        });
    }

	/**
	 * Displays the table with the set of people and jobs assigned to each person for each optimal solution
	 * Also displays the total cost of the optimal solution for multiple solutions
	 */
	public void finalDisplay2()
	{
		String s1=null;
		int sum=0;
		int j;
		for(int i=0;i<n;i++)
		{
			j=rowLabel[i];			
			sum+=temp[i][j];	
		}
		s1="There are multiple solutions with the cost of "+sum;
		JTable t = new JTable();
        testTableMultiple(t,(2*noOfPeople)+2);
        JScrollPane scrollPane = new JScrollPane(t); 
        JOptionPane.showMessageDialog(null,scrollPane, s1, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String args[])
	{
		Hungarian h = new Hungarian();
		h.hungarian();
	}
}
