package introduction;

//Just adds a simple AcetateLayer to place a line & points on the
//map. Mercifully this works if the drawing is done in Java terms,
//using frame coordinates (pixels).  Using an AcetateLayer has the
//advantage that an AcetateLayer is transparent, and this solves
//a problem that transparent layers in Java are somewhat tricky
//to do, e.g. a canvas can not be made transparent. In addition,
//the line is drawn in stages by using a thread.  The thread is
//wise and necessary.  In this  version, the acetate layers
//are removed, within the loop, so they do not accumulate
//and slow things down. The loop should work with two arbitrary
//points!  Those points are parameters to the thread code.
 
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import com.esri.mo2.ui.bean.*;
import com.esri.mo2.cs.geom.Point;
import com.esri.mo2.ui.tb.ZoomPanToolBar;
import com.esri.mo2.ui.tb.SelectionToolBar;

public class QuickStart4f extends JFrame { 
static Map map = new Map(); 
Layer layer = new Layer();
Layer layer2 = new Layer();
Toc toc = new Toc();

String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\World\\world30.shp";
String s2 = "C:\\ESRI\\MOJ20\\Samples\\Data\\World\\country.shp";

//String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\China\\China.shp";
//String s2 = "C:\\ESRI\\MOJ20\\Samples\\Data\\China\\Chinese_cities.shp";


//String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
//String s2 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\capitals.shp";
ZoomPanToolBar zptb = new ZoomPanToolBar();
SelectionToolBar stb = new SelectionToolBar();
JPanel myjp = new JPanel();
JButton myjb = new JButton("select[1]");
ActionListener actlis;
TocAdapter mytocadapter;


public QuickStart4f() {

   super("Quick Start");
   this.setBounds(80, 80, 1250, 600);
   myjp.setBorder(new CompoundBorder());
   myjp.setBackground(Color.red);
   myjp.setBounds(40, 40, 850, 250);
   zptb.setMap(map);
   stb.setMap(map);
   addInteractivePanel(); 
   //map.addLayerListener(toc);
   actlis = new ActionListener() {
       public void actionPerformed(ActionEvent ae) {
           stb.setSelectedLayer(map.getLayer(1));
           System.out.println(map.getLayer(1).getName());//this is same
           // as the name that appears in the table of contents
       }
   };


   toc.setMap(map);
   mytocadapter = new TocAdapter() {
       public void click(TocEvent e) {
           System.out.println("aloha");
           stb.setSelectedLayer((e.getLegend()).getLayer());
       }
   };
   toc.addTocListener(mytocadapter);
   myjb.addActionListener(actlis);
   myjp.add(zptb);
   myjp.add(stb);
   myjp.add(myjb);
   getContentPane().add(map, BorderLayout.CENTER);
   getContentPane().add(myjp, BorderLayout.NORTH);
   //getContentPane().add(myjp, BorderLayout.NORTH);
   
  // getContentPane().add(stb,BorderLayout.NORTH);
   addShapefileToMap(layer, s1);
   addShapefileToMap(layer2, s2);

   //Flash flash = new Flash(300.0, 200.0, 500.0, 400.0);
   //flash.start();
   getContentPane().add(toc, BorderLayout.WEST);
}


JTextField lattitudeOne = new JTextField("X1", 5);
JTextField longitudeOne = new JTextField("Y1", 5);
JTextField lattitudeTwo = new JTextField("X2", 5);
JTextField longitudeTwo = new JTextField("Y2", 5);
JTextField lattitudeThree = new JTextField("X3", 5);
JTextField longitudeThree = new JTextField("Y3", 5);

JCheckBox checkForTransformation = new JCheckBox("Transform");
JCheckBox checkForTriangle = new JCheckBox("Triangle");
JButton submitButton =new JButton("Go");


 private void addInteractivePanel() {

     lattitudeThree.setVisible(false);
     longitudeThree.setVisible(false);
     submitButton.setSize(40,40);
     submitButton.setBackground(Color.red);

     myjp.add(lattitudeOne);
     myjp.add(longitudeOne);
     myjp.add(lattitudeTwo);
     myjp.add(longitudeTwo);
     myjp.add(lattitudeThree);
     myjp.add(longitudeThree);
     myjp.add(checkForTransformation);
     myjp.add(checkForTriangle);
     myjp.add(submitButton);

     checkForTriangle();

     try {

         submitButton.addActionListener((ActionEvent ae) -> {

             Double p1 = Double.parseDouble(lattitudeOne.getText());
             Double p2 = Double.parseDouble(longitudeOne.getText());
             Double p3 = Double.parseDouble(lattitudeTwo.getText());
             Double p4 = Double.parseDouble(longitudeTwo.getText());


             if(checkForTriangle.isSelected()){
            	 
            	 JOptionPane.showMessageDialog(checkForTriangle, "Are you sure you want to draw triangle ??.");

                 Double p5 = Double.parseDouble(lattitudeThree.getText());
                 Double p6 = Double.parseDouble(longitudeThree.getText());
                 
                 if(checkForTransformation.isSelected()){
                	 java.awt.Point pt1 = map.transformWorldToPixel(p2, p1);
                	 java.awt.Point pt2 = map.transformWorldToPixel(p4, p3);
                	 java.awt.Point pt3 = map.transformWorldToPixel(p6, p5);
                	 drawTriangle(pt1.getX(),pt1.getY(), pt2.getX(), pt2.getY(), pt3.getX(), pt3.getY());

                	 
                 }else
                 {
                	 drawTriangle(p1,p2, p3, p4, p5, p6);

                 }
                 
                 
             } else {

                 ToDrawLine customizeFlash = checkForTransformation.isSelected() ?
                         useGeodeticCoordinates(p1, p2, p3, p4) :
                         usePixelCoordinates(p1, p2, p3, p4);
                 customizeFlash.start();
             }
         });

     } catch (Exception ex){
         System.out.println(ex.getMessage());
     }
 }


