package AnswersCheck;

import javax.swing.JOptionPane;

public class average_grades
{
public static void main (String []args)
{
    int num_grades = 0;
    int total_grade = 0;
    int average_grade = 0;
    int student_grade = 0;
    while (student_grade != 999)
        {
            student_grade = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the student's grade?"));
            if (student_grade < 0)
                {
                    JOptionPane.showMessageDialog(null,"Invalid Entry");
                }
            else
                {
            		if(student_grade != 999)
            		{
                    num_grades++;
                    total_grade = total_grade + student_grade;
                    average_grade = total_grade/num_grades;
            		}            		
                }

        }
JOptionPane.showMessageDialog(null, "Average: " + average_grade);
}
}