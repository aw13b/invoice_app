import java.util.Scanner;
import java.lang.Math;
import java.text.NumberFormat;
import java.util.InputMismatchException;

public class InvoiceApp
{
	public static void main(String[] args)
	{
		System.out.println("Welcome to the Invoice App. This app calculates your subtotals and applies the associated discounts");
		
		//Initialize Variables
		double subtotal=0.0;
		double invoice=0.0;
		double avgInvoice=0.0;
		String choice="y";
		double finalInvoice=0.0;
		double discountAmt=0.0;
		double finalTotal=0.0;
		double combinedTotal=0.0;
		double combinedDiscount=0.0;
		String customer="";
		double avgDicount = 0.0; 
		
		
		//Create Scanner object
		Scanner userInput = new Scanner(System.in);
		
		//Using the NumberFormat class to format values
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		NumberFormat percent= NumberFormat.getPercentInstance();
		
		//Run program while choice is not equal to "n"
		while(!choice.equalsIgnoreCase("n"))
		{
			//Ask user to select a specific customer type and then asks for the subtotal
			System.out.println("Enter customer type (r/c): " );
			
			//Calls getValidCustomerType() method
			customer= getValidCustomerType(userInput);
			
			//Ask user for subtotal
			System.out.println("Enter Subtotal: ");
			
			//Calls getValidSubtotal() method
			double userSubtotal= getValidSubtotal(userInput);
			
			//Calls static method getDiscountPercentage with two arguments
			double usersDiscountPercent	= getDiscountPercentage(customer, userSubtotal);	
			
			//Calculates the discount the user gets
			discountAmt=userSubtotal*usersDiscountPercent;
			
			//Calculates the final total by subtracting the discount amount from the subtotal
			finalTotal=userSubtotal-discountAmt;			
			
			String message = "Discount Applied: " + percent.format(usersDiscountPercent) + "\n"
							+ "Discount Amount taken off subtotal: " + currency.format(discountAmt) + "\n"
							+ "Invoice Total " + currency.format(finalTotal) + "\n";
			System.out.println(message);		
			
			//Adds the number of invoices the users inputs
			invoice= invoice+1;
			//Adds all final total of each invoice to get a total amount
			combinedTotal= combinedTotal+finalTotal;
			//Add all final discount amount given to get a total amount for the combined discount 
			combinedDiscount= combinedDiscount+discountAmt;
			
			//Asks user if they have another invoice to add and uses a loop to make sure user enters a valid input 
			System.out.println("Do you have another invoice to add? (y/n)");
			choice = userInput.next();
			while(!choice.equalsIgnoreCase("n") && !choice.equalsIgnoreCase("y"))
			{
				System.out.println("Invalid Entry. Please type \'y\' or \'n\'");
				System.out.println("Do you have another invoice to add? (y/n)");
				choice= userInput.next();
			}
			System.out.println();
		}//end of while loop
		
		//Calculates the average number of invoice and the average discounts
		avgInvoice = combinedTotal/invoice;
		avgDicount=combinedDiscount/invoice;
		
		//Displays the final results
		System.out.println("Number of invoices entered: " + (int)invoice);
		System.out.println("Average Invoices: " + currency.format(avgInvoice));
		System.out.println("Average discount: " + currency.format(avgDicount));
		
	}//end of main method
	
		//Get Valid Customer Type method that takes one parameter
		public static String getValidCustomerType(Scanner userInput)
		{
			boolean isValid= false;
			String customerType="";
			while(isValid==false)
			{
				//Create String variable and set its value to the user's input for customer type.
				String user="";
				user=userInput.next();
				if(user.equalsIgnoreCase("r") || user.equalsIgnoreCase("c"))
				{
					//if true, set user to customerType and make isValid set to true to end loop
					customerType=user;
					isValid=true;
				}
				else
				{
					System.out.println("Invalid Entry! Please type \'r\' or \'c\'");
				}
				userInput.nextLine(); //discard the entire line
			}
			return customerType;
		}//end of getValidCustomerType()
		
		//Get Valid Subtotal method that takes one parameter
		public static double getValidSubtotal(Scanner userInput)
		{
			double subtotal=0.0;
			boolean isValid= false;
			while(isValid==false)
			{
				try
				{
					//Asks for a double data type and set it to subtotal
					subtotal=userInput.nextDouble();
					//call the getSubtotalRange() method to evaluate the range of subtotal and then set isValid to true if input is valid
					subtotal=getSubtotalRange(subtotal, 0, 1000);
					isValid=true;
				}
				catch(InputMismatchException e)
				{
					userInput.next();
					System.out.print("Invalid Entry. Subtotal must be a number. Please try again. ");
					continue;//continues to the beginning of the loop
				}	
			}
			
			return subtotal;
		}//end of getValidSubtotal() method
		
		
		public static double getSubtotalRange(double subtotal, double min, double max)
		{
			Scanner userInput= new Scanner(System.in);
			
			//Using the NumberFormat class to format values
			NumberFormat currency = NumberFormat.getCurrencyInstance();
			NumberFormat percent= NumberFormat.getPercentInstance();
			boolean isValid= false;
			while(isValid==false)
			{
				//subtotal=userInput.nextDouble();
				if(subtotal<= min)
				{
					System.out.println("Invalid Entry! Subtotal must be greater than " +  currency.format(min) + " Please try again.");
					subtotal=userInput.nextDouble();
				}
				else if(subtotal>= max)
				{
					System.out.println("Invalid Entry! Subtotal must be less than " +  currency.format(max) + " Please try again.");
					subtotal=userInput.nextDouble();
				}
				else
				{
					isValid=true;
				}
			}
			return subtotal;
		}
		
		//Get Discount Percentage method passes two parameters
		public static double getDiscountPercentage(String customerType, double subtotal)
		{
			double discountsPercentage=0.0;
			if (customerType.equalsIgnoreCase("R"))
			{
				if(subtotal >= 250 && subtotal < 500)
				{
					discountsPercentage=0.25;
				}
				else if(subtotal >= 500)
				{
					discountsPercentage=0.3;
				}	
			}
			else if(customerType.equalsIgnoreCase("c"))
			{
				discountsPercentage =.2;
			}
			else
			{
				System.out.println("");
				System.out.println("No discounts taken off for this type of customer.");
			}
			return discountsPercentage;
			
		}//end of getDiscountPercentage() method
				
	
}//end of InvoiveApp