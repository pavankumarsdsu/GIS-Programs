package introduction;

import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*; 

import com.esri.mo2.ui.bean.Map;
import com.esri.mo2.ui.bean.Layer;
import com.esri.mo2.ui.bean.Toc;

public class QuickStart extends JFrame {
	Map map = new Map();
	Layer layer = new Layer();
	Toc toc = new Toc();
	
	public QuickStart(){
		
		super("Quick Start");
		this.setSize(400, 300);
		getContentPane().add(map, BorderLayout.CENTER);
		addShapefileToMap();
		toc.setMap(map);
		getContentPane().add(toc,  BorderLayout.WEST);
	}
	
	private void addShapefileToMap(){
		String datapath = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
		layer.setDataset("0;" + datapath);
		map.add(layer);
	}
	
	public static void main(String[] args){
		QuickStart qstart = new QuickStart();
		qstart.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.out.println("thanks, Quick Start exists");
				System.exit(0);
			}
		});
		
		qstart.setVisible(true);
		
	}
	
}
