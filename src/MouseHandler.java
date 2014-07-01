import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;

public class MouseHandler implements MouseListener, MouseMoveListener {
	
	/**
	 * ��������
	 */

	private Canvas canvas;
	private int downtime = 0;
	private boolean mouseDown;
	private boolean isDrawing = false;
	private boolean readyToMove = false;

	private Point pt;
	private CShape shape;
	private int moveCnt;

	public MouseHandler(Canvas canvas) {
		// TODO Auto-generated constructor stub
		this.canvas = canvas;

	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {

	}

	@Override
	public void mouseDown(MouseEvent e) {
		mouseDown = true;
		readyToMove = false;
		downtime = e.time;
		pt = new Point(e.x, e.y);
		moveCnt = 0;
		if (e.button == 1) {
			if(!PaintPanel.selectMode){
				isDrawing = true;
				shape = CShape.getShape(PaintPanel.currShape); // ������ȡʵ��
				shape.setEdgeType(PaintPanel.currEdgetype);
				shape.setFillColor(Display.getDefault().getSystemColor(
						PaintPanel.currColor));
				
			}else if(PaintPanel.hasShapeSelect){
				if(PaintPanel.shapesList.get(PaintPanel.selectIndex).isOnShape(e.x, e.y))
					readyToMove = true;
				
			}

		}
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseDown = false;
		isDrawing = false;
		if (arg0.time - downtime < 150) { // ���С��150msΪ�����¼�

			this.onMouseClick(arg0);
			
		} else {
			if (arg0.button == 1 && !PaintPanel.selectMode) {

				// PaintPanel.shapesList.add(shape); //������˸�����ӵ���mousedown����
				// canvas.redraw();
			}

		}
	}

	public void onMouseClick(MouseEvent e) {
		if (PaintPanel.selectMode) {
			// TODO ��ӵ���ѡ��
			PaintPanel.selectRect.clear();
			PaintPanel.hasShapeSelect = false;
			for(int i =0;i<PaintPanel.shapesList.size();i++){
				CShape shape = PaintPanel.shapesList.get(i);
				if(shape.isOnShape(e.x, e.y)){
					PaintPanel.hasShapeSelect = true;
					PaintPanel.selectIndex = i;
					shape.addSelected();
					break;
				}
			}
			canvas.redraw();
		}

	}


	@Override
	public void mouseMove(MouseEvent e) {
		
		// �϶���ͼ
		if (mouseDown && isDrawing) {
			if(moveCnt++ == 0)
				PaintPanel.shapesList.add(shape); 
			shape.draw(new GC(canvas), pt, new Point(e.x, e.y), true);
			canvas.redraw();

		}
		
		// �����ƶ�ͼ�ε�����
		if(mouseDown && readyToMove){
			shape = PaintPanel.shapesList.get(PaintPanel.selectIndex);
			int moveX = e.x-pt.x;
			int moveY = e.y-pt.y;
			Point ps = shape.getpStart();
			ps.x = ps.x+moveX;
			ps.y = ps.y+ moveY;
			Point pe = shape.getpEnd();
			pe.y = pe.y+ moveY;
			pe.x = pe.x+moveX;
			pt.x = e.x;
			pt.y = e.y;
			shape.addSelected();
			canvas.redraw();
		}
	}

}
