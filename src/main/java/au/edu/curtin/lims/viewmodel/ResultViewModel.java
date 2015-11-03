/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.viewmodel;

import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.lims.domain.Result;

public class ResultViewModel extends BaseViewModel {
    public String igsn;
    
    public String resultTypeName;
    
    public String value;
    
    public ResultViewModel(Result result) {
        super(result.getResultId());
        this.igsn = result.getGeosample().getIgsn(); 
        this.resultTypeName = result.getAnalysisRequest().getAnalysisCapability().getResultType().getResultTypeName();
        this.value = result.getValue();
    }
    
    public static List<ResultViewModel> createList(List<Result> results) {
        List<ResultViewModel> resultVMs = new ArrayList<ResultViewModel>(); 
        
        for (Result result : results) {
            resultVMs.add(new ResultViewModel(result));
        }
        
        return resultVMs;
    }
}
