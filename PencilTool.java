package Painter.tool;
import Painter.*;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

// Ǧ�ʹ���
public class PencilTool extends AbstractTool {
	public PencilTool(MyFrame frame) {
		super(frame);
	}
	//�϶����
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		// ��ȡͼƬ��Graphics����
		Graphics g = getFrame().getBufferedImage().getGraphics();
		if (getPressX() > 0 && getPressY() > 0) {
			g.setColor(AbstractTool.color);
			g.drawLine(getPressX(), getPressY(), e.getX(), e.getY());
			setPressX(e.getX());
			setPressY(e.getY());
			getFrame().getDrawSpace().repaint();
		}
	}
}