 private ToDrawLine usePixelCoordinates(
         Double lattitudeOne,
         Double longitudeOne,
         Double lattitudeTwo,
         Double longitudeTwo) {

     return new ToDrawLine(
             lattitudeOne,
             longitudeOne,
             lattitudeTwo,
             longitudeTwo
     );
 }

 private ToDrawLine useGeodeticCoordinates(
         Double lattitudeOne,
         Double longitudeOne,
         Double lattitudeTwo,
         Double longitudeTwo) {

     java.awt.Point pt1 =  map.transformWorldToPixel( longitudeOne , lattitudeOne);
     java.awt.Point pt2 =  map.transformWorldToPixel(longitudeTwo , lattitudeTwo);

     return new ToDrawLine(
             pt1.getX(),
             pt1.getY(),
             pt2.getX(),
             pt2.getY()
     );

 }

 
 private void checkForTriangle() {
     checkForTriangle.addActionListener( ae -> {

         if(checkForTriangle.isSelected()) {
             lattitudeThree.setVisible(true);
             longitudeThree.setVisible(true);
         }else{
             lattitudeThree.setVisible(false);
             longitudeThree.setVisible(false);
         }
         myjp.revalidate();
     });
 }

 private void drawTriangle(
         Double lattitudeOne,
         Double longitudeOne,
         Double lattitudeTwo,
         Double longitudeTwo,
         Double lattitudeThree,
         Double longitudeThree) {

     ToDrawLine lineOne = new ToDrawLine(
             lattitudeOne,
             longitudeOne,
             lattitudeTwo,
             longitudeTwo
     );
     ToDrawLine lineTwo = new ToDrawLine(
             lattitudeThree,
             longitudeThree,
             lattitudeOne,
             longitudeOne
     );
     ToDrawLine lineThree = new ToDrawLine(
             lattitudeTwo,
             longitudeTwo,
             lattitudeThree,
             longitudeThree
     );
     lineOne.start(); 
     lineTwo.start();
     lineThree.start();

 }

 
 
 private void addShapefileToMap(Layer layer,String s) {
 //stb.setSelectedLayer(map.getLayer(0));
	 String datapath = s; //"C:\\ESRI\\MOJ20\\Samples\\Data\\World\\country.shp";

	 
//String datapath = s; //"C:\\ESRI\\MOJ20\\Samples\\Data\\China\\China.shp";
	// String datapath = s; //"C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
	 
 layer.setDataset("0;"+datapath);
 map.add(layer);
}

public static void main(String[] args) {
 QuickStart4f qstart = new QuickStart4f();
 qstart.addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e) {
         System.out.println("Thanks, Quick Start exits");
         System.exit(0);
     }
 });
 qstart.setVisible(true);
}
}
class ToDrawLine extends Thread {
AcetateLayer acetLayer = new AcetateLayer();
double x1,y1,x2,y2;

ToDrawLine(double x11,double y11,double x22, double y22) {
	x1 = x11;y1=y11;x2=x22;y2=y22;
}
public void run() {
	for (int i=0;i<21;i++) {
	  try {
		Thread.sleep(300);
		final int j = i;
		if (acetLayer != null) QuickStart4f.map.remove(acetLayer);
		acetLayer = new AcetateLayer() {
			public void paintComponent(java.awt.Graphics g) {
				java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
					//Line2D.Double line = new Line2D.Double(startx,starty,endx,endy);
					Line2D.Double line = new Line2D.Double(x1,y1,x1+j*(x2-x1)/20.0,y1+j*(y2-y1)/20.0);
					g2d.setColor(new Color(0,0,250));
					g2d.draw(line);
					g2d.setColor(new Color(250,0,0));
					g2d.fillOval( (int)x1, (int)y1,5,5);
					g2d.fillOval((int)x2, (int)y2,5,5);
		       }
		    };
		acetLayer.setMap(QuickStart4f.map);
     QuickStart4f.map.add(acetLayer);
	  } catch (Exception e) {}
 }
}
}
