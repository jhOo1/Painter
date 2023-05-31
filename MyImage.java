package Painter;
import java.awt.image.BufferedImage;

//Õº∆¨∂‘œÛ
public class MyImage extends BufferedImage {
	public MyImage(int width, int height, int type) {
		super(width, height, type);
		this.getGraphics().fillRect(0, 0, width, height);
	}
}
