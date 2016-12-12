package colorpicker.event;

import java.awt.Point;

public class MousePointEvent{

	private final Point point;

	public MousePointEvent(Point point){
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
}