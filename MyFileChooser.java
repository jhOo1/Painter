package Painter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;

// 文件对话框,打开文件
public class MyFileChooser extends JFileChooser {

	//使用默认路径创建一个ImageFileChooser
	public MyFileChooser() {
		setAcceptAllFileFilterUsed(false);
		addFilter();
	}
	//获取后缀名
	public String getSuf() {
		// 获取文件过滤对象
		FileFilter fileFilter = this.getFileFilter();
		String desc = fileFilter.getDescription();
		String[] sufarr = desc.split(" ");
		String suf = sufarr[0].equals("所有图形文件") ? "" : sufarr[0];
		return suf.toLowerCase();
	}

	// 增加文件过滤器
	private void addFilter() {
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP" }, "BMP (*.BMP)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".JPG"}, "JPG (*.JPG)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".PNG" }, "PNG (*.PNG)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP", ".JPG", ".PNG"}, "所有图形文件"));
	}

	class MyFileFilter extends FileFilter {
		// 后缀名数组
		String[] suffarr;
		// 描述
		String decription;

		//用包含后缀名的数组与描述创建一个MyFileFilter
		public MyFileFilter(String[] suffarr, String decription) {
			this.suffarr = suffarr;
			this.decription = decription;
		}
		//重写boolean accept( File f )方法
		public boolean accept(File f) {
			// 如果文件的后缀名合法，返回true
			for (String s : suffarr)
				if (f.getName().toUpperCase().endsWith(s)) return true;
			// 如果是目录，返回true,或者返回false
			return f.isDirectory();
		}

		//获取描述信息
		public String getDescription() {
			return this.decription;
		}
	}
}