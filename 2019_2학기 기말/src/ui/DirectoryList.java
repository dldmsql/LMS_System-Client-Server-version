package ui;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.util.Vector;

//import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import Client.Stub;
import Server.VO;

@SuppressWarnings("serial")
public class DirectoryList extends JList<String> {

	Vector<VO> directories;

	@SuppressWarnings("unchecked")
	public DirectoryList(ListSelectionListener listSelectionHandler) {

		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();
		stub.sendParam("CDirectory", "getItems", "YesReturn", "root", null);
		// receive data from Server
		System.out.println("\r\n" + "directoryList ���� ��");
		this.directories = (Vector<VO>) stub.receive();
		// finalize
		stub.finalize();

		Vector<String> listData = new Vector<String>();
		for (VO eDirectory : directories) {
			listData.add(eDirectory.getName());
		}
		this.setPreferredSize(new Dimension(100, 200));
		this.setListData(listData);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.addListSelectionListener(listSelectionHandler);

	}

	public String getSelectedFileName() {
		int selectedIndex = this.getSelectedIndex();
		if (selectedIndex == -1) {
			selectedIndex = 0;
		}
		return this.directories.get(selectedIndex).getHyperLink();
	}

	@SuppressWarnings("unchecked")
	public String refresh(String fileName) throws FileNotFoundException {
		// set protocol Info
		Stub stub = new Stub();
		stub.initialize();

		stub.sendParam("CDirectory", "getItems", "YesReturn", fileName, null);
		// receive data from Server
		this.directories = (Vector<VO>) stub.receive();

		// finalize
		stub.finalize();

		Vector<String> listData = new Vector<String>();
		for (VO vo : directories) {
			listData.add(vo.getName());
		}
		this.setListData(listData);
		//		this.setSelectedIndex(0); // ������ ���� ���� ��� --> -1�� ǥ��/-1�� ������ �ذ�.
		this.repaint(); // �׸��� �ٽ� �׸��� ��ɾ�
		return this.directories.get(0).getHyperLink();
	}

	//	public String itemRefresh(Vector<VO> directoryVector) {
	//		this.directories = directoryVector;
	//		Vector<String> listData = new Vector<String>();
	//		for (VO vo : directories) {
	//			listData.add(vo.getName());
	//		}
	//		this.setListData(listData);
	////		this.setSelectedIndex(0); // ������ ���� ���� ��� --> -1�� ǥ��/-1�� ������ �ذ�.
	//		this.repaint(); // �׸��� �ٽ� �׸��� ��ɾ�
	//		System.out.println(this.directories.get(0).getHyperLink());
	//		return this.directories.get(0).getHyperLink();
	//	}

}