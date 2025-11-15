package model;

public class Room implements IRoom{
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String toString() {
        return "RoomNumber: "+roomNumber+", RoomType:"+enumeration +", Price: "+price;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof IRoom) return ((IRoom) o).getRoomNumber().equals(this.roomNumber);
        return false;
    }
    @Override
    public int hashCode() {
        return roomNumber.hashCode();
    }
    @Override
    public String getRoomNumber(){
        return roomNumber;
    }
    @Override
    public Double getRoomPrice(){
        return price;
    }
    @Override
    public RoomType getRoomType(){
        return enumeration;
    }
    @Override
    public boolean isFree(){
        return price==0.0;
    }
}
