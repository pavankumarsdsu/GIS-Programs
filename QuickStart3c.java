import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
import com.esri.mo2.ui.bean.*;
import com.esri.mo2.ui.tb.*;
import com.esri.mo2.ui.toc.*;
import com.esri.mo2.cs.geom.InvalidValuesException;
 
public class QuickStart3c extends JFrame { 
  Map map = new Map();
  Layer layer = new Layer();
  Layer layer2 = new Layer();
  TreeToc toc = new TreeToc();
  String s1 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
  String s2 = "C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\capitals.shp";
  ZoomPanToolBar zptb = new ZoomPanToolBar();
  SelectionToolBar stb = new SelectionToolBar();
  JPanel myjp = new JPanel();
  TreeTocAdapter mytocadapter;

  public QuickStart3c() throws ParseException {
    //add a title to the window
    super("Quick Start3c");
    //set the size
    setSize(550, 340);
    //add the map to the frame
    zptb.setMap(map);
    stb.setMap(map);
    toc.setMap(map);
    // The following is a longcut mandated by an ESRI decision to
    // garbage collect immediately any non-hard references;  it is
    // discussed in the FAQ in MOJ10;   the code may not work without
    // this hard reference to the TocAdapter object!!!!
    mytocadapter = new TreeTocAdapter() {
	  public void click(TreeTocEvent e) {
	    zptb.setSelectedLayer((toc.getSelectedLayers()) [0] );
	  }
    };
    toc.addTocListener(mytocadapter);
    myjp.add(zptb); myjp.add(stb);
    getContentPane().add(map, BorderLayout.CENTER);
    getContentPane().add(myjp,BorderLayout.NORTH);
    //getContentPane().add(stb,BorderLayout.SOUTH);
    //add a shape file to the map
    addShapefileToMap(layer,s1);
    addShapefileToMap(layer2,s2);
    //add a Toc

    stb.setSelectedLayer(map.getLayer(0));
    //stb.selectedLayer = map.getLayer(1);// error: variable is protected
    getContentPane().add(toc, BorderLayout.WEST);
  }
  private void addShapefileToMap(Layer layer,String s) {
    String datapath = s; //"C:\\ESRI\\MOJ20\\Samples\\Data\\USA\\States.shp";
    layer.setDataset("0;"+datapath);
    map.add(layer);
  }

  public static void main(String[] args) throws ParseException {
    QuickStart3c qstart = new QuickStart3c();
    qstart.setIconImage(com.esri.mo2.util.Resource.getIcon("print.gif"));
    qstart.addWindowListener(new WindowAdapter() {
     public void windowClosing(WindowEvent e) {
        System.out.println("Thanks, Quick Start exits");
        System.exit(0);
      }
    });
    qstart.setVisible(true);
  }
}
