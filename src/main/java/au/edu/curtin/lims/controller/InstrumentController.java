/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IInstrumentRepository;
import au.edu.curtin.lims.db.IManufacturerRepository;
import au.edu.curtin.lims.domain.Instrument;
import au.edu.curtin.lims.domain.Manufacturer;
import au.edu.curtin.lims.form.InstrumentForm;
import au.edu.curtin.lims.viewmodel.InstrumentViewModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Instrument")
public class InstrumentController extends BaseController {
    @Inject
    private IManufacturerRepository manufacturerRepository;
    
    @Inject
    private IInstrumentRepository instrumentRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentController.class);

    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public ModelAndView index(
            Locale locale) {
        ModelAndView model = this.getModelAndView("index", locale);
        List<Instrument> instruments = this.instrumentRepository.findAll();
        
        model.addObject(
                "instruments",
                InstrumentViewModel.createList(instruments));
        return model;
    }

    @RequestMapping(value = "/{instrumentId:[0-9]+}", method = RequestMethod.GET)
    public ModelAndView detail(
            @PathVariable("instrumentId") final int instrumentId,
            Locale locale) {
        ModelAndView model = this.getModelAndView("detail", locale);
        Instrument instrument = instrumentRepository.findById(instrumentId);
        model.addObject("instrument", new InstrumentViewModel(instrument));
        return model;
    }
    
    @RequestMapping(value = "/Create", method = RequestMethod.GET)
    public ModelAndView create(Locale locale) {
        ModelAndView model = this.getModelAndView("create", locale);
        model.addObject("manufacturers", this.manufacturerRepository.findAll());
        model.addObject("instrumentForm", new InstrumentForm());
        return model;
    }
    
    @RequestMapping(value = "/Create", method = RequestMethod.POST)
    public String create(
            InstrumentForm instrumentForm,
            Model model) throws Exception {
        Instrument instrument = new Instrument();
        instrument.setInstrumentName(instrumentForm.getInstrumentName());
        
        int manufacturerId = instrumentForm.getManufacturerId(); 
        
        if (manufacturerId == 0) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setManufacturerName(instrumentForm.getManufacturerName());
            manufacturerRepository.save(manufacturer);
            manufacturerId = manufacturer.getManufacturerId();
        }
        
        instrument.setManufacturer(
            this.manufacturerRepository.findById(
                    manufacturerId));

        this.instrumentRepository.save(instrument);
        return "redirect:/Instrument";
    }
}
