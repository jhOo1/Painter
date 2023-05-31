package Painter;
import Painter.tool.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

//��ͼ���ߴ����߼���(�ǹ���)
public class MyService {
	private MyFileChooser fileChooser = new MyFileChooser();
	//��ȡ��Ļ�ķֱ���
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}

	//repaint
	public void repaint(Graphics g, BufferedImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		// ���÷ǻ滭������ɫ
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int) screenSize.getWidth() * 10, (int) screenSize.getHeight() * 10);
		// �����϶������ɫ
		g.setColor(Color.BLACK);
		g.fillRect(drawWidth, drawHeight, 4, 4);
		g.fillRect(drawWidth, drawHeight / 2 - 2, 4, 4);
		g.fillRect(drawWidth / 2 - 2, drawHeight, 4, 4);
		// �ѻ����ͼƬ�滭����
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}


	// ����JViewport
	public static void setViewport(JScrollPane scroll, JPanel panel, int width, int height) {
		// �½�һ��JViewport
		JViewport viewport = new JViewport();
		// ����viewport�Ĵ�С
		panel.setPreferredSize(new Dimension(width + 50, height + 50));
		// ����viewport
		viewport.setView(panel);
		scroll.setViewport(viewport);
	}

	// ����ͼƬ
	public void save(MyFrame frame) {
		// ���ѡ�񱣴�
		if (fileChooser.showSaveDialog(frame) == MyFileChooser.APPROVE_OPTION) {
			// ��ȡ��ǰ·��
			File currentDirectory = fileChooser.getCurrentDirectory();
			// ��ȡ�ļ���
			String fileName = fileChooser.getSelectedFile().getName();
			// ��ȡ��׺��
			String suf = fileChooser.getSuf();
			// ��ϱ���·��
			String savePath = currentDirectory + "\\" + fileName + "." + suf;
			try {
					// ��ͼƬд������·��
					ImageIO.write(frame.getBufferedImage(), suf, new File(savePath));
				} catch (java.io.IOException ie) {
					ie.printStackTrace();
				}
				// ���ñ����Ĵ��ڱ���
				frame.setTitle(fileName + "." + suf + " - ��ͼ");
			}
		}

	//��ͼƬ
	public void open(MyFrame frame) {
		save(frame);
		// �����һ���ļ�
		if (fileChooser.showOpenDialog(frame) == MyFileChooser.APPROVE_OPTION) {
			// ��ȡѡ����ļ�
			File file = fileChooser.getSelectedFile();
			// ���õ�ǰ�ļ���
			fileChooser.setCurrentDirectory(file);
			BufferedImage image = null;
			try {
				// ���ļ���ȡͼƬ
				image = ImageIO.read(file);
			} catch (java.io.IOException e) {
				e.printStackTrace();
				return;
			}
			// ����
			int width = image.getWidth();
			int height = image.getHeight();
			AbstractTool.drawWidth = width;
			AbstractTool.drawHeight = height;
			// ����һ��MyImage
			MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
			// �Ѷ�ȡ����ͼƬ����myImage����
			myImage.getGraphics().drawImage(image, 0, 0, width, height, null);
			frame.setBufferedImage(myImage);
			// repaint
			frame.getDrawSpace().repaint();
			// ��������viewport
			MyService.setViewport(frame.getScroll(), frame.getDrawSpace(), width, height);
			// ���ñ����Ĵ��ڱ���
			frame.setTitle(fileChooser.getSelectedFile().getName()+"������ͼ");
		}
	}

	//��ͼƬ
	public void createGraphics(MyFrame frame) {
		save(frame);
		// ����
		int width = (int) getScreenSize().getWidth() / 2;
		int height = (int) getScreenSize().getHeight() / 2;
		AbstractTool.drawWidth = width;
		AbstractTool.drawHeight = height;
		// ����һ��MyImage
		MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = myImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		frame.setBufferedImage(myImage);
		// repaint
		frame.getDrawSpace().repaint();
		// ��������viewport
		MyService.setViewport(frame.getScroll(), frame.getDrawSpace(),
				width, height);
		// ���ñ����Ĵ��ڱ���
		frame.setTitle("��ͼ");
	}


	// �༭��ɫ
	public void editColor(MyFrame frame) {
		// ��ȡ��ɫ
		Color color = JColorChooser.showDialog(new JColorChooser(), "�༭��ɫ", Color.BLACK);
		// ���ù��ߵ���ɫ
		AbstractTool.color = color;
		// ����Ŀǰ��ʾ����ɫ
		frame.getCurrentColorPanel().setBackground(color);
	}

	//�˳�
	public void exit(MyFrame frame) {
		save(frame);
		System.exit(0);
	}

	//�����Ƿ�ɼ�
	public void setVisible(JPanel panel) {
		boolean b = panel.isVisible() ? false : true;
		panel.setVisible(b);
	}

	//����˵��¼�
	public void menuDo(MyFrame frame, String cmd) {
		if (cmd.equals("�༭��ɫ")) editColor(frame);
		if (cmd.equals("������(T)")) setVisible(frame.getToolPanel());
		if (cmd.equals("���Ϻ�(C)")) setVisible(frame.getColorPanel());
		if (cmd.equals("�½�(N)")) createGraphics(frame);
		if (cmd.equals("��(O)")) open(frame);
		if (cmd.equals("����(S)")) save(frame);
		if (cmd.equals("�˳�(X)")) exit(frame);
	}
}