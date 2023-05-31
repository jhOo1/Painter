package Painter;
import Painter.tool.*;
import static java.awt.Color.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

//�������
public class MyFrame extends JFrame {
	public MyFrame(String title) throws IOException {
		super(title);
		// ��ʼ��
		init();
	}
	// ���廭ͼ���ߴ�����(�ǹ���)
	private MyService service = new MyService();


	// ��ʼ����Ļ�ĳߴ�
	private Dimension screenSize = service.getScreenSize();



	// ����Ĭ�ϻ���
	private JPanel drawSpace = createDrawSpace();

	// ��ȡ����
	public JPanel getDrawSpace() {
		return this.drawSpace;
	}


	// ���û���ͼƬ
	private MyImage bufferedImage = new MyImage((int) screenSize.getWidth() / 2, (int) screenSize.getHeight() / 2, BufferedImage.TYPE_INT_RGB);
	//��ȡͼƬ
	public MyImage getBufferedImage() {
		return this.bufferedImage;
	}
	//����ͼƬ
	public void setBufferedImage(MyImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}


	// ���õ�ǰʹ�õĹ���
	private Tool tool = null;
	//���ù���
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	//��ȡ����
	public Tool getTool() {
		return this.tool;
	}


	// ��ǰ��ɫ��ʾ���
	private JPanel currentColorPanel = null;
	//��ȡ��ɫ��ʾ���
	public JPanel getCurrentColorPanel() {
		return this.currentColorPanel;
	}


	// Ĭ��JScrollPane���������
	private JScrollPane scroll = new JScrollPane(drawSpace);;
	// ��ȡJScroolPane
	public JScrollPane getScroll() {
		return this.scroll;
	}

	// ������
	private JPanel toolPanel = createToolPanel();
	// ��ȡ������
	public JPanel getToolPanel() {
		return this.toolPanel;
	}


	// ��ɫ���
	private JPanel colorPanel = createColorPanel();
	// ��ȡ��ɫ���
	public JPanel getColorPanel() {
		return this.colorPanel;
	}


	// ����һ��JMenuBar���ò˵�
	private JMenuBar menuBar = createMenuBar();



	// ��ʼ��ImageFrame
	public void init() {
		// ��ȡĬ��ʹ�õĹ���
		tool = new PencilTool(this);
		// ��������˶�������
		MouseMotionListener motionListener = new MouseMotionAdapter() {
			// �϶����
			public void mouseDragged(MouseEvent e) {
				tool.mouseDragged(e);
			}
			// �ƶ����
			public void mouseMoved(MouseEvent e) {
				tool.mouseMoved(e);
			}
		};
		// ������������
		MouseListener mouseListener = new MouseAdapter() {
			// �ɿ����
			public void mouseReleased(MouseEvent e) {
				tool.mouseReleased(e);
			}
			// �������
			public void mousePressed(MouseEvent e) {
				tool.mousePressed(e);
			}
			// ������
			public void mouseClicked(MouseEvent e) {
				tool.mouseClicked(e);
			}
		};
		//Ĭ�ϻ�������Ӽ�����
		drawSpace.addMouseMotionListener(motionListener);
		drawSpace.addMouseListener(mouseListener);
		// ����Viewport�����ڣ�
		MyService.setViewport(scroll, drawSpace, bufferedImage.getWidth(), bufferedImage.getHeight());
		// ����JMenubar
		this.setJMenuBar(menuBar);
		// ����Ҫ�õ���panel�ӵ�Frame����
		this.add(scroll, BorderLayout.CENTER);
		this.add(toolPanel, BorderLayout.WEST);
		this.add(colorPanel, BorderLayout.NORTH);
	}



