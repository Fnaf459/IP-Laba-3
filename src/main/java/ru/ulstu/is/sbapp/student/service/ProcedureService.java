package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Procedure;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.model.Visit;


import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ProcedureService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Procedure addProcedure(String procedurName, String fioMaster, int price) {
        if (!StringUtils.hasText(procedurName) || !StringUtils.hasText(fioMaster) || !StringUtils.hasText(String.valueOf(price))) {
            throw new IllegalArgumentException("Procedure name is null or empty");
        }
        final Procedure procedure = new Procedure(procedurName, fioMaster, price);
        em.persist(procedure);
        return procedure;
    }

    @Transactional(readOnly = true)
    public Procedure findProcedure(Long id) {
        final Procedure procedure = em.find(Procedure.class, id);
        if (procedure == null) {
            throw new EntityNotFoundException(String.format("Procedure with id [%s] is not found", id));
        }
        return procedure;
    }

    @Transactional(readOnly = true)
    public List<Procedure> findAllProcedure() {
        return em.createQuery("select s from Procedure s", Procedure.class)
                .getResultList();
    }

    @Transactional
    public Procedure updateProcedure(Long id, String procedureName, String fioMaster, int price) {
        if (!StringUtils.hasText(procedureName) || !StringUtils.hasText(fioMaster) || !StringUtils.hasText(String.valueOf(price))) {
            throw new IllegalArgumentException("Procedure name is null or empty");
        }
        final Procedure currentProcedure = findProcedure(id);
        currentProcedure.setProcedureName(procedureName);
        currentProcedure.setFioMaster(fioMaster);
        currentProcedure.setPrice(price);
        return em.merge(currentProcedure);
    }

    @Transactional
    public Procedure deleteProcedure(Long id) {
        final Procedure currentProcedure = findProcedure(id);
        em.remove(currentProcedure);
        return currentProcedure;
    }

    @Transactional
    public void deleteAllProcedure() {
        em.createQuery("delete from Procedure").executeUpdate();
    }

    @Transactional
    public void addVisitsToProcedure(Visit visit, Procedure procedure) {
        if (visit.toString().isEmpty()) {
            throw new IllegalArgumentException("Visit is null or empty");
        }
        procedure.addVisits(visit);
        em.merge(procedure);
    }


}
