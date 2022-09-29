package ru.nessing.test_task.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nessing.test_task.services.ServerService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ServerController {

    private final ServerService service;

    @GetMapping
    public String getId() {
        return service.getId() + "";
    }

    @PostMapping
    public String load(@RequestParam("file") MultipartFile file,
                       @RequestParam("id") Long id) throws IOException, IllegalAccessException {
        service.load(file, id);
        return "the file was downloaded";
    }

    @PostMapping("/result")
    public String result(@RequestParam Long id) {
        return service.result(id);
    }
}
