package project;






import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Flight{
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int availableseats;
    private double economyPrice;
    private double businessPrice;
    private double firstclassPrice;
    
    public Flight() {
        this.economyPrice = 300;
        this.businessPrice = 400;
        this.firstclassPrice = 500;
    }

    public Flight(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Flight(String flightNumber, String airline, String origin, String destination, String departureTime, String arrivalTime, int availableseats, double economyPrice, double businessPrice, double firstclassPrice) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableseats = availableseats;
        this.economyPrice = economyPrice;
        this.businessPrice = businessPrice;
        this.firstclassPrice = firstclassPrice;
    }

    

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAvailableseats(int availableseats) {
        this.availableseats = availableseats;
    }

    public void setEconomyPrice(double economyPrice) {
        this.economyPrice = economyPrice;
    }

    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public void setFirstclassPrice(double firstclassPrice) {
        this.firstclassPrice = firstclassPrice;
    }
    

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getAvailableseats() {
        return availableseats;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }

    public double getFirstclassPrice() {
        return firstclassPrice;
    }
    
    
   public boolean checkAvailability(String flightNumber ) throws FileNotFoundException{
        FileManager f = new FileManager();
        for(int i = 0;i<f.loadflights().size();i+=10){
            if(flightNumber.equals(f.loadflights().get(i))){
                String availableSeats = f.loadflights().get(i+6);
                int available_seats = Integer.parseInt(availableSeats);
                System.out.println("available seats: "+available_seats);
                if(available_seats!= 0 ){
                    return true;
                }
                }     
        }
        System.out.println("This flight isn't available ): ");
        return false;  
    }
   
   public void displaySchedule() throws FileNotFoundException{
       FileManager f = new FileManager();
       File file = new File("filghts.txt");
       for(int i = 0; i<f.loadflights().size();i+=10){
           System.out.println("flight number: "+f.loadflights().get(i)+" takes off at "+f.loadflights().get(i+4)+" lands at "+f.loadflights().get(i+5));
       }
       
   }
   public void updateSchedule(String flightId,String departureTime, String arrivalTime) throws FileNotFoundException,IOException{
    List<String> lines = Files.readAllLines(Paths.get("flights.txt"));      
      for(int i = 0; i<lines.size();i++){
          String [] parts = lines.get(i).split(",");
      if(parts[0].equals(flightId)){
        parts[4]= departureTime;
        parts[5]= arrivalTime;
        lines.set(i, String.join(",", parts));
        Files.write(Paths.get("flights.txt"),lines);
          System.out.println("Schedule has been updated successfully {;");

      }
      }
      displaySchedule();
   }
    @Override
    public String toString(){
        return flightNumber+","+airline+","+origin+","+destination+","+departureTime+","+arrivalTime+","+availableseats+","+economyPrice+","+businessPrice+","+firstclassPrice;
    }
    
    public double calculatePrice(String flightnum,int choice) throws IOException{
            List<String> lines = Files.readAllLines(Paths.get("flights.txt"));
            boolean found = false;
            for(int i =0; i< lines.size(); i++){
            String [] parts = lines.get(i).split(",");
            if(parts[0].equalsIgnoreCase(flightnum)){
                found = true;
                switch(choice){
            case 1: 
                return Double.parseDouble(parts[7]);
            case 2:
                return Double.parseDouble(parts[8]);
            case 3:
                return Double.parseDouble(parts[9]);
            default:
                System.out.println("Invalid input!");
                break;
        }
                }
            }
            if(!found)
                System.out.println("flight number doesn't exist");
        return 0.0;
    }
    public boolean Isexisted(String flightnum) throws FileNotFoundException{
         FileManager f = new FileManager();
      for(int i = 0; i<f.loadflights().size();i+=10){
      if(flightnum.equals(f.loadflights().get(i))){
          return true;
      }
      }
      System.out.println("This flight doesn't exist !");
      return false;
    }
    
    public boolean reserveSeats(String flightnumber) throws FileNotFoundException{
        Flight f = new Flight();
        if(f.Isexisted(flightnumber)){
            if (availableseats >0){
                availableseats --;
                return true;
        }else{
                return false;
            }
        }
        return false;
    }
    
    
    
    
    public void deleteflight(String flightnum)throws FileNotFoundException,IOException{
    List<String> lines = Files.readAllLines(Paths.get("flights.txt"));
    List<String> updatedflights = new ArrayList<>();
    for(String line: lines){
        String [] parts = line.split(",");
    if(parts[0].equals(flightnum)){
                System.out.println("deleting flight number: "+flightnum);
                continue;
    }
        updatedflights.add(line);
    }
        Files.write(Paths.get("flights.txt"),updatedflights);
    List<String> blines = Files.readAllLines(Paths.get("bookings.txt"));
    List<String> updatedbokings = new ArrayList<>();
    for(String bline : blines){
    String [] bparts = bline.split(",");
    if(bparts[2].equals(flightnum)){
        System.out.println("deleting booking for flight number: "+flightnum);
        continue;
    }
    updatedbokings.add(bline);
    }
               Files.write(Paths.get("bookings.txt"),updatedbokings);
 
    }
    public int getAvailableSeats(){
        return availableseats;
    }
    
}

class Booking{
   private String bookingReference;
   private Customer customer; 
   private Flight flight;
   private ArrayList<Passenger> Passengers;
   private Passenger passenger;
   private String seatselection;
   private  String status;
   private  String paymentStatus ;
   private  String paymentMethod ;

    public Booking() {
        Passengers = new ArrayList<>();
    }

