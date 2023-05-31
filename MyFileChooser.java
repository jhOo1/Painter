package Painter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.File;

// �ļ��Ի���,���ļ�
public class MyFileChooser extends JFileChooser {

	//ʹ��Ĭ��·������һ��ImageFileChooser
	public MyFileChooser() {
		setAcceptAllFileFilterUsed(false);
		addFilter();
	}
	//��ȡ��׺��
	public String getSuf() {
		// ��ȡ�ļ����˶���
		FileFilter fileFilter = this.getFileFilter();
		String desc = fileFilter.getDescription();
		String[] sufarr = desc.split(" ");
		String suf = sufarr[0].equals("����ͼ���ļ�") ? "" : sufarr[0];
		return suf.toLowerCase();
	}

	// �����ļ�������
	private void addFilter() {
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP" }, "BMP (*.BMP)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".JPG"}, "JPG (*.JPG)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".PNG" }, "PNG (*.PNG)"));
		this.addChoosableFileFilter(new MyFileFilter(new String[] { ".BMP", ".JPG", ".PNG"}, "����ͼ���ļ�"));
	}

	class MyFileFilter extends FileFilter {
		// ��׺������
		String[] suffarr;
		// ����
		String decription;

		//�ð�����׺������������������һ��MyFileFilter
		public MyFileFilter(String[] suffarr, String decription) {
			this.suffarr = suffarr;
			this.decription = decription;
		}
		//��дboolean accept( File f )����
		public boolean accept(File f) {
			// ����ļ��ĺ�׺���Ϸ�������true
			for (String s : suffarr)
				if (f.getName().toUpperCase().endsWith(s)) return true;
			// �����Ŀ¼������true,���߷���false
			return f.isDirectory();
		}

		//��ȡ������Ϣ
		public String getDescription() {
			return this.decription;
		}
	}
}