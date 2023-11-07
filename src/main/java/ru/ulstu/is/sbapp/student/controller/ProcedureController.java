package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Procedure;
import ru.ulstu.is.sbapp.student.service.ProcedureService;

import java.util.List;

@RestController
@RequestMapping("/procedure")
public class ProcedureController {
    private final ProcedureService procedureService;

    public ProcedureController(ProcedureService procedureService) {
        this.procedureService = procedureService;
    }

    @GetMapping("/{id}")
    public Procedure getProcedure(@PathVariable Long id) {
        return procedureService.findProcedure(id);
    }

    @GetMapping("/")
    public List<Procedure> getProcedure() {
        return procedureService.findAllProcedure();
    }

    @PostMapping("/")
    public Procedure createProcedure(@RequestParam("procedureName") String procedureName,
                                     @RequestParam("fioMaster") String fioMaster,
                                     @RequestParam("price") int price) {
        return procedureService.addProcedure(procedureName, fioMaster, price);
    }

    @PatchMapping("/{id}")
    public Procedure updateProcedure(@PathVariable Long id,
                                     @RequestParam("procedureName") String procedureName,
                                     @RequestParam("fioMaster") String fioMaster,
                                     @RequestParam("price") int price) {
        return procedureService.updateProcedure(id, procedureName, fioMaster, price);
    }

    @DeleteMapping("/{id}")
    public Procedure deleteProcedure(@PathVariable Long id) {
        return procedureService.deleteProcedure(id);
    }
}
