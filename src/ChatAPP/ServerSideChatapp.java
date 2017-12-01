package ChatAPP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerSideChatapp extends Application {


	Socket clsoc;
	ServerSideThread [] threads= new ServerSideThread[50];
	Socket s;
	BufferedReader br;
	String str;
	PrintWriter out;
	ClientSideThread thread2;
	public static void main(String[] args) {
	
			launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
//***************JOIN GRUOP**************//
				
		
				//-----JOIN GROUP SCENE--------
				BorderPane p= new BorderPane();
				VBox v1= new VBox();
				Label ip=new Label("Enter Server's IP address ");
				TextField iptxt= new TextField();
				Label port=new Label("Enter Port Number");
				TextField porttxt= new TextField();
				Button joingrp=new Button("Join Group");
				v1.getChildren().addAll(ip,iptxt,port,porttxt,joingrp);
				p.setCenter(v1);
				Scene joinGroup=new Scene(p);
				
				
				
				//-----APPLICATION SCENE after joining group--------
				TextField txt=new TextField();
				Button b= new Button("send");
				TextArea tarea=new TextArea();
				Label grname= new Label();
				
				HBox hb1= new HBox();
				hb1.getChildren().addAll(txt,b);
				BorderPane bpan=new BorderPane();
				bpan.setBottom(hb1);
				bpan.setTop(grname);
				bpan.setCenter(tarea);
				Scene chatbox=new Scene(bpan);
				
				
				
				b.setOnAction((e)->{   // On presss of send button send the message to server
				String	str=txt.getText();
					System.out.println(str);
					out.write(str+"\n");
					out.flush();
				
					txt.setText("");
					str="";
				});
				
				txt.setOnKeyPressed(new EventHandler<KeyEvent>() {  // On presss of Enter key send the message to server
				    public void handle(KeyEvent keyEvent) {
				        if (keyEvent.getCode() == KeyCode.ENTER)  {
				        	String	str=txt.getText();
				        	System.out.println(str);
							out.write(str+"\n");
							out.flush();
							txt.setText("");
							str="";
				        }
				    }
				});
				
				
				joingrp.setOnAction((e)->{
					
					
					try {
						System.out.println("*****CLIENT SIDE*******");
						
						s = new Socket(iptxt.getText(),Integer.parseInt(porttxt.getText()));//192.168.1.** IS IP ADD OF KNOW IT
						br=new BufferedReader(new InputStreamReader(s.getInputStream()));
						out=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
						System.out.println("connection accepted");
						System.out.println(s);
						thread2=new ClientSideThread(s, out, br, tarea,grname);
						thread2.t.start();
						
						primaryStage.setScene(chatbox);
					} catch (IOException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
					
				});
		
		
//**************CREATE GROUP**********************//
				
				//-----CREATE GROUP SCENE--------
		BorderPane p2= new BorderPane();
		VBox v= new VBox();
		Label portno=new Label("Enter PORT Number ");
		TextField portnotxt= new TextField();
		Label grpname=new Label("Enter Group Name");
		TextField grpnametxt= new TextField();
		
		Button creatgrp=new Button("Create Group");
		v.getChildren().addAll(portno,portnotxt,grpname,grpnametxt,creatgrp);
		p2.setCenter(v);
		Scene creatGroup=new Scene(p2);
	
		creatgrp.setOnAction((e)->{
			String prt=portnotxt.getText();
			String gpname=grpnametxt.getText();
			
			try 
			{
				InetAddress IP=InetAddress.getLocalHost();
				 MyIpAddress myIpAddress = new MyIpAddress();
				 String ipadd=myIpAddress.main();// get the IP address of local host
				 
				 
				System.out.println("IP of my system is := "+ipadd);
				gpname="'"+gpname+"'  ,To JOIN group ip: "+ipadd+" Port no:"+prt;
				
				GroupCreatThread newgrp= new GroupCreatThread(gpname,prt);// It creates the thread array for clients 
													// server will be contantly listenning to this threads and it will 
													//pass the recieved message to all the other client threads
																	
				newgrp.t.start();
				
				System.out.println("this is server socket "+IP.getHostAddress());
				s = new Socket(ipadd,Integer.parseInt(prt));
				br=new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				System.out.println("connection accepted");
				System.out.println(s);
				
				thread2=new ClientSideThread(s, out, br, tarea,grname);
				thread2.t.start();
				
				primaryStage.setScene(chatbox);
				
			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
			
		});	
		
		
		//------------START PAGE SCENE-------------//
		BorderPane p1= new BorderPane();
		Label header= new Label("Pointer's Chat APP");
		Label hk=new Label("Chant HARE KRISHNA BE HAPPY");
		VBox v2= new VBox();
		v2.getChildren().addAll(header,hk);
		p1.setTop(v2);
		Button creatGrp=new Button("CREAT GROUP");
		Button joinGrp=new Button("JOIN GROUP");
		HBox h1=new HBox();
		h1.getChildren().addAll(creatGrp,joinGrp);
		
		Image image = new Image("image/SSRVCC.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(image);
        
        p1.setCenter(iv1);
        p1.setBottom(h1);
		Scene startscene=new Scene(p1);// created the scene for home page
		primaryStage.setHeight(500);
		primaryStage.setWidth(500);
		creatGrp.setOnAction((e)->{
			primaryStage.setScene(creatGroup);// go to create group scene
		});
		
		joinGrp.setOnAction((e)->{
			primaryStage.setScene(joinGroup);// go to join group scene
		});
	
		
		primaryStage.setScene(startscene);// showed home page
		primaryStage.setTitle("POINTERS Chatt APP (3.3)");
		
		primaryStage.show();
	}

}
