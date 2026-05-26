package com.rikkei.bai5.controller;

import com.rikkei.bai5.model.KnowledgeRequest;
import com.rikkei.bai5.model.TransformRequest;
import com.rikkei.bai5.model.WealthRequest;
import com.rikkei.bai5.model.WishHistory;
import com.rikkei.bai5.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class GenieController {

    private final WishService wishService;

    public GenieController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/wealth")
    public ResponseEntity<WishHistory> wishForWealth(@RequestBody WealthRequest request) {
        WishHistory result = wishService.grantWealthWish(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/knowledge")
    public ResponseEntity<WishHistory> wishForKnowledge(@RequestBody KnowledgeRequest request) {
        WishHistory result = wishService.grantKnowledgeWish(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/transform")
    public ResponseEntity<WishHistory> wishForTransformation(@RequestBody TransformRequest request) {
        WishHistory result = wishService.grantTransformWish(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<WishHistory>> getHistory() {
        return new ResponseEntity<>(wishService.getHistory(), HttpStatus.OK);
    }
}
