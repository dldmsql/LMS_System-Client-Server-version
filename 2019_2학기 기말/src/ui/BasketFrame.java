package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import Server.VO;

public class BasketFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Font font;
	private BasketTable basketTable;
	private SincheongTable sincheongTable;
	private JButton selectbutton;
	private JButton deletebutton;
	private JButton cancelbutton;
	private JButton backToBasket;
	private JMenuBar mb;
	private JMenu menu;
	private JMenuItem userInfo;
	private JMenuItem logout;
	private JMenu menu2;
	private JComboBox fontcom;
	private String id;
	private JLabel score;
	private int a;

	public BasketFrame(ActionListener actionHandler) {
		this.setLayout(new FlowLayout());

		// setting
		this.setTitle("����������");
		this.setSize(800, 700);
		this.setFont(font);
		this.setLocationRelativeTo(null);

		// imageIcon
		Toolkit toolkit = this.getToolkit();
		Image img = toolkit.createImage("image/mju_icon.jpg");
		this.setIconImage(img);
		// Font
		this.font = new Font("���", Font.PLAIN, 10);

		// panel
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.white);

		// menubar
		mb = new JMenuBar();
		mb.setBackground(new Color(0X55CCCCFF));
		menu = new JMenu("Info");
		menu.setFont(font);
		menu.setMnemonic('I');
		menu2 = new JMenu("Exit");
		menu2.setFont(font);
		menu2.setMnemonic('E');
		mb.add(menu);
		mb.add(menu2);
		userInfo = new JMenuItem("user");

		userInfo.setFont(font);
		userInfo.addActionListener(actionHandler);
		userInfo.setToolTipText("�������� �����ϱ�");
		userInfo.setActionCommand("user");
		userInfo.setAccelerator(KeyStroke.getKeyStroke('U', KeyEvent.CTRL_MASK));
		menu.add(userInfo);
		logout = new JMenuItem("logout");
		logout.setFont(font);
		logout.setToolTipText("�α׾ƿ��ϱ�");
		logout.addActionListener(actionHandler);
		logout.setActionCommand("logout");
		logout.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_MASK));
		menu2.add(logout);
		panel.add(mb);

		// toolbar
		JToolBar tool = new JToolBar("fontSize");
		tool.setBackground(Color.white);
		panel.add(tool);
		JLabel fontLabel = new JLabel("�۾� ũ��");
		tool.add(fontLabel);
		fontcom = new JComboBox();
		fontcom.addItem("10");
		fontcom.addItem("13");
		fontcom.addItem("15");
		fontcom.addItem("17");
		fontcom.setFont(font);
		fontcom.setBackground(Color.white);
		fontcom.addActionListener(actionHandler);
		fontcom.setActionCommand("fontSize");
		tool.add(fontcom);

		JPanel blabelP = new JPanel();
		blabelP.setBackground(Color.white);
		blabelPanel(blabelP);
		panel.add(blabelP);

		// Table
		JScrollPane scrollPane = new JScrollPane();
		this.basketTable = new BasketTable();
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(this.basketTable);
		panel.add(scrollPane);

		JPanel btPanel = new JPanel();
		btPanel.setBackground(Color.white);
		buttonPanel(btPanel, actionHandler);
		panel.add(btPanel);

		JPanel slabelP = new JPanel();
		slabelP.setBackground(Color.white);
		slabelPanel(slabelP);
		panel.add(slabelP);

		scrollPane = new JScrollPane();
		this.sincheongTable = new SincheongTable();
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(this.sincheongTable);
		panel.add(scrollPane);

		JPanel sbtPanel = new JPanel();
		sbtPanel.setBackground(Color.white);
		buttonPanel2(sbtPanel, actionHandler);
		panel.add(sbtPanel);

		this.add(panel);
	}

	private void slabelPanel(JPanel slabelP) {
		slabelP.setLayout(new BorderLayout());

		// Label
		JLabel sincheongLabel = new JLabel("        ��û����");
		sincheongLabel.setFont(new Font("���", Font.BOLD, 15));
		slabelP.add(sincheongLabel, BorderLayout.WEST);
	}

	private void blabelPanel(JPanel blabelP) {
		blabelP.setLayout(new BorderLayout());

		// Label
		JLabel basketLabel = new JLabel("         å����");
		basketLabel.setFont(new Font("���", Font.BOLD, 15));
		blabelP.add(basketLabel, BorderLayout.WEST);
	}

	private void buttonPanel2(JPanel sbtPanel, ActionListener actionHandler) {
		this.setLayout(new BorderLayout());
		// button
		cancelbutton = new JButton("����");
		cancelbutton.setFont(font);
		cancelbutton.setToolTipText("��û�������� �����");
		cancelbutton.addActionListener(actionHandler);
		cancelbutton.setActionCommand("cancelSincheong");
		cancelbutton.setBackground(new Color(0XCCCCFF));
		sbtPanel.add(cancelbutton, BorderLayout.EAST);

		backToBasket = new JButton("�ǵ�����");
		backToBasket.setFont(font);
		backToBasket.setToolTipText("å�������� �ǵ�����");
		backToBasket.addActionListener(actionHandler);
		backToBasket.setActionCommand("backToBasket");
		backToBasket.setBackground(new Color(0XCCCCFF));
		sbtPanel.add(backToBasket, BorderLayout.EAST);
		
	}

	private void buttonPanel(JPanel btPanel, ActionListener actionHandler) {

		this.setLayout(new BorderLayout());

		// Button
		selectbutton = new JButton("��û");
		selectbutton.setFont(font);
		selectbutton.setToolTipText("��û�ϱ�");
		selectbutton.addActionListener(actionHandler);
		selectbutton.setActionCommand("select");
		selectbutton.setBackground(new Color(0XCCCCFF));
		btPanel.add(selectbutton, BorderLayout.EAST);

		deletebutton = new JButton("����");
		deletebutton.setFont(font);
		deletebutton.setToolTipText("å���濡�� �����");
		deletebutton.addActionListener(actionHandler);
		deletebutton.setActionCommand("deleteBasket");
		deletebutton.setBackground(new Color(0XCCCCFF));
		btPanel.add(deletebutton, BorderLayout.EAST);
	}

	// basketTable�� isselected �� ����
	public void addItems(Vector<VO> isselected) {
		this.basketTable.addItems(isselected);
	}

	// basketTable select�Լ� ����
	public Vector<VO> showFrame() {
		return this.basketTable.select();
	}

	// sincheongTable�� basketSelected�� ������
	public void givebasket(Vector<VO> basketSelected) {
		this.sincheongTable.addItems(basketSelected); // basket -> sinchoeng
		for(int i =0; i< basketSelected.size(); i++) {
			if(a + Integer.parseInt(basketSelected.get(i).getCredit()) <= 18) {
				a += Integer.parseInt(basketSelected.get(i).getCredit());
			} else {JOptionPane.showMessageDialog(null, "�ִ� 18������ �ʰ��ϼ̽��ϴ�.");
				this.selectbutton.setEnabled(false);
			}
		}
	}

	public void cancel() {
		this.basketTable.cancel();
	}

	public void cancelSincheong() {
		Vector<VO> cancelSincheong = new Vector<VO>();
		cancelSincheong = this.sincheongTable.cancel();
		for(int i =0; i< cancelSincheong.size(); i++) {
			a -= Integer.parseInt(cancelSincheong.get(i).getCredit());
//			score.setText(Integer.toString(a));
		}
		this.selectbutton.setEnabled(true);
	}

	public Vector<VO> backToBasket() {
		Vector<VO> backToBasket = new Vector<VO>();
		backToBasket = this.sincheongTable.backToBasket();
		for(int i =0; i< backToBasket.size(); i++) {
			a -= Integer.parseInt(backToBasket.get(i).getCredit());
//			score.setText(Integer.toString(a));
		}
		this.selectbutton.setEnabled(true);
		return backToBasket;
	}

	public void initiate(String id) {
		this.id = id;
		this.basketTable.initiate(id);
		Vector<VO> db_data = new Vector<VO>();
		db_data = this.sincheongTable.initiate(id);
		for(int i =0; i< db_data.size(); i++) {
			a += Integer.parseInt(db_data.get(i).getCredit());
//			score.setText(Integer.toString(a));
		}
	}

	public Vector<VO> saveBasket() {
		return this.basketTable.saveBasket();
	}

	public Vector<VO> saveSincheong() {
		return this.sincheongTable.saveSincheong();
	}

	public void fontSize() {
		int index = fontcom.getSelectedIndex();
		this.basketTable.fontSize(index);
		this.sincheongTable.fontSize(index);
		if (index == 0) {
			Font font1 = new Font("���", Font.PLAIN, 10);
			menu.setFont(font1);
			menu2.setFont(font1);
			fontcom.setFont(font1);
			cancelbutton.setFont(font1);
			backToBasket.setFont(font1);
			selectbutton.setFont(font1);
			deletebutton.setFont(font1);
		} else if (index == 1) {
			Font font2 = new Font("���", Font.PLAIN, 13);
			menu.setFont(font2);
			menu2.setFont(font2);
			fontcom.setFont(font2);
			cancelbutton.setFont(font2);
			backToBasket.setFont(font2);
			selectbutton.setFont(font2);
			deletebutton.setFont(font2);
		} else if (index == 2) {
			Font font3 = new Font("���", Font.PLAIN, 15);
			menu.setFont(font3);
			menu2.setFont(font3);
			fontcom.setFont(font3);
			cancelbutton.setFont(font3);
			backToBasket.setFont(font3);
			selectbutton.setFont(font3);
			deletebutton.setFont(font3);
		} else if (index == 3) {
			Font font4 = new Font("���", Font.PLAIN, 17);
			menu.setFont(font4);
			menu2.setFont(font4);
			fontcom.setFont(font4);
			cancelbutton.setFont(font4);
			backToBasket.setFont(font4);
			selectbutton.setFont(font4);
			deletebutton.setFont(font4);
		}
	}
}
