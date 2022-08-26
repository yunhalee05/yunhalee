package com.yunhalee.flo.layout.controller;

import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.dto.LayoutResponses;
import com.yunhalee.flo.layout.service.LayoutService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layouts")
public class LayoutController {

    private final LayoutService layoutService;

    public LayoutController(LayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @PostMapping
    public ResponseEntity<LayoutResponse> createLayout(@RequestBody LayoutRequest request) {
        LayoutResponse layout = layoutService.createLayout(request);
        return ResponseEntity.created(URI.create("/layouts/" + layout.getId())).body(layout);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LayoutResponse> findLayout(@PathVariable("id") String id) {
        return ResponseEntity.ok(layoutService.findLayout(id));
    }

    @GetMapping
    public ResponseEntity<LayoutResponses> findLayouts() {
        return ResponseEntity.ok(layoutService.findLayouts());
    }


    @PutMapping("/{id}")
    public ResponseEntity<LayoutResponse> updateLayout(@PathVariable("id") String id, @RequestBody LayoutRequest request) {
        return ResponseEntity.ok(layoutService.updateLayout(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLayout(@PathVariable("id") String id) {
        layoutService.deleteLayout(id);
        return ResponseEntity.noContent().build();
    }

}
