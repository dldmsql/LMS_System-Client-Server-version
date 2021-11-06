package ui;

import java.awt.Color;
import java.io.FileNotFoundException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class DirectoryPanel extends JPanel {

	private DirectoryList campusList;
	private DirectoryList collegeList;
	private DirectoryList departmentList;

	public DirectoryPanel (ListSelectionListener listHandler) {
		this.setBackground(Color.white);
		
		JScrollPane scrollpane = new JScrollPane();
		this.campusList = new DirectoryList(listHandler);
		scrollpane.setViewportView(this.campusList);
		this.add(scrollpane);

		scrollpane = new JScrollPane();
		this.collegeList = new DirectoryList(listHandler);
		scrollpane.setViewportView(this.collegeList);
		this.add(scrollpane);

		scrollpane = new JScrollPane();
		this.departmentList = new DirectoryList(listHandler);
		scrollpane.setViewportView(this.departmentList);
		this.add(scrollpane);
		
	}
	
	public String getSelectedName(ListSelectionEvent event) {
		String fileName = "";
		if(event.getSource() == null) {
			fileName = "root, root";
		}else if(event.getSource() == campusList) {
			fileName = campusList.getSelectedFileName();
			fileName = fileName + ",campus";
		}else if(event.getSource() == collegeList) {
			fileName = this.collegeList.getSelectedFileName();
			fileName = fileName + ",college";
		}else {
			fileName = this.departmentList.getSelectedFileName();
			fileName = fileName + ",department";
		}
		return fileName;
	}
	
	// refresh : list ���� �Լ�
	public String refresh(ListSelectionEvent event) {
		String fileName = "";
		try {
			if (event.getSource() == campusList) {
				fileName = campusList.getSelectedFileName();
				fileName = this.collegeList.refresh(fileName);
				fileName = this.departmentList.refresh(fileName); // lecture �����Ͱ� ����Ǿ��ִ� fileName.

			} else if (event.getSource() == collegeList) {
				fileName = this.collegeList.getSelectedFileName();
				fileName = this.departmentList.refresh(fileName);

			} else if (event.getSource() == departmentList) {
				fileName = this.departmentList.getSelectedFileName();

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public void init() {
		campusList.setSelectedIndex(0);
	}

}