package Painter;
import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static String position;
	public static String section;
	public static void main(String[] args) throws IOException {
		Scanner sc=new Scanner(System.in);
		position = sc.nextLine();
		//section = sc.nextLine();
		//position ="conf\\Painter-1.properties";
		//position ="conf\\Painter-2.ini";
		//position ="conf\\Painter.ini";
		//section = "Painter-1";
		JFrame frame = new MyFrame("Painter¡ª¡ª»­Í¼");
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}