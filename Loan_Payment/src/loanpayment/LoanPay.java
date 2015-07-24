package loanpayment;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.text.NumberFormat;
import java.util.Locale;



/*switched from applet to Swing application
 * Replaced the applet tag with java class 
 */
public class LoanPay implements ActionListener{
	
	JFrame jfrm;//create a top-level controller
	JTextField amountText, paymentText, periodText, rateText;
	JButton doIt,reSet;
	
	double principal; //original principal
	double intRate; //interest rate
	double numYears; //length of loan
	
	/*No. of payments per year
	 You can make it user-generated
	 */
	final int payPerYear = 12;
	
	NumberFormat nf;
	
	
	//Replaced init method with main method
	public static void main(String args[]) {
		
		try{
			LoanPay lp = new LoanPay();
			SwingUtilities.invokeAndWait(new Runnable()//invokeAndWait is synchronous in task execution
		    {
				public void run()
				{
					lp.makeGUI();//Initialize the GUI
				}
		    });
		}
	    catch(Exception exc)
		{
	    	System.out.println("Cannot start GUI due to " + exc);
		}
	}
	
	//Set up and initialize the GUI
	private void makeGUI()
	{
		//Use a grid bag layout
		
		jfrm = new JFrame();
		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		jfrm.setLayout(gb);
		jfrm.setSize(500,300);
		jfrm.setVisible(true);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setTitle("Swing Application");
		
		
		JLabel heading = new JLabel("Compute Monthly Loan Payments");
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setFont(new Font("Arial", Font.ITALIC, 14));
		
		JLabel amountLab = new JLabel("Amount");
		JLabel periodLab = new JLabel("Period");
		JLabel rateLab = new JLabel("Interest rate");
		JLabel payLab = new JLabel("Monthly Payments");
		
		amountText = new JTextField(10);
		periodText = new JTextField(10);
		rateText = new JTextField(10);
		paymentText = new JTextField(10);

		//Set field for display only
		paymentText.setEditable(false);
		
		doIt = new JButton("Compute");
		reSet = new JButton("Reset");
		
		//Define grid bag
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(heading, gbc);
		
		//Anchor most components to right
		gbc.anchor = GridBagConstraints.EAST;
		
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(amountLab, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(amountText, gbc);
		
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(periodLab, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(periodText, gbc);
		
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(rateLab, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(rateText, gbc);
		
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gb.setConstraints(payLab, gbc);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gb.setConstraints(paymentText, gbc);
		
		gbc.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(doIt, gbc);
		gbc.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(reSet, gbc);
		
		jfrm.add(heading);
		jfrm.add(amountLab);
		jfrm.add(amountText);
		jfrm.add(periodLab);
		jfrm.add(periodText);
		jfrm.add(rateLab);
		jfrm.add(rateText);
		jfrm.add(payLab);
		jfrm.add(paymentText);
		jfrm.add(doIt);
		jfrm.add(reSet);
		
		//Register to receive action events
		amountText.addActionListener(this);
		periodText.addActionListener(this);
		rateText.addActionListener(this);
		doIt.addActionListener(this);
		reSet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				amountText.setText("");
				periodText.setText("");
				rateText.setText("");
				paymentText.setText("");
			}
		});
		
		//Create a NumberFormat
		nf = NumberFormat.getCurrencyInstance(Locale.US);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	/* User pressed Enter on a text field
	 * or pressed Compute. Display result 
	 * if all fields are filled
	 */
	public void actionPerformed(ActionEvent ae)
	{
		double result = 0.0;
		
		String amountStr = amountText.getText();
		String periodStr = periodText.getText();
		String rateStr = rateText.getText();
		
		try
		{
			if(amountStr.length() !=0 && periodStr.length() !=0 && rateStr.length() !=0)
			{
				principal = Double.parseDouble(amountStr);
				intRate = Double.parseDouble(rateStr)/100;
				numYears = Double.parseDouble(periodStr);
				
				result = compute();
				
				paymentText.setText(nf.format(result));
				
			}
			
			
		}
		
		catch(NumberFormatException nfe)
		   {
			System.out.println("Invalid Data");
			paymentText.setText("");
		   }
	  }
	
	
	//Compute loan payment
	public double compute(){
		double nume;
		double deno;
		double b, e;
		
		nume = intRate*principal/payPerYear;
		b = (intRate/payPerYear)+1.0;
		e = -(payPerYear*numYears);
		deno = 1.0 - Math.pow(b, e);
		
		return nume/deno;
	}
	
	
	
}

