package introduction;
import java.awt.event.*;
import java.awt.*; 


public class LayoutFlow {

	public static void main(String[] args) {
		Frame f = new Frame();
		f.setTitle(" Flow Layout");
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.out.println("thanks");
				System.exit(0);
			}
		});
		
		f.setBounds(50, 50, 300, 300);
		f.setLayout(new FlowLayout()); 
		Button b1 = new Button("North");
		f.add(b1);
		Button b2 = new Button("South");
		f.add(b2);
		Button b3 = new Button("West");
		f.add(b3);
		Button b4 = new Button("East");
		f.add(b4);
		
		Button b5 = new Button("center");
		f.add(b5);
		f.show();

	}

}
