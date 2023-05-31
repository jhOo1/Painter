package Painter.tool;
import Painter.*;
import java.awt.Graphics;

// 椭圆形工具
public class RoundTool extends AbstractTool {
	public RoundTool(MyFrame frame) {
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
		// 画椭圆
		g.drawOval(x, y, Math.abs(x1 - x2), Math.abs(y1 - y2));
	}
}
