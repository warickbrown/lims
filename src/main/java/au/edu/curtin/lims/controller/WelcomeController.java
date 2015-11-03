/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.controller;

import java.util.Locale;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IInstrumentRepository;
import au.edu.curtin.lims.db.IManufacturerRepository;
import au.edu.curtin.lims.domain.Instrument;
import au.edu.curtin.lims.domain.Manufacturer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController extends BaseController {
    @Inject
    private IManufacturerRepository manufacturerRepository;

    @Inject
    private IInstrumentRepository instrumentRepository;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(Locale locale) {
        ModelAndView model = super.getModelAndView("index", locale);
        
        String output = locale.getCountry();

        // Execute the ManyToOne (Many Instruments belong to one Manufacturer) relationship to get the manufacturer and its name from the instrument:
        Instrument i2 = instrumentRepository.findById(1);
        output += i2.getManufacturer().getManufacturerName();

        // Execute the OneToMany (One Manufacturer has many Instruments) relationship to get the instruments that belong to a given manufacturer:
        Manufacturer m2 = manufacturerRepository.findById(1);
        output += m2.getInstruments().iterator().next().getInstrumentName();
        
        model.addObject("name", output);
                
        LOGGER.debug("super.getUser().getUsername(): {}", super.getUser().getUsername());
        LOGGER.debug("super.getUser().getPassword(): {}", super.getUser().getPassword());
        
        for (GrantedAuthority grantedAuthority : super.getUser().getAuthorities()) {
            LOGGER.debug("grantedAuthority.getAuthority(): {}", grantedAuthority.getAuthority());
        }

        return model;
    }
}
