package Painter.tool;
import Painter.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

//喷墨工具
public class PointTool extends AbstractTool {
	public PointTool(MyFrame frame) {
		super(frame);
	}

	/**
	 * 画图形
	 * @param g Graphics
	 * @param x1 起点x坐标
	 * @param y1 起点y坐标
	 * @param x2 终点x坐标
	 * @param y2 终点y坐标
	 * @return void
	 */
	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		// 计算起点
		int x = x2 > x1 ? x1 : x2;
		int y = y2 > y1 ? y1 : y2;
		int max = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
		// 画点
		g.fillOval(x, y, max , max);
	}
}
