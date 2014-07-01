import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class CRectAngle extends CShape {
	


	@Override
	public void draw(GC gc, Point pStart, Point pEnd, boolean isNew) {
		// TODO Auto-generated method stub
		super.draw(gc, pStart, pEnd, isNew);
		getSurround();
		gc.setBackground(this.fillColor);
		// ÏÈ»­ÔÙÌî³äÄÜ¹»±ÜÃâ±ß½ç³öÏÖ¶ªÊ§
		gc.fillRectangle(startX, startY, width, height);
		gc.drawRectangle(startX, startY, width, height);
	}

	@Override
	public boolean isOnShape(int x, int y) {
		// TODO Auto-generated method stub
		int maxX = Math.max(pStart.x, pEnd.x);
		int minX = Math.min(pStart.x, pEnd.x);
		int maxY = Math.max(pStart.y, pEnd.y);
		int minY = Math.min(pStart.y, pEnd.y);
		return x >= minX-2 && x <= maxX+2 && y >= minY-2 && y <= maxY+2;
	}

	@Override
	public void addSelected() {
		// TODO Auto-generated method stub
		super.addSelected();
		Rectangle rs = new Rectangle(startX+width-4, startY-4, 8, 8);
		Rectangle re = new Rectangle(startX-4, startY+height-4, 8, 8);
		PaintPanel.selectRect.add(rs);
		PaintPanel.selectRect.add(re);
	}
	

	

}
