/*
 * Copyright Curtin University, 2015.
 */
package au.edu.curtin.lims.viewmodel;

import java.util.ArrayList;
import java.util.List;

import au.edu.curtin.lims.domain.Authority;
import au.edu.curtin.lims.domain.Faction;
import au.edu.curtin.lims.domain.Person;

public class PersonDetailViewModel extends BaseViewModel {
    public String username;
    
    public boolean enabled;
    
    public List<String> authorities = new ArrayList<String>();
    
    public List<String> factions = new ArrayList<String>();
    
    public List<String> factionDerivedAuthorities = new ArrayList<String>();

    public PersonDetailViewModel(Person person) {
        super(person.getPersonId());
        
        this.username = person.getUsername();
        this.enabled = person.getEnabled();
        
        for (Authority authority : person.getAuthorities()) {
            this.authorities.add(authority.getAuthorityName());           
        }
        
        for (Faction faction : person.getFactions()) {
            this.factions.add(faction.getFactionName());
            
            for (Authority factionDerivedAuthority : faction.getAuthorities()) {
                this.factionDerivedAuthorities.add(factionDerivedAuthority.getAuthorityName());
            }
        }
    }
    
    public static List<PersonDetailViewModel> createList(List<Person> people) {
        List<PersonDetailViewModel> peopleVMs = new ArrayList<PersonDetailViewModel>(); 
        
        for (Person person : people) {
            peopleVMs.add(new PersonDetailViewModel(person));
        }
        
        return peopleVMs;
    } 
}
