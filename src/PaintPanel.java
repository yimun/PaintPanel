import java.util.ArrayList;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class PaintPanel {

	// widget
	protected Shell shell;
	private Canvas canvas;
	private MenuItem openItem;
	private MenuItem saveItem;
	private MenuItem lineItem;
	private MenuItem rectItem;
	private MenuItem ellipseItem;
	private MenuItem fullItem;
	private MenuItem dottdItem;
	private MenuItem selectItem;
	private MenuItem addwordItem;
	private MenuItem deleteItem;
	private Menu menu_1;
	private MenuItem whiteItem;
	private MenuItem yellowItem;
	private MenuItem blueItem;
	private MenuItem blackItem;
	private MenuItem greenItem;
	private MenuItem redItem;
	private Menu menuPop;

	// Data
	static int colors[] = new int[]{SWT.COLOR_WHITE,SWT.COLOR_YELLOW,SWT.COLOR_BLUE,SWT.COLOR_BLACK,SWT.COLOR_GREEN,SWT.COLOR_RED};
	private MouseHandler mHandler;

	public static boolean selectMode = true; // 是否处于选择模式
	public static boolean mouseDown = false;

	public static int currEdgetype = SWT.LINE_SOLID;
	public static int currColor = colors[0];
	public static int currShape;

	public static ArrayList<CShape> shapesList = new ArrayList<CShape>();

	public static ArrayList<Rectangle> selectRect = new ArrayList<Rectangle>();
	public static boolean hasShapeSelect = false;
	public static int selectIndex;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PaintPanel window = new PaintPanel();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		initData();
		initMenuEvent();
		initCanvas();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		// shell.setSize(721, 480);
		shell.setMaximized(true);
		shell.setText("简单画图程序");

		// 顶部菜单栏
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		{
			MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
			mntmFile.setText("\u6587\u4EF6");
			Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
			mntmFile.setMenu(fileMenu);
			{
				openItem = new MenuItem(fileMenu, SWT.NONE);
				openItem.setText("打开");
				saveItem = new MenuItem(fileMenu, SWT.NONE);
				saveItem.setText("保存");
			}

			MenuItem mntmShape = new MenuItem(menu, SWT.CASCADE
					| SWT.NO_RADIO_GROUP);
			mntmShape.setText("形状");
			Menu shapeMenu = new Menu(shell, SWT.DROP_DOWN);
			
			mntmShape.setMenu(shapeMenu);

			{
				selectItem = new MenuItem(shapeMenu, SWT.RADIO);
				selectItem.setText("选择模式");
				selectItem.setSelection(true);
				lineItem = new MenuItem(shapeMenu, SWT.RADIO);
				lineItem.setText("直线");
				rectItem = new MenuItem(shapeMenu, SWT.RADIO);
				rectItem.setText("长方形");
				ellipseItem = new MenuItem(shapeMenu, SWT.RADIO);
				ellipseItem.setText("椭圆");
			}

			MenuItem mntmColor = new MenuItem(menu, SWT.CASCADE);
			mntmColor.setText("\u586B\u5145\u989C\u8272");
			
			menu_1 = new Menu(mntmColor);
			mntmColor.setMenu(menu_1);
			
			whiteItem = new MenuItem(menu_1, SWT.RADIO);
			whiteItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[0];
					
				}
			});
			whiteItem.setText("\u767D\u8272");
			whiteItem.setSelection(true);
			
			yellowItem = new MenuItem(menu_1, SWT.RADIO);
			yellowItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[1];
				}
			});
			yellowItem.setText("\u9EC4\u8272");
			
			blueItem = new MenuItem(menu_1, SWT.RADIO);
			blueItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[2];
				}
			});
			blueItem.setText("\u84DD\u8272");
			
			blackItem = new MenuItem(menu_1, SWT.RADIO);
			blackItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[3];
				}
			});
			blackItem.setText("\u9ED1\u8272");
			
			greenItem = new MenuItem(menu_1, SWT.RADIO);
			greenItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[4];
				}
			});
			greenItem.setText("\u7EFF\u8272");
			
			redItem = new MenuItem(menu_1, SWT.RADIO);
			redItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					currColor = colors[5];
				}
			});
			redItem.setText("\u7EA2\u8272");
			{

			}

			MenuItem mntmCanvas = new MenuItem(menu, SWT.CASCADE
					| SWT.NO_RADIO_GROUP);
			mntmCanvas.setText("画笔类型");
			Menu canvasMenu = new Menu(shell, SWT.DROP_DOWN);
			mntmCanvas.setMenu(canvasMenu);
			{
				fullItem = new MenuItem(canvasMenu, SWT.RADIO);
				fullItem.setText("实线");
				fullItem.setSelection(true);
				dottdItem = new MenuItem(canvasMenu, SWT.RADIO);
				dottdItem.setText("虚线");

			}
		}

		GridLayout gl = new GridLayout();
		gl.marginLeft = 0;
		gl.marginHeight = 0;
		gl.marginRight = 0;
		gl.marginTop = 0;
		gl.marginWidth = 0;
		gl.marginBottom = 0;
		gl.verticalSpacing = 0;
		shell.setLayout(gl);

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 50;

		gd = new GridData(GridData.FILL_BOTH);
		canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		canvas.setLayoutData(gd);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setBackgroundMode(SWT.INHERIT_FORCE);

		menuPop = new Menu(canvas);
		// canvas.setMenu(menuPop);

		addwordItem = new MenuItem(menuPop, SWT.NONE);
		addwordItem.setText("\u6DFB\u52A0\u6587\u5B57");

		deleteItem = new MenuItem(menuPop, SWT.NONE);
		deleteItem.setText("\u5220\u9664");

	}

	public void initData() {
		shapesList.clear();
		
	}

	public void initMenuEvent() {

		selectItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (selectItem.getSelection()) {
					selectMode = true;
				} else {
					selectMode = false;
				}

			}

		});
		lineItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (lineItem.getSelection()) {
					currShape = CShape.SHAPE_LINE;
				}

			}

		});
		rectItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (rectItem.getSelection()) {
					currShape = CShape.SHAPE_RECTANGLE;
				}
			}

		});
		ellipseItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (ellipseItem.getSelection()) {
					currShape = CShape.SHAPE_ELLIPSE;
				}
			}

		});

		fullItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (fullItem.getSelection()) {
					currEdgetype = SWT.LINE_SOLID;
				}
			}

		});

		dottdItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (dottdItem.getSelection()) {
					currEdgetype = SWT.LINE_DOT;
				}
			}

		});

		addwordItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (hasShapeSelect) {
					CShape getShape = shapesList.get(selectIndex);
					InputDialog id = new InputDialog(shell,"输入文本","","",null);
					if(id.open() == Window.OK){
						getShape.setInnerText(id.getValue());
						canvas.redraw();
					}
				}
			}

		});

		deleteItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (hasShapeSelect) {
					hasShapeSelect = false;
					shapesList.remove(selectIndex);
					selectRect.clear();
					canvas.redraw();
					// System.out.println("clear"+selectIndex+"  "+shapesList.size());
				}
			}

		});

	}

	private void initCanvas() {

		mHandler = new MouseHandler(canvas);
		canvas.addMouseListener(mHandler);
		canvas.addMouseMoveListener(mHandler);
		// redraw回调方法
		canvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {

				for (int i = 0; i < shapesList.size(); i++) {
					CShape shape = shapesList.get(i);
					shape.draw(e.gc, shape.getpStart(), shape.getpEnd(), false);
					String text = shape.getInnerText();
					if(null != text){
						int centerX = (shape.getpStart().x+shape.getpEnd().x)/2;
						int centerY = (shape.getpStart().y+shape.getpEnd().y)/2;
						Point realSize = e.gc.stringExtent(text); // 实际渲染大小
						e.gc.drawText(text,centerX-realSize.x/2,centerY-realSize.y/2);
					}
				}
				if (!selectRect.isEmpty()) {
					for (int i = 0; i < selectRect.size(); i++) {
						Rectangle shape = selectRect.get(i);
						e.gc.drawRectangle(shape);
					}
				}
			}
		});

		// 上下文菜单
		canvas.addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {
				// TODO Auto-generated method stub
				// 这里的坐标是相对于窗口的，将其映射到相对于canvas
				Point p = canvas.getDisplay().map(null, canvas, e.x, e.y);
				hasShapeSelect = false;
				selectRect.clear();
				canvas.setMenu(null);
				for (int i = 0; i < shapesList.size(); i++) {
					CShape shape = shapesList.get(i);
					if (shape.isOnShape(p.x, p.y)) {
						hasShapeSelect = true;
						shape.addSelected();
						selectIndex = i;
						canvas.setMenu(menuPop);
						break;
					}
				}
				canvas.redraw();

			}

		});

	}
}
