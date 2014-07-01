import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;




public class CEllipse extends CShape {

	@Override
	public void draw(GC gc, Point pStart, Point pEnd, boolean isNew) {
		// TODO Auto-generated method stub
		super.draw(gc, pStart, pEnd, isNew);
		getSurround();
		gc.setBackground(this.fillColor);
		gc.fillOval(startX, startY, width, height);
		gc.drawOval(startX, startY, width, height);
	}

	@Override
	public boolean isOnShape(int x, int y) {
		// TODO Auto-generated method stub
		// 椭圆上的判定，主要思想随是带入标准方程看是否小于1
		float a = this.width/2;
		float b = this.height/2;
		float relx = x-(this.startX + a);
		float rely = y-(this.startY + b);
		return relx*relx/(a*a)+rely*rely/(b*b) <= 1F;
	}

	@Override
	public void addSelected() {
		// TODO Auto-generated method stub
		super.addSelected();
		PaintPanel.selectRect.clear();
		Rectangle rt = new Rectangle(startX+width/2-4, startY-4, 8, 8);
		Rectangle rl = new Rectangle(startX-4, startY+height/2-4, 8, 8);
		Rectangle rr = new Rectangle(startX+width-4, startY+height/2-4, 8, 8);
		Rectangle rb = new Rectangle(startX+width/2-4, startY+height-4, 8, 8);
		PaintPanel.selectRect.add(rt);
		PaintPanel.selectRect.add(rl);
		PaintPanel.selectRect.add(rr);
		PaintPanel.selectRect.add(rb);
	}
	
	
	
	
	



	

}
