package Painter.tool;
import Painter.*;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;

//���߳�����

public abstract class AbstractTool implements Tool {
	// ����MyFrame
	private MyFrame frame = null;
	// ���廭��Ŀ�
	public static int drawWidth = 0;
	// ���廭��ĸ�
	public static int drawHeight = 0;
	// ����Ĭ�����ָ��
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	// ��������x����
	private int pressX = -1;
	// ��������y����
	private int pressY = -1;
	// ��ɫ
	public static Color color = Color.BLACK;

	public AbstractTool(MyFrame frame) {
		this.frame = frame;
		AbstractTool.drawWidth = frame.getBufferedImage().getWidth();
		AbstractTool.drawHeight = frame.getBufferedImage().getHeight();
	}


	//��ȡMyFrame
	public MyFrame getFrame() {
		return this.frame;
	}

    //��ȡĬ�����ָ��
	public Cursor getDefaultCursor() {
		return this.defaultCursor;
	}

	//����x����
	public void setPressX(int x) {
		this.pressX = x;
	}

	//����y����
	public void setPressY(int y) {
		this.pressY = y;
	}

	// ��ȡx����
	public int getPressX() {
		return this.pressX;
	}

	//��ȡy����
	public int getPressY() {
		return this.pressY;
	}

	//�϶����
	public void mouseDragged(MouseEvent e) {
		// �϶�ͼ�α߽�
		dragBorder(e);
		// ��ͼ
		Graphics g = getFrame().getDrawSpace().getGraphics();
		createShape(e, g);
	}

	//�ƶ����
	public void mouseMoved(MouseEvent e) {
		// ��ȡ������ڵ�x��y����
		int x = e.getX();
		int y = e.getY();
		// ��ȡĬ�����ָ��
		Cursor cursor = getDefaultCursor();
		// ������ָ�������½�
		if (x > AbstractTool.drawWidth - 4 && x < AbstractTool.drawWidth + 4
				&& y > AbstractTool.drawHeight - 4
				&& y < AbstractTool.drawHeight + 4) {
			// �����ָ��ı�Ϊ�����϶���״
			cursor = new Cursor(Cursor.NW_RESIZE_CURSOR);
		}
		// ������ָ��������
		if (x > AbstractTool.drawWidth - 4 && x < AbstractTool.drawWidth + 4
				&& y > (int) AbstractTool.drawHeight / 2 - 4
				&& y < (int) AbstractTool.drawHeight / 2 + 4) {
			// �����ָ��ı�Ϊ���϶���״
			cursor = new Cursor(Cursor.W_RESIZE_CURSOR);
		}
		// ������ָ��������
		if (y > AbstractTool.drawHeight - 4 && y < AbstractTool.drawHeight + 4
				&& x > (int) AbstractTool.drawWidth / 2 - 4
				&& x < (int) AbstractTool.drawWidth / 2 + 4) {
			// �����ָ��ı�Ϊ���϶���״
			cursor = new Cursor(Cursor.S_RESIZE_CURSOR);
		}
		// �������ָ������
		getFrame().getDrawSpace().setCursor(cursor);
	}

	//�ɿ����
	public void mouseReleased(MouseEvent e) {
		// ��ͼ
		Graphics g = getFrame().getBufferedImage().getGraphics();
		createShape(e, g);
		// ��pressX��pressY����Ϊ��ʼֵ
		setPressX(-1);
		setPressY(-1);
		// �ػ�
		getFrame().getDrawSpace().repaint();
	}

	//��ͼ��
	private void createShape(MouseEvent e, Graphics g) {
		// ���λ���ڻ�����
		if (getPressX() > 0 && getPressY() > 0 && e.getX() > 0
				&& e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			// ������ͼƬ�ػ�
			g.drawImage(getFrame().getBufferedImage(), 0, 0,
					AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			// ������ɫ
			g.setColor(AbstractTool.color);
			// ��ͼ��
			draw(g, getPressX(), getPressY(), e.getX(), e.getY());
		}
	}

	//�������
	public void mousePressed(MouseEvent e) {
		// ���λ����ͼƬ��Χ�ڣ����ð��µ�����
		if (e.getX() > 0 && e.getX() < AbstractTool.drawWidth && e.getY() > 0
				&& e.getY() < AbstractTool.drawHeight) {
			setPressX(e.getX());
			setPressY(e.getY());
		}
	}

	//���
	public void mouseClicked(MouseEvent e) {
	}

	//��ͼ��
	public void draw(Graphics g, int x1, int y1, int x2, int y2) {}

	//�϶�ͼ�α߽�
	private void dragBorder(MouseEvent e) {
		// ��ȡ������ڵ�x��y����
		int cursorType = getFrame().getDrawSpace().getCursor().getType();
		int x = cursorType == Cursor.S_RESIZE_CURSOR ? AbstractTool.drawWidth : e.getX();
		int y = cursorType == Cursor.W_RESIZE_CURSOR ? AbstractTool.drawHeight : e.getY();
		MyImage img = null;
		// ������ָ�����϶�״̬
		if ((cursorType == Cursor.NW_RESIZE_CURSOR || cursorType == Cursor.W_RESIZE_CURSOR || cursorType == Cursor.S_RESIZE_CURSOR) && (x > 0 && y > 0)) {
			// �ı�ͼ���С
			img = new MyImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.getGraphics();
			g.setColor(Color.WHITE);
			g.drawImage(getFrame().getBufferedImage(), 0, 0, AbstractTool.drawWidth, AbstractTool.drawHeight, null);
			getFrame().setBufferedImage(img);
			// ���û����Ĵ�С
			AbstractTool.drawWidth = x;
			AbstractTool.drawHeight = y;
			// ����viewport
			MyService.setViewport(frame.getScroll(), frame.getDrawSpace(), x, y);
		}
	}
}