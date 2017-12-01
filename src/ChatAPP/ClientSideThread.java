package ChatAPP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ClientSideThread implements Runnable {

	Socket clsoc;
	PrintWriter pw;
	BufferedReader br=null;
	String str="";
	Thread t;
	TextArea tarea;
	Label grname;
	String usrname;
	public ClientSideThread(Socket clsoc, PrintWriter pw, BufferedReader br, TextArea tarea,Label grname) {
		super();
		this.clsoc = clsoc;
		this.pw = pw;
		this.br = br;
		this.tarea = tarea;
		this.grname=grname;
		t=new Thread(this);
		
	}
	
	@Override
	public void run() {
		tarea.appendText("This is Pointer Chat APP :-)");
		tarea.appendText("\n"+"threading socket is :"+clsoc);
		try {
			str=br.readLine();// acccept the first msg
			grname.setText("Group name :"+str);// asign the group name and its properties to label
		while(true)// continuesly be in this loop to accept the msg of the server and prit it on text box
		{
				while(true)
				{
					
							str=br.readLine();
							if(str!=null)
							{
								System.out.println(str);
								tarea.appendText("\n"+str);
								break;
							}
				}
				
		}	
		} 
		catch (IOException e) {
			e.printStackTrace();
			pw.close();
			try {
				clsoc.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		
	}

}
