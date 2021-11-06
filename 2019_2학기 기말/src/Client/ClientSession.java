package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Vector;

import javax.swing.JOptionPane;

import Server.VO;
import sql_test2.Secret;
import ui.BasketFrame;
import ui.ChangePW;
import ui.FindFrame;
import ui.FindLecture;
import ui.LectureEvaluation;
import ui.LoginFrame;
import ui.MainFrame;
import ui.RegisterFrame;

public class ClientSession {
	// Handler
	private ActionHandler actionHandler;
	private KeyHandler keyHandler;
	private WindowHandler windowHandler;
	private ListSelectionHandler listSelctionHandler;
	// frames
	private LoginFrame loginFrame;
	private MainFrame mainFrame;
	private BasketFrame basketFrame;
	private RegisterFrame registerFrame;
	private FindFrame findFrame;
	private ChangePW changePW;
	private LectureEvaluation lectureEvaluation;
	private FindLecture findLecture;
	// values
	private Vector<VO> isselected;
	private Vector<VO> basketSelected;
	// add values
	private VO result;

	public ClientSession() {
		// Handler
		this.windowHandler = new WindowHandler();
		this.actionHandler = new ActionHandler();
		this.keyHandler = new KeyHandler();
		this.listSelctionHandler = new ListSelectionHandler();
		// Frames
		this.loginFrame = new LoginFrame(actionHandler, keyHandler);
		this.mainFrame = new MainFrame(actionHandler, windowHandler);
		this.basketFrame = new BasketFrame(actionHandler);
		this.registerFrame = new RegisterFrame(actionHandler);
		this.findFrame = new FindFrame(actionHandler);
		this.changePW = new ChangePW(actionHandler);
		this.lectureEvaluation = new LectureEvaluation();
		// values
		this.isselected = new Vector<VO>();
		this.basketSelected = new Vector<VO>();
	}

