package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Procedure;
import ru.ulstu.is.sbapp.student.model.Visit;
import ru.ulstu.is.sbapp.student.service.ProcedureService;
import org.slf4j.LoggerFactory;
import ru.ulstu.is.sbapp.student.service.VisitService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
public class JpaProcedureTests {
    private static final Logger log = LoggerFactory.getLogger(JpaProcedureTests.class);

    @Autowired
    private ProcedureService procedureService;
    @Autowired
    private VisitService visitService;

    @Test
    void testProcedureCreate() {
        procedureService.deleteAllProcedure();
        final Procedure procedure = procedureService.addProcedure("Стрижка", "Иванов Иван Иванович", 1000);
        log.info(procedure.toString());
        Assertions.assertNotNull(procedure.getId());
    }

    @Test
    void testProcedureRead() {
        procedureService.deleteAllProcedure();
        final Procedure procedure = procedureService.addProcedure("Стрижка", "Иванов Иван Иванович", 1000);
        log.info(procedure.toString());
        final Procedure findProcedure = procedureService.findProcedure(procedure.getId());
        log.info(findProcedure.toString());
        Assertions.assertEquals(procedure.getId(), findProcedure.getId());
    }

    @Test
    void testProcedureReadNotFound() {
        procedureService.deleteAllProcedure();
        Assertions.assertThrows(EntityNotFoundException.class, () -> procedureService.findProcedure(-1L));
    }

    @Test
    void testProcedureReadAll() {
        procedureService.deleteAllProcedure();
        procedureService.addProcedure("Стрижка", "Иванов Иван Иванович", 1000);
        procedureService.addProcedure("Маникюр", "Соловьева Марина Евгеньевна", 800);
        final List<Procedure> procedures = procedureService.findAllProcedure();
        log.info(procedures.toString());
        Assertions.assertEquals(procedures.size(), 2);
    }

    @Test
    void testProcedureReadAllEmpty() {
        procedureService.deleteAllProcedure();
        final List<Procedure> procedures = procedureService.findAllProcedure();
        log.info(procedures.toString());
        Assertions.assertEquals(procedures.size(), 0);
    }

    @Test
    void testVisitToProcedure() {
        visitService.deleteAllVisit();
        procedureService.deleteAllProcedure();
        final Procedure procedure = procedureService.addProcedure("Стрижка", "Иванов Иван Иванович", 1000);
        final Visit visit = visitService.addVisit("20.04.2022");
        procedureService.addVisitsToProcedure(visit, procedure);

        log.info(procedure.toString());
        Assertions.assertEquals(visit, procedure.getVisits().get(0));
    }
}
