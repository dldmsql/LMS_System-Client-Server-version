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
			// 암호화
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
				JOptionPane.showMessageDialog(null, "로그인에 성공하셨습니다.");
				createFile(result.getUserId());
				createFileS(result.getUserId());
				this.loginFrame.dispose();
				// UI
				this.mainFrame.setVisible(true);
				this.mainFrame.setID(result.getUserId());
				this.mainFrame.setImage();
				this.basketFrame.initiate(result.getUserId());
			} else {JOptionPane.showMessageDialog(null, "일치하는 정보가 없습니다.");}
			// finalize
			stub.finalize();
		} catch (UnsupportedEncodingException | GeneralSecurityException | NullPointerException e) {JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 입력해주세요.");}
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
		System.out.println(sincheongmessage + "신청 테이블까지 보냈댜.!!!!!!!!!!!!!!");
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
		JOptionPane.showMessageDialog(null, "현재 최종 수강 학점은 " + result + " 입니다." + "\n" + "현재 최종 수강 강좌의 수는 " + count +" 개 입니다.");
		this.mainFrame.dispose();
		this.basketFrame.dispose();
	}
	public void register() { 	registerFrame.setVisible(true);}

	protected void select() { //책가방 -> sincheong
		basketSelected = basketFrame.showFrame();
		if (basketSelected != null) { 	basketFrame.givebasket(basketSelected);}
	}

	// 책가방에서 삭제
	public void cancel() { basketFrame.cancel();}

	// 신청내역에서 삭제
	public void cancelSincheong() { basketFrame.cancelSincheong();}

	public void backToBasket() { 	// 신청내역에서 책가방으로 되돌리기
		basketFrame.addItems(basketFrame.backToBasket()); // basketFrame.backToBasket() : 리턴 받는 selectedrow가 담겨 있다.
	}

	public void cancelregister() { // 회원가입 취소 버튼
		JOptionPane.showMessageDialog(null, "회원가입을 취소하셨습니다.");
		this.registerFrame.dispose();
	}

	public void store() { // 회원가입 내용 저장
		this.registerFrame.store();
		this.loginFrame = new LoginFrame(actionHandler, keyHandler);
	}

	public void showLoginFrame() { // 로그인창 띄우기
		this.registerFrame.dispose();
		this.loginFrame.setVisible(true);
	}

	public void overlap() { // Id 중복확인
		// get data from UI
		String id = this.registerFrame.id.getText();
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.send("CLogin", "overlap", "YesReturn", id);
		// receive data from Server
		String getID = (String) stub.receive();
		System.out.println(getID);
		if (getID != null) { JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다."); 
		} else { JOptionPane.showMessageDialog(null, "사용가능한 ID입니다.");}
		// finalize
		stub.finalize();
	}

	public void findidpw() {this.findFrame.setVisible(true);}

	public void findID() { // 아이디 찾기
		String findID = this.findFrame.findID();
		if (findID != null) { JOptionPane.showMessageDialog(findFrame, "아이디 :" + findID);} 
		else { JOptionPane.showMessageDialog(findFrame, "가입된 정보가 존재하지 않습니다.");}
	}

	public void findPW() { // 비밀번호 찾기
		String findPW = this.findFrame.findPW();
		if (findPW != null) { JOptionPane.showMessageDialog(findFrame, "비밀번호 : " + findPW);} 
		else { JOptionPane.showMessageDialog(findFrame, "가입된 정보가 존재하지 않습니다."); }
	}

	public void cancelfind() { // 아이디/비밀번호 찾기 창 닫기
		JOptionPane.showMessageDialog(null, "아이디/비밀번호 찾기를 취소하셨습니다.");
		this.findFrame.dispose();
	}

	public void showlogin() { // 아이디/비밀번호 찾기에서 로그인 화면으로 이동
		JOptionPane.showMessageDialog(null, "로그인화면으로 이동합니다.");
		this.findFrame.dispose();
	}

	public void userinfo() { // 개인정보열람
		String[] userinfo = this.mainFrame.userInfo(this.loginFrame.idText.getText());
		JOptionPane.showMessageDialog(mainFrame, userinfo);
	}

	public void showBasket() {	this.basketFrame.setVisible(true);}

	// 새로고침 버튼
	public void refresh() {
		this.registerFrame.refresh();
		this.findFrame.refresh();
	}

	// 글씨 크기 조절_회원가입창에서만
	public void combo() {this.registerFrame.combo();}

	// basketFrame에서 개인정보 확인창 띄우기
	public void user() {
		String[] userinfo = this.mainFrame.userInfo(this.loginFrame.idText.getText());
		JOptionPane.showMessageDialog(basketFrame, userinfo);
	}

	public void fontSize() {this.basketFrame.fontSize();}

	public void gotourl() {// 명지대학교 홈페이지 연결
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
	public class KeyHandler extends KeyAdapter { // 엔터키 기능
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