	public void loginBtn() {	// loginBtn
		// get data from UI
		String id = this.loginFrame.idText.getText();
		char[] pass = this.loginFrame.pwText.getPassword();
		String pw = new String(pass);
		try {
			// UI
			// ��ȣȭ
			Secret secret = new Secret();
			String encryptPW = secret.encrypt(pw);
			VO vo = new VO();
			vo.setUserId(id);
			vo.setPassword(encryptPW);
			// set protocol Info
			Stub stub = new Stub();
			stub.initialize();
			stub.sendVO("CLogin", "authenticate", "YesReturn", null, vo);
			// receive data from Server
			this.result = (VO) stub.receive();
			if (result.getUserId().equals(id)) {
				JOptionPane.showMessageDialog(null, "�α��ο� �����ϼ̽��ϴ�.");
				createFile(result.getUserId());
				createFileS(result.getUserId());
				this.loginFrame.dispose();
				// UI
				this.mainFrame.setVisible(true);
				this.mainFrame.setID(result.getUserId());
				this.mainFrame.setImage();
				this.basketFrame.initiate(result.getUserId());
			} else {JOptionPane.showMessageDialog(null, "��ġ�ϴ� ������ �����ϴ�.");}
			// finalize
			stub.finalize();
		} catch (UnsupportedEncodingException | GeneralSecurityException | NullPointerException e) {JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ�� �Է����ּ���.");}
	}

	public void createFile(String ID) {
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CLogin", "createFile", null, ID, null);
		// receive data from Server
		String message = (String) stub.receive();
		System.out.println(message);
		// finalize
		stub.finalize();
	}

	public void createFileS(String ID) {
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CLogin", "createFileS", null, ID, null);
		// receive data from Server
		String message = (String) stub.receive();
		System.out.println(message);
		// finalize
		stub.finalize();
	}

	protected void show() { // lecture select
		// UI
		this.isselected = this.mainFrame.showFrame();
		if (this.isselected != null) {
			this.basketFrame.setVisible(true);
			this.basketFrame.addItems(this.isselected);

		}
	}
	protected void logout() { // logout
		// get data from UI
		Vector<VO> saveBasket = this.basketFrame.saveBasket();
		Vector<VO> saveSincheong = this.basketFrame.saveSincheong();
		// set protocol Info - delete
		Stub stub3 = new Stub();
		stub3.initialize();
		stub3.sendnull("CBasket", "deleteAll", null, this.loginFrame.idText.getText(), saveBasket);
		// receive data from Server
		String basketDeleteAll = (String) stub3.receive();
		System.out.println(basketDeleteAll);
		stub3.finalize();

		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendVector("CBasket", "add", null, this.result.getUserId(), saveBasket);
		// receive data from Server
		String basketmessage = (String) stub.receive();
		System.out.println(basketmessage);
		// finalize
		stub.finalize();

		// set protocol Info - delete 
		Stub stub4 = new Stub();
		stub4.initialize();
		stub4.sendnull("CSincheong", "deleteAll", null, this.loginFrame.idText.getText(), saveSincheong);
		// receive data from Server
		String sincheongDeleteAll = (String) stub4.receive();
		System.out.println(sincheongDeleteAll);
		stub4.finalize();

		// set protocol Info
		Stub stub2 = new Stub();
		stub2.initialize();
		stub2.sendVector("CSincheong", "add", null, this.result.getUserId(), saveSincheong);
		// receive data from Server
		String sincheongmessage = (String) stub2.receive();
		System.out.println(sincheongmessage + "��û ���̺���� ���´�.!!!!!!!!!!!!!!");
		// finalize
		stub2.finalize();

		// set protocol Info
		Stub stub5 = new Stub();
		stub5.initialize();
		stub5.send("CSincheong", "computeScore", "YesReturn", this.result.getUserId());
		// receive data from Server
		String result = (String) stub5.receive();
		stub5.finalize();
		// set protocol Info
		Stub stub6 = new Stub();
		stub6.initialize();
		stub6.send("CSincheong", "countLecture", "YesReturn", this.result.getUserId());
		//receive data from Server
		String count = (String) stub6.receive();
		stub6.finalize();
		// UI
		JOptionPane.showMessageDialog(null, "���� ���� ���� ������ " + result + " �Դϴ�." + "\n" + "���� ���� ���� ������ ���� " + count +" �� �Դϴ�.");
		this.mainFrame.dispose();
		this.basketFrame.dispose();
	}
	public void register() { 	registerFrame.setVisible(true);}

	protected void select() { //å���� -> sincheong
		basketSelected = basketFrame.showFrame();
		if (basketSelected != null) { 	basketFrame.givebasket(basketSelected);}
	}

	// å���濡�� ����
	public void cancel() { basketFrame.cancel();}

	// ��û�������� ����
	public void cancelSincheong() { basketFrame.cancelSincheong();}

	public void backToBasket() { 	// ��û�������� å�������� �ǵ�����
		basketFrame.addItems(basketFrame.backToBasket()); // basketFrame.backToBasket() : ���� �޴� selectedrow�� ��� �ִ�.
	}

	public void cancelregister() { // ȸ������ ��� ��ư
		JOptionPane.showMessageDialog(null, "ȸ�������� ����ϼ̽��ϴ�.");
		this.registerFrame.dispose();
	}

	public void store() { // ȸ������ ���� ����
		this.registerFrame.store();
		this.loginFrame = new LoginFrame(actionHandler, keyHandler);
	}

	public void showLoginFrame() { // �α���â ����
		this.registerFrame.dispose();
		this.loginFrame.setVisible(true);
	}

	public void overlap() { // Id �ߺ�Ȯ��
		// get data from UI
		String id = this.registerFrame.id.getText();
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.send("CLogin", "overlap", "YesReturn", id);
		// receive data from Server
		String getID = (String) stub.receive();
		System.out.println(getID);
		if (getID != null) { JOptionPane.showMessageDialog(null, "�̹� �����ϴ� ID�Դϴ�."); 
		} else { JOptionPane.showMessageDialog(null, "��밡���� ID�Դϴ�.");}
		// finalize
		stub.finalize();
	}

	public void findidpw() {this.findFrame.setVisible(true);}

	public void findID() { // ���̵� ã��
		String findID = this.findFrame.findID();
		if (findID != null) { JOptionPane.showMessageDialog(findFrame, "���̵� :" + findID);} 
		else { JOptionPane.showMessageDialog(findFrame, "���Ե� ������ �������� �ʽ��ϴ�.");}
	}

	public void findPW() { // ��й�ȣ ã��
		String findPW = this.findFrame.findPW();
		if (findPW != null) { JOptionPane.showMessageDialog(findFrame, "��й�ȣ : " + findPW);} 
		else { JOptionPane.showMessageDialog(findFrame, "���Ե� ������ �������� �ʽ��ϴ�."); }
	}

	public void cancelfind() { // ���̵�/��й�ȣ ã�� â �ݱ�
		JOptionPane.showMessageDialog(null, "���̵�/��й�ȣ ã�⸦ ����ϼ̽��ϴ�.");
		this.findFrame.dispose();
	}

	public void showlogin() { // ���̵�/��й�ȣ ã�⿡�� �α��� ȭ������ �̵�
		JOptionPane.showMessageDialog(null, "�α���ȭ������ �̵��մϴ�.");
		this.findFrame.dispose();
	}

	public void userinfo() { // ������������
		String[] userinfo = this.mainFrame.userInfo(this.loginFrame.idText.getText());
		JOptionPane.showMessageDialog(mainFrame, userinfo);
	}

	public void showBasket() {	this.basketFrame.setVisible(true);}

	// ���ΰ�ħ ��ư
	public void refresh() {
		this.registerFrame.refresh();
		this.findFrame.refresh();
	}

	// �۾� ũ�� ����_ȸ������â������
	public void combo() {this.registerFrame.combo();}

	// basketFrame���� �������� Ȯ��â ����
	public void user() {
		String[] userinfo = this.mainFrame.userInfo(this.loginFrame.idText.getText());
		JOptionPane.showMessageDialog(basketFrame, userinfo);
	}

	public void fontSize() {this.basketFrame.fontSize();}

	public void gotourl() {// �������б� Ȩ������ ����
		Runtime runtime = Runtime.getRuntime();
		try { runtime.exec("explorer.exe http://www.mju.ac.kr/");}
		catch (IOException e) {e.printStackTrace();} }

	public void colorChange() {this.registerFrame.colorChange();}

	public void changePW() { this.changePW.setVisible(true);}

	public void newPW() {this.changePW.newPW(this.loginFrame.idText.getText());}

	public void lectureEvaluation() {
		this.lectureEvaluation.setVisible(true); 
		this.lectureEvaluation.setID(this.result.getUserId());}

	public void findlectureBtn() {
		this.findLecture = new FindLecture(this.result.getUserId(),listSelctionHandler);
		this.findLecture.setVisible(true);
	}
	public void listLecture() {
		Vector<VO> listLectures = this.findLecture.listLecture();
		this.basketFrame.addItems(listLectures);
		System.out.println("==========================================!");
	}
	public class KeyHandler extends KeyAdapter { // ����Ű ���
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {loginBtn();}
		}
	}
	class ListSelectionHandler extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			listLecture();

		}
	}

	class WindowHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {logout();}
	}
	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("login")) {	loginBtn();}
			else if (e.getActionCommand().equals("register")) {register();}
			else if (e.getActionCommand().equals("lectureSelect")) {show();}
			else if (e.getActionCommand().equals("select")) {select();}
			else if (e.getActionCommand().equals("logout")) {logout();}
			else if (e.getActionCommand().equals("deleteBasket")) {cancel();}
			else if (e.getActionCommand().equals("cancelSincheong")) {cancelSincheong();}
			else if (e.getActionCommand().equals("backToBasket")) {backToBasket();}
			else if (e.getActionCommand().equals("signup")) {showLoginFrame();}
			else if (e.getActionCommand().equals("store")) {	store();}
			else if (e.getActionCommand().equals("cancelRegister")) {cancelregister();}
			else if (e.getActionCommand().equals("idoverlap")) {overlap();}
			else if (e.getActionCommand().equals("find")) {findidpw();}
			else if (e.getActionCommand().equals("findID")) {findID();}
			else if (e.getActionCommand().equals("findPW")) {findPW();}
			else if (e.getActionCommand().equals("cancelfind")) {cancelfind();}
			else if (e.getActionCommand().equals("find&login")) {showlogin();}
			else if (e.getActionCommand().equals("userinfo")) {userinfo();}
			else if (e.getActionCommand().equals("showBasket")) {showBasket();}
			else if (e.getActionCommand().equals("refresh")) {refresh();}
			else if (e.getActionCommand().equals("combo")) {combo();}
			else if (e.getActionCommand().equals("user")) {	user();}
			else if (e.getActionCommand().equals("fontSize")) {	fontSize();}
			else if (e.getActionCommand().equals("gotourl")) {gotourl();}
			else if (e.getActionCommand().equals("Color")) {	colorChange();}
			else if (e.getActionCommand().equals("changePW")) {changePW();}
			else if (e.getActionCommand().equals("newPW")) {newPW();} 
			else if (e.getActionCommand().equals("lectureEvaluation")) {lectureEvaluation();}
			else if(e.getActionCommand().equals("findlectureBtn")) {findlectureBtn();}
		}
	}
}
