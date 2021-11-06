package ui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import Client.Stub;
import Server.VO;
import sql_test2.Lecture;

public class FindLecture extends JFrame{
	private static final long serialVersionUID = 1L;
	private Font font;
	private JTextField input;
	private JList<Lecture> area;
	private JButton btn;
	private String id;
	private JLabel info;
	private JScrollPane sc;
	private DefaultListModel<Lecture> listModel;

	public FindLecture(String id, MouseListener listSelctionHandler) {
		this.setLayout(new FlowLayout());
		this.id = id;

		// setting
		this.setTitle("마이페이지");
		this.setSize(500, 600);
		this.setFont(font);
		this.setLocationRelativeTo(null);
		// imageIcon
		Toolkit toolkit = this.getToolkit();
		Image img = toolkit.createImage("image/mju_icon.jpg");
		this.setIconImage(img);
		// Font
		this.font = new Font("고딕", Font.PLAIN, 15);

		//panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		// label 
		info = new JLabel("찾으시는 강좌를 입력해주세요.");
		info.setFont(font);
		panel.add(info);
		// textField
		input = new JTextField(30);
		input.setFont(font);
		panel.add(input);
		// list
		listModel = new DefaultListModel<>();
		area = new JList<>(listModel);
		area.setFont(font);
		area.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		area.addMouseListener(listSelctionHandler);
		//scrollPane
		sc = new JScrollPane(area);
		panel.add(sc);
		// button
		btn = new JButton("검색");
		btn.setFont(font);
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e1) {
				try {
					Stub stub = new Stub();
					stub.initialize();
					stub.send("CLecture", "getLectures", "YesReturn", id);
					listModel.removeAllElements();
					//receive data from Server
					for(Lecture lecture : (Vector<Lecture>) stub.receive()) {
						if (lecture.getName().contains(input.getText())) {
							listModel.addElement(lecture);
						}
					}
				}catch(StringIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(btn);
		this.add(panel);
	}
	public Vector<VO> listLecture(){
		Vector<VO> listLecture = new Vector<>();
		for (int i:area.getSelectedIndices()) {
			Lecture lecture = listModel.get(i);
			VO vo = new VO();
			vo.setNumber(Integer.toString(lecture.getNumber()));
			vo.setName(lecture.getName());
			vo.setProfessorName(lecture.getProfessor());
			vo.setCredit(Integer.toString(lecture.getCredit()));
			vo.setTime(lecture.getTime());
			listLecture.add(vo);
		}
		return listLecture;
	}
}
