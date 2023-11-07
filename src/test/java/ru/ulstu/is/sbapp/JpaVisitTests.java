package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Procedure;
import ru.ulstu.is.sbapp.student.model.Visit;
import ru.ulstu.is.sbapp.student.service.ProcedureService;
import ru.ulstu.is.sbapp.student.service.VisitService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaVisitTests {
    private static final Logger log = LoggerFactory.getLogger(JpaVisitTests.class);

    @Autowired
    private VisitService visitService;
    @Autowired
    private ProcedureService procedureService;

    @Test
    void testVisitCreate() {
        visitService.deleteAllVisit();
        final Visit visit = visitService.addVisit("20.04.2022");
        log.info(visit.toString());
        Assertions.assertNotNull(visit.getId());
    }

    @Test
    void testVisitRead() {
        visitService.deleteAllVisit();
        final Visit visit = visitService.addVisit("20.04.2022");
        log.info(visit.toString());
        final Visit findVisit = visitService.findVisit(visit.getId());
        log.info(findVisit.toString());
        Assertions.assertEquals(visit, findVisit);
    }

    @Test
    void testVisitReadNotFound() {
        visitService.deleteAllVisit();
        Assertions.assertThrows(EntityNotFoundException.class, () -> visitService.findVisit(-1L));
    }

    @Test
    void testVisitReadAll() {
        visitService.deleteAllVisit();
        visitService.addVisit("20.04.2022");
        visitService.addVisit("23.04.2022");
        final List<Visit> visits = visitService.findAllVisit();
        log.info(visits.toString());
        Assertions.assertEquals(visits.size(), 2);
    }

    @Test
    void testVisitReadAllEmpty() {
        visitService.deleteAllVisit();
        final List<Visit> visits = visitService.findAllVisit();
        log.info(visits.toString());
        Assertions.assertEquals(visits.size(), 0);
    }

    @Test
    void testDisciplineAddTypeReporting() {
        visitService.deleteAllVisit();
        procedureService.deleteAllProcedure();
        final Procedure procedure = procedureService.addProcedure("Стрижка", "Иванов Иван Иванович", 1000);
        final Visit visit = visitService.addVisit("20.04.2022");
        procedureService.addVisitsToProcedure(visit, procedure);

        log.info(procedure.toString());
        Assertions.assertEquals(procedure, visit.getProcedurs().get(0));
    }
}
