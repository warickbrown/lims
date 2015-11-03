/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.form;

public class AnalysisRequestForm {
    private Integer analysisRequestId;

    private Integer analysisCapabilityId;
    
    public AnalysisRequestForm() { }

    public Integer getAnalysisRequestId() {
        return this.analysisRequestId;
    }
    
    public void setAnalysisRequestId(Integer analysisRequestId) {
        this.analysisRequestId = analysisRequestId;
    }

    public Integer getAnalysisCapabilityId() {
        return this.analysisCapabilityId;
    }
    
    public void setAnalysisCapabilityId(Integer analysisCapabilityId) {
        this.analysisCapabilityId = analysisCapabilityId;
    }
}
