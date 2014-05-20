package costPredictor_V2;
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
//version 2.0																		* *
//*********************************************************************************** * 
//*************************************************************************************

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CostPredictorV2 
{
	public static String mnth;
	public static Integer gt;
	public static Integer cu_pre;
	
	public void temp_file_handler()
	{
		File tempfile=new File(mnth+"_costtemp.txt");
		File realfile=new File(mnth+"_cost.txt");
		System.out.println(realfile.delete());
		System.out.println(tempfile.renameTo(realfile));
	}
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
	
	public void delete() throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
		Integer line_counter=0,i=0;
		String []data_a=new String[1000];
		String data_s;
		System.out.println("Sl. no.  Date   Data  Expenditure\n------------------------");
		while((data_s=br.readLine())!=null)
		{
			line_counter++;
			if(line_counter>3)
			{
				data_a[i]=data_s;
				i++;
			}
		}
		br.close();
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
		System.out.println("Enter Serial no to delete");
		String s_no=new BufferedReader(new InputStreamReader(System.in)).readLine();
		Integer pos=0;
		Integer line_temp=0;
		for(int j=0;j<i;j++)
		{
			for(int k=0;k<data_a[j].length();k++)
			{
				if(data_a[j].charAt(k)=='-')
					pos=k;
				break;
			}
			//System.out.println(data_a[j].substring(0,pos+1));
			if(!s_no.equals(data_a[j].substring(0,pos+1)))
				line_temp++;
		}
		String EmptyLine = null;
		BufferedWriter writer = new BufferedWriter(new FileWriter(mnth+"_costtemp.txt"));
		BufferedReader br_del=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));

		if(line_temp==i)
		{
			System.out.println("Error!! Serial number not exists");
		}
		else
		{
			Integer counter=0;
			Integer sno_i=Integer.parseInt(s_no);
			//System.out.println(data_a[sno_i-1]);
			int ct_t=0;
			String read_buffer=new String();
			while((read_buffer=br_del.readLine())!=null)
			{
				Integer pos_t=0;
				String read_buffer_temp=null;
				counter++;
				if(counter==(sno_i+3))
					continue;
				else
					ct_t++;
				if(counter>3)
				{
					for(int k=0;k<read_buffer.length();k++)
					{
						if(read_buffer.charAt(k)=='-')
						{
							pos_t=k;
							break;
						}	
					}
					read_buffer_temp=read_buffer.substring(pos_t,read_buffer.length());
					writer.write((ct_t-3)+read_buffer_temp);
				}
				else
					writer.write(read_buffer);
				writer.append("\n");
			}
		}
		writer.close();
		br_del.close();
		System.out.println("temp file write complete");
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(mnth+"_cost.txt"));
		BufferedReader br_del1=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_costtemp.txt"))));
		String tmp_s=null;
		while((tmp_s=br_del1.readLine())!=null)
		{
			if(tmp_s.length()==0)
				continue;
			writer1.write(tmp_s);
			writer1.write("\n");
		}
		writer1.close();
		br_del1.close();
		//new CostPredictorV2().temp_file_handler();
	}
	
	public void details() throws Exception
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
		Integer line_counter=0,i=0;
		String []data_a=new String[1000];
		String data_s;
		System.out.println("Sl. no.  Date   Data  Expenditure\n------------------------");
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
		Integer line_no=0;
		Boolean cont=true;
		String date=new String("");
		while(cont)
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(mnth+"_cost.txt"))));
			while(br.readLine()!=null)
				line_no++;
			FileWriter fwrite=new FileWriter(mnth+"_cost.txt",true);
			cont=false;
			Boolean d_c=true;
			while(d_c)
			{
				try
				{
					System.out.println("Enter Date:");
					date=new BufferedReader(new InputStreamReader(System.in)).readLine();
					if(Integer.parseInt(dateFormat.format(cal.getTime()))-Integer.parseInt(date)<0)
						System.out.println("Exceeds current day retry!");
					else
						d_c=false;
				}
				catch(Exception e)
				{
					System.out.println("----Wrong format!! Retry----");
					d_c=true;
				}
			}
			System.out.println("Enter Data:");
			String data=new BufferedReader(new InputStreamReader(System.in)).readLine();
			Integer cost=0;
			while(true)
			{
				try
				{
					System.out.println("Enter Expenditure:");
					String cost_data=new BufferedReader(new InputStreamReader(System.in)).readLine();
					cost=Integer.parseInt(cost_data);
					break;
				}
				catch(Exception e)
				{
					System.out.println("----Format error!! Try again----");
				}
			}
			fwrite.append("\n"+(line_no-2)+"-----"+date+"/"+mnth+"-----"+data+" "+cost);
			System.out.println("\nEnter more :");
			if(new BufferedReader(new InputStreamReader(System.in)).readLine().charAt(0)=='y')
				cont=true;
			br.close();
			fwrite.close();
			line_no=0;
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
				System.out.println("2.Delete entry from the database");
				System.out.println("3.see total cost");
				System.out.println("4. Details");
				System.out.println("5.lag or lead??");
				System.out.println("6.Exit");
				String choice=new BufferedReader(new InputStreamReader(System.in)).readLine();
				switch(choice)
				{
				case "1":new CostPredictorV2().DB_entry();
				break;
				case "2":new CostPredictorV2().delete();
				case "3":new CostPredictorV2().total();
				break;
				case "4":new CostPredictorV2().details();
				break;
				case "5":new CostPredictorV2().ll();
				break;
				case "6":System.exit(0);
				break;
				default: System.out.println("Wrong choice");
				}
			}
		}
	}
	
	public static void main(String []a) throws Exception
	{
		System.out.println("----Expenditure management tool------");
		System.out.println("---------Author:Aritra Dhar----------");
		System.out.println("1. See current month\n2. Enter new month\n3. Exit");
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
