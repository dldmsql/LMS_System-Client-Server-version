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
		font = new Font("고딕", Font.ITALIC, 10);

		// Color
		color = new Color(0XCCCCFF);

		// header
		String header[] = { "강좌번호", "강좌이름", "교수명 ", "학점", "강좌시간" };

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
		this.setBackground(Color.WHITE); // 셀 색 변경
		this.getTableHeader().setFont(new Font("고딕", Font.BOLD, 15));
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
	public void refresh(String fileName) {// fileName 받아와서 add 실행
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

	private void add(Vector<VO> lectures) {// lectureTable에 강좌 정보 담는 함수
		this.model.setRowCount(0); // 그 전에 넣어놨던 데이터를 지운다.
		// rowdata : lecture정보 담고 있는 벡터
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

	public Vector<VO> select() {// selectedrow : lectureTable에서 선택한 강좌 담은 벡터
		Vector<VO> selectedrow = new Vector<VO>();

		for (int i = 0; i < this.getRowCount(); i++) {

			if (this.isRowSelected(i)) {

				selectedrow.add(lectures.get(i));
			}
		}
		return selectedrow;
	}
}
