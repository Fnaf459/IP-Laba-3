package ru.ulstu.is.sbapp.student.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.student.model.Visit;
import ru.ulstu.is.sbapp.student.service.VisitService;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/{id}")
    public Visit getVisit(@PathVariable Long id) {
        return visitService.findVisit(id);
    }

    @GetMapping("/")
    public List<Visit> getVisit() {
        return visitService.findAllVisit();
    }

    @PostMapping("/")
    public Visit createVisit(@RequestParam("date") String date) {
        return visitService.addVisit(date);
    }

    @PatchMapping("/{id}")
    public Visit updateVisit(@PathVariable Long id,
                             @RequestParam("date") String date) {
        return visitService.updateVisit(id, date);
    }

    @DeleteMapping("/{id}")
    public Visit deleteVisit(@PathVariable Long id) {
        return visitService.deleteVisit(id);
    }
}
