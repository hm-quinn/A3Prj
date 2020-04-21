package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.geom.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {
	private GameWorld gw;
	private TextArea myText;
	private static int width;
	private static int height;
	private static Point mapPoint;
	public MapView() {
		// TODO Auto-generated constructor stub
		this.getAllStyles().setBorder(Border.createLineBorder(4,ColorUtil.rgb(255, 0, 0)));
		this.setLayout(new BorderLayout());
		myText = new TextArea();
		myText.setEditable(false);
		myText.getAllStyles().setBgTransparency(0);
		this.setScrollable(true);
		
		this.width = this.height= 1000;
		this.add(BorderLayout.CENTER, myText);
	}
	
	public void setMapPoint(Point p) {
		this.mapPoint = p;
	}
	
	public static Point getMapPoint() {
		return mapPoint;
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		gw = (GameWorld) data;
		
		this.repaint();
	}
	
	public void setWidth() {
		this.width = this.getWidth();
	}
	
	public void setHeight() {
		this.height = this.getHeight();
	}
	
	public static int getMapWidth() {
		return width;
	}
	
	public static int getMapHeight() {
		return height;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		IIterator itr = gw.getCollection().getIterator();
		while(itr.hasNext()) {
			GameObject object = itr.getNext();
			if (object instanceof IDrawable) {
				((IDrawable) object).draw(g, pCmpRelPrnt);
			}
		}
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		int px = x - getParent().getAbsoluteX();
		int py = y - getParent().getAbsoluteY();
		
		Point pPtrRelPrnt = new Point(px, py);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		if(gw.getPause())
			gw.rePos(pPtrRelPrnt, pCmpRelPrnt);
	}
}