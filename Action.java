package anuj.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Action implements ActionListener{
	int valueOfi;
	static int sixthString,fifthString,fourthString,thirdString,secondString,firstString;
	
	Action(int i){
		this.valueOfi=i;
		
	}
	
	public void six(int note){
		sixthString=note+45;
	}
	
	//determine which note is to be played
	public void actionPerformed(ActionEvent e){
		if(valueOfi>=0 && valueOfi<=12)
			six(valueOfi);
		if(valueOfi>12 && valueOfi<=25)
			fifthString=valueOfi+37;
		if(valueOfi>25 && valueOfi<=38)
			fourthString=valueOfi+29;
		if(valueOfi>38 && valueOfi<=51)
			thirdString=valueOfi+20;
		if(valueOfi>51 && valueOfi<=64)
			secondString=valueOfi+12;
		else firstString=valueOfi+4;
		//System.out.println(valueOfi+ " note number: "+fifthString+"     "+sixthString+ "   "+fourthString);
	}

}