    public Booking(String passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passenger = new Passenger(passengerId,name,passportNumber,dateOfBirth,specialRequests) ;
    }

    public Booking(String bookingReference, Customer customer, Flight flight, ArrayList<Passenger> Passengers, String seatselection,String status ,String paymentStatus ) {
        this.bookingReference = bookingReference;
        this.customer = customer;
        this.flight = flight;
        this.Passengers = Passengers;
        this.seatselection = seatselection;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
   
 
    

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPassengers(ArrayList<Passenger> Passengers) {
        this.Passengers = Passengers;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public ArrayList<Passenger> getPassengers() {
        return Passengers;
    }

    public String getSeatselection() {
        return seatselection;
    }

    public void setSeatselection(String seatselection) {
        this.seatselection = seatselection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

   
    
    
   
   
   @Override
   public String toString(){
        return bookingReference+","+flight+","+Passengers+","+seatselection+","+status+","+paymentMethod;
    } 
   
   public void addPassenger(Passenger p) throws IOException {  
    FileManager f = new FileManager();
    if (Passengers == null) {
        Passengers = new ArrayList<>();
    }
    List<String> existingPassengers = f.loadpassengers();
    boolean exists = false;
    for (int i = 0; i < existingPassengers.size(); i += 5) {
        if (p.getPassengerId().equals(existingPassengers.get(i))) {
            exists = true;
            break;
        }
    }
    if (exists) {
        System.out.println("This passenger already exists.");
    } else {
        Passengers.add(p);
        System.out.println("Adding passenger: " + p.getName());
        String line = p.getPassengerId() + "," + p.getName() + "," + p.getPassportNumber() + ","+p.getDateOfBirth() + "," +p.getSpecialRequests();
        Files.write(Paths.get("passengers.txt"),(line + System.lineSeparator()).getBytes(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Passenger added successfully to file.");
    }
}
   public ArrayList<Passenger> getpassengers(){
       return Passengers;
   }
   
   public double calcTotalPrice(String flightnum, int choice) throws IOException{
       double total = 0.0;
       for (Passenger p : Passengers){
            total +=flight.calculatePrice(flightnum,choice);
       }
       return total;
   }

   
 
   
  public boolean confirmBooking(String BookingRef , String flightId) throws FileNotFoundException,IOException{
        List<String> lines = Files.readAllLines(Paths.get("bookings.txt")); 
        boolean found = false;
        for(int i=0; i<lines.size();i++){
        String [] parts = lines.get(i).split(",");
        if(parts[0].equalsIgnoreCase(BookingRef) && parts[2].equalsIgnoreCase(flightId)){
            System.out.println("your booking have been confirmed successfully");
            found = true;
        }
        }
    if(found)
        return true;
    else{
        System.out.println("no such Reference has been found");
        return false;
    }
}
   
   public void cancelBooking(String passengerId) throws IOException{
       boolean exist = false;
       FileManager f = new FileManager();
       for(int i=0;i<f.loadbookings().size();i+=8){
           if(passengerId.equals(f.loadbookings().get(i+3))){
               exist = true;
               f.loadbookings().remove(i);
               f.loadbookings().remove(i+1);
               f.loadbookings().remove(i+2);
               f.loadbookings().remove(i+3);
               f.loadbookings().remove(i+4);
               f.loadbookings().remove(i+5);
               f.loadbookings().remove(i+6);
               f.loadbookings().remove(i+7);

               
           }
           
       }
       List<String> lines = Files.readAllLines(Paths.get("bookings.txt"));
       List<String> updatedLines = new ArrayList<>();
       if(exist){
       for(String line :lines){
           if(!line.contains(passengerId)){
               updatedLines.add(line);
           }
       }
       System.out.println("Cancelled successfully!");
       Files.write(Paths.get("bookings.txt"), updatedLines, StandardOpenOption.TRUNCATE_EXISTING);
       
       }else{
           System.out.println("This passenger isn't exist!");
       }  
   }
   
   public void generateItinerary(Passenger p , Flight f){
       System.out.println("Itinerary generated for: "+p.getName());
       System.out.println("*Flight Itinerary*\n"+"Passenger Name: "+p.getName()+"\nPassenger ID: "+p.getPassengerId()+"\nFlight number: "+f.getFlightNumber()+"\nFrom: "+f.getOrigin()+"   To: "+f.getDestination()+"\nDeparture: "+f.getDepartureTime()+"  Arrival: "+f.getArrivalTime()+"\nSpecial requests: "+p.getSpecialRequests());
       
   }
}

class Passenger{
    private String passengerId;
    private String name;
    private String  passportNumber;
    private String dateOfBirth;
    private String specialRequests;

    public Passenger() {
    }

    public Passenger(String passengerId) {
        this.passengerId = passengerId;
    }

    public Passenger(String passengerId, String name, String passportNumber, String dateOfBirth, String specialRequests) {
        this.passengerId = passengerId;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
        this.specialRequests = specialRequests;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }
    
    
    
    public void updateInfo(String passengerId,String newpassportNumber,String newSpecialRequests) throws FileNotFoundException{
        FileManager f = new FileManager();
      for(int i = 0; i<f.loadpassengers().size();i+=5){
      if(passengerId.equals(f.loadpassengers().get(i))){
       f.loadpassengers().set(i+2,newpassportNumber );
       f.loadpassengers().set(i+4,newSpecialRequests );
      }
      }
        this.passportNumber = newpassportNumber;
        this.specialRequests = newSpecialRequests;
        
    }
    
    
    public String toString(){
        return passengerId+","+name+","+passportNumber+","+dateOfBirth+","+specialRequests;
    }
    
    public void getPassengerDetails(String passengerId) throws FileNotFoundException {
        FileManager f = new FileManager();
       for(int i = 0; i<f.loadpassengers().size();i+=5){
            if(passengerId.equals(f.loadpassengers().get(i))){ 
               System.out.println("Passenger ID: "+f.loadpassengers().get(i)+" Name: "+f.loadpassengers().get(i+1)+" Passport number: "+f.loadpassengers().get(i+2)+" Date of birth: "+f.loadpassengers().get(i+3)+" Special Requests: "+f.loadpassengers().get(i+4));
            }
        }
    }  
}

class Payment{
    private String paymentId;
    private String bookingRefrence;
    private String amount;
    private String method;
    private String status;
    private String transactionData;

    public Payment() {
    }

    public Payment(String paymentId, String bookingRefrence, String amount, String method, String status, String transactionData) {
        this.paymentId = paymentId;
        this.bookingRefrence = bookingRefrence;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.transactionData = transactionData;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setBookingRefrence(String bookingRefrence) {
        this.bookingRefrence = bookingRefrence;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTransactionData(String transactionData) {
        this.transactionData = transactionData;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public String getBookingRefrence() {
        return bookingRefrence;
    }

    public String getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getTransactionData() {
        return transactionData;
    }
    public boolean processPayment(String bookingReference,String paymentmethod) throws FileNotFoundException, IOException {
    FileManager f = new FileManager();
    List<String> bookings = f.loadbookings();
    boolean found = false;
    for (int i = 0; i < bookings.size(); i++) {
        String[] parts = bookings.get(i).split(",");
        if (parts[0].trim().equalsIgnoreCase(bookingReference.trim())) {               
            System.out.println("Payment processing...");
            validatepaymentdetails(bookingReference,paymentmethod);
            System.out.println("Payment completed and booking confirmed.");
            found = true;
        }
         }
    if(found){
    return true;
    }
    else
        return false;
}
public void validatepaymentdetails(String bookingRef,String paymentmethod)throws IOException{
        List<String> lines = Files.readAllLines(Paths.get("bookings.txt"));
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
        if (parts[0].equals(bookingRef)) {
              if(parts[6].equals("unpaid")){  
                 parts[6] = "paid";
                 if(!parts[5].equalsIgnoreCase(paymentmethod))
                     parts[5]=paymentmethod;
                lines.set(i, String.join(",", parts));
               System.out.println("Your validation has been done succeessfully (;");
              }else{
                  System.out.println("you have paid already, sir (:");
              }
            break;
        }
    }
        Files.write(Paths.get("bookings.txt"), lines);
    
    }
    public void updateStatus(String flightnum)throws IOException{
    Flight f = new Flight();
    List<String> lines = Files.readAllLines(Paths.get("bookings.txt"));
    List<String> flight = Files.readAllLines(Paths.get("flights.txt"));
    for(int i = 0; i < flight.size(); i++){
        String[] parts = flight.get(i).split(",");
        if (parts[0].equals(flightnum)){
        int availableseats=Integer.parseInt(parts[6]);
        if(availableseats>0){
            System.out.println("booking for flight number "+flightnum+" is available");
        }
        else{
        for(int j = 0; j < lines.size(); j++){
        String [] part = lines.get(i).split(",");
        if(part[2].equals(flightnum)){
        part[7]="unavailable";
        lines.set(i, String.join(",", part));
        System.out.println("unavailable! seats are complete");
        Files.write(Paths.get("bookings.txt"), lines);

        }
        }
        
        }
}
    }
    }
}

class BookingSystem{
    private User users;
    private Flight flights;
    private Booking bookings;
    private Passenger passengers;

    public BookingSystem() {
    }

    public BookingSystem(User users, Flight flights, Booking bookings, Passenger passengers) {
        this.users = users;
        this.flights = flights;
        this.bookings = bookings;
        this.passengers = passengers;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public void setFlights(Flight flights) {
        this.flights = flights;
    }

    public void setBookings(Booking bookings) {
        this.bookings = bookings;
    }

    public void setPassengers(Passenger passengers) {
        this.passengers = passengers;
    }

    public User getUsers() {
        return users;
    }

    public Flight getFlights() {
        return flights;
    }

    public Booking getBookings() {
        return bookings;
    }

    public Passenger getPassengers() {
        return passengers;
    }
    
public boolean searchFlights(String origin , String destination) throws FileNotFoundException{
        FileManager f = new FileManager();
        boolean found = false;
        for (int i = 0;i< f.loadflights().size();i+=10){
           if(origin.equals(f.loadflights().get(i+2)) && destination.equals(f.loadflights().get(i+3))){
               if(Integer.parseInt(f.loadflights().get(i+6))!=0){
                   System.out.println("there are available seats(:");   
                System.out.println("Flight number: "+f.loadflights().get(i)+" Airline: "+f.loadflights().get(i+1)+" Origin: "+f.loadflights().get(i+2)+" Destination: "+f.loadflights().get(i+3)+" Departure time: "+f.loadflights().get(i+4)+" Arrival time: "+f.loadflights().get(i+5)+" Available seats: "+f.loadflights().get(i+6));
                found= true;
                }
               else{
               System.out.println("no available seats");
               return false;
               }       
           }
        }
        if(!found){
        System.out.println("The flight doesn't exist ):");
        return false;
        }
                return false;

    }
public void createBooking(String flightNumber , int numoftickets) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get("flights.txt"));
        boolean updated = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
        if (parts[0].equals(flightNumber)) {
            int seats = Integer.parseInt(parts[6]);
            if (seats > 0&& seats > numoftickets) {
                seats-= numoftickets;
                parts[6] = String.valueOf(seats);
                lines.set(i, String.join(",", parts));
                updated = true;
                System.out.println("Booking successful for flight " + flightNumber + ". Remaining seats: " + seats);
            } else {
                System.out.println("No seats available for flight " + flightNumber);
            }
            break;
        }
    }
    if (updated) {
        Files.write(Paths.get("flights.txt"), lines);
        System.out.println("Flight updated successfully.");
    }
    else{
            System.out.println("this flight isn't existed");

    }
}
Payment p = new Payment();
public void generateticket(String bookingRefrence, String paymentmethod) throws FileNotFoundException, IOException {
    List<String> bookings = Files.readAllLines(Paths.get("bookings.txt"));
    String customerId = null;
    String filghtnum = null;
    boolean bookingFound = false;
    if (p.processPayment(bookingRefrence, paymentmethod)) {
        System.out.println("Generating your ticket....");
        System.out.println("**************************************************");
        System.out.println("Bo_Re   cus_Id   fli_Id  pas_Id  seat  pay_sta");
        for (int i = 0; i < bookings.size(); i++) {
            String[] bparts = bookings.get(i).split(",");
            if (bparts[0].trim().equals(bookingRefrence)) {
                customerId = bparts[1].trim();
                filghtnum = bparts[2].trim();
                bookingFound = true;

        for (int j = 0; j < bparts.length; j++) {
            if (j == 5 || j == 7) 
                continue;
            System.out.print(bparts[j].trim() + "\t");
        }
        System.out.println();
                break;
            }
        }

        if (!bookingFound) {
            System.out.println("Booking reference not found.");
            return;
        }

        System.out.println("**************************************************");
        System.out.println("userID      username    name            contactInfo");
        List<String> users = Files.readAllLines(Paths.get("users.txt"));
        boolean userFound = false;
        for (String userLine : users) {
            String[] uparts = userLine.split(",");
            if (uparts.length < 6) continue; 
            if (uparts[0].trim().equalsIgnoreCase(customerId)) {
                userFound = true;
                System.out.print(uparts[0].trim() + "\t"); 
                System.out.print(uparts[1].trim() + "\t"); 
                System.out.print(uparts[3].trim() + "\t"); 
                System.out.print(uparts[5].trim() + "\t"); 
                System.out.println();
                break;
            }
        }

        if (!userFound) {
            System.out.println("User details not found.");
        }

        System.out.println("**************************************************");
        System.out.println("airline     destination  departureTime");
        List<String> flights = Files.readAllLines(Paths.get("flights.txt"));
        boolean flightFound = false;
         for (int i = 0; i < flights.size(); i++) {
            String[] fparts = flights.get(i).split(",");
            if (fparts.length < 7) 
                continue; 

            if (fparts[0].trim().equalsIgnoreCase(filghtnum)) {
                flightFound = true;
                System.out.print(fparts[1].trim() + "\t"); 
                System.out.print(fparts[3].trim() + "\t"); 
                System.out.print(fparts[4].trim() + "\t"); 
                System.out.println();
                break;
            }
        }

        if (!flightFound) {
            System.out.println("Flight details not found.");
        }

        System.out.println("**************************************************");

    } else {
        System.out.println("Reference not found. Cannot generate ticket.");
    }
}
}

class FileManager{
           
    public ArrayList<String> loadusers() throws FileNotFoundException{
        File user = new File("users.txt");
        Scanner input = new Scanner(user);
        ArrayList <String> loadusers =new ArrayList<String>();
        if(input.hasNextLine()){
            input.nextLine(); //to ignore the first line int the file
        }
        while (input.hasNextLine()){ //to read all lines 
        String line = input.nextLine();
        String [] parts = line.split(",");
        for(int i = 0; i<parts.length;i++){
            loadusers.add(parts[i]); // to use the user and the pass only  
        }
        }
        input.close();
        return loadusers;
    }
    
    public ArrayList<String> loadbookings() throws FileNotFoundException{
        File bookings = new File("bookings.txt");
        Scanner input = new Scanner(bookings);
        ArrayList<String> loadbookings = new ArrayList<String>();
        if(input.hasNextLine()){
        input.nextLine();
        }
        while(input.hasNextLine()){
            String line = input.nextLine();
            String [] parts = line.split(",");
        for(int i = 0; i<parts.length; i++){
            loadbookings.add(parts[i]);
                }
        }
    input.close();
    return loadbookings;   
}

    public ArrayList<String> loadpassengers() throws FileNotFoundException{
        File passengers = new File("passengers.txt");
        Scanner input = new Scanner(passengers);
        ArrayList<String> loadpassengers = new ArrayList<String>();
        if(input.hasNextLine())
            input.nextLine();
        while(input.hasNextLine()){
            String line = input.nextLine();
            String [] parts = line.split(",");
        for(int i = 0; i < parts.length; i++){
            loadpassengers.add(parts[i]);
            }
        }
    input.close();
    return loadpassengers;
    }

    public ArrayList<String> loadflights() throws FileNotFoundException{
        File flights = new File("flights.txt");
        Scanner input = new Scanner(flights);
        ArrayList<String> loadflights = new ArrayList<String> ();
        if(input.hasNextLine())
            input.nextLine();
        while(input.hasNextLine()){
            String line = input.nextLine();
            String [] parts = line.split(",");
        for(int i = 0; i<parts.length; i++){
            loadflights.add(parts[i]);
            }
        }
    input.close();
    return loadflights;
    }
    
    public ArrayList<String> loadsettings() throws FileNotFoundException{
        File settings = new File("settings.txt");
        Scanner input = new Scanner(settings);
        ArrayList<String> loadsettings = new ArrayList<String>();
        if(input.hasNextLine())
            input.nextLine();
        while(input.hasNextLine()){
            String line = input.nextLine();
            String [] parts = line.split(",");
        for(int i = 0; i < parts.length; i++){
            loadsettings.add(parts[i]);
            }
        }
    input.close();
    return loadsettings;
    }
    
    public ArrayList<String> loadlogs() throws FileNotFoundException{
        File user = new File("logs.txt");
        Scanner input = new Scanner(user);
        ArrayList <String> loadlogs =new ArrayList<String>();
        if(input.hasNextLine()){
            input.nextLine(); //to ignore the first line int the file
        }
        while (input.hasNextLine()){ //to read all lines 
        String line = input.nextLine();
        String [] parts = line.split(",");
        for(int i = 0; i<parts.length;i++){
            loadlogs.add(parts[i]); // to use the user and the pass only  
        }
        }
        input.close();
        return loadlogs;
    }
    
    
    public void saveflights(Flight f) throws FileNotFoundException, IOException{
    HashSet<String> lines = new HashSet<>();
    File file = new File("flights.txt");
    if(file.exists()){
    BufferedReader b = new BufferedReader(new FileReader(file));
    String line;
    while((line = b.readLine()) !=null){
    lines.add(line.trim());
    }
    b.close();
    }
    String fdata = f.toString();
    if(!lines.contains(fdata)){
    FileWriter w = new FileWriter("flights.txt",true);
    PrintWriter p = new PrintWriter(w);
    p.println(fdata);
    p.close();
    w.close();
    }
    }
    
        public void saveUsers(User u) throws FileNotFoundException, IOException{
    HashSet<String> lines = new HashSet<>();
    File file = new File("users.txt");
    if(file.exists()){
    BufferedReader b = new BufferedReader(new FileReader(file));
    String line;
    while((line = b.readLine()) !=null){
    lines.add(line.trim()); // remove spaces
    }
    b.close();
    }
    String fdata = u.toString();
    if(!lines.contains(fdata)){
    FileWriter w = new FileWriter("users.txt",true);
    PrintWriter p = new PrintWriter(w);
    p.println(fdata);
    p.close();
    w.close();
    }
    }
    public void saveBooking( String Bookingref, String customerId, String flightId, String passengerId, String seatselection, String paymentmethod, String paymentstatus, String status) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("bookings.txt"));
        Set<String> existingRefs = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
        String[] parts = lines.get(i).split(",");
        if (parts.length > 0) {
            existingRefs.add(parts[0].trim());
        }
    }
        if (!existingRefs.contains(Bookingref.trim())) {
        String newLine = String.join(",",Bookingref,customerId,flightId,passengerId,seatselection,paymentmethod,paymentstatus,status);
        lines.add(newLine);
        Files.write(Paths.get("bookings.txt"), lines);
        System.out.println("Booking added successfully!");
    } else {
        System.out.println("Booking reference already exists!");
    }
}
    
    public void savePassenger(Passenger ps ) throws FileNotFoundException, IOException{
    HashSet<String> lines = new HashSet<>();
    File file = new File("passengers.txt");
    if(file.exists()){
    BufferedReader b = new BufferedReader(new FileReader(file));
    String line;
    while((line = b.readLine()) !=null){
    lines.add(line.trim());
    }
    b.close();
    }
    String fdata = ps.toString();
    if(!lines.contains(fdata)){
    FileWriter w = new FileWriter("passengers.txt",true);
    PrintWriter p = new PrintWriter(w);
    p.println(fdata);
    p.close();
    w.close();
    }
    }
    
    
}

abstract class User{
    private String userId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String contactInfo;

    public User() {
    }

    public User(String userId, String username, String password, String name, String email, String contactInfo) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.contactInfo = contactInfo;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactInfo() {
        return contactInfo;
    }
    
    
    public boolean login(String username , String password) throws FileNotFoundException, IOException{
        FileManager f = new FileManager();
        List<String> users = Files.readAllLines(Paths.get("users.txt"));
        boolean found = false;
        for(int i = 1;i<users.size();i++){
            String [] parts = users.get(i).split(",");
            if(parts[1].equalsIgnoreCase(username)){
                if(parts[2].equalsIgnoreCase(password)){
                    if(!parts[6].equalsIgnoreCase("inactive")){
                    System.out.println("Successfully login (: ");
                    String log = username+" logged in at: "+java.time.LocalDateTime.now();
                    Files.write(Paths.get("logs.txt"),(log +System.lineSeparator()).getBytes(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
                    System.out.println("Logs have been updated successfully {;");
                    found = true;
                    }else{
                    System.out.println("you are blocked");
                }
                }
                }     
        }
    if(found)
        return true;
    else
        return false;
    }
    
    
    
    public void logout(){
        System.out.println("Logging out.....");
    }
    
    
    public void updateProfile(String username) throws FileNotFoundException,IOException{
       List<String> lines = Files.readAllLines(Paths.get("users.txt"));
       Scanner input = new Scanner(System.in);
       for(int i = 0; i<lines.size(); i++){
       String [] parts = lines.get(i).split(",");
       if(parts[1].equals(username)){
           boolean loop = true;
           while(loop){System.out.println("\n---which one would you like to change---");
           System.out.println("1. password\n2. email\n3. contact info\n4. exit");
           int choice = input.nextInt();
           switch(choice){
               case 1:
                   System.out.println("enter your new password (string)");
                    parts[2] = input.next();
                    lines.set(i, String.join(",", parts));
                    System.out.println("password updated successfully");
                    break;
               case 2:
                   System.out.println("enter your new email");
                    parts[4] = input.next();
                    lines.set(i, String.join(",", parts));
                    System.out.println("email updated successfully");
                    break;
               case 3:
                   System.out.println("enter your new contact info");
                    parts[5] = input.next();
                    lines.set(i, String.join(",", parts));
                    System.out.println("contact info updated successfully");
                    break;
               case 4:
                   loop = false;
                   break;
               default:
                   System.out.println("invalid number");
                   break;
           
           }
       }
       }
       }
       Files.write(Paths.get("users.txt"),lines);
      }
    
    
 public String toString(){
        return userId+","+username+","+password+","+name+","+email+","+contactInfo;
    }   
 
}

class Customer extends User{
    private String customerId;
    private String address;
    private String bookingHistory;
    private String preferences;

    public Customer() {
    }

    public Customer(String customerId) {
        this.customerId = customerId;
    }
    
    public Customer(String userId, String username, String password, String name, String email, String contactInfo) {
        super(userId, username, password, name, email, contactInfo);
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBookingHistory(String bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getBookingHistory() {
        return bookingHistory;
    }

    public String getPreferences() {
        return preferences;
    }
    BookingSystem BS = new BookingSystem();
    public boolean searchFlights(String orrigin, String destination) throws FileNotFoundException{
        return BS.searchFlights(orrigin,destination);
    }
    public void createBooking(String flightNumber , int tickets) throws IOException {
        
        BS.createBooking(flightNumber ,tickets);
    }
    public void viewbookings() throws FileNotFoundException{
       FileManager f = new FileManager();
       for(int i = 0; i<f.loadbookings().size();i+=8){
           System.out.println("Booking reference: "+f.loadbookings().get(i)+" customer Id: "+f.loadbookings().get(i+1)+" flight Id: "+f.loadbookings().get(i+2));
       }
    }
    public void cancelBooking(String passengerId) throws IOException{
        Booking b = new Booking();
        b.cancelBooking(passengerId);
    }

}

class Agent extends User{
    private String agentId;
    private String department;
    private String commission;

    public Agent() {
    }

    public Agent(String userId, String username, String password, String name, String email, String contactInfo) {
        super(userId, username, password, name, email, contactInfo);
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getDepartment() {
        return department;
    }

    public String getCommission() {
        return commission;
    }
    
    public void manageflights(Flight flight) throws IOException{
    FileManager f = new FileManager();
    Scanner input = new Scanner(System.in);
    String flightnum;
    String departime;
    String arrivtime;
    boolean loop = true;
    while(loop){
        System.out.println("\n---Manage Flights---");
        System.out.println("1. add new flight\n2. edit filght\n3. delete flight\n4. view all flights\n5. exit");
        int choice = input.nextInt();
        switch(choice){
            case 1:
                System.out.println("enter flight number:");
                flight.setFlightNumber(input.next());
                System.out.println("enter the airline:");
                flight.setAirline(input.next());
                System.out.println("enter the origin:");
                flight.setOrigin(input.next());
                System.out.println("enter the destenation:");
                flight.setDestination(input.next());
                System.out.println("enter departure time:");
                flight.setDepartureTime(input.next());
                System.out.println("enter arrival time:");
                flight.setArrivalTime(input.next());
                System.out.println("enter total seat number:");
                flight.setAvailableseats(input.nextInt());
                System.out.println("enter economy price:");
                flight.setEconomyPrice(input.nextDouble());
                System.out.println("enter business price:");
                flight.setBusinessPrice(input.nextDouble());
                System.out.println("enter first class price:");
                flight.setFirstclassPrice(input.nextDouble());
                f.saveflights(flight);
                break;
            case 2:
                List<String> fline = Files.readAllLines(Paths.get("flights.txt"));
                boolean found = false;
                System.out.println("enter the flight number:");
                flightnum = input.next();
                for(int i =0; i< fline.size(); i++){
                String [] parts = fline.get(i).split(",");
                if(parts[0].equalsIgnoreCase(flightnum)){
                System.out.println("enter new depature time:");
                departime = input.next();
                System.out.println("enter new arrival time:");
                arrivtime = input.next();
                flight.updateSchedule(flightnum, departime,arrivtime );
                found = true;
                    }
                }
                if(!found)
                    System.out.println("flight doesn't exist");
                break;
            case 3:
                System.out.println("enter the flight number:");
                flightnum = input.next();
                flight.deleteflight(flightnum);
                break;
           case 4:
               flight.displaySchedule();
               break;
           case 5:
               loop= false;
               break;
           default:
               System.out.println("invalid choice");
               break;
        }
    
    }
    }
    public void creatbookingforcust(Flight f,int tickets) throws IOException{
    BookingSystem B = new BookingSystem();
    FileManager fi = new FileManager();
    Payment p = new Payment();
    List<String> fline = Files.readAllLines(Paths.get("flights.txt"));
    Scanner input = new Scanner(System.in);
    System.out.println("enter the number of the flight:");
    String flightnum = input.next();
    boolean found = false;
    for(int i =0; i< fline.size(); i++){
    String [] fparts = fline.get(i).split(",");
    if(fparts[0].equalsIgnoreCase(flightnum)){
        found = true;
        B.createBooking(flightnum,tickets);
        System.out.println("enter the booking reference:");
        String bookingref = input.next();
        System.out.println("enter the customer ID:");
        String cID = input.next();
        List<String> uline = Files.readAllLines(Paths.get("users.txt"));
        boolean ufound = false;
        for(int j =0; j< uline.size(); j++){
        String [] uparts = uline.get(j).split(",");
        if(uparts[0].equalsIgnoreCase(cID)){
        System.out.println("enter the passenger ID: ");
        String passengerId = input.next();
        System.out.println("enter seat class: ");
        String seatnum = input.next();
        fi.saveBooking(bookingref, cID, flightnum, passengerId, seatnum, null, "unpaid", "available");
        System.out.println("would you like to pay now?!");
        boolean yes = true;
        System.out.println("1. yes i'd love to\n2. no not now");
        int choice = input.nextInt();
        while(yes){
        switch(choice){
            case 1:
            System.out.println("what pay method do you prefer?");
            System.out.println("1. cash\n2. credit");
            String paym = input.next();
            if(seatnum.equalsIgnoreCase("first"))
                    System.out.println("total price is: "+tickets*f.calculatePrice(flightnum,3));
            else if(seatnum.equalsIgnoreCase("business"))
                    System.out.println("total price is: "+tickets*f.calculatePrice(flightnum,2));
            else
                    System.out.println("total price is: "+tickets*f.calculatePrice(flightnum,1));
            if(paym.equals("1")){
            p.processPayment(bookingref, "cash");
            }
            else
               p.processPayment(bookingref, "credit");
            yes = false;
            break;
            case 2:
                yes = false;
                break;
            default:
                System.out.println("invalid choice");
                break;
                 }
        }
        ufound = true;
         }
        }
        if(!ufound)
            System.out.println("user id doesn't exist");
            }
    }
    if(!found){
        System.out.println("flight doesn't exist");
    }
    }
    
    
    public void modifybooking(String username) throws IOException{
    updateProfile(username);
    }
    
    
    
    public void generatereports() throws FileNotFoundException{
       Flight f = new Flight ();
        Scanner input = new Scanner(System.in);
        System.out.println("\n---what report would you like to generate---");
        System.out.println("1. flight report\n2. booking report\n3. customer report\n4. exit");
        int choice = input.nextInt();
        boolean loop = true;
        while(loop){
        switch(choice){
            case 1:
                f.displaySchedule();
               loop = false;
                break;
            case 2:
               FileManager fi = new FileManager();
               for(int i = 0; i<fi.loadbookings().size();i+=8){
               System.out.println("customer id: "+fi.loadbookings().get(i+1)+" payment status: "+fi.loadbookings().get(i+6));
               }
              loop = false;
               break;
            case 3:
                FileManager fil = new FileManager();
               for(int i = 0; i<fil.loadusers().size();i+=8){
               System.out.println("user id: "+fil.loadusers().get(i)+" user name: "+fil.loadusers().get(i+1));
               }
               loop = false;
               break;
            case 4:
                loop = false;
                break;
            default:
                System.out.println("invalid number");
                loop = false;
                break;
               }
        }

    }
    
}

class Admin extends User{
    private String adminId ;
    private int securityLevel;

    public Admin() {
    }

    public Admin(String userId, String username, String password, String name, String email, String contactInfo) {
        super(userId, username, password, name, email, contactInfo);
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getAdminId() {
        return adminId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }
    
    public void createUser(String userId,String username,String password,String name,String email, String number ) throws IOException{
        List<String> users = Files.readAllLines(Paths.get("users.txt"));
        boolean exist = false;
        for (String line: users){
            String[] parts = line.split(",");
            if(parts.length>1 && parts[1].equals(username)){
                exist = true;
                break;
            }
        }if(!exist){
            String userLine = userId+","+username+","+password+","+name+","+email+","+number+","+"unknown"+","+"unknown";
            Files.write(Paths.get("users.txt"), 
                    (userLine + System.lineSeparator()).getBytes(), 
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("User created successfully.");

        }else{
            System.out.println("User already exists!");
        }
    }
    
    public  void modifySystemSetting(String settingname, String newValue) throws IOException {
    Path path = Paths.get("settings.txt");
    List<String> lines = new ArrayList<>();
    boolean found = false;
    if (Files.exists(path)) {
        List<String> fileLines = Files.readAllLines(path);
        for (String line : fileLines) {
            String trimmedLine = line.trim();
            if (trimmedLine.toLowerCase().startsWith(settingname.toLowerCase() + "=")) {
                lines.add(settingname + "= " + newValue);
                found = true;
            } else {
                lines.add(line);
            }
        }
    }

    if (!found) {    
        lines.add(settingname + "=" + newValue);
    }
    Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    System.out.println("Setting '" + settingname + "' updated to '" + newValue + "'");
}
    

    public void viewLogs() throws FileNotFoundException, IOException{
        FileManager f = new FileManager();
        Path path = Paths.get("logs.txt");
        List<String> lines = Files.readAllLines(path);
        System.out.println("=============================================================");
        for (int i= 0;i<f.loadlogs().size();i++){
            System.out.println("Username: "+f.loadlogs().get(i));
                
        }
        System.out.println("=============================================================");

}
 
    
    public void manageUserAccess(String username , int action) throws IOException{
        Path path = Paths.get("users.txt");
        List<String> lines = Files.readAllLines(path);
        boolean updated = false;
        for(int i=0;i<lines.size();i++){
            String [] parts = lines.get(i).split(",");
            if(parts.length >= 6 && parts[1].trim().equals(username)){
                switch(action){
                    case 1: //disable account
                        parts[6] = "inactive";
                        break;
                    case 2: //enable account
                        parts[6] = "enable";
                        break;
                    case 3: //promote to be admin
                        parts[7] ="admin";
                        break;
                    case 4: // demote to user
                        parts[7] ="user";
                        break;
                    default:
                        System.out.println("Invalid input ):");
                        break;
                        
                }
                lines.set(i, String.join(",", parts));
                updated = true;
                break;
            }
        }
       if(updated){
           Files.write(path,lines);
           System.out.println("User access updated successfully!");
       } else{
           System.out.println("User not found ): ");
       }
    }
    
}


        

    
    




public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        Scanner input = new Scanner(System.in);
        System.out.println("\n---WELCOME TO OUT FLIGHT BOOKING SYSTEM!---");
        System.out.println("Are you a/an:\n1. Customer\n2. Agent\n3. Admin");
        System.out.println("===============================================");
        int cho = input.nextInt();
        System.out.println("Trying to logging you in.....");
        switch(cho){
            case 1:
               Customer c = new Customer();
                System.out.println("enter your username: ");
                String usna = input.next();
                System.out.println("enter your password: ");
                String pass = input.next();
               if(c.login(usna, pass)){
                   boolean out = true;
                   while(out){
                   System.out.println("---MENU---");
                  System.out.println("1. Search Flight\n2. View Bookings\n3. Cancle Booking\n4. Update Profile\n5. logout");
                   switch(input.nextInt()){
                       case 1:
                           System.out.println("enter flight origin:");
                           String or = input.next();
                           System.out.println("enter flight destination");
                           String des = input.next();
                           if(c.searchFlights(or, des)){
                           continue;
                           }
                           break;
                       case 2:
                           c.viewbookings();
                           break;
                       case 3:
                           System.out.println("enter the passenger id: ");
                           String pi = input.next();
                           c.cancelBooking(pi);
                           break;
                       case 4:
                           c.updateProfile(usna);
                           break;
                       case 5:
                           c.logout();
                           out = false;
                           break;
                       default:
                           System.out.println("invalid number");
                           break;
                   }
               }
                   
               }else{
                   System.out.println("invalid username or password");
               }
               break;
            case 2:
                 Agent a = new Agent();
                System.out.println("enter your username: ");
                String usnam = input.next();
                System.out.println("enter your password: ");
                String passw = input.next();
               if(a.login(usnam, passw)){
                   boolean out = true;
                   while(out){
                   System.out.println("---MENU---");
                   System.out.println("1. Manage Flights\n2. Creat Booking For Customer\n3. Modify Bookings\n4. Generate Reports\n5. Update Profile\n6. logout");
                   switch(input.nextInt()){
                       case 1:
                           Flight f = new Flight(); 
                           a.manageflights(f);
                           break;
                       case 2:
                           Flight fl = new Flight(); 
                           System.out.println("enter the tickets amount: ");
                           int sn = input.nextInt();                           
                           a.creatbookingforcust(fl, sn);
                           break;
                       case 3:
                           a.modifybooking(usnam);
                           break;
                       case 4:
                           a.generatereports();
                           break;
                       case 5:
                           a.updateProfile(usnam);
                           break;
                       case 6:
                           a.logout();
                           out = false;
                           break;
                       default:
                           System.out.println("invalid number");
                           break;
                   }
               }
                   
               }else{
                   System.out.println("invalid username or password");
               }
               break;
            case 3:
                Admin d = new Admin();
                System.out.println("enter your username: ");
                String usname = input.next();
                System.out.println("enter your password: ");
                String passwo = input.next();
                if(d.login(usname, passwo)){
                   boolean out = true;
                   while(out){
                   System.out.println("---MENU---");
                   System.out.println("1. Creat User\n2. Modify System Sttings\n3. View System Logs\n4. Manage User Access\n5. Update Profile\n6. logout");
                   switch(input.nextInt()){
                       case 1:
                           System.out.println("enter user ID:");
                           String uid = input.next();
                           System.out.println("enter username:");
                           String un = input.next();
                           System.out.println("enter password:");
                           String pa = input.next();
                           System.out.println("enter your name:");
                           String na = input.next();
                           System.out.println("enter your email:");
                           String ema = input.next();
                           System.out.println("enter your phone number:");
                           String ph = input.next();
                           d.createUser(uid, un, pa, na, ema,ph);
                           break;
                       case 2:
                           System.out.println("enter setting name:");
                           String st = input.next();
                           System.out.println("enter the new value:");
                           String v = input.next();
                           d.modifySystemSetting(st, v);
                           break;
                       case 3:
                           d.viewLogs();
                           break;
                       case 4:
                           System.out.println("enter username:");
                           String u = input.next();
                           System.out.println("---enter your choice---");
                           System.out.println("1. disable account\n2. enable account\n3. promote to be admin\n4. demote to user");
                          int ch = input.nextInt();
                           d.manageUserAccess(u, ch);
                           break;
                       case 5:
                           d.updateProfile(usname);
                           break;
                       case 6:
                           d.logout();
                           out = false;
                           break;
                       default:
                           System.out.println("invalid number");
                           break;
                   }
               }
                   
               }else{
                   System.out.println("invalid username or password");
               }
               break;
            default:
                System.out.println("invalid choice");
        }
        System.out.println("---THANKS FOR USING OUR SYSTEM!---");
    }
    
}
