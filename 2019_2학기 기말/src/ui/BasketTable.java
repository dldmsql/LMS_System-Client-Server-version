package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Client.Stub;
import Server.VO;

public class BasketTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private Font font;
	private Vector<VO> lectures;
	private Vector<VO> storeddata;
	private String id;

	public BasketTable() {
		this.lectures = new Vector<VO>();
		this.storeddata = new Vector<VO>();

		// font
		font = new Font("���", Font.PLAIN, 13);

		// header
		String header[] = { "���¹�ȣ", "�����̸�", "������", "����", "���½ð�" };

		this.model = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;}};
		this.setModel(model);
		this.setFont(font);
		this.getTableHeader().setBackground(new Color(0X55CCCCFF));
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setFont(new Font("���", Font.BOLD, 15));
		this.setAutoCreateRowSorter(true); // �ڵ� �� ���� ���
		CenterAlignedTableCellRenderer renderer = new CenterAlignedTableCellRenderer(); // row ���� ��� ����
		this.setDefaultRenderer(this.getColumnClass(0), renderer);
	}

	private class CenterAlignedTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CenterAlignedTableCellRenderer() {
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	// lectureTable���� ������ �׸� basket�� �߰��ϴ� �Լ�
	public void addItems(Vector<VO> isselected) { // Vector isselected = Vector lecutres
		Vector<String> rowdata;
		for (VO vo : isselected) {
			rowdata = new Vector<String>();
			if (!lcOverlap(vo)) {
				rowdata.add(vo.getNumber());
				rowdata.add(vo.getName());
				rowdata.add(vo.getProfessorName());
				rowdata.add(vo.getCredit());
				rowdata.add(vo.getTime());
				this.model.addRow(rowdata);
				this.lectures.add(vo);
			}
		}
		this.updateUI(); // ������Ʈ ���ִ� �޼ҵ�
	}
	private boolean lcOverlap(VO vo) {// ���� �ߺ� üũ
		boolean ihaveit = false;
		int i = 0;
		for (VO vo2 : lectures) {
			if (vo2.getNumber().equals(vo.getNumber())) {
				ihaveit = true;
				JOptionPane.showMessageDialog(this, vo.getName() + " ���Ǵ� �̹� �ֽ��ϴ�.", "�˸�",
						JOptionPane.INFORMATION_MESSAGE);
				this.changeSelection(i, 0, false, false); // ������� �˷���
			}
			i++;
		}
		return ihaveit;
	}

	// BasketTable���� ���� �����ϴ� �Լ�
	public Vector<VO> select() {

		Vector<VO> selectedrow = new Vector<VO>();

		for (int i = this.getRowCount() - 1; i >= 0; i--) {

			if (this.isRowSelected(i)) {

				selectedrow.add(this.lectures.get(i));
				this.lectures.remove(i);

				model.removeRow(i); // ���õ� index ����
			}
		}
		return selectedrow;
	}

	// å���濡�� ���õ� �͵� ����
	public void cancel() {

		for (int i = this.getRowCount() - 1; i >= 0; i--) {

			if (this.isRowSelected(i)) {

				this.lectures.remove(i);
				model.removeRow(i); // ���õ� index ����
			}
		}
		this.updateUI();
	}

	// ���Ͽ��� ����� å���� ���� �ҷ�����
	@SuppressWarnings("unchecked")
	public void initiate(String id) {
		this.id = id;
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CBasket", "getItems", "YesReturn", id, null);
		System.out.println("   basket FIle ���� �ҷ����󱸿���");
		// receive data from Server
		this.storeddata = (Vector<VO>) stub.receive();
		this.addItems(this.storeddata);
		// finalize
		stub.finalize();

	}

	public Vector<VO> saveBasket() { return lectures;	}

	public void fontSize(int index) {
		if (index == 0) {
			Font font1 = new Font("���", Font.PLAIN, 10);
			this.setFont(font1);
			this.getTableHeader().setFont(font1);
		} else if (index == 1) {
			Font font2 = new Font("���", Font.PLAIN, 13);
			this.setFont(font2);
			this.getTableHeader().setFont(font2);
		} else if (index == 2) {
			Font font3 = new Font("���", Font.PLAIN, 15);
			this.setFont(font3);
			this.getTableHeader().setFont(font3);
		} else if (index == 3) {
			Font font4 = new Font("���", Font.PLAIN, 17);
			this.setFont(font4);
			this.getTableHeader().setFont(font4);
		}
	}
}
