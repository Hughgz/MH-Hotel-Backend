package com.example.MH_Hotel_Backend.apis;

import com.example.MH_Hotel_Backend.service.RoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomApi {
    private final RoomService service;

    public RoomApi(RoomService service) {
        this.service = service;
    }

    public
}
