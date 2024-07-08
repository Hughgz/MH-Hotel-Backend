package com.example.MH_Hotel_Backend.service;

import com.example.MH_Hotel_Backend.dtos.RoomDTO;
import com.example.MH_Hotel_Backend.exception.InternalServerException;
import com.example.MH_Hotel_Backend.exception.ResourceNotFoundException;
import com.example.MH_Hotel_Backend.model.Room;
import com.example.MH_Hotel_Backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    private final RoomRepository repository;
    @Override
    public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!photo.isEmpty()){
            byte[] photoBytes = photo.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return repository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return repository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(int roomId) throws SQLException {
        Optional<Room> room = repository.findById(roomId);
        if(room.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Room not found");
        }
        Blob photoBlob = room.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoom(int roomId) {
        Optional<Room> room = repository.findById(roomId);
        if(room.isPresent()){
            repository.deleteById(roomId);
        }
    }

    @Override
    public Room updateRoom(int roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = repository.findById(roomId).orElseThrow(() -> new RuntimeException("Room does not exists"));
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new InternalServerException("Fail updating room");
            }
        }
        return repository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(int roomId) {
        Optional<Room> room = repository.findById(roomId);
        if(room.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Room is empty");
        }
        return room;
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return repository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
    }

    @Override
    public RoomDTO convert(Room room) {
        return new ModelMapper().map(room, RoomDTO.class);
    }
}
