package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.student.model.Client;
import ru.ulstu.is.sbapp.student.model.Procedure;
import ru.ulstu.is.sbapp.student.model.Visit;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
public class VisitService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Visit addVisit(String date) {
        if (!StringUtils.hasText(date)) {
            throw new IllegalArgumentException("Visit date is null or empty");
        }
        final Visit visit = new Visit(date);
        em.persist(visit);
        return visit;
    }

    @Transactional(readOnly = true)
    public Visit findVisit(Long id) {
        final Visit visit = em.find(Visit.class, id);
        if (visit == null) {
            throw new EntityNotFoundException(String.format("Visit with id [%s] is not found", id));
        }
        return visit;
    }

    @Transactional(readOnly = true)
    public List<Visit> findAllVisit() {
        return em.createQuery("select s from Visit s", Visit.class)
                .getResultList();
    }

    @Transactional
    public Visit updateVisit(Long id, String  date) {
        if (!StringUtils.hasText(date)) {
            throw new IllegalArgumentException("Visit date is null or empty");
        }
        final Visit currentVisit = findVisit(id);
        currentVisit.setDate(date);
        return em.merge(currentVisit);
    }

    @Transactional
    public Visit deleteVisit(Long id) {
        final Visit currentVisit = findVisit(id);
        em.remove(currentVisit);
        return currentVisit;
    }

    @Transactional
    public void deleteAllVisit() {
        em.createQuery("delete from Visit").executeUpdate();
    }

    @Transactional
    public void addProcedureToVisit(Visit visit, Procedure procedure) {
        if (procedure.toString().isEmpty()) {
            throw new IllegalArgumentException("Procedure is null or empty");
        }
        visit.addProcedure(procedure);
        em.merge(visit);
    }
}
