package colorpicker;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Robot;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import colorpicker.event.MousePointEvent;
import colorpicker.event.MousePointEventListener;
import colorpicker.util.ColorUtil;

public class ColorInfoPanel extends JPanel implements MousePointEventListener{

	private JTextField colorTextField;
	private JPanel colorPaintPane;

	public ColorInfoPanel(){
		this.setVisible(true);

		this.setLayout(new GridLayout(1, 3));
		this.add(new JLabel("Color : "));
		colorTextField = new JTextField();
		this.add(colorTextField);

		this.colorPaintPane = new JPanel(){
		};
		this.add(colorPaintPane);

		this.validate();
	}

	@Override
	public void mousePointChanged(MousePointEvent mpe) {
		Point p = mpe.getPoint();
		Robot r = DataStore.getInstance().getRobot();

		Color c = r.getPixelColor(p.x, p.y);
		String colorCode = ColorUtil.toHTMLColorCode(c);
		this.colorTextField.setText(colorCode);

		this.colorPaintPane.setBackground(c);
		this.colorPaintPane.repaint();

	}

}
