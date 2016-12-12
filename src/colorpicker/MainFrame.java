package colorpicker;

import java.awt.BorderLayout;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyEventDispatcher;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import colorpicker.event.MousePointEvent;
import colorpicker.event.MousePointEventListener;

public class MainFrame extends JFrame{

	EventListenerList elList = new EventListenerList();

	private MouseInfoPanel mouseInfoPanel;
	private ScreenInfoPanel screenPanel;
	private ColorInfoPanel colorInfoPanel;

	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ColorPicker");
		this.setLayout(new BorderLayout());
		this.setBounds(100, 100, 260, 260);
		this.setVisible(true);

		//色情報パネル
		this.colorInfoPanel = new ColorInfoPanel();
		this.add(colorInfoPanel, BorderLayout.NORTH);

		//スクリーン情報パネル
		this.screenPanel = new ScreenInfoPanel();
		this.add(this.screenPanel, BorderLayout.CENTER);

		//マウス情報パネル
		this.mouseInfoPanel = new MouseInfoPanel();
		this.add(this.mouseInfoPanel, BorderLayout.SOUTH);

		//リスナーの追加
		this.elList.add(MousePointEventListener.class, colorInfoPanel);
		this.elList.add(MousePointEventListener.class, screenPanel);
		this.elList.add(MousePointEventListener.class, mouseInfoPanel);

		//バックグラウンドのキー操作を受け付ける
		KeyEventDispatcher dispatcher = new KeyEventDispatcher(){

			private boolean isCTRLPressed;
			private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				int eventType = e.getID();
				int keyCode = e.getKeyCode();

				Robot r = DataStore.getInstance().getRobot();
				Point p = MouseInfo.getPointerInfo().getLocation();

				if(eventType == KeyEvent.KEY_PRESSED){
					//System.out.println("Key : " + keyCode + " Pressed");
					switch(keyCode){
					case KeyEvent.VK_UP:
						r.mouseMove(p.x, p.y-1);
						break;
					case KeyEvent.VK_DOWN:
						r.mouseMove(p.x, p.y+1);
						break;
					case KeyEvent.VK_LEFT:
						r.mouseMove(p.x-1, p.y);
						break;
					case KeyEvent.VK_RIGHT:
						r.mouseMove(p.x+1, p.y);
						break;
//					case KeyEvent.VK_CONTROL:
//						isCTRLPressed = true;
//						break;
//					case KeyEvent.VK_C:
//						if(isCTRLPressed){
//							Color c = r.getPixelColor(p.x, p.y);
//							String colorCode = ColorUtil.toHTMLColorCode(c);
//							System.out.println("colorCode");
//							StringSelection selection = new StringSelection(colorCode);
//							clipboard.setContents(selection, selection);
//						}
//						break;
					}
				}
//				if(eventType == KeyEvent.KEY_RELEASED){
//					switch(keyCode){
//					case KeyEvent.VK_CONTROL:
//						isCTRLPressed = false;
//						break;
//					}
//				}
				return false;
			}

		};
		DefaultKeyboardFocusManager
		.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(dispatcher);

		//マウス位置を別スレッドで監視
		new Thread(){
			@Override
			public void run(){
				Point p = MouseInfo.getPointerInfo().getLocation();

				while(true){
					Point pCur = MouseInfo.getPointerInfo().getLocation();
					if(!p.equals(pCur)){
						for(MousePointEventListener mie : elList.getListeners(MousePointEventListener.class)){
							mie.mousePointChanged(new MousePointEvent(pCur));
						}
						p = pCur;
					}
					try{
						Thread.sleep(10);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}.start();

		//コンポーネント追加したので再構築
		this.validate();
	}

	public static void main(String[] args) {
		new MainFrame();
	}


}
