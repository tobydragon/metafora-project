package com.analysis.server.xmppsx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//import com.analysis.server.xmppold.XMPPBridge;





public class IOManager extends XmppActionListener{
	int intval=5; // 5 seconds
	int lineCount=0;
	private ArrayList<String>  data;
public IOManager(){
	/*
	try {
		XMPPBridge.createConnection("BasilicaCommandTestUgur", "BasilicaCommandTestUgur", "BasilicaCommandTestUgur", "logger@conference.metafora.ku-eichstaett.de", "BasilicaCommandTestUgur", "BasilicaCommandTestUgur");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	
	
}

public void startXmmpListener(){
	
	//Thread th = new Thread(new XmppActionListener());
	//th.start();
	
}


public void processFile() {
	int i=0;
	while(data.size()>0){
		i++;
		
		System.out.println(i);
	String line=data.get(0);
	System.out.println("Proces Status: " +i+" out of " +lineCount);
	processMessage(line);
	data.remove(0);
	}
	System.out.println("ALL MESSAGES ARE PROCESSED!!");
	
}



public String getMessage(String textline,String student,String time) {
	String message=textline.replaceAll(time, "");
	message=message.substring(2);
	message=message.replaceAll(student+":", "");
	return message.trim();
}

public String getStudent(String textline) {
	
	
	String [] temp=textline.split(" ");
	if(temp.length>1){
	if(temp[1]!=null){	
		temp[1]=temp[1].substring(0,temp[1].length()-1);
		return temp[1].trim();
		
	}}
	return "";
	
}

public String getTime(String textline) {
	String [] temp=textline.split(" ");
	if(temp[0]!=null){	
		return temp[0].trim();
		
	}
	return "";
	
}


public void setInterval(int interval) {
	intval=interval;
	
}



public void processMessage(String textline) {
	
	String student=getStudent(textline);
	
	String time=getTime(textline);
	String message=getMessage(textline,student,time);
	System.out.println("*****************");
	System.out.println("Student:"+student);
	System.out.println("Time:"+time);
	System.out.println("Message:"+message);
	
	System.out.println("###################");
	SendtoTutor(message,student,time);
	

	
}


String composeAction(String message,String User,String Time){
	
	String action="<action time=\"1320447004493\">" +
			"<actionType logged=\"true\" type=\"chat-msg\" classification=\"other\"/>" +
			"<user role=\"originator\" id=\""+User+"\"/>" +
			"<object type=\"container\" id=\"0\">" +
			"<object type=\"msg\" id=\"1\">" +
			"<properties>" +
			"<property name=\"MESSAGE\" value=\""+message+"\"/>" +
			"<property name=\"MAP-ID\" value=\"1\"/>" +
			"</properties>" +
			"</object>" +
			"<properties/>" +
			"</object>" +
			"</action>";
	
	return action;
}
void SendtoTutor(String _message,String _student,String _time){
	

	//XMPPBridge xmppBridge;
	//xmppBridge = XMPPBridge.getConnection("BasilicaCommandTestUgur");
	//xmppBridge.connect(false);
	
	
	  long t0, t1;

      t0 =  System.currentTimeMillis();

      do{
          t1 = System.currentTimeMillis();
      }
      while (t1 - t0 < 2500);

      //xmppBridge.sendMessage(composeAction(_message,_student,""));
	System.out.println("Message is Sent!!");
	
}


public void  readFile(String fileName) {
	 String line = "";
	 data = new ArrayList<String> ();//consider using ArrayList<int>
	  try {
	   FileReader fr = new FileReader(fileName);
	   BufferedReader br = new BufferedReader(fr);//Can also use a Scanner to read the file
	   while((line = br.readLine()) != null) {
	 
	    data.add(line);
	   }
	   
	   lineCount=data.size();
	  }
	  catch(FileNotFoundException fN) {
	   fN.printStackTrace();
	  }
	  catch(IOException e) {
	   System.out.println(e);
	  }
	  
	
}






}
