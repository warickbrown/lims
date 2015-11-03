/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.form;

public class PersonForm {
    private Integer personId;

    private String username;
    
    private String password;
    
    public PersonForm() { }

    public Integer getPersonId() {
        return this.personId;
    }
    
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}