	//�����˵���
	public JMenuBar createMenuBar() {
		//�����˵���
		JMenuBar menuBar = new JMenuBar();
		// �˵��������飬�������menuItemArrһһ��Ӧ
		String[] menuArr = { "�ļ�(F)", "�鿴(V)", "��ɫ(C)" };
		// �˵�����������
		String[][] menuItemArr = { { "�½�(N)", "��(O)", "����(S)", "-", "�˳�(X)" }
				, { "������(T)", "���Ϻ�(C)" }, { "�༭��ɫ" }};
		// ����menuArr��menuItemArrȥ�����˵�
		// �Ӹ��˵����¼�������
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.menuDo(MyFrame.this, e.getActionCommand());
			}
		};
		for (int i = 0; i < menuArr.length; i++) {
			// �½�һ��JMenu�˵�
			JMenu menu = new JMenu(menuArr[i]);
			for (int j = 0; j < menuItemArr[i].length; j++) {
				// ���menuItemArr[i][j]����"-"
				if (menuItemArr[i][j].equals("-")) {
					// ���ò˵��ָ�
					menu.addSeparator();
				} else {
					// �½�һ��JMenuItem�˵���
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(menuListener);
					// �Ѳ˵���ӵ�JMenu�˵�����
					menu.add(menuItem);
				}
			}
			// �Ѳ˵��ӵ�JMenuBar��
			menuBar.add(menu);
		}
		return menuBar;
	}



	//��ӹ�����
	//���ļ���value��û��ָ����״
	public boolean Find(String text,String shapes){
//�ҳ����еĵ���
		String[] array = {".", " ", "?", "!", "\""};
		for (int i = 0; i < array.length; i++) {
			text = text.replace(array[i],",");
		}
		String[] textArray = text.split(",");
//���� ��¼
		Map map = new HashMap();
		for (int i = 0; i < textArray.length; i++) {
			String key = textArray[i];
			String key_l = key.toLowerCase();//ֱ�ӽ�key�Ĵ�д�ַ���ת��ΪСд�ַ���
			if(!"".equals(key_l)) {//�ж����������Ƿ�����ͬһ���󣬲��ǵĻ����ͽ���
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
		// ����һ��JPanel
		JPanel panel = new JPanel();
		// ����һ������Ϊ"����"�Ĺ�����
		JToolBar toolBar = new JToolBar("����");
		// ����Ϊ��ֱ����
		toolBar.setOrientation(toolBar.VERTICAL);
		// ����Ϊ�����϶�
		toolBar.setFloatable(true);
		// ������߽�ľ���
		toolBar.setMargin(new Insets(1, 1, 1, 1));
		// ���ò��ַ�ʽ������ʽ����
		toolBar.setLayout(new GridLayout(6, 2, 1, 1));
		// ��������
		Stack <String> toolarr = new Stack<>();
		toolarr.add("PencilTool");
		toolarr.add("BrushTool");
		toolarr.add("EraserTool");
		toolarr.add("LineTool");

		//���������ļ���ӿ�ѡ������
		Configuration configuration = new Configuration(Main.position);
		//���ӵ�ini�ļ�
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
		//���򵥵�ini�ļ�
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
		//����
		ImageAction EnlargeAction = new ImageAction(new ImageIcon("img/" + "EnlargeTool" + ".jpg"), "EnlargeTool", this);
		JButton Enlargebutton = new JButton(EnlargeAction);
		//��С
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
			// ��ͼ�괴��һ���µ�button
			JButton button = new JButton(action);
			// ��button�ӵ���������
			toolBar.add(button);
		}
		panel.add(toolBar);
		// ����
		return panel;
	}


	//��������ɫѡ���
	public JPanel createColorPanel() {
		// �½�һ��JPanel
		JPanel panel = new JPanel();
		// ���ò��ַ�ʽ
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// �½�һ��JToolBar
		JToolBar toolBar = new JToolBar("��ɫ");
		// ����Ϊ�����϶�
		toolBar.setFloatable(true);
		// ������߽�ľ���
		toolBar.setMargin(new Insets(2, 2, 2, 2));
		// ���ò��ַ�ʽ������ʽ����
		toolBar.setLayout(new GridLayout(1, 20, 2, 2));
		// Color���е�������ɫ
		Color[] colorArr = { BLACK, BLUE, CYAN, GRAY, GREEN, LIGHT_GRAY, MAGENTA, ORANGE, PINK, RED, WHITE, YELLOW };
		JButton[] panelArr = new JButton[colorArr.length];
		// ����ʹ�õ���ɫ
		currentColorPanel = new JPanel();
		currentColorPanel.setBackground(BLACK);
		currentColorPanel.setPreferredSize(new Dimension(20, 20));
		// ������Щ��ɫ��button
		for (int i = 0; i < panelArr.length; i++) {
			// ����JButton
			panelArr[i] = new JButton(new ImageAction(colorArr[i], currentColorPanel));
			// ����button����ɫ
			panelArr[i].setBackground(colorArr[i]);
			// ��button�ӵ�toobar��
			toolBar.add(panelArr[i]);
		}
		panel.add(currentColorPanel);
		panel.add(toolBar);
		// ����
		return panel;
	}

	//������ͼ����
	public JPanel createDrawSpace() {
		JPanel drawSpace = new DrawSpace();
		// ����drawSpace�Ĵ�С
		drawSpace.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight() - 150));
		return drawSpace;
	}

	// ��ͼ����
	public class DrawSpace extends JPanel {
		// ��дvoid paint( Graphics g )����
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
			// ���ø��๹����
			super("", icon);
			this.name = name;
			this.frame = frame;
		}

		//��дvoid actionPerformed( ActionEvent e )����
		public void actionPerformed(ActionEvent e) {
			// ����tool
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
				// ��������ʹ�õ�tool
				frame.setTool(tool);
			}
			if (color != null) {
				// ��������ʹ�õ���ɫ
				AbstractTool.color = color;
				colorPanel.setBackground(color);
			}
		}
	}
}