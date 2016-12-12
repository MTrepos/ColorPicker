package colorpicker;

import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import colorpicker.event.MousePointEvent;
import colorpicker.event.MousePointEventListener;

public class MouseInfoPanel extends JPanel implements MousePointEventListener{

	private JTextField mouseXTextField;
	private JTextField mouseYTextField;

	public MouseInfoPanel(){
		this.setVisible(true);

		this.setLayout(new GridLayout(1, 4));
		this.add(new JLabel(" X : "));
		mouseXTextField = new JTextField();
		this.add(mouseXTextField);
		this.add(new JLabel(" Y : "));
		mouseYTextField = new JTextField();
		this.add(mouseYTextField);

		this.validate();
	}

	@Override
	public void mousePointChanged(MousePointEvent mpe) {
		Point p = mpe.getPoint();
		this.mouseXTextField.setText(Integer.valueOf(p.x).toString());
		this.mouseYTextField.setText(Integer.valueOf(p.y).toString());
	}

}
