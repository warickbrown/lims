/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.viewmodel;

import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.lims.domain.Instrument;

public class InstrumentViewModel extends BaseViewModel {
    public String instrumentName;
    
    public String manufacturerName; 
    
    public InstrumentViewModel(Instrument instrument) {
        super(instrument.getInstrumentId());
        this.instrumentName = instrument.getInstrumentName();
        this.manufacturerName = instrument.getManufacturer().getManufacturerName();
    }
    
    public static List<InstrumentViewModel> createList(List<Instrument> instruments) {
        List<InstrumentViewModel> instrumentVMs = new ArrayList<InstrumentViewModel>(); 
        
        for (Instrument instrument : instruments) {
            instrumentVMs.add(new InstrumentViewModel(instrument));
        }
        
        return instrumentVMs;
    }
}
