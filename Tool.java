package Painter.tool;
import Painter.*;
import java.awt.event.MouseEvent;

//���й��ߵĽӿ�
public interface Tool {
	// �϶����
	public void mouseDragged(MouseEvent e);

	//�ƶ����
	public void mouseMoved(MouseEvent e);

	//�ɿ����
	public void mouseReleased(MouseEvent e);

	//�������
	public void mousePressed(MouseEvent e);

	//���
	public void mouseClicked(MouseEvent e);
}