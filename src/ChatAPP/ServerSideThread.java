package ChatAPP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerSideThread implements  Runnable {
	Socket clsoc;
	BufferedReader br;
	String str;
	ServerSideThread thread1;
	ServerSideThread [] threads;
	PrintStream os = null;
	Thread t;
	String name;
	String grpname;
	
	public ServerSideThread(Socket clsoc, ServerSideThread[] threads,String grpname) {
		super();
		this.clsoc = clsoc;
		this.threads = threads;
		this.grpname=grpname;
		t=new Thread(this);
	}


	public void run() {
		
		try {

			br=new BufferedReader(new InputStreamReader(clsoc.getInputStream()));
			os = new PrintStream(clsoc.getOutputStream());
			os.println(grpname);
			os.println("Enter your name.");
		    name = br.readLine();
		    os.println("Hello " + name + " Welcome to pointers Chat app");//////////////
		      for (int i = 0; i < 50; i++) {
		        if (threads[i] != null && threads[i] != this) 
		        {
		          threads[i].os.println("*** A new friend " + name +"("+ clsoc.getInetAddress()+")"+" is on the chat app now !!! ***");
		        }
		      }
			
			
			while(true)
			{
				str=br.readLine();
				if(str!=null)
				{
					int clientCount=0;
					for(int i=0;i<50;i++)
					{
						if(threads[i]!=null)
						{
							threads[i].os.println(name+" :"+str);
							clientCount++;
						}
					}
					System.out.println(str+" this message is sent to "+clientCount+" clients");
					
				}
				
			}	
			
		} catch (IOException e) {
			
			for (int i = 0; i < 50; i++) 
			{
		        if (threads[i] != null && threads[i] != this)
		        {
		          threads[i].os.println("*** The user " + name
		              + " is leaving the chat room !!! ***");
		      }
			}
			 os.println("*** Bye " + name + " ***");
			 for (int i = 0; i < 50; i++) {
			        if (threads[i] == this) {
			          threads[i] = null;
			        }
			      }
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		finally
		{
			try {
				clsoc.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
 

}
