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

public class SincheongTable extends JTable {

	private static final long serialVersionUID = 1L;

	private DefaultTableModel model;
	private Font font;
	private Vector<VO> lectures;
	private int a;
	private Vector<VO> storeddata;

	public SincheongTable() {
		this.lectures = new Vector<VO>();
		this.storeddata = new Vector<VO>();
		// font
		font = new Font("���", Font.PLAIN, 10);

		// header
		String header[] = { "���¹�ȣ", "�����̸�", "������", "����", "���½ð�" };

		this.model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		this.setModel(model);
		this.setFont(font);
		this.getTableHeader().setBackground(new Color(0X55CCCCFF));
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setFont(new Font("���", Font.BOLD, 15));
		this.setAutoCreateRowSorter(true); // �ڵ� �� ���� ���
		CenterAlignedTableCellRenderer renderer = new CenterAlignedTableCellRenderer();
		this.setDefaultRenderer(this.getColumnClass(0), renderer);
	}

	private class CenterAlignedTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CenterAlignedTableCellRenderer() {
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	// ��û ���� row �޾ƿ���
	public void addItems(Vector<VO> storeddata2) {
		Vector<String> rowdata;
		for (VO vo : storeddata2) {
				rowdata = new Vector<String>();
				if (!(lcOverlap(vo.getNumber()))) {
					rowdata.add(vo.getNumber());
					rowdata.add(vo.getName());
					rowdata.add(vo.getProfessorName());
					rowdata.add(vo.getCredit());
					rowdata.add(vo.getTime());
					this.model.addRow(rowdata);
					this.lectures.add(vo);
				}else{JOptionPane.showMessageDialog(this, vo.getName() + " ���Ǵ� �̹� �ֽ��ϴ�.", "�˸�",
						JOptionPane.INFORMATION_MESSAGE); }
		}
		this.updateUI();
	}

	// ���� �ߺ� üũ
		private boolean lcOverlap(String number) {
			int i = 0;
			for (VO vo2 : lectures) {
				if (vo2.getNumber().equals(number)) {
					this.changeSelection(i, 0, false, false); // ������� �˷���
					return true;
				}
				i++;
			}
			return false;
		}

	// ��û�������� ������ ���� ����
	public Vector<VO> cancel() {
		for (int i = this.getRowCount() - 1; i >= 0; i--) {
			if (this.isRowSelected(i)) {
				this.lectures.remove(i);
				model.removeRow(i); // ���õ� index ����
			}
		}
		this.updateUI();
		return this.lectures;
	}

	// ��û�������� å�������� �ǵ��� �� ���õ� ���� ��� ����
	public Vector<VO> backToBasket() {
		Vector<VO> selectedrow = new Vector<VO>();

		for (int i = this.getRowCount() - 1; i >= 0; i--) {

			if (this.isRowSelected(i)) {

				selectedrow.add(this.lectures.get(i));
				model.removeRow(i);
				lectures.remove(i); // ���õ� index ����
			}
		}
		this.updateUI();
		return selectedrow;
	}

	// ���Ͽ��� ����� ��û���� �ҷ�����
	@SuppressWarnings("unchecked")
	public Vector<VO> initiate(String id) {
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CSincheong", "getItems", "YesReturn", id, null);
		System.out.println("  file���� �ҷ���.!!!!");
		// receive data from Server
		storeddata = (Vector<VO>) stub.receive();
		this.addItems(storeddata);
		// finalize
		stub.finalize();
		return this.storeddata;
	}

	public Vector<VO> saveSincheong() { return this.lectures; }

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
