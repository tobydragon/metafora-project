package de.kuei.metafora.gwt.smack.client;


import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ScrollPanel;


public class TableLayout extends ScrollPanel{
	protected FlexTable box = new FlexTable();
	protected int row = 0;
	protected int column = 2;

	TableLayout() {
		box.setCellPadding(2);
		setPixelSize(600,80);
		add(box);
		setVisible(true);
	}
}
