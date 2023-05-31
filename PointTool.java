package Painter.tool;
import Painter.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

//��ī����
public class PointTool extends AbstractTool {
	public PointTool(MyFrame frame) {
		super(frame);
	}

	/**
	 * ��ͼ��
	 * @param g Graphics
	 * @param x1 ���x����
	 * @param y1 ���y����
	 * @param x2 �յ�x����
	 * @param y2 �յ�y����
	 * @return void
	 */
	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// �������
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		int max = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		// ����
		g.fillOval(x, y, max , max);
	}
}
