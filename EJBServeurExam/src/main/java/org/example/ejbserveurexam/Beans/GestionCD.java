package org.example.ejbserveurexam.Beans;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.ejbserveurexam.entity.CD;
import org.example.ejbserveurexam.entity.Emprunt;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class GestionCD implements GestionCDRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void emprunterCD(Long cdId, String utilisateur) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && cd.isDisponible()) {
            cd.setDisponible(false);
            Emprunt emprunt = new Emprunt();
            emprunt.setCd(cd);
            emprunt.setUtilisateur(utilisateur);
            emprunt.setDateEmprunt(LocalDate.now());
            em.persist(emprunt);
        }
    }

    @Override
    public void retournerCD(Long cdId) {
        CD cd = em.find(CD.class, cdId);
        if (cd != null && !cd.isDisponible()) {
            cd.setDisponible(true);
            // Trouver l'emprunt associé et le supprimer ou l'archiver
            Emprunt emprunt = em.createQuery("SELECT e FROM Emprunt e WHERE e.cd.id = :cdId", Emprunt.class)
                    .setParameter("cdId", cdId)
                    .getSingleResult();
            if (emprunt != null) {
                em.remove(emprunt);
            }
        }
    }

    @Override
    public List<CD> listerCDsDisponibles() {
        return em.createQuery("SELECT c FROM CD c WHERE c.disponible = true", CD.class).getResultList();
    }

    @Override
    public List<Emprunt> voirEmprunts(String utilisateur) {
        return em.createQuery("SELECT e FROM Emprunt e WHERE e.utilisateur = :utilisateur", Emprunt.class)
                .setParameter("utilisateur", utilisateur)
                .getResultList();
    }
}
