package Painter.tool;
import Painter.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

//刷子工具
public class BrushTool extends AbstractTool {
	public BrushTool(MyFrame frame) {
		super(frame);
	}

	// 画笔大小，可在这里进行调节
	private static int size = 4;
	public static void Enlargesize(){
		size+=2;
	}
	public static void Reducesize(){
		size-=2;
	}
	public static int getSize(){
		return size;
	}
	//拖动鼠标
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		int x = 0;
		int y = 0;
		if (getPressX() > 0 && getPressY() > 0
				&& e.getX() < AbstractTool.drawWidth && e.getY() < AbstractTool.drawHeight) {
			g.setColor(AbstractTool.color);
			x = e.getX() - getPressX() > 0 ? getPressX() : e.getX();
			y = e.getY() - getPressY() > 0 ? getPressY() : e.getY();
			g.fillRect(x - size, y - size, Math.abs(e.getX() - getPressX()) + size,
					Math.abs(e.getY() - getPressY()) + size);
			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}
}
