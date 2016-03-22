package anuj.java;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class FinalLayout implements Runnable {
	static int noteToBePlayed;
	
	public void run(){
	}
	
	public static void main(String args[]) throws MidiUnavailableException{
		final JFrame frame=new JFrame("Guitar Tuner");
		GridLayout grid=new GridLayout(16,6);
		JPanel panel=new JPanel();
		panel.setLayout(grid);
		JLabel sixth=new JLabel("6th String");
		panel.add(sixth);
		JLabel fifth=new JLabel("5th String");
		panel.add(fifth);
		JLabel fourth=new JLabel("4th String");
		panel.add(fourth);
		JLabel third=new JLabel("3rd String");
		panel.add(third);
		JLabel second=new JLabel("2nd String");
		panel.add(second);
		JLabel first=new JLabel("1st String");
		panel.add(first);
		
		grid.setHgap(30);
		
		//Declare all the button groups
		ButtonGroup[] groups=new ButtonGroup[7];
		for(int c=0;c<7;c++){
			groups[c]=new ButtonGroup();
		}
		
		int i,j;
		int counter=12;
		int countersCounter;
		int groupCounter;
		int starter=12;
		String chordNames[]={"A2","A#2","B2","C3","C#3","D3","D#3","E3","F3","F#3","G3","G#3","A3","A#3","B3","C4","C#4","D4","D#4","E4","F4","F#4","G4","G#4","A4","A#4","B4","C5","C#5","D5","D#5","E5","F5","F#5","G5","G#5","A5" };
		JRadioButton radioButtons[]=new JRadioButton[78];
		
		for(j=0;j<13;j++){
			starter=12-j;      //keeps track of where i should start with each time
			counter=12-j;      //keeps track of which note is to be assigned to which button
			countersCounter=1; //keeps track of that weird change in 2nd string so it's reduced by 4 not 5
			groupCounter=6;    //assigns a note to each group..6 is for 6th string and so on
			for(i=starter;i<78;i+=13){
				radioButtons[i]=new JRadioButton(chordNames[counter]);
				System.out.println("Button no:"+i+" chord name:"+chordNames[counter]);
				panel.add(radioButtons[i]);
				groups[groupCounter].add(radioButtons[i]);
				System.out.println("Group:"+groupCounter);
				if(countersCounter==3){
					counter+=4;
					countersCounter++;
					groupCounter-=1;
					continue;
				}
				counter+=5;
				countersCounter++;
				groupCounter-=1;
			}
			
		}
		
		
		//get all six play buttons with playButton[6] being the one for 6th string and so on
		JButton[] playButtons=new JButton[7];
		for(int btnCounter=6;btnCounter>0;btnCounter--){
			playButtons[btnCounter]=new JButton("Play");
			panel.add(playButtons[btnCounter]);
		}
		
		for(i=0;i<78;i++){
			radioButtons[i].addActionListener(new Action(i));
		}
		
		
		JButton stopButton=new JButton("Stop");
		panel.add(stopButton);
		
		
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		FinalLayout r=new FinalLayout();
		Thread sleeper=new Thread(r);
		
		
		Synthesizer synth = MidiSystem.getSynthesizer();
		MidiChannel[] channels = synth.getChannels();
		
		
		//actually plays the note;
		Thread player=new Thread(){public void run(){
			try {
				synth.open();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
			channels[0].noteOn(noteToBePlayed, 100);
			System.out.println("Playing note: "+noteToBePlayed);
			try {
				sleeper.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			run();
		}
	};
	
	//ActionListeners for all the play buttons, stop button
	playButtons[6].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.sixthString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});
	
	playButtons[5].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.fifthString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});
	
	playButtons[4].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.fourthString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});
	
	playButtons[3].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.thirdString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});
	
	playButtons[2].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.secondString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});
	
	playButtons[1].addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			noteToBePlayed=Action.firstString;
			if(!player.isAlive()){	
			player.start();
			}
			else player.resume();
		}
	});

	stopButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			player.suspend();
			//synth.close();
			System.out.println("Synth closed");
			//sleeper.stop();
			
		}
	});
	
	//initialize to standard guitar tuning (EADGBE)
	radioButtons[7].doClick();
	radioButtons[20].doClick();
	radioButtons[33].doClick();
	radioButtons[47].doClick();
	radioButtons[59].doClick();
	radioButtons[72].doClick();
	
	//Maximize frame on load
	frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
}
}
