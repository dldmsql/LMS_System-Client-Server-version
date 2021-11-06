package ui;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Client.Stub;
import sql_test2.Lecture;

public class LectureEvaluationTable extends JTable{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private Font font;

	public LectureEvaluationTable(String id, String name) {

		font = new Font("고딕", Font.PLAIN, 13);
		// header
		String header[] = { "아이디", "강좌명", "강의평가내용" };

		this.model = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		this.setModel(model);
		this.setFont(font);
		this.getTableHeader().setBackground(new Color(0X55CCCCFF));
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setFont(new Font("고딕", Font.BOLD, 15));
		this.setAutoCreateRowSorter(true); // 자동 행 정렬 기능
		this.getColumn("아이디").setPreferredWidth(35);
		this.getColumn("강좌명").setPreferredWidth(40);
		this.getColumn("강의평가내용").setPreferredWidth(200);
		CenterAlignedTableCellRenderer renderer = new CenterAlignedTableCellRenderer(); // row 값들 가운데 정렬
		this.setDefaultRenderer(this.getColumnClass(0), renderer);
		//set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendLectureE("CLecture", "getEvaluation", "YesReturn", null, id, name);
		// receive data from Server
		Vector<Lecture> getEvaluation = new Vector<Lecture>();
		getEvaluation = (Vector<Lecture>) stub.receive();
		System.out.println(getEvaluation);
		stub.finalize();
		addItems(getEvaluation);
	}

	private class CenterAlignedTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CenterAlignedTableCellRenderer() {
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	public void addItems (Vector<Lecture> getEvaluation) {

		Vector<String> rowdata;
		for(Lecture lecture : getEvaluation) {
			rowdata = new Vector<String>();
			rowdata.add(lecture.getId());
			rowdata.add(lecture.getName());
			rowdata.add(lecture.getText());
			this.model.addRow(rowdata);
		}
		this.updateUI();
	}
}

