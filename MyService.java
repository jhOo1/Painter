package Painter;
import Painter.tool.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

//画图工具处理逻辑类(非工具)
public class MyService {
	private MyFileChooser fileChooser = new MyFileChooser();
	//获取屏幕的分辨率
	public Dimension getScreenSize() {
		Toolkit dt = Toolkit.getDefaultToolkit();
		return dt.getScreenSize();
	}

	//repaint
	public void repaint(Graphics g, BufferedImage bufferedImage) {
		int drawWidth = bufferedImage.getWidth();
		int drawHeight = bufferedImage.getHeight();
		Dimension screenSize = getScreenSize();
		// 设置非绘画区的颜色
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, (int) screenSize.getWidth() * 10, (int) screenSize.getHeight() * 10);
		// 设置拖动点的颜色
		g.setColor(Color.BLACK);
		g.fillRect(drawWidth, drawHeight, 4, 4);
		g.fillRect(drawWidth, drawHeight / 2 - 2, 4, 4);
		g.fillRect(drawWidth / 2 - 2, drawHeight, 4, 4);
		// 把缓冲的图片绘画出来
		g.drawImage(bufferedImage, 0, 0, drawWidth, drawHeight, null);
	}


	// 设置JViewport
	public static void setViewport(JScrollPane scroll, JPanel panel, int width, int height) {
		// 新建一个JViewport
		JViewport viewport = new JViewport();
		// 设置viewport的大小
		panel.setPreferredSize(new Dimension(width + 50, height + 50));
		// 设置viewport
		viewport.setView(panel);
		scroll.setViewport(viewport);
	}

	// 保存图片
	public void save(MyFrame frame) {
		// 如果选择保存
		if (fileChooser.showSaveDialog(frame) == MyFileChooser.APPROVE_OPTION) {
			// 获取当前路径
			File currentDirectory = fileChooser.getCurrentDirectory();
			// 获取文件名
			String fileName = fileChooser.getSelectedFile().getName();
			// 获取后缀名
			String suf = fileChooser.getSuf();
			// 组合保存路径
			String savePath = currentDirectory + "\\" + fileName + "." + suf;
			try {
					// 将图片写到保存路径
					ImageIO.write(frame.getBufferedImage(), suf, new File(savePath));
				} catch (java.io.IOException ie) {
					ie.printStackTrace();
				}
				// 设置保存后的窗口标题
				frame.setTitle(fileName + "." + suf + " - 画图");
			}
		}

	//打开图片
	public void open(MyFrame frame) {
		save(frame);
		// 如果打开一个文件
		if (fileChooser.showOpenDialog(frame) == MyFileChooser.APPROVE_OPTION) {
			// 获取选择的文件
			File file = fileChooser.getSelectedFile();
			// 设置当前文件夹
			fileChooser.setCurrentDirectory(file);
			BufferedImage image = null;
			try {
				// 从文件读取图片
				image = ImageIO.read(file);
			} catch (java.io.IOException e) {
				e.printStackTrace();
				return;
			}
			// 宽，高
			int width = image.getWidth();
			int height = image.getHeight();
			AbstractTool.drawWidth = width;
			AbstractTool.drawHeight = height;
			// 创建一个MyImage
			MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 把读取到的图片画到myImage上面
			myImage.getGraphics().drawImage(image, 0, 0, width, height, null);
			frame.setBufferedImage(myImage);
			// repaint
			frame.getDrawSpace().repaint();
			// 重新设置viewport
			MyService.setViewport(frame.getScroll(), frame.getDrawSpace(), width, height);
			// 设置保存后的窗口标题
			frame.setTitle(fileChooser.getSelectedFile().getName()+"——画图");
		}
	}

	//新图片
	public void createGraphics(MyFrame frame) {
		save(frame);
		// 宽，高
		int width = (int) getScreenSize().getWidth() / 2;
		int height = (int) getScreenSize().getHeight() / 2;
		AbstractTool.drawWidth = width;
		AbstractTool.drawHeight = height;
		// 创建一个MyImage
		MyImage myImage = new MyImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = myImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		frame.setBufferedImage(myImage);
		// repaint
		frame.getDrawSpace().repaint();
		// 重新设置viewport
		MyService.setViewport(frame.getScroll(), frame.getDrawSpace(),
				width, height);
		// 设置保存后的窗口标题
		frame.setTitle("画图");
	}


	// 编辑颜色
	public void editColor(MyFrame frame) {
		// 获取颜色
		Color color = JColorChooser.showDialog(new JColorChooser(), "编辑颜色", Color.BLACK);
		// 设置工具的颜色
		AbstractTool.color = color;
		// 设置目前显示的颜色
		frame.getCurrentColorPanel().setBackground(color);
	}

	//退出
	public void exit(MyFrame frame) {
		save(frame);
		System.exit(0);
	}

	//设置是否可见
	public void setVisible(JPanel panel) {
		boolean b = panel.isVisible() ? false : true;
		panel.setVisible(b);
	}

	//处理菜单事件
	public void menuDo(MyFrame frame, String cmd) {
		if (cmd.equals("编辑颜色")) editColor(frame);
		if (cmd.equals("工具箱(T)")) setVisible(frame.getToolPanel());
		if (cmd.equals("颜料盒(C)")) setVisible(frame.getColorPanel());
		if (cmd.equals("新建(N)")) createGraphics(frame);
		if (cmd.equals("打开(O)")) open(frame);
		if (cmd.equals("保存(S)")) save(frame);
		if (cmd.equals("退出(X)")) exit(frame);
	}
}