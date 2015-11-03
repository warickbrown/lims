/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.viewmodel;

import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.lims.domain.Geosample;

public class GeosampleViewModel extends BaseViewModel {
    public String igsn;
    
    public String geosampleName;
    
    public GeosampleViewModel(Geosample geosample) {
        super(geosample.getGeosampleId());
        this.igsn = geosample.getIgsn();
        this.geosampleName = geosample.getGeosampleName();
    }
    
    public static List<GeosampleViewModel> createList(List<Geosample> geosamples) {
        List<GeosampleViewModel> geosampleVMs = new ArrayList<GeosampleViewModel>(); 
        
        for (Geosample geosample : geosamples) {
            geosampleVMs.add(new GeosampleViewModel(geosample));
        }
        
        return geosampleVMs;
    }
}
