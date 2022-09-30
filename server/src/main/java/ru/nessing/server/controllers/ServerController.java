package ru.nessing.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nessing.server.services.ServerService;

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
        return service.load(file, id);
    }

    @PostMapping("/result")
    public String result(@RequestParam Long id) {
        return service.result(id);
    }
}
