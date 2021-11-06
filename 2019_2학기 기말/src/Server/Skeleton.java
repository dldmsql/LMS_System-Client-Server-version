package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Vector;

import sql_test2.Lecture;

public class Skeleton extends Thread {
	// socket Info
	private Socket clientSocket;
	// streams
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public Skeleton(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			this.ois = new ObjectInputStream(this.clientSocket.getInputStream());
			this.oos = new ObjectOutputStream(this.clientSocket.getOutputStream());
		} catch (IOException e) {e.printStackTrace();}
	}

	public void initialize() {}
	@SuppressWarnings("unchecked")
	public void run() {
		System.out.println("프로토콜 받을 준비 됐어");
		try {
			Vector<String> receive = (Vector<String>) this.ois.readObject();
			receivepro(receive);
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();}
	}

	public void finalize() {
		try { // finalize
			this.ois.close();
			this.oos.close();
			this.clientSocket.close();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void receivepro(Vector<String> receive) {
		System.out.println(receive.toString());
		System.out.println("클라이언트로부터 프로토콜 수신");
		try { // receive protocol Info
			String className = receive.get(0);
			String methodName = receive.get(1);
			String isReturn = receive.get(2);
			String param = receive.get(3);
			System.out.println(className + "/" + methodName + "/" + isReturn + "/" + param);
			if (isReturn == null) {
				if (param != null) {
					if (methodName.equals("add")) { // logout
						logoutR(className, methodName, param);
					} else if(methodName.equals("deleteAll")) {
						deleteR(className,methodName, param);
					} else if(methodName.equals("lectureEvaluation")) {
						lectureEvaluation(className, methodName, param);}
					else { // createFile && createFileS
						createFileR(className, methodName);
					}
				} else { // param == null
					if (className == null) {
						this.finalize();
					}else {
						registerR(className, methodName);
					}
				}
			} else { // isReturn != null
				if (param == null) {
					if(methodName.equals("findID")) {
						findIDR(className, methodName);
					} else if (methodName.equals("newPW")) {
						newPWR(className, methodName);
					}else if(methodName.equals("getEvaluation")) {
						getEvaluation(className, methodName);
					} else { // login
						loginR(className, methodName); }
				} else { // param != null
					 if (methodName.equals("overlap")) {
						overlapR(className, methodName, param);} 
					else if(methodName.equals("userInfo")) {userInfoR(className, methodName, param);}
					else if(methodName.equals("findPW")) {findPWR(className,methodName);}
					else if(methodName.equals("computeScore")) {computeR(className, methodName);}
					else if(methodName.equals("countLecture")) {System.out.println("-----------------");computeR(className, methodName);}
					else if(methodName.equals("getLectures")) {getLectures(className, methodName);}
					else if(methodName.equals("getImages")) {getImages(className, methodName);}
					else { // directory && table
						tableR(className, methodName, param);
					}
				}
			}
			System.out.println("data 전송 완료");
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (Throwable e) {e.printStackTrace();		}
	}

	private void getImages(String className, String methodName) {
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class};
			Object[] methodParamObject = new Object[] {  clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String getLectures = null;
			sendpro(getLectures);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void getLectures(String className, String methodName) {
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class};
			Object[] methodParamObject = new Object[] {  clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			Vector<Lecture> result = (Vector<Lecture>) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String getLectures = "getLectures";
			sendpro(getLectures);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void computeR(String className, String methodName) {
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] {  String.class};
			Object[] methodParamObject = new Object[] {  clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			int result = (int) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String computeScore = null;
			sendpro(computeScore);
			// send data to Client
			this.oos.writeObject(Integer.toString(result));
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void getEvaluation(String className, String methodName) {
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			String clientData2 = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class , String.class};
			Object[] methodParamObject = new Object[] {  clientData, clientData2 };
			Method method = service.getMethod(methodName, methodParamClass);
			Vector<Lecture> result = (Vector<Lecture>) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String getEvaluation = "getEvaluation";
			sendpro(getEvaluation);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void lectureEvaluation(String className, String methodName, String param) { // lectureEvaluation
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			String clientData2 = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class, String.class , String.class};
			Object[] methodParamObject = new Object[] { param, clientData, clientData2 };
			Method method = service.getMethod(methodName, methodParamClass);
			method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String lectureEvaluation = null;
			sendpro(lectureEvaluation);
			// send data to Client
			this.oos.writeObject("lectureEvaluation done!");
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void findIDR(String className, String methodName) { // findID
		try { // receive data from Client
			Vector<String> clientData = (Vector<String>) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { Vector.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String findID = null;
			sendpro(findID);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void findPWR(String className, String methodName) { // findPW
		try {// receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] {String.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);
			System.out.println("skeleton : " + result);
			// send paramType to Client
			String userInfo = null;
			sendpro(userInfo);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void userInfoR(String className, String methodName, String param) { //userInfo
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] {String.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result[] = (String[]) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String userInfo = "userInfo";
			sendpro(userInfo);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	private void deleteR(String className, String methodName, String param) { // delete basket, sincheong
		try { // receive data from Client
			Vector<VO> clientData = (Vector<VO>) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class, Vector.class };
			Object[] methodParamObject = new Object[] { param, clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String deleteAll = null;
			sendpro(deleteAll);
			// send data to Client
			this.oos.writeObject("delete done");
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void newPWR(String className, String methodName) { // newPW
		// reflection
		try {
			// receive data from Client
			Vector<String> clientData = (Vector<String>) this.ois.readObject(); // String ID , param pw
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { Vector.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);
			System.out.println("디비에서 바꾼 비밀번호 : "+ result);
			// send paramType to Client
			String saveId = null;
			sendpro(saveId);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void overlapR(String className, String methodName, String param) {
		System.out.println(className + methodName);
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String register = null;
			sendpro(register);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void registerR(String className, String methodName) { // store 
		try { // receive data from Client
			Vector<String> clientData = (Vector<String>) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { Vector.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			String result = (String) method.invoke(newObj, methodParamObject);

			// send paramType to Client
			String register = null;
			sendpro(register);
			// send data to Client
			this.oos.writeObject(result);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	// basketTable && sincheongTable && lectureTable
	public void tableR(String className, String methodName, String param) {
		System.out.println(className + methodName + "reflection");
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			Vector<VO> resultObj = (Vector<VO>) method.invoke(newObj, methodParamObject);
			// send paramType to Client
			String message;
			if (className.equals("CBasket")) {
				message = "basketVector";
				sendpro(message);
			} else if (className.equals("CDirectory")) {
				message = "directoryVector";
				sendpro(message);
			} else if (className.equals("CLecture")) {
				message = "lectureVector";
				sendpro(message);
			} else if (className.equals("CSincheong")) {
				message = "sincheongVector";
				sendpro(message);
			}
			// send data to Client
			this.oos.writeObject(resultObj);
			System.out.println(resultObj + " table관련");
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}


	public void logoutR(String className, String methodName, String param) { // logout
		System.out.println(className + methodName + "reflection");
		Vector<VO> vo;
		try { // receive data from Client
			vo = (Vector<VO>) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { Vector.class, String.class };
			Object[] methodParamObject = new Object[] { vo, param };
			Method method = service.getMethod(methodName, methodParamClass);
			method.invoke(newObj, methodParamObject);
			// send paramType to Client
			String message = null;
			sendpro(message);
			// send data to Client
			this.oos.writeObject("logout done");
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void loginR(String className, String methodName) {// loginBtn
		System.out.println(className + methodName + "reflection");
		VO vo;
		try { // receive data from Client
			vo = (VO) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { VO.class };
			Object[] methodParamObject = new Object[] { vo };
			Method method = service.getMethod(methodName, methodParamClass);
			VO resultObj = (VO) method.invoke(newObj, methodParamObject);
			// send paramType to Client
			String paramType = "VO";
			sendpro(paramType);
			// send data to Client
			this.oos.writeObject(resultObj);
			this.oos.flush();
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();		}
	}

	public void createFileR(String className, String methodName) { // createFile
		System.out.println(className + methodName + "reflection");
		try { // receive data from Client
			String clientData = (String) this.ois.readObject();
			Class service = Class.forName("controller." + className);
			Object newObj = service.newInstance();
			Class[] methodParamClass = new Class[] { String.class };
			Object[] methodParamObject = new Object[] { clientData };
			Method method = service.getMethod(methodName, methodParamClass);
			method.invoke(newObj, methodParamObject);
			// send paramType to Client
			String message = null;
			sendpro(message);
			// send data to Client
			this.oos.writeObject("createFile done");
		} catch (ClassNotFoundException | IOException e) {e.printStackTrace();
		} catch (InstantiationException e) {e.printStackTrace();
		} catch (IllegalAccessException e) {e.printStackTrace();
		} catch (NoSuchMethodException e) {		e.printStackTrace();
		} catch (SecurityException e) {e.printStackTrace();
		} catch (IllegalArgumentException e) {e.printStackTrace();
		} catch (InvocationTargetException e) {e.printStackTrace();}
	}

	public void sendpro(String param) {
		try { // send protocol Info
			this.oos.writeObject(param);
			System.out.println(param + " 클라이언트한테 보내는 paramType");
			this.oos.flush();
			System.out.println("클라이언트한테 프로토콜 전송");
		} catch (IOException e) { e.printStackTrace();	}
	}
}
