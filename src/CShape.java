

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

public class CShape {
	
	
	public static final int SHAPE_LINE = 1;
	public static final int SHAPE_RECTANGLE = 2;
	public static final int SHAPE_ELLIPSE = 3;
	
	
	protected Color fillColor;
	protected String innerText;
	protected int edgeType; // ��������
	protected Point pStart;
	protected Point pEnd;
	protected int startX;
	protected int startY;
	protected int width;
	protected int height;
	
	public CShape() {
		
	}
	
	public Point getpStart() {
		return pStart;
	}

	public void setpStart(Point pStart) {
		this.pStart = pStart;
	}

	public Point getpEnd() {
		return pEnd;
	}

	public void setpEnd(Point pEnd) {
		this.pEnd = pEnd;
	}

	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public String getInnerText() {
		return innerText;
	}
	public void setInnerText(String innerText) {
		this.innerText = innerText;
	}
	public int getEdgeType() {
		return edgeType;
	}
	public void setEdgeType(int edgeType) {
		this.edgeType = edgeType;
	}
	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * ��ȡ�߽�����
	 */
	protected void getSurround(){
		
		startX = pStart.x < pEnd.x ? pStart.x : pEnd.x;
		startY = pStart.y < pEnd.y ? pStart.y : pEnd.y;
		width = Math.abs(pStart.x-pEnd.x);
		height = Math.abs(pStart.y-pEnd.y);
		
	}
	
	/**
	 * ����ģʽ��ȡʵ��
	 * @param type
	 * @return
	 */
	public static CShape getShape(int type){
		switch(type){
		case SHAPE_LINE:
			return new CLine();
		case SHAPE_RECTANGLE:
			return new CRectAngle();
		case SHAPE_ELLIPSE:
			return new CEllipse();
		}
		return null;
		
	}
	
	/**
	 * ����ͼ�������ڻ�ͼ���̺��ػ�
	 * @param gc
	 * @param pStart
	 * @param pEnd
	 * @param isNew �Ƿ�Ϊ�ػ�ͼ��
	 */
	public void draw(GC gc,Point pStart,Point pEnd,boolean isNew) {
		if(isNew){
			this.edgeType = PaintPanel.currEdgetype;
			this.fillColor = Display.getDefault().getSystemColor(
					PaintPanel.currColor);
			this.pStart = pStart;
			this.pEnd = pEnd;
		}
		gc.setLineStyle(this.edgeType);
	}
	
	/**
	 * �жϵ��Ƿ���ͼ����
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOnShape(int x,int y){
		return false;
	}
	
	/**
	 * ���ѡ�б�ǵ�С���ο�
	 */
	public void addSelected() {
		PaintPanel.selectRect.clear();
		Rectangle rs = new Rectangle(pStart.x-4, pStart.y-4, 8, 8);
		Rectangle re = new Rectangle(pEnd.x-4, pEnd.y-4, 8, 8);
		PaintPanel.selectRect.add(rs);
		PaintPanel.selectRect.add(re);
		
	}
}
