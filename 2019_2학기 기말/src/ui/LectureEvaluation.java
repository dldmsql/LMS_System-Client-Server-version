package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Client.Stub;

public class LectureEvaluation extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton finishButton;
	private JPanel mainPanel;
	private Font font;
	private HintTextField hint;
	private String id;
	private ActionHandler actionHandler;
	private JTextField lecture;
	private JTextField professorName;
	private JButton list;
	private JPanel right;
	private JPanel middle;
	private JPanel left;
	private JButton back;
	private LectureEvaluationTable table;
	private JScrollPane scrollPane;
	private CardLayout cards = new CardLayout(10,10);
	public LectureEvaluation() {
		this.actionHandler = new ActionHandler();
		//setting
		this.setTitle("강의평가");
		this.setSize(600, 400);
		this.setFont(font);
		this.setLocationRelativeTo(null);
		this.font = new Font("고딕", Font.PLAIN, 13);
		this.setBackground(Color.white);
		//imageIcon
		Toolkit toolkit = this.getToolkit();
		Image img = toolkit.createImage("image/mju_icon.jpg");
		this.setIconImage(img);
		//mainPanel
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		this.setBackground(Color.white);
		//sub Panel
		middle = new JPanel();
		middle.setBackground(Color.white);
		middle.setLayout(cards);
		//left Panel
		left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setBackground(Color.white);
		this.lecture =  new HintTextField(" write to lecture name");
		left.add(lecture);
		this.professorName = new HintTextField(" write to professor name");;
		left.add(professorName);
		//textField
		this.hint = new HintTextField(" write to your thinking about lecture or professor");
		left.add(hint);
		middle.add(left, "left");
		//right Panel
		right = new JPanel();
		right.setBackground(Color.white);
		this.scrollPane = new JScrollPane();
		this.scrollPane.getViewport().setBackground(Color.white);
		this.scrollPane.setBorder(BorderFactory.createEmptyBorder());
		right.add(scrollPane);
		middle.add(right,"right");
		this.mainPanel.add(middle);
		// btn Panel
		JPanel btn = new JPanel();
		btn.setLayout(new FlowLayout());
		btn.setBackground(Color.white);
		//button
		this.finishButton = new JButton("저장");
		this.finishButton.setFont(font);
		this.finishButton.setBackground(new Color(0X55CCCCFF));
		this.finishButton.addActionListener(this.actionHandler);
		this.finishButton.setActionCommand("finishButton");
		btn.add(finishButton);
		this.list = new JButton("강의평가내역");
		this.list.setFont(font);
		this.list.setBackground(new Color(0X55CCCCFF));
		this.list.addActionListener(actionHandler);
		this.list.setActionCommand("lectureList");
		this.list.setToolTipText("강의평가내역확인");
		btn.add(list);
		this.back = new JButton("돌아가기");
		this.back.setFont(font);
		this.back.setBackground(new Color(0X55CCCCFF));
		this.back.addActionListener(actionHandler);
		this.back.setActionCommand("back");
		btn.add(back);
		this.mainPanel.add(btn);
		this.add(this.mainPanel);

	}
	public void setID (String id) { this.id = id; }
	public void close() {this.dispose();}
	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("finishButton")) {
				//set protocol Info
				Stub stub = new Stub();
				stub.initialize();
				stub.sendLectureE("CLecture", "lectureEvaluation", null, id, hint.getText(), lecture.getText());
				// receive data from Server
				String lectureEvaluations = (String) stub.receive();
				System.out.println(lectureEvaluations);
				stub.finalize();
				close();
			} else if(e.getActionCommand().equals("lectureList")) {
				table = new LectureEvaluationTable(id, lecture.getText());
				scrollPane.setViewportView(table);
				cards.show(middle, "right");
			} else if(e.getActionCommand().equals("back")) {
				cards.show(middle, "left");
			}
		}
	}
}
