package ChatAPP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GroupCreatThread implements Runnable {
	String grpname;
	String Portno;
	ServerSocket ss;
	Socket clsoc;
	Thread t;
	ServerSideThread [] threads= new ServerSideThread[150];// thread for cleints to get connected
	
	public GroupCreatThread(String grpname, String portno) {
		super();
		this.grpname = grpname;
		Portno = portno;
		t=new Thread(this);
	}


	public void run() {
		try
		{
				ss=new ServerSocket(Integer.parseInt(Portno));// created server socket on local host with given port no
				System.out.println("group name is "+grpname);
				System.out.println("server is ready");
				System.out.println("Now server went to accpeting mode");
				System.out.println(ss);
				while(true)// continuesly waiting here for accepting the request of the client
				{	
					clsoc=ss.accept();
					System.out.println("client got connected"+clsoc);
					for(int i=0;i<150;i++)
					{
						if(threads[i]==null)
						{
							threads[i]= new ServerSideThread(clsoc,threads,grpname);
							threads[i].t.start();
							break;
						}
					}
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try 
			{
				ss.close();
				clsoc.close();
			}
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		
		}
			
	} 
	
	}



