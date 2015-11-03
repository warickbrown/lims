/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.form;

public class InstrumentForm {
    private Integer instrumentId;

    private Integer manufacturerId;
    
    private String manufacturerName;

    private String instrumentName;
    
    public InstrumentForm() { }

    public Integer getInstrumentId() {
        return this.instrumentId;
    }
    
    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Integer getManufacturerId() {
        return this.manufacturerId;
    }
    
    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    
    public String getManufacturerName() {
        return this.manufacturerName;
    }
    
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    

    public String getInstrumentName() {
        return this.instrumentName;
    }
    
    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }
}
