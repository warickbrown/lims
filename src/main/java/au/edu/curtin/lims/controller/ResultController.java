/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.controller;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IAnalysisRequestRepository;
import au.edu.curtin.lims.db.IGeosampleRepository;
import au.edu.curtin.lims.db.IResultRepository;
import au.edu.curtin.lims.domain.AnalysisRequest;
import au.edu.curtin.lims.domain.Geosample;
import au.edu.curtin.lims.domain.Result;

import org.hibernate.criterion.Restrictions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/Result")
public class ResultController extends BaseController {
    @Inject
    private IAnalysisRequestRepository analysisRequestRepository;
    
    @Inject
    private IGeosampleRepository geosampleRepository;
    
    @Inject
    private IResultRepository resultRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultController.class);
    
    private String getStagedFile(int analysisRequestId, int geosampleId) {
        return stagingPath + "/AR" + analysisRequestId + "GS" + geosampleId;
    }
    
    @RequestMapping(value = "/Upload", method = RequestMethod.POST)
    public String upload(
            @RequestParam("analysisRequestId") final int analysisRequestId,
            @RequestParam("geosampleId") final int geosampleId,
            @RequestParam("file") MultipartFile file) throws IOException {
            
        try {
            super.saveMultipartFile(file, this.getStagedFile(analysisRequestId, geosampleId));
                        
            AnalysisRequest analysisRequest = this.analysisRequestRepository.findById(analysisRequestId);
            Geosample geosample = this.geosampleRepository.findById(geosampleId);
            
            Result result = new Result();
            result.setAnalysisRequest(analysisRequest);
            result.setGeosample(geosample);
            result.setHasStagedFile(true);
            
            this.resultRepository.save(result);
            return "redirect:/AnalysisRequest/" + analysisRequestId;
        }
        catch (Exception e) {
            // TODO: Make this give a proper error
            return "You failed to upload " + file.getName() +  " => " + e.getMessage();
        }
    }
    
    @RequestMapping(value = "/Remove", method = RequestMethod.POST)
    public String remove(
            final int analysisRequestId,
            final int geosampleId) throws Exception {
        // Remove the file from the staging location:
        new File(this.getStagedFile(analysisRequestId, geosampleId)).delete();
        
        // Remove the result from the database:
        Result result = this.resultRepository.findUniqueByCriteria(
                Restrictions.eq("analysisRequest", this.analysisRequestRepository.findById(analysisRequestId)),
                Restrictions.eq("geosample", this.geosampleRepository.findById(geosampleId)));
        
        this.resultRepository.delete(result.getResultId());
        return "redirect:/AnalysisRequest/" + analysisRequestId;
    }
}
