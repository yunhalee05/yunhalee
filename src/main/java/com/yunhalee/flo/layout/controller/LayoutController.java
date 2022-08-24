package com.yunhalee.flo.layout.controller;

import com.yunhalee.flo.layout.dto.LayoutRequest;
import com.yunhalee.flo.layout.dto.LayoutResponse;
import com.yunhalee.flo.layout.service.LayoutService;
import com.yunhalee.flo.product.dto.ProductRequest;
import com.yunhalee.flo.product.dto.ProductResponse;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @PutMapping("/{id}")
    public ResponseEntity<LayoutResponse> updateLayout(@PathVariable("id") String id, @RequestBody LayoutRequest request) {
        return ResponseEntity.ok(layoutService.updateLayout(id, request));
    }
}
