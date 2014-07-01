
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

public class CLine extends CShape {

	@Override
	public void draw(GC gc, Point pStart, Point pEnd,boolean isNew) {
		super.draw(gc, pStart, pEnd,isNew);
		
		gc.drawLine(pStart.x, pStart.y, pEnd.x, pEnd.y); // 相对于父布局啊啊啊
		// 万万不可销毁gcc，重绘时这个gc是循环使用的，不然后面都画不出了
		//gc.dispose();
		
	}

	@Override
	public boolean isOnShape(int x, int y) {
		// 满足两个条件，点在直线矩形区域内：点到直线的距离小于某一阈值
		int maxX = Math.max(pStart.x, pEnd.x);
		int minX = Math.min(pStart.x, pEnd.x);
		int maxY = Math.max(pStart.y, pEnd.y);
		int minY = Math.min(pStart.y, pEnd.y);
		
		if(maxX-minX < 3)
			return x >= minX-2 && x <= maxX+2 && y >= minY && y <= maxY;
			
		float A = (float)(pEnd.y-pStart.y)/(float)(pEnd.x-pStart.x); // K
		float B = -1F;
		float C =  pEnd.y-A*pEnd.x;
		
		float distance = (float) (Math.abs(A*x+B*y+C)/Math.sqrt(A*A+B*B));
//		System.out.println(" "+distance);
		return x >= minX && x <= maxX && y >= minY && y <= maxY && distance < 7;
		
	}
	

}
