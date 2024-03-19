
package Terminal.Based.UI;
import UI.DataTransferLayer.UIInterface;
import bl.backend.weather.app.sda.project.CacheManager;


public class TerminalUI implements UIInterface {

   
    public TerminalUI() {
    }

    @Override
    public double[] getInputValues() {
        LocationInput li = new LocationInput();
        double[] coor = li.getCoors();
        return coor;
    }
    
    @Override 
    public void showData(CacheManager cm)
    {
        
    }
 
}
