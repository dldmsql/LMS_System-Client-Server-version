package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JOptionPane;

import Server.Constant;
import Server.VO;
import sql_test2.Lecture;

public class Stub {
	// socket Info
	private String IP;
	private int portNumber;
	public Socket socket;
	// streams
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Stub() { // socket Info
		this.IP = Constant.IP;
		this.portNumber = Constant.PORTNUMBER;
	}

	public void initialize() {
		try {
			this.socket = new Socket(this.IP, this.portNumber);
			// streams
			this.oos = new ObjectOutputStream(this.socket.getOutputStream());
			this.ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) { e.printStackTrace();}
	}

	public void finalize() {
		try { // close
			this.ois.close();
			this.oos.close();
			this.socket.close();
		} catch (IOException e) { e.printStackTrace();}
	}

	public void sendVO(String className, String methodNamd, String isReturn, String parameter, VO vo) { // login
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		try { // send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 login 프로토콜 전송완료");
			// send data to Server
			this.oos.writeObject(vo);
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendVector(String className, String methodNamd, String isReturn, String parameter, Vector<VO> vector) { // logout
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		try {	// send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 logout 프로토콜 전송완료");
			// send data to Server
			this.oos.writeObject(vector);
			System.out.println(vector + "로그아웃중");
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	// VO & Vector<VO> => null //cbasket.getItems(); && createFile
	public void sendParam(String className, String methodNamd, String isReturn, String parameter, Vector<VO> vector) {
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		try {// send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 프로토콜 전송완료");
			// send data to Server
			this.oos.writeObject(parameter);
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public Object receive() {
		System.out.println("서버한테 data 받았어");
		// receive protocol from Server
		String protocol;
		try {
			protocol = (String) this.ois.readObject();
			// receive data from Server
			System.out.println("서버한테 받은 프로토콜 : "+ protocol);
			if (protocol == null) {
				String message;
				return message = (String) this.ois.readObject();
			} else {
				if (protocol.equals("directoryVector")) {
					Vector<VO> directoryVector = new Vector<VO>();
					return directoryVector = (Vector<VO>) this.ois.readObject();
				} else if (protocol.equals("lectureVector")) {
					Vector<VO> lectureVector = new Vector<VO>();
					return lectureVector = (Vector<VO>) this.ois.readObject();
				} else if (protocol.equals("basketVector")) {
					Vector<VO> basketVector = new Vector<VO>();
					return basketVector = (Vector<VO>) this.ois.readObject();
				} else if (protocol.equals("VO")) { // loginBtn
					VO vo = new VO();
					return vo = (VO) this.ois.readObject();
				} else if (protocol.equals("sincheongVector")) {
					Vector<VO> sincheongVector = new Vector<VO>();
					return sincheongVector = (Vector<VO>) this.ois.readObject();
				} else if (protocol.equals("saveId")) {
					Vector<String> saveId = new Vector<String>();
					return saveId = (Vector<String>) this.ois.readObject();
				} else if(protocol.equals("userInfo")) {
					String userInfo[];
					return userInfo = (String[]) this.ois.readObject();
				} else if(protocol.equals("getEvaluation")) {
					Vector<Lecture> getEvaluation = new Vector<Lecture>();
					return getEvaluation = (Vector) this.ois.readObject();
				} else if(protocol.equals("semaphore")) {
					int a;
					return a = (int) this.ois.readObject();
				} else if(protocol.equals("getLectures")) {
					Vector<Lecture> getLectures = new Vector<Lecture>();
					return getLectures = (Vector<Lecture>) this.ois.readObject();
				}
			}
		} catch (IOException | ClassNotFoundException e1) {e1.printStackTrace();} 
		return null;
	}

	public void sendStringV(String className, String methodNamd, String isReturn, String parameter,
			Vector<String> register) { // store / findID
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		// send protocol to Server
		try {
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 store/ findID/ newPW 프로토콜 전송완료");
			// send data to Server
			this.oos.writeObject(register);
			System.out.println(register + "회원가입중 / 아이디찾는중/ 비밀번호바꾸는중");
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void send(String className, String methodNamd, String isReturn, String parameter) { // overlap
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		try { // send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 overlap // userInfo // findPW // getImages // compute프로토콜 전송완료");
		} catch (IOException e) { e.printStackTrace();}
		try { // send data to Server
			this.oos.writeObject(parameter);
			System.out.println("overlap // userInfo // findPW // getImages ");
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendfinalize(String className, String methodNamd, String isReturn, String parameter) { // finalize
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodNamd);
		send.add(isReturn);
		send.add(parameter);
		try { 	// send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 quit보내자");
			// send data to Server
			this.oos.writeObject("quit");
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendSaveId(String className, String methodName, String isReturn, String parameter, VO vo) { // saveid
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodName);
		send.add(isReturn);
		send.add(parameter);
		try {// send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 saveid 보내자");
			// send data to Server
			this.oos.writeObject(vo);
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendnull(String className, String methodName, String isReturn, String parameter, Vector<VO> saveBasket) {
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodName);
		send.add(isReturn);
		send.add(parameter);
		try {// send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 delete 보내자");
			// send data to Server
			this.oos.writeObject(saveBasket);
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendLectureE(String className, String methodName, String isReturn , String parameter, String param2, String param3) {
		// set protocol Info
		Vector<String> send = new Vector<String>();
		send.add(className);
		send.add(methodName);
		send.add(isReturn);
		send.add(parameter);
		try { // send protocol to Server
			this.oos.writeObject(send);
			this.oos.flush();
			System.out.println("서버한테 lectureEvaluation 보내자");
			// send data to Server
			this.oos.writeObject(param2);
			this.oos.writeObject(param3);
			this.oos.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}

	public void sendSema(String message) {
		Vector<String> messages = new Vector<String>();
		messages.add(message);
		messages.add(null);
		messages.add(null);
		messages.add(null);
		try { // send protocol to Server
			this.oos.writeObject(messages);
			this.oos.flush();
			System.out.println("서버한테 : " + message + "보내쟈");
		} catch (IOException e) { 
			JOptionPane.showMessageDialog(null, "서버와의 접속이 끊어졌습니다.");
			System.exit(0);}
	}
}
