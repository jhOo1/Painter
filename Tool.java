package Painter.tool;
import Painter.*;
import java.awt.event.MouseEvent;

//所有工具的接口
public interface Tool {
	// 拖动鼠标
	public void mouseDragged(MouseEvent e);

	//移动鼠标
	public void mouseMoved(MouseEvent e);

	//松开鼠标
	public void mouseReleased(MouseEvent e);

	//按下鼠标
	public void mousePressed(MouseEvent e);

	//点击
	public void mouseClicked(MouseEvent e);
}