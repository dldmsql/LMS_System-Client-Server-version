package ui;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Client.Stub;

public class Animation extends Applet implements Runnable, ActionListener{
	private JButton start;
	private JButton stop;
	private JPanel panel;
	// image
	private Image img;
	private boolean flag = false;
	private String fileName;
	private String fileName1;
	private String fileName2;
	private String fileName3;
	public Animation() {
		//set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.send("CDirectory", "getImage", "YesReturn", "animation");
		// receive data from Server
		fileName = (String) stub.receive();
		System.out.println(fileName );
		String[] array = fileName.split("T");
		fileName1 = array[0]; // duck/T
		fileName2 = array[1];
		fileName3 = fileName1.substring(2);
		init(fileName);
	}
	public void init(String fileName) {
		this.setLayout(new BorderLayout());
		
		this.start = new JButton("start");
		this.stop = new JButton("stop");
		this.panel.add(start);
		this.panel.add(stop);
		
		this.img = this.getImage(getDocumentBase(), fileName);
		
		this.add(panel, "North");
	}
	public void start() {
		this.start.addActionListener(this);
		this.stop.addActionListener(this);
	}
	public void paint(Graphics g){
		g.drawImage(this.img, 50, 50, 100, 100, this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		Thread t = new Thread(Animation.this);
		t.start();
		if(o==start){
			this.flag=true;
			
		}else if(o==stop){
			this.flag=false;
		}
	}

	@Override
	public void run() {
		int i = 1;
		while(this.flag){
			if(i == 10){
				i=1;
			}
			i = i+1;
			this.img=this.getImage(getDocumentBase(), fileName1+ i +fileName3);
			repaint();
			try{
				Thread.sleep(100);
			}catch(Exception e){}
		}
	}

}
