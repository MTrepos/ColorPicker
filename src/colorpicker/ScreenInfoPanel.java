package colorpicker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import colorpicker.event.MousePointEvent;
import colorpicker.event.MousePointEventListener;

public class ScreenInfoPanel extends JPanel implements MousePointEventListener{

	public ScreenInfoPanel(){
		this.setVisible(true);
		this.setPreferredSize(new Dimension(31, 31));
		this.validate();
	}

	@Override
	public void paintComponent(Graphics g){

		super.paintComponent(g);

		DataStore ds = DataStore.getInstance();
		Robot r = ds.getRobot();

		Graphics2D g2 = (Graphics2D) g;

		// 1. パネルのクリア
		g2.setBackground(Color.WHITE);
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());

		// 2. マウス付近の±15pixel内のスクリーンショットを得る
		Point p = MouseInfo.getPointerInfo().getLocation();
		BufferedImage img = r.createScreenCapture(new Rectangle(p.x-15, p.y-15, 30, 30));

		// 3. スクリーンショットで撮ったデータを加工する
		Graphics2D g2img = img.createGraphics();
		g2img.setColor(Color.LIGHT_GRAY);
		g2img.drawLine(14, 0, 14, 31);
		g2img.drawLine(16, 0, 16, 31);
		g2img.drawLine(0, 14, 31, 14);
		g2img.drawLine(0, 16, 31, 16);
		g2img.dispose();

		// 4. パネルに書き込む
		g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

		g2.dispose();
	}

	@Override
	public void mousePointChanged(MousePointEvent mpe) {
		this.repaint();
	}

}
