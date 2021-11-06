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

@SuppressWarnings("serial")
public class LectureTable extends JTable {

	private Vector<VO> lectures;
	private DefaultTableModel model;
	private Font font;
	private Color color;

	public LectureTable() {
		// font
		font = new Font("���", Font.ITALIC, 10);

		// Color
		color = new Color(0XCCCCFF);

		// header
		String header[] = { "���¹�ȣ", "�����̸�", "������ ", "����", "���½ð�" };

		this.model = new DefaultTableModel(header, 0) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		this.setModel(model);
		this.setFont(font);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setBackground(color);
		this.setBackground(Color.WHITE); // �� �� ����
		this.getTableHeader().setFont(new Font("���", Font.BOLD, 15));
		CenterAlignedTableCellRenderer renderer = new CenterAlignedTableCellRenderer();
		this.setDefaultRenderer(this.getColumnClass(0), renderer);
	}

	private class CenterAlignedTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CenterAlignedTableCellRenderer() {
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	@SuppressWarnings("unchecked")
	public void refresh(String fileName) {// fileName �޾ƿͼ� add ����
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CLecture", "getItems", "YesReturn", fileName, null);
		System.out.println("\r\n" + "lectureTable refresh");
		// receive data from Server
		this.lectures = (Vector<VO>) stub.receive();
		add(this.lectures);
		// finalize
		stub.finalize();
	}

	private void add(Vector<VO> lectures) {// lectureTable�� ���� ���� ��� �Լ�
		this.model.setRowCount(0); // �� ���� �־���� �����͸� �����.
		// rowdata : lecture���� ��� �ִ� ����
		Vector<String> rowdata;
		for (VO eLecture : lectures) {
			rowdata = new Vector<String>();
			rowdata.add(eLecture.getNumber());
			rowdata.add(eLecture.getName());
			rowdata.add(eLecture.getProfessorName());
			rowdata.add(eLecture.getCredit());
			rowdata.add(eLecture.getTime());
			this.model.addRow(rowdata);
		}
	}

	public Vector<VO> select() {// selectedrow : lectureTable���� ������ ���� ���� ����
		Vector<VO> selectedrow = new Vector<VO>();

		for (int i = 0; i < this.getRowCount(); i++) {

			if (this.isRowSelected(i)) {

				selectedrow.add(lectures.get(i));
			}
		}
		return selectedrow;
	}
}
