package org.example.jsfclient;

import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.naming.NamingException;
import org.example.ejbserveurexam.entity.Emprunt;
import org.example.ejbserveurexam.Beans.GestionCDRemote;

import java.util.List;

@ManagedBean
@SessionScoped
public class CDBean {
    private List<CD> cdsDisponibles;
    private List<Emprunt> mesEmprunts;
    private String utilisateur;

    private GestionCDRemote gestionCD;

    @PostConstruct
    public void init() {
        try {
            gestionCD = EJBServiceLocator.lookup("ejb:/MyEJBServer/GestionCD!org.example.myejbserver.GestionCDRemote", GestionCDRemote.class);
            cdsDisponibles = gestionCD.listerCDsDisponibles();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void emprunter(Long cdId) {
        gestionCD.emprunterCD(cdId, utilisateur);
        cdsDisponibles = gestionCD.listerCDsDisponibles();
    }

    public void retourner(Long cdId) {
        gestionCD.retournerCD(cdId);
        mesEmprunts = gestionCD.voirEmprunts(utilisateur);
    }

    // Getters et setters
}
