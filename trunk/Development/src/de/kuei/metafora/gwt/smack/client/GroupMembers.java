package de.kuei.metafora.gwt.smack.client;

import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;

public class GroupMembers extends TableLayout{
/* Auszug aus allgemeinem Onlinestatus. Muss konkret später ausdefiniert werden 
 * Frage: Schickt Groupmembers Infos an Onlinestatus oder schickt Onlinestatus die Infos an GroupMembers?
*/
	Vector<Student> member = new Vector<Student>(); // name, group, online ("t"=true, "f"=false)
	
	GroupMembers(){
		super();
		Student s;
		for(int j=0; j<=3; j++){
			if(j==2){
				s = new Student("Otto2", false, 3);
				member.add(s);
			}
			else{
				s = new Student("Otto"+j, true, 3);
				member.add(s);
			}
		}
		
		for(int i=0; i<member.size(); i++){
			s = member.get(i);
			if(s.group==3){
				addTable(s.name, s.online, i);
			}
		}
	}
	
	public void addTable(String s, boolean b, int i){
		HTML name = new HTML(s);
		HTML onl;
		if(b){
			onl = new HTML("+");
		}
		else{ 
			onl = new HTML("-");
		}
		box.setWidget(i, column-2, onl);
    	box.setWidget(i, column-1, name);
	}
}
