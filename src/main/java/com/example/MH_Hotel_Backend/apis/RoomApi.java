package com.example.MH_Hotel_Backend.apis;

import com.example.MH_Hotel_Backend.dtos.BookingRoomDTO;
import com.example.MH_Hotel_Backend.dtos.RoomDTO;
import com.example.MH_Hotel_Backend.exception.PhotoRetrievalException;
import com.example.MH_Hotel_Backend.exception.ResourceNotFoundException;
import com.example.MH_Hotel_Backend.model.BookingRoom;
import com.example.MH_Hotel_Backend.model.Room;
import com.example.MH_Hotel_Backend.service.IBookingService;
import com.example.MH_Hotel_Backend.service.IRoomService;
import com.example.MH_Hotel_Backend.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.ResponseCache;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoomApi {
    private final IRoomService service;
    private final IBookingService bookingService;
    @PostMapping("/new-room")
    public ResponseEntity<RoomDTO> addRoom(@RequestParam("photo")MultipartFile photo,
                                           @RequestParam("roomType") String roomType,
                                           @RequestParam("roomPrice") BigDecimal roomPrice) throws SQLException, IOException {
        Room saveRoom = service.addNewRoom(photo, roomType, roomPrice);
        RoomDTO roomDTO = new RoomDTO(saveRoom.getId(), saveRoom.getRoomType(), saveRoom.getRoomPrice());
        return ResponseEntity.status(201).body(roomDTO);
    }
    @GetMapping("/room-type")
    public ResponseEntity<List<String>> getRoomType(){
        List<String> getListType = service.getAllRoomTypes();
        return ResponseEntity.ok(getListType);
    }
    @GetMapping("/all-room")
    public ResponseEntity<List<RoomDTO>> getAllRoom() throws SQLException {
        List<Room> rooms = service.getAllRooms();
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for(Room room : rooms){
            byte[] photoBytes = service.getRoomPhotoByRoomId(room.getId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.getEncoder().encodeToString(photoBytes);
                RoomDTO roomDTO = getRoomResponse(room);
                roomDTO.setPhoto(base64Photo);
                roomDTOS.add(roomDTO);
            }
        }
        return ResponseEntity.ok(roomDTOS);
    }

    @DeleteMapping("/delete-room/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable int roomId){
        service.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-room/{roomId}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable int roomId,
                                              @RequestParam(required = false) String roomType,
                                              @RequestParam(required = false) BigDecimal roomPrice,
                                              @RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {

        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : service.getRoomPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
        Room theRoom = service.updateRoom(roomId, roomType, roomPrice, photoBytes);
        theRoom.setPhoto(photoBlob);
        RoomDTO roomDTO = getRoomResponse(theRoom);
        return ResponseEntity.ok(roomDTO);
    }
    @GetMapping("/getId/{roomId}")
    public ResponseEntity<Optional<RoomDTO>> getById(@PathVariable int roomId){
        Optional<Room> theRoom = service.getRoomById(roomId);
        return theRoom.map(room -> {
            RoomDTO RoomDTO = getRoomResponse(room);
            return  ResponseEntity.ok(Optional.of(RoomDTO));
        }).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    }


    private RoomDTO getRoomResponse(Room room) {
        List<BookingRoom> bookings = bookingService.getAllBookingsByRoomId(room.getId());
//        List<BookingRoomDTO> bookingInfo = bookings
//                .stream()
//                .map(booking -> new BookingRoomDTO(booking.getId(),
//                        booking.getCheckInDate(),
//                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new RoomDTO(room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooking(), photoBytes);
    }
}
