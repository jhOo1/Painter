package Painter.tool;
import Painter.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;

// ÏðÆ¤²Á¹¤¾ß

public class EraserTool extends AbstractTool {
	public EraserTool(MyFrame frame) {
		super(frame);
	}

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
//ÍÏ¶¯Êó±ê
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		Graphics g = getFrame().getBufferedImage().getGraphics();
		int x = 0;
		int y = 0;
		// ÏðÆ¤²Á
		if (getPressX() > 0 && getPressY() > 0) {
			g.setColor(Color.WHITE);
			x = e.getX() - getPressX() > 0 ? getPressX() : e.getX();
			y = e.getY() - getPressY() > 0 ? getPressY() : e.getY();
			g.fillRect(x - size, y - size, Math.abs(e.getX() - getPressX()) + size, Math.abs(e.getY() - getPressY()) + size);
			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}

}
