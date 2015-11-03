/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import au.edu.curtin.lims.db.IPersonRepository;
import au.edu.curtin.lims.domain.Person;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Transactional
public abstract class BaseController {
    protected final int bufferSize = 1024*4;
    
    public static String stagingPath;
    
    public static String scriptsPath;
    
    public static String redboxPath;
    
    public static String ddfePath;
    
    public static String databasePassword;
    
    public static String databaseHostname;
    
    public static String databaseConnectionString;
    
    public static String sesarCsvImportPath;
    
    public static String sesarUsername;
    
    public static String sesarPassword;
    
    public static String sesarUserCode;
    
    @Inject
    private SessionFactory sessionFactory;
    
    @Inject
    private MessageSource messageSource;
    
    @Inject
    private IPersonRepository personRepository;
    
    private User user;
    
    private Person person;
    
    private final String controllerName = this.getClass().getSimpleName().replaceFirst("Controller$", "");

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    protected User getUser() {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = (User)authentication.getPrincipal();
        }

        return this.user;
    }
    
    protected Person getPerson() throws Exception {
        if (this.person == null) {
            this.person = this.personRepository.findUniqueByCriteria(
                    Restrictions.eq(
                            "username",
                            this.getUser().getUsername()));
        }
        
        return this.person;
    }
    
    protected ModelAndView getModelAndView(String viewName, Locale locale) {
        ModelAndView model = new ModelAndView();
        model.setViewName(this.controllerName + "/" + viewName);
        model.addObject(
                "title", 
                messageSource.getMessage(this.controllerName + "." + viewName + ".title", null, locale));
        
        return model;
    }
    
    protected void saveMultipartFile(MultipartFile file, String location) throws IOException {
        if (!file.isEmpty()) {
            BufferedInputStream bufferedInputStream = null;
            FileOutputStream fileOutputStream = null;
            BufferedOutputStream outputStream = null;
            
            try {
                bufferedInputStream = new BufferedInputStream(file.getInputStream(), bufferSize);

                File stagedFile = new File(location); 
                fileOutputStream = new FileOutputStream(stagedFile);
                outputStream = new BufferedOutputStream(fileOutputStream, bufferSize);
                
                byte[] buffer = new byte[bufferSize];
                int count = 0;
                while ((count = bufferedInputStream.read(buffer, 0, bufferSize)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
            }
            finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            }
        }
        else {
            throw new IOException("The file was empty.");
        }
    }
}
