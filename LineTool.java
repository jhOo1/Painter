package Painter.tool;
import Painter.*;
import java.awt.Graphics;


public class LineTool extends AbstractTool {
	public LineTool(MyFrame frame) {
		super(frame);
		// super.setShape( new LineShape() );
	}

	// »­Í¼ÐÎ
	public void draw(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
}
