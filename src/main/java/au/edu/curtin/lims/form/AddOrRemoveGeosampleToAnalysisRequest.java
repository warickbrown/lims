/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.form;

public class AddOrRemoveGeosampleToAnalysisRequest {
    private Integer analysisRequestId;

    private Integer geosampleId;
    
    private boolean add;
    
    public AddOrRemoveGeosampleToAnalysisRequest() { }

    public Integer getAnalysisRequestId() {
        return this.analysisRequestId;
    }
    
    public void setAnalysisRequestId(Integer analysisRequestId) {
        this.analysisRequestId = analysisRequestId;
    }
    
    public Integer getGeosampleId() {
        return this.geosampleId;
    }
    
    public void setGeosampleId(Integer geosampleId) {
        this.geosampleId = geosampleId;
    }
    
    public boolean getAdd() {
        return this.add;
    }
    
    public void setAdd(boolean add) {
        this.add = add;
    }
}
