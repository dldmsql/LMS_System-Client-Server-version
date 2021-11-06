package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Server.VO;

@SuppressWarnings("serial")
public class SelectionPanel extends JPanel {
	
	private DirectoryPanel departmentSelection;
	private LectureTable lectureSelection;
	private ListSelectionHandler listHandler;
	private JButton button;
	private JButton logoutbutton;
	private JButton userinfo;
	private JButton basket;
	private JButton urlbutton;
	private Font font;
	private JLabel label;
	private JLabel tableLabel;
	private ImageIcon image;
	private JLabel imageLabel;
	private ImageIcon userImage;
	private JLabel uILabel;
	private JButton changePW;
	private JButton lectureEvaluation;
	private JButton findlectureBtn;
	private String imageID;
	public SelectionPanel(ActionListener actionHandler, String id) {
		// layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// event
		this.listHandler = new ListSelectionHandler();
		// font
		font = new Font("���", Font.BOLD, 15);
		// ��ܹ� Panel
		JPanel upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());
		upPanel.setBackground(new Color(0X55CCCCFF));
		// image
		this.image = new ImageIcon("image/mju_2.png");
		this.imageLabel = new JLabel(image, JLabel.CENTER);
		upPanel.add(imageLabel, BorderLayout.LINE_START);
		// collge Label
		JLabel college = new JLabel("�������б�");
		college.setFont(new Font("���", Font.BOLD, 45));
		college.setForeground(Color.white);
		upPanel.add(college, BorderLayout.CENTER);
		this.add(upPanel);
		// sub Panel
		JScrollPane scrollPane = new JScrollPane();
		JPanel subP = new JPanel();
		scrollPane.setViewportView(subP);
		subP.setLayout(new BorderLayout());
		subP.setBackground(Color.white);
		this.add(scrollPane);
		// Left Panel
		JPanel leftP = new JPanel();
		leftP.setLayout(new BoxLayout(leftP, BoxLayout.Y_AXIS));
		leftP.setBackground(Color.white);
		subP.add(leftP, BorderLayout.WEST);
		// userImage
		userImage = new ImageIcon("image/user.jpg");
		uILabel = new JLabel(userImage, JLabel.CENTER);
		leftP.add(uILabel);
		// ���̵�Label
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(font);
		leftP.add(label);
		// ��������Ȯ�� button
		userinfo = new JButton("��������Ȯ��");
		userinfo.setFont(font);
		userinfo.setOpaque(false);
		userinfo.setBorderPainted(false);
		userinfo.setBackground(new Color(0X55CCCCFF));
		userinfo.setToolTipText("��������Ȯ���ϱ�");
		userinfo.addActionListener(actionHandler);
		userinfo.setActionCommand("userinfo");
		leftP.add(userinfo);
		// basket button
		basket = new JButton("å����");
		basket.setFont(font);
		basket.setOpaque(false);
		basket.setBorderPainted(false);
		basket.setBackground(new Color(0X55CCCCFF));
		basket.setToolTipText("å���� ���� �� ��û ���� Ȯ���Ϸ� ����");
		basket.addActionListener(actionHandler);
		basket.setActionCommand("showBasket");
		leftP.add(basket);
		// Ȩ������ ����
		urlbutton = new JButton("�ٷΰ���");
		urlbutton.setFont(font);
		urlbutton.setOpaque(false);
		urlbutton.setBorderPainted(false);
		urlbutton.setBackground(Color.white);
		urlbutton.setToolTipText("������ Ȩ������ �ٷΰ���");
		urlbutton.addActionListener(actionHandler);
		urlbutton.setActionCommand("gotourl");
		leftP.add(urlbutton);
		// logout button
		logoutbutton = new JButton("�α׾ƿ�");
		logoutbutton.setFont(font);
		logoutbutton.setOpaque(false);
		logoutbutton.setBorderPainted(false);
		logoutbutton.setBackground(new Color(0X55CCCCFF));
		logoutbutton.setToolTipText("�α׾ƿ�");
		logoutbutton.addActionListener(actionHandler);
		logoutbutton.setActionCommand("logout");
		leftP.add(logoutbutton, BorderLayout.EAST);
		// ��й�ȣ ����
		changePW = new JButton();
		changePW.setFont(font);
		changePW.setText("��й�ȣ ����");
		changePW.setOpaque(false);
		changePW.setBorderPainted(false);
		changePW.setBackground(Color.white);
		changePW.setToolTipText("��й�ȣ �����ϱ�");
		changePW.addActionListener(actionHandler);
		changePW.setActionCommand("changePW");
		leftP.add(changePW);
		// ������
		lectureEvaluation = new JButton("������");
		lectureEvaluation.setFont(font);
		lectureEvaluation.setOpaque(false);
		lectureEvaluation.setBorderPainted(false);
		lectureEvaluation.setBackground(Color.white);
		lectureEvaluation.setToolTipText("�������Ϸ�����");
		lectureEvaluation.addActionListener(actionHandler);
		lectureEvaluation.setActionCommand("lectureEvaluation");
		leftP.add(lectureEvaluation);
		//���� ã��
		findlectureBtn = new JButton("����ã��");
		findlectureBtn.addActionListener(actionHandler);
		findlectureBtn.setActionCommand("findlectureBtn");
		findlectureBtn.setOpaque(false);
		findlectureBtn.setBorderPainted(false);
		findlectureBtn.setBackground(Color.white);
		leftP.add(findlectureBtn);
		// right Panel
		JPanel rightP = new JPanel();
		rightP.setBackground(Color.white);
		rightP.setLayout(new BoxLayout(rightP, BoxLayout.Y_AXIS));
		subP.add(rightP);
		// Label Panel
		JPanel labelPanel = new JPanel();
		labelPanel.setBackground(Color.white);
		labelPanel.setLayout(new FlowLayout());
		rightP.add(labelPanel);
		// deprtmentSelection Label
		JLabel dpLabel = new JLabel();
		dpLabel.setText("Campus        ");
		dpLabel.setFont(new Font("���", Font.BOLD, 16));
		labelPanel.add(dpLabel);
		JLabel dpLabel2 = new JLabel();
		dpLabel2.setText("College");
		dpLabel2.setFont(new Font("���", Font.BOLD, 16));
		labelPanel.add(dpLabel2);
		JLabel dpLabel3 = new JLabel();
		dpLabel3.setText("        Department");
		dpLabel3.setFont(new Font("���", Font.BOLD, 16));
		labelPanel.add(dpLabel3);
		// departmentSelection Panel
		this.departmentSelection = new DirectoryPanel(listHandler);
		rightP.add(this.departmentSelection);
		// Tabel Label
		tableLabel = new JLabel();
		tableLabel.setText("�������� Table");
		tableLabel.setFont(new Font("���", Font.BOLD, 18));
		rightP.add(tableLabel);
		// lectureTable
		scrollPane = new JScrollPane();
		this.lectureSelection = new LectureTable();
		scrollPane.setPreferredSize(new Dimension(400, 200));
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(this.lectureSelection);
		rightP.add(scrollPane);
		// Button_select
		button = new JButton("����");
		button.setFont(font);
		button.setBackground(new Color(0X55CCCCFF));
		button.setToolTipText("å���濡 ���");
		button.addActionListener(actionHandler);
		button.setActionCommand("lectureSelect");
		rightP.add(button);
		// down Panel
		JPanel downP = new JPanel();
		downP.setBackground(Color.white);
		this.add(downP);
		// address Label
		JLabel downLabel = new JLabel();
		downLabel.setText("�ι�ķ�۽� : ����Ư���� ���빮�� �źϰ�� 34 02)300-1700 " + "�ڿ�ķ�۽� : ��⵵ ���ν� ó�α� ������ 116 031)330-6114");
		downLabel.setFont(font);
		downP.add(downLabel);
	}

	// event
	public class ListSelectionHandler implements ListSelectionListener {

		// departmentSelection���� event �޾ƿ� ���� fileName�� ��� lectureSelection�� ������.
		@Override
		public void valueChanged(ListSelectionEvent event) {
			String fileName = departmentSelection.refresh(event);
			lectureSelection.refresh(fileName);
		}
	}

	// list�� ó������ ���õǵ��� �������ִ� �Լ�
	public void initiate() {
		departmentSelection.init();
	}

	// Vector isselected�� ������� �Ѱ��ִ� �Լ�
	public Vector<VO> showFrame() {
		return lectureSelection.select();
	}

	public void setID(String id) {
		this.label.setText("      " + id + "��");
	}

	public void setImage(String imageID) {
		try {
			this.userImage = new ImageIcon(ImageIO.read(new File("image/"+imageID)));
			uILabel.setIcon(this.userImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}