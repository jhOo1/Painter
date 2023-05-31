package Painter;
import Painter.tool.*;
import static java.awt.Color.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

//界面对象
public class MyFrame extends JFrame {
	public MyFrame(String title) throws IOException {
		super(title);
		// 初始化
		init();
	}
	// 定义画图工具处理类(非工具)
	private MyService service = new MyService();


	// 初始化屏幕的尺寸
	private Dimension screenSize = service.getScreenSize();



	// 设置默认画布
	private JPanel drawSpace = createDrawSpace();

	// 获取画布
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}


	// 设置缓冲图片
	private MyImage bufferedImage = new MyImage((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2, BufferedImage.TYPE_INT_RGB);
	//获取图片
	public MyImage getBufferedImage() {
		return this.bufferedImage;
	}
	//设置图片
	public void setBufferedImage(MyImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}


	// 设置当前使用的工具
	private Tool tool = null;
	//设置工具
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	//获取工具
	public Tool getTool() {
		return this.tool;
	}


	// 当前颜色显示面板
	private JPanel currentColorPanel = null;
	//获取颜色显示面板
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}


	// 默认JScrollPane，滚动面板
	private JScrollPane scroll = new JScrollPane(drawSpace);;
	// 获取JScroolPane
	public JScrollPane getScroll() {
		return this.scroll;
	}

	// 工具栏
	private JPanel toolPanel = createToolPanel();
	// 获取工具栏
	public JPanel getToolPanel() {
		return this.toolPanel;
	}


	// 颜色面板
	private JPanel colorPanel = createColorPanel();
	// 获取颜色面板
	public JPanel getColorPanel() {
		return this.colorPanel;
	}


	// 创建一个JMenuBar放置菜单
	private JMenuBar menuBar = createMenuBar();



	// 初始化ImageFrame
	public void init() {
		// 获取默认使用的工具
		tool = new PencilTool(this);
		// 创建鼠标运动监听器
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			// 拖动鼠标
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}
			// 移动鼠标
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		// 创建鼠标监听器
		MouseListener mouseListener = new MouseAdapter() {
			// 松开鼠标
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}
			// 按下鼠标
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}
			// 点击鼠标
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};
		//默认画板中添加监听器
		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);
		// 设置Viewport（窗口）
		MyService.setViewport(scroll, drawSpace, bufferedImage.getWidth(), bufferedImage.getHeight());
		// 设置JMenubar
		this.setJMenuBar(menuBar);
		// 将需要用到的panel加到Frame上面
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.NORTH);
	}



	//创建菜单栏
	public JMenuBar createMenuBar() {
		//创建菜单栏
		JMenuBar menuBar = new JMenuBar();
		// 菜单文字数组，与下面的menuItemArr一一对应
		String[] menuArr = { "文件(F)", "查看(V)", "颜色(C)" };
		// 菜单项文字数组
		String[][] menuItemArr = { { "新建(N)", "打开(O)", "保存(S)", "-", "退出(X)" }
				, { "工具箱(T)", "颜料盒(C)" }, { "编辑颜色" }};
		// 遍历menuArr与menuItemArr去创建菜单
		// 加给菜单的事件监听器
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.menuDo(MyFrame.this, e.getActionCommand());
			}
		};
		for (int i = 0; i < menuArr.length; i++) {
			// 新建一个JMenu菜单
			JMenu menu = new JMenu(menuArr[i]);
			for (int j = 0; j < menuItemArr[i].length; j++) {
				// 如果menuItemArr[i][j]等于"-"
				if (menuItemArr[i][j].equals("-")) {
					// 设置菜单分隔
					menu.addSeparator();
				} else {
					// 新建一个JMenuItem菜单项
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					// 把菜单项加到JMenu菜单里面
					menu.add(menuItem);
				}
			}
			// 把菜单加到JMenuBar上
			menuBar.add(menu);
		}
		return menuBar;
	}



	//添加工具栏
	//赵文件的value有没有指定形状
	public boolean Find(String text,String shapes){
//找出所有的单词
		String[] array = {".", " ", "?", "!", "\""};
		for (int i = 0; i < array.length; i++) {
			text = text.replace(array[i],",");
		}
		String[] textArray = text.split(",");
//遍历 记录
		Map map = new HashMap();
		for (int i = 0; i < textArray.length; i++) {
			String key = textArray[i];
			String key_l = key.toLowerCase();//直接将key的大写字符串转化为小写字符串
			if(!"".equals(key_l)) {//判断两个参数是否引用同一对象，不是的话，就进行
				Integer num = (Integer) map.get(key_l);
				if(num == null || num == 0){
					map.put(key_l, 1);
				}else if(num > 0){
					map.put(key_l, num+1);
				}
			}
		}
		Iterator iter = map.keySet().iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			if (key.equals(shapes)) return true;
		}
		return false;
	}
	public JPanel createToolPanel() throws IOException {
		// 创建一个JPanel
		JPanel panel = new JPanel();
		// 创建一个标题为"工具"的工具栏
		JToolBar toolBar = new JToolBar("工具");
		// 设置为垂直排列
		toolBar.setOrientation(toolBar.VERTICAL);
		// 设置为可以拖动
		toolBar.setFloatable(true);
		// 设置与边界的距离
		toolBar.setMargin(new Insets(1, 1, 1, 1));
		// 设置布局方式，网格式布局
		toolBar.setLayout(new GridLayout(6, 2, 1, 1));
		// 工具数组
		Stack <String> toolarr = new Stack<>();
		toolarr.add("PencilTool");
		toolarr.add("BrushTool");
		toolarr.add("EraserTool");
		toolarr.add("LineTool");

		//根据配置文件添加可选择的组件
		Configuration configuration = new Configuration(Main.position);
		//复杂的ini文件
//		if (Find(configuration.getValue("Shapes",Main.section),"point")){;
//			toolarr.add("PointTool");
//		}
//		if (Find(configuration.getValue("Shapes",Main.section),"rectangle")){
//			toolarr.add("RectTool");
//			toolarr.add("RoundRectTool");
//		}
//		if (Find(configuration.getValue("Shapes",Main.section),"circle")){
//			toolarr.add("RoundTool");
//		}
		//读简单的ini文件
		if (Find(configuration.getValue("Shapes"),"point")){;
			toolarr.add("PointTool");
		}
		if (Find(configuration.getValue("Shapes"),"rectangle")){
			toolarr.add("RectTool");
			toolarr.add("RoundRectTool");
		}
		if (Find(configuration.getValue("Shapes"),"circle")){
			toolarr.add("RoundTool");
		}
		//扩大
		ImageAction EnlargeAction = new ImageAction(new ImageIcon("img/" + "EnlargeTool" + ".jpg"), "EnlargeTool", this);
		JButton Enlargebutton = new JButton(EnlargeAction);
		//缩小
		ImageAction ReduceAction = new ImageAction(new ImageIcon("img/" + "ReduceTool" + ".jpg"), "ReduceTool", this);
		JButton Reducebutton = new JButton(ReduceAction);
		Enlargebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReduceAction.setEnabled(true);
				if(BrushTool.getSize()>14&&EraserTool.getSize()>14) {
					Enlargebutton.setEnabled(false);
				}
			}
		});
		Reducebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Enlargebutton.setEnabled(true);
				if(BrushTool.getSize()<4&&EraserTool.getSize()<4) {
					ReduceAction.setEnabled(false);
				}
			}
		});
		toolBar.add(Reducebutton);
		toolBar.add(Enlargebutton);
		for (int i = 0; i < toolarr.size(); i++) {
			ImageAction action = new ImageAction(new ImageIcon("img/" + toolarr.get(i) + ".jpg"), toolarr.get(i), this);
			// 以图标创建一个新的button
			JButton button = new JButton(action);
			// 把button加到工具栏中
			toolBar.add(button);
		}
		panel.add(toolBar);
		// 返回
		return panel;
	}


	//创建简单颜色选择板
	public JPanel createColorPanel() {
		// 新建一个JPanel
		JPanel panel = new JPanel();
		// 设置布局方式
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// 新建一个JToolBar
		JToolBar toolBar = new JToolBar("颜色");
		// 设置为不可拖动
		toolBar.setFloatable(true);
		// 设置与边界的距离
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		// 设置布局方式，网格式布局
		toolBar.setLayout(new GridLayout(1, 20, 2, 2));
		// Color类中的已有颜色
		Color[] colorArr = { BLACK, BLUE, CYAN, GRAY, GREEN, LIGHT_GRAY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW };
		JButton[] panelArr = new JButton[colorArr.length];
		// 正在使用的颜色
		currentColorPanel = new JPanel();
		currentColorPanel.setBackground(BLACK);
		currentColorPanel.setPreferredSize(new Dimension(20, 20));
		// 创建这些颜色的button
		for (int i = 0; i < panelArr.length; i++) {
			// 创建JButton
			panelArr[i] = new JButton(new ImageAction(colorArr[i], currentColorPanel));
			// 设置button的颜色
			panelArr[i].setBackground(colorArr[i]);
			// 把button加到toobar中
			toolBar.add(panelArr[i]);
		}
		panel.add(currentColorPanel);
		panel.add(toolBar);
		// 返回
		return panel;
	}

	//创建画图区域
	public JPanel createDrawSpace() {
		JPanel drawSpace = new DrawSpace();
		// 设置drawSpace的大小
		drawSpace.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight() - 150));
		return drawSpace;
	}

	// 画图区域
	public class DrawSpace extends JPanel {
		// 重写void paint( Graphics g )方法
		public void paint(Graphics g) {
			// draw
			service.repaint(g, bufferedImage);
		}
	}


	public class ImageAction extends AbstractAction{
		private String name = "";
		private MyFrame frame = null;
		private Color color = null;
		private Tool tool = null;
		private JPanel colorPanel = null;
		public ImageAction(Color color, JPanel colorPanel) {
			this.color = color;
			this.colorPanel = colorPanel;
		}

		public ImageAction(ImageIcon icon, String name, MyFrame frame) {
			// 调用父类构方法
			super("", icon);
			this.name = name;
			this.frame = frame;
		}

		//重写void actionPerformed( ActionEvent e )方法
		public void actionPerformed(ActionEvent e) {
			// 设置tool
			if(name=="PencilTool") tool =new PencilTool(frame);
			if(name=="EraserTool") tool = new EraserTool(frame);
			if(name=="BrushTool") tool = new BrushTool(frame);
			if(name=="LineTool") tool = new LineTool(frame) ;
			if(name=="RectTool") tool = new RectTool(frame);
			if(name=="RoundRectTool")tool = new RoundRectTool(frame);
			if(name=="PointTool") tool = new PointTool(frame);
			if(name=="RoundTool") tool = new RoundTool(frame);
			if(name=="EnlargeTool"){
				if(BrushTool.getSize()<=14) BrushTool.Enlargesize();
				if(EraserTool.getSize()<=14) EraserTool.Enlargesize();
			}
			if(name=="ReduceTool"){
				if(BrushTool.getSize()>=4) BrushTool.Reducesize();
				if(EraserTool.getSize()>=4) EraserTool.Reducesize();
			}
			if (tool != null) {
				// 设置正在使用的tool
				frame.setTool(tool);
			}
			if (color != null) {
				// 设置正在使用的颜色
				AbstractTool.color = color;
				colorPanel.setBackground(color);
			}
		}
	}
}