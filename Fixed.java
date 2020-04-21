package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Fixed extends GameObject implements ISelectable {
	private boolean select = false;
	
	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		this.select = b;
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return this.select;
	}

	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int dia = super.getSize() / 2;
		int left = (int)Math.round(super.getPosX() - dia + pCmpRelPrnt.getX());
		int right = left + super.getSize();
		int top = (int)Math.round(super.getPosY() - dia + pCmpRelPrnt.getY());
		int bottom = top + super.getSize();
		
		return(pPtrRelPrnt.getX() > left && pPtrRelPrnt.getX() < right &&
				pPtrRelPrnt.getY() > top && pPtrRelPrnt.getY() < bottom);
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		
	}

}
