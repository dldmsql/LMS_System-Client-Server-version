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
		font = new Font("고딕", Font.PLAIN, 13);

		// header
		String header[] = { "강좌번호", "강좌이름", "교수명", "학점", "강좌시간" };

		this.model = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;}};
		this.setModel(model);
		this.setFont(font);
		this.getTableHeader().setBackground(new Color(0X55CCCCFF));
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setResizingAllowed(false);
		this.getTableHeader().setFont(new Font("고딕", Font.BOLD, 15));
		this.setAutoCreateRowSorter(true); // 자동 행 정렬 기능
		CenterAlignedTableCellRenderer renderer = new CenterAlignedTableCellRenderer(); // row 값들 가운데 정렬
		this.setDefaultRenderer(this.getColumnClass(0), renderer);
	}

	private class CenterAlignedTableCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CenterAlignedTableCellRenderer() {
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}

	// lectureTable에서 선택한 항목 basket에 추가하는 함수
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
		this.updateUI(); // 업데이트 해주는 메소드
	}
	private boolean lcOverlap(VO vo) {// 강좌 중복 체크
		boolean ihaveit = false;
		int i = 0;
		for (VO vo2 : lectures) {
			if (vo2.getNumber().equals(vo.getNumber())) {
				ihaveit = true;
				JOptionPane.showMessageDialog(this, vo.getName() + " 강의는 이미 있습니다.", "알림",
						JOptionPane.INFORMATION_MESSAGE);
				this.changeSelection(i, 0, false, false); // 어딨는지 알려줘
			}
			i++;
		}
		return ihaveit;
	}

	// BasketTable에서 과목 선택하는 함수
	public Vector<VO> select() {

		Vector<VO> selectedrow = new Vector<VO>();

		for (int i = this.getRowCount() - 1; i >= 0; i--) {

			if (this.isRowSelected(i)) {

				selectedrow.add(this.lectures.get(i));
				this.lectures.remove(i);

				model.removeRow(i); // 선택된 index 삭제
			}
		}
		return selectedrow;
	}

	// 책가방에서 선택된 것들 삭제
	public void cancel() {

		for (int i = this.getRowCount() - 1; i >= 0; i--) {

			if (this.isRowSelected(i)) {

				this.lectures.remove(i);
				model.removeRow(i); // 선택된 index 삭제
			}
		}
		this.updateUI();
	}

	// 파일에서 저장된 책가방 내역 불러오기
	@SuppressWarnings("unchecked")
	public void initiate(String id) {
		this.id = id;
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CBasket", "getItems", "YesReturn", id, null);
		System.out.println("   basket FIle 에서 불러오라구우우우");
		// receive data from Server
		this.storeddata = (Vector<VO>) stub.receive();
		this.addItems(this.storeddata);
		// finalize
		stub.finalize();

	}

	public Vector<VO> saveBasket() { return lectures;	}

	public void fontSize(int index) {
		if (index == 0) {
			Font font1 = new Font("고딕", Font.PLAIN, 10);
			this.setFont(font1);
			this.getTableHeader().setFont(font1);
		} else if (index == 1) {
			Font font2 = new Font("고딕", Font.PLAIN, 13);
			this.setFont(font2);
			this.getTableHeader().setFont(font2);
		} else if (index == 2) {
			Font font3 = new Font("고딕", Font.PLAIN, 15);
			this.setFont(font3);
			this.getTableHeader().setFont(font3);
		} else if (index == 3) {
			Font font4 = new Font("고딕", Font.PLAIN, 17);
			this.setFont(font4);
			this.getTableHeader().setFont(font4);
		}
	}
}
