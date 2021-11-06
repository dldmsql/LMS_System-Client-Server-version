package ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JFrame;

import Client.Stub;
import Server.VO;

@SuppressWarnings({ "serial", "unused" })
public class MainFrame extends JFrame {

	private SelectionPanel selectionPanel;
	private String id;
	public String rUserName;
	public String rUserID;
	public String rUserPW;
	public String rUserMajor;
	public String rUserGrade;
	public MainFrame(ActionListener actionHandler, WindowListener windowHandler) {
		
		this.setTitle("학사관리 시스템");
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(windowHandler);

		//imageIcon
		Toolkit toolkit = this.getToolkit();
		Image img = toolkit.createImage("image/mju_icon.jpg");
		this.setIconImage(img);

		this.selectionPanel = new SelectionPanel(actionHandler,id);
		this.selectionPanel.initiate();
		this.add(selectionPanel);
	}
	// lectureTable에서 선택한 값들을 vector에 담아서 전달.
	public Vector<VO> showFrame() {
		return this.selectionPanel.showFrame();
	}
	public void setID(String id) {
		this.id = id;
		this.selectionPanel.setID(id);
	}
	//개인정보열람
	public String[] userInfo(String id) {
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.send("CLogin", "userInfo", "YesReturn", id);
		// receive data from Server
		String a[] = (String[]) stub.receive();
		stub.finalize();
		return a;
	}
	public void setImage() {
		//set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.send("CLogin", "getImages", "YesReturn", id);
		// receive data from Server
		String imageID = (String) stub.receive();
		stub.finalize();
		System.out.println(imageID);
		this.selectionPanel.setImage(imageID);
	}
}