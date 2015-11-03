/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.edu.curtin.lims.domain.AnalysisRequest;
import au.edu.curtin.lims.domain.Geosample;

public class AnalysisRequestDetailViewModel extends BaseViewModel {
    public String analysisRequestStateName;
    
    public String analysisTypeName;
    
    public String instrumentName;
    
    public String username;
    
    public Date requestDate;
    
    public List<Integer> geosampleIds = new ArrayList<Integer>();
    
    public AnalysisRequestDetailViewModel(AnalysisRequest analysisRequest) {
        super(analysisRequest.getAnalysisRequestId());
        this.analysisRequestStateName = analysisRequest.getAnalysisRequestState().getAnalysisRequestStateName(); 
        this.analysisTypeName = analysisRequest.getAnalysisCapability().getAnalysisType().getAnalysisTypeName();
        this.instrumentName = analysisRequest.getAnalysisCapability().getInstrument().getInstrumentName();
        this.username = analysisRequest.getPerson().getUsername();
        this.requestDate = analysisRequest.getRequestDate();

        for (Geosample geosample : analysisRequest.getGeosamples()) {
            this.geosampleIds.add(geosample.getGeosampleId());           
        }
    }
    
    public static List<AnalysisRequestDetailViewModel> createList(List<AnalysisRequest> analysisRequests) {
        List<AnalysisRequestDetailViewModel> analysisRequestVMs = new ArrayList<AnalysisRequestDetailViewModel>(); 
        
        for (AnalysisRequest analysisRequest : analysisRequests) {
            analysisRequestVMs.add(new AnalysisRequestDetailViewModel(analysisRequest));
        }
        
        return analysisRequestVMs;
    }
}
