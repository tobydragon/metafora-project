package de.uds.MonitorInterventionMetafora.misc.jackson;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;



class JacksonTest {
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	int num = 5;
	String word = "hello\"}";
	
	public String toString(){
		return word + num;
	}
	
	public static void main(String[] args){
		JacksonTest myTest = new JacksonTest();
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(myTest));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}