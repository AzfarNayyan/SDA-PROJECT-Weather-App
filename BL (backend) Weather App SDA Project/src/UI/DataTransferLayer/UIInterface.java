
package UI.DataTransferLayer;
import bl.backend.weather.app.sda.project.CacheManager;

public interface UIInterface 
{
    double[] getInputValues();
    void showData(CacheManager cm);
}