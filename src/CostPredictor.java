//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar																* *
//MT12004																			* *
//M.TECH CSE																		* * 
//INFORMATION SECURITY																* *
//IIIT-Delhi																		* *	 
//---------------------------------------------------------------------------------	* *																				* *
/////////////////////////////////////////////////									* *
//The program will do the following::::        //									* *
//1.expenditure database					   //									* *
//2.cost prediction					  		   //									* *
//											   //									* *	 
//				   							   //									* *
/////////////////////////////////////////////////									* *
//version 1.0																		* *
//*********************************************************************************** * 
//*************************************************************************************

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CostPredictor 
{
	public static String mnth;
	public static Integer gt;
	public static Integer cu_pre;
	
	public static void set_cu_pre() throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
		Integer line_counter=0;
		String data_s=new String("");
		String buffer=new String("");
		int sep_pos=0;
		while((data_s=br.readLine())!=null)
		{
			line_counter++;
			if(line_counter==2)
			{
				buffer=data_s;
			}
		}
		for(int i=0;i<buffer.length();i++)
		{
			
			if(buffer.charAt(i)==':')
				sep_pos=i;
		}
		cu_pre=Integer.parseInt(buffer.substring(sep_pos+1, buffer.length()));
	}
	public static void new_month() throws Exception
	{
		System.out.println("Month:");
		String mnth=new BufferedReader(new InputStreamReader(System.in)).readLine();
		System.out.println("Enter predicted monthly cost for month "+mnth+":");
		String cost_s=new BufferedReader(new InputStreamReader(System.in)).readLine();
		Integer cost_m=Integer.parseInt(cost_s);
		FileWriter fwrite=new FileWriter(mnth+"_cost.txt");
		fwrite.append("month:"+mnth);
		fwrite.append("\nPredicted cost:"+cost_m);
		fwrite.append("\nTotal cost:");
		fwrite.close();
	}
	
	public void ll() throws Exception
	{
		try
		{
		System.out.println("----Only applied on current month----");
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		Integer current_d=Integer.parseInt(dateFormat.format(cal.getTime()));
		//new CostPredictor().total();
		System.out.println("Make sure to have total");
		System.out.print("Enter fixed cost(like house rent etc which are constant for every month):");
		Integer fixed_cost=Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
		Integer grnd_total=gt-fixed_cost;
		Integer avg=grnd_total/current_d;
		Integer predicted_ex=avg*30+fixed_cost;
		System.out.println("Predicted cost base on current expenditure "+predicted_ex);
		set_cu_pre();
		System.out.println("Predicted cost "+cu_pre);
		if((predicted_ex-cu_pre)>0)
			System.out.println("lagging behing by "+(predicted_ex-cu_pre));
		else
			System.out.println("leading by "+(cu_pre-predicted_ex));
		}
		catch(Exception e)
		{
			System.out.println("Make sure to have total");
		}
		current_mnth();
	}
	
	public void details() throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
		Integer line_counter=0,i=0;
		String []data_a=new String[1000];
		String data_s;
		System.out.println("Date   Data  Expenditure\n------------------------");
		while((data_s=br.readLine())!=null)
		{
			line_counter++;
			if(line_counter>3)
			{
				data_a[i]=data_s;
				i++;
			}
		}
		for(int j=0;j<i;j++)
		{
			for(int k=0;k<data_a[j].length();k++)
			{
				if(data_a[j].charAt(k)=='-')
					System.out.print(" ");
				else
					System.out.print(data_a[j].charAt(k));
			}
			System.out.println();
		}
		current_mnth();
	}
	
	public void total() throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
		Integer line_counter=0,i=0;
		String data_s;
		String []data_a=new String[1000];
		Integer sp_pos=0;
		Integer total=0;
		while((data_s=br.readLine())!=null)
		{
			line_counter++;
			if(line_counter>3)
			{
				data_a[i]=data_s;
				//System.out.println(data_a[i]);
				i++;
			}
		}
		for(int j=0;j<i;j++)
		{
			for(int k=0;k<data_a[j].length();k++)
			{
				if(data_a[j].charAt(k)==' ')
					sp_pos=k;
			}
			total+=Integer.parseInt(data_a[j].substring(sp_pos+1, data_a[j].length()));
		}
		System.out.println("Total="+total);
		gt=total;
		line_counter=0;
		br.close();
		current_mnth();
	}
	
	public void DB_entry() throws Exception
	{
		DateFormat dateFormat = new SimpleDateFormat("dd");
		Calendar cal = Calendar.getInstance();
		
		Boolean cont=true;
		String date=new String("");
		while(cont)
		{
			FileWriter fwrite=new FileWriter(mnth+"_cost.txt",true);
			cont=false;
			Boolean d_c=true;
			while(d_c)
			{
				System.out.println("Enter Date:");
				date=new BufferedReader(new InputStreamReader(System.in)).readLine();
				if(Integer.parseInt(dateFormat.format(cal.getTime()))-Integer.parseInt(date)<0)
					System.out.println("Exceeds current day retry!");
				else
					d_c=false;
			}
			System.out.println("Enter Data:");
			String data=new BufferedReader(new InputStreamReader(System.in)).readLine();
			System.out.println("Enter Expenditure:");
			String cost_data=new BufferedReader(new InputStreamReader(System.in)).readLine();
			Integer cost=Integer.parseInt(cost_data);
			fwrite.append("\n"+date+"/"+mnth+"-----"+data+" "+cost);
			System.out.println("\nEnter more :");
			if(new BufferedReader(new InputStreamReader(System.in)).readLine().charAt(0)=='y')
				cont=true;
			fwrite.close();
		}
		
		System.out.println("\nDatabase write complete!!");
		total();
	}
	
	public static void current_mnth() throws Exception
	{
		System.out.println("Enter month : ");
		mnth=new BufferedReader(new InputStreamReader(System.in)).readLine();
		if(!new File(mnth+"_cost.txt").exists())
			System.out.println("File for month "+mnth+" not exists");
		else
		{
			while(true)
			{
				System.out.println("1.Entry to the database");
				System.out.println("2.see total cost");
				System.out.println("3. Details");
				System.out.println("4.lag or lead??");
				System.out.println("5.Exit");
				String choice=new BufferedReader(new InputStreamReader(System.in)).readLine();
				if(choice.compareToIgnoreCase("1")==0)
					new CostPredictor().DB_entry();
				if(choice.compareToIgnoreCase("2")==0)
					new CostPredictor().total();
				if(choice.compareToIgnoreCase("3")==0)
					new CostPredictor().details();
				if(choice.compareToIgnoreCase("4")==0)
					new CostPredictor().ll();
				if(choice.compareToIgnoreCase("5")==0)
					System.exit(0);
				else
					System.out.println("Wrong choice");
			}
		}
	}
	
	public static void main(String []a) throws Exception
	{
		System.out.println("1. See current month\n2. Enter new month\n3.Exit");
		String choice=new BufferedReader(new InputStreamReader(System.in)).readLine();
		switch(choice)
		{
			case "1": current_mnth();
				  break;
			case "2": new_month();
			      break;
			case "3": System.exit(0);
				  break;
			default:  System.out.println("Wrong choice!");
		}
	}

}
