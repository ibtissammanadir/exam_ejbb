package org.example.ejbserveurexam.Beans;

import java.util.List;
import jakarta.ejb.Remote;
import org.example.ejbserveurexam.entity.CD;
import org.example.ejbserveurexam.entity.Emprunt;

@Remote
public interface GestionCDRemote {
    void emprunterCD(Long cdId, String utilisateur);
    void retournerCD(Long cdId);
    List<CD> listerCDsDisponibles();
    List<Emprunt> voirEmprunts(String utilisateur);
}