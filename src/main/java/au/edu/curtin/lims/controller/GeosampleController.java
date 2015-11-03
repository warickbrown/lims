/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IGeosampleRepository;
import au.edu.curtin.lims.domain.Geosample;
import au.edu.curtin.lims.viewmodel.GeosampleViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Geosample")
public class GeosampleController extends BaseController {
    @Inject
    private IGeosampleRepository geosampleRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(GeosampleController.class);

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public ModelAndView index(Locale locale) {
        ModelAndView model = this.getModelAndView("index", locale);
        List<Geosample> geosamples = this.geosampleRepository.findAll();
        
        model.addObject(
                "geosamples",
                GeosampleViewModel.createList(geosamples));
        return model;
    }
    
    @RequestMapping(value = "/Upload", method = RequestMethod.POST)
    public ModelAndView upload(
            @RequestParam("file") MultipartFile file,
            Locale locale) throws IOException {
        String path = Files.createTempFile(GeosampleController.class.getName(), null).toString();
        ModelAndView model = this.index(locale); 
                
        try {            
            super.saveMultipartFile(file, path);
            LOGGER.info("Saved uploaded file to: {}", path);
        }
        catch (Exception e) {
            // TODO: Make this give a proper error
            model.addObject(
                    "error",
                    "You failed to upload " + file.getName() +  " => " + e.getMessage());
            
            return model;
        }
        
        // Process the uploaded CSV:
        StringBuffer output = new StringBuffer();
        Process p;

        String[] environmentVariables = {
            "SESAR_USERNAME=" + BaseController.sesarUsername,
            "SESAR_PASSWORD=" + BaseController.sesarPassword,
            "SESAR_USER_CODE=" + BaseController.sesarUserCode
        };

        try {
            String[] command = {
                    "python",
                    BaseController.sesarCsvImportPath,
                    path,
                    BaseController.databaseConnectionString
            };
            
            p = Runtime.getRuntime().exec(command, environmentVariables);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            reader.close();

            String response = output.toString();
            LOGGER.info("Command output: {}", response);
            
            if (response.startsWith("Error")) {
                model.addObject("error", response.replaceAll("\n", "<br/>"));
            }
            else {
                model.addObject("newIgsns", response.split("\n"));
            }

            return model; 
        } catch (Exception e) {
            e.printStackTrace();

            // TODO: proper error.
            model.addObject("error", e.toString());
            return model;
        }
    }
}
