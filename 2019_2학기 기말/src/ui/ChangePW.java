package ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import Client.Stub;

public class ChangePW extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel pw;
	private JPasswordField pwText;
	private JLabel pw2;
	private JPasswordField pwText2;
	private JButton changeButton;
	private Font font;
	private JPanel mainPanel;
	private JLabel name;
	private JTextField nameT;
	private JLabel major;
	private JTextField majorT;
	private JLabel grade;
	private JTextField gradeT;

	public ChangePW(ActionListener actionHandler) {
		//setting
		this.setTitle("개인정보 변경");
		this.setFont(font);
		this.setSize(500,450);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		//font
		font = new Font("고딕", Font.PLAIN, 15);

		//imageIcon 
		Toolkit toolkit = this.getToolkit();
		Image img = toolkit.createImage("image/mju_icon.jpg");
		this.setIconImage(img);

		//Main Panel
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.white);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		//empty Panel
		JPanel ePanel = new JPanel();
		ePanel.setBackground(Color.white);
		ePanel.setLayout(new BoxLayout(ePanel, BoxLayout.Y_AXIS));
		// name Panel
		JPanel nPanel = new JPanel();
		nPanel.setBackground(Color.white);
		this.name = new JLabel("이름");
		nPanel.add(name);
		this.nameT = new JTextField(10);
		nPanel.add(nameT);
		ePanel.add(nPanel);
		// major Panel
		JPanel mPanel = new JPanel();
		mPanel.setBackground(Color.white);
		this.major = new JLabel("학과/학부");
		mPanel.add(major);
		this.majorT = new JTextField(10);
		mPanel.add(majorT);
		ePanel.add(mPanel);
		// grade Panel
		JPanel gPanel = new JPanel();
		gPanel.setBackground(Color.white);
		this.grade = new JLabel("학년");
		gPanel.add(grade);
		this.gradeT = new JTextField(10);
		gPanel.add(gradeT);
		ePanel.add(gPanel);
		mainPanel.add(ePanel);
		//pw Panel
		JPanel pwPanel = new JPanel();
		pwPanel.setLayout(new FlowLayout());
		pwPanel.setBackground(Color.white);
		//pwLabel 
		pw = new JLabel("새 비밀번호");
		pw.setFont(font);
		pw.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length() >= 10) {
					JOptionPane.showMessageDialog(null, "비밀번호가 10자리를 넘었습니다.");
				} 
			}
		});
		pwPanel.add(pw);
		pwText = new JPasswordField(10);
		pwText.setToolTipText("비밀번호는 영문자와 숫자를 합쳐서 10자리 이내로 설정해주세요.");
		pwPanel.add(pwText);
		mainPanel.add(pwPanel);

		//pw2 Panel
		JPanel pw2Panel = new JPanel();
		pw2Panel.setLayout(new FlowLayout());
		pw2Panel.setBackground(Color.white);
		//pw2 Label
		pw2 = new JLabel("비밀번호 확인");
		pw2.setFont(font);
		pw2.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField)ke.getSource();
				if(src.getText().length() >= 10) {
					JOptionPane.showMessageDialog(null, "비밀번호가 10자리를 넘었습니다.");
				} 
			}
		});
		pw2Panel.add(pw2);
		pwText2 = new JPasswordField(10);
		pwText2.setToolTipText("비밀번호는 영문자와 숫자를 합쳐서 10자리 이내로 설정해주세요.");
		pwText2.registerKeyboardAction(actionHandler,"newPW",KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),JComponent.WHEN_FOCUSED);
		pw2Panel.add(pwText2);
		mainPanel.add(pw2Panel);

		//button Panel
		JPanel btPanel = new JPanel();
		btPanel.setBackground(Color.white);
		changeButton = new JButton("비밀번호 변경");
		changeButton.setFont(font);
		changeButton.setToolTipText("비밀번호 변경하기");
		changeButton.setBackground(new Color(0X55CCCCFF));
		changeButton.addActionListener(actionHandler);
		changeButton.setActionCommand("newPW");
		btPanel.add(changeButton);
		mainPanel.add(btPanel);

		this.add(mainPanel);
	}

	public void newPW(String ID) {
		if(pwText.getText().equals(pwText2.getText())) {
			// get data from UI
			Vector<String> change = new Vector<String>();
			change.add(ID);
			change.add(new String(this.pwText2.getText()));
			change.add(nameT.getText());
			change.add(majorT.getText());
			change.add(gradeT.getText());
			//set protocol Info
			Stub stub = new Stub();
			stub.initialize();
			stub.sendStringV("CLogin", "newPW", "YesReturn", null, change);
			// receive data from Server
			String message = (String) stub.receive();
			System.out.println("서버한테 받은 변경된 비밀번호 : " + message);
			if(message.equals(pwText.getText())) {
				JOptionPane.showMessageDialog(null, "비밀번호를 성공적으로 바꾸셨습니다.");
				this.dispose();
				stub.finalize();
			}
		}else {
			JOptionPane.showMessageDialog(null, "비밀번호를 알맞게 입력하였는지 다시 확인해주세요.");
		}
	}
}
