package Painter.tool;
import Painter.*;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

//工具抽象类

public abstract class AbstractTool implements Tool {
	// 定义MyFrame
	private MyFrame frame = null;
	// 定义画板的宽
	public static int drawWidth = 0;
	// 定义画板的高
	public static int drawHeight = 0;
	// 定义默认鼠标指针
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	// 按下鼠标的x坐标
	private int pressX = -1;
	// 按下鼠标的y坐标
	private int pressY = -1;
	// 颜色
	public static Color color = Color.BLACK;

	public AbstractTool(MyFrame frame) {
		this.frame = frame;
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
	}


	//获取MyFrame
	public MyFrame getFrame() {
		return this.frame;
	}

    //获取默认鼠标指针
	public Cursor getDefaultCursor() {
		return this.defaultCursor;
	}

	//设置x坐标
	public void setPressX(int x) {
		this.pressX = x;
	}

	//设置y坐标
	public void setPressY(int y) {
		this.pressY = y;
	}

	// 获取x坐标
	public int getPressX() {
		return this.pressX;
	}

	//获取y坐标
	public int getPressY() {
		return this.pressY;
	}

	//拖动鼠标
	public void mouseDragged(MouseEvent e) {
		// 拖动图形边界
		dragBorder(e);
		// 画图
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e, g);
	}

	//移动鼠标
	public void mouseMoved(MouseEvent e) {
		// 获取鼠标现在的x与y坐标
		int x = e.getX();
		int y = e.getY();
		// 获取默认鼠标指针
		Cursor cursor = getDefaultCursor();
		// 如果鼠标指针在右下角
		if (x > AbstractTool.drawWidth - 4 && x < AbstractTool.drawWidth + 4
				&& y > AbstractTool.drawHeight - 4
				&& y < AbstractTool.drawHeight + 4) {
			// 将鼠标指针改变为右下拖动形状
			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
		}
		// 如果鼠标指针在右中
		if (x > AbstractTool.drawWidth - 4 && x < AbstractTool.drawWidth + 4
				&& y > (int) AbstractTool.drawHeight / 2 - 4
				&& y < (int) AbstractTool.drawHeight / 2 + 4) {
			// 将鼠标指针改变为右拖动形状
			cursor = new Cursor(Cursor.W_RESIZE_CURSOR);
		}
		// 如果鼠标指针在下中
		if (y > AbstractTool.drawHeight - 4 && y < AbstractTool.drawHeight + 4
				&& x > (int) AbstractTool.drawWidth / 2 - 4
				&& x < (int) AbstractTool.drawWidth / 2 + 4) {
			// 将鼠标指针改变为下拖动形状
			cursor = new Cursor(Cursor.S_RESIZE_CURSOR);
		}
		// 设置鼠标指针类型
		getFrame().getDrawSpace().setCursor(cursor);
	}

	//松开鼠标
	public void mouseReleased(MouseEvent e) {
		// 画图
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e, g);
		// 把pressX与pressY设置为初始值
		setPressX(-1);
		setPressY(-1);
		// 重绘
		getFrame().getDrawSpace().repaint();
	}

	//画图形
	private void createShape(MouseEvent e, Graphics g) {
		// 如果位置在画布内
		if (getPressX() > 0 && getPressY() > 0 && e.getX() > 0
				&& e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			// 将整张图片重画
			g.drawImage(getFrame().getBufferedImage(), 0, 0,
					AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			// 设置颜色
			g.setColor(AbstractTool.color);
			// 画图形
			draw(g, getPressX(), getPressY(), e.getX(), e.getY());
		}
	}

	//按下鼠标
	public void mousePressed(MouseEvent e) {
		// 如果位置在图片范围内，设置按下的坐标
		if (e.getX() > 0 && e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			setPressX(e.getX());
			setPressY(e.getY());
		}
	}

	//点击
	public void mouseClicked(MouseEvent e) {
	}

	//画图形
	public void draw(Graphics g, int x1, int y1, int x2, int y2) {}

	//拖动图形边界
	private void dragBorder(MouseEvent e) {
		// 获取鼠标现在的x与y坐标
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth : e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight : e.getY();
		MyImage img = null;
		// 如果鼠标指针是拖动状态
		if ((cursorType == Cursor.NW_RESIZE_CURSOR || cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR) && (x > 0 && y > 0)) {
			// 改变图像大小
			img = new MyImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(), 0, 0, AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			getFrame().setBufferedImage(img);
			// 设置画布的大小
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			// 设置viewport
			MyService.setViewport(frame.getScroll(), frame.getDrawSpace(), x, y);
		}
	}
}