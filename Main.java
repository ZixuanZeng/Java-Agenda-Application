import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Collections;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * @author Ahmet Faruk Bayrak
 * This is Main class.
 */

public class Main {

    private LinkedList<User> users = new LinkedList<User>();
    private LinkedList<Event> events = new LinkedList<Event>();

    /**
     * Finds user with specified id
     *
     * @param number user id to search
     * @param shouldFind throws UserNotFoundException if user is not found
     * @return User object idsnatcne, or null, if not found
     */
     private User getUser(int number, boolean shouldFind) throws UserNotFoundException {
         for(User user : users) {
             if (user.getId()==number)
                 return user;
         }

         if (shouldFind)
            throw new UserNotFoundException(number);  
         return null;
     }

    /**
     * Finds event with specified id
     *
     * @param number event id to search
     * @param shouldFind throws UserNotFoundException if user is not found
     * @return User object idsnatcne, or null, if not found
     */
     private Event getEvent(int number, boolean shouldFind) throws EventNotFoundException {
         for(Event event : events) {
             if (event.getId()==number)
                 return event;
         }

         if (shouldFind)
            throw new EventNotFoundException(number);  
         return null;
     }

    /**
     * Merges last words into simple string, separated by spaces
     * Usually it's used as names and descriptions
     *
     * @param parts splitted string by spaces
     * @param startIndex starting index of name
     * @return assembled name or description
     */
    private String getName(String[] parts, int startIndex) {
        String name = parts[startIndex];

        for(int i=startIndex+1; i<parts.length; i++) 
            name += " " + parts[i];

         return name;
    }

    private void addUser(String[] parts) throws DuplicatedUserIdException {
        
        String name = getName(parts, 3); 

        int number = Integer.parseInt(parts[2]);
        User newUser = new User(number, name);
        
        try {
            if (getUser(number, false)==null) {
                users.add(newUser);
                System.out.println(newUser.getName()+" SAVED");
            }
            else {
                throw new DuplicatedUserIdException(newUser.getId());
            }
        }
        catch(UserNotFoundException ex) {
        }
    }

    private void addAnniversary(String[] parts) throws UserNotFoundException, DuplicatedEventIdException {
        String name = getName(parts, 5); 

        int number = Integer.parseInt(parts[4]);
        int userNumber = Integer.parseInt(parts[3]);

        User user = getUser(userNumber, true);
        Event newEvent = new Anniversary(name, user, number, parts[2]);
        try {
            if (getEvent(number, false)==null) {
                events.add(newEvent);
                System.out.println(name + " ADDED TO " + user.getName()+"'S AGENDA");
            }
            else {
                throw new DuplicatedEventIdException(number);
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private void addBirthday(String[] parts) throws UserNotFoundException, DuplicatedEventIdException {
        String name = getName(parts, 5); 

        int number = Integer.parseInt(parts[4]);
        int userNumber = Integer.parseInt(parts[3]);

        User user = getUser(userNumber, true);
        Event newEvent = new Birthday(name, user, number, parts[2]);
        try {
            if (getEvent(number, false)==null) {
                events.add(newEvent);
                System.out.println(name + " ADDED TO " + user.getName()+"'S AGENDA");
            }
            else {
                throw new DuplicatedEventIdException(number);        
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private void arrangeCourse(String[] parts) throws DuplicatedEventIdException {

        String name = getName(parts, 6); 

        int number = Integer.parseInt(parts[5]);
        int quota = Integer.parseInt(parts[2]);

        Course newCourse = new Course(name, quota, number, parts[3], parts[4]);
        try {
            if (getEvent(number, false)==null) {
                events.add(newCourse);
                System.out.println(newCourse.getDescription()+" ARRANGED ON "+newCourse.getDayOfWeek()+" "+newCourse.getTime());
            }
            else {
                throw new DuplicatedEventIdException(number);        
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private void arrangeEntertaimentEvent(String[] parts) throws DuplicatedEventIdException {

        String name = getName(parts, 6); 

        int number = Integer.parseInt(parts[5]);
        int quota = Integer.parseInt(parts[2]);

        EntertaimentEvent newEvent = null;
        if (parts[1].equals("SPORT"))
            newEvent = new Sport(name, quota, number, parts[3], parts[4]);
        else if (parts[1].equals("THEATER"))
            newEvent = new Theater(name, quota, number, parts[3], parts[4]);
        else if (parts[1].equals("CONCERT"))
            newEvent = new Concert(name, quota, number, parts[3], parts[4]);

        try {
            if (getEvent(number, false)==null) {
                events.add(newEvent);
                System.out.println(newEvent.getDescription()+" ARRANGED ON "+newEvent.getDayOfWeek()+" "+newEvent.getTime());
            }
            else {
                throw new DuplicatedEventIdException(number);        
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private void arrangeNewMeeting(String[] parts) throws DuplicatedEventIdException {
        int number = Integer.parseInt(parts[6]);
        int duration = Integer.parseInt(parts[4]);

        Meeting newMeeting = new Meeting(number, parts[2], parts[3], duration);
        try {
            if (getEvent(number, false)==null) {
                boolean cancelled = arrangeUsers(newMeeting, parts[5].split(","));
                if (!cancelled) {      
                    events.add(newMeeting);
                    System.out.println("A MEETING ARRANGED FOR " + newMeeting.getUserIds() + " ON "+newMeeting.getDate()+" "+newMeeting.getTime());
                }
            }
            else {
                throw new DuplicatedEventIdException(number);        
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private boolean arrangeUsers(Appointment appointment, String[] ids) {
        boolean cancel = false;

        for(int i=0; i<ids.length && !cancel; i++) {
            try {
                User user = getUser(Integer.parseInt(ids[i]), true);
                if (isAvailable(user, appointment)) {
                    appointment.attend(user);
                }
                else {
                    System.out.println("INCOMPATIBLE USER: "+ids[i]+"’S AGENDA IS NOT COMPATIBLE");
                    cancel = true;
                }                
            }
            catch(UserNotFoundException ex) {
                System.out.println(ex.getMessage());
                cancel = true;
            }
        }

        return cancel;
    }


    private void arrangeNewAppointment(String[] parts) throws DuplicatedEventIdException {

        int number = Integer.parseInt(parts[6]);
        int duration = Integer.parseInt(parts[4]);

        Appointment newAppointment = new Appointment(number, parts[2], parts[3], duration);
        try {
            if (getEvent(number, false)==null) {
                boolean cancelled = arrangeUsers(newAppointment, parts[5].split(","));
                if (!cancelled) {      
                    events.add(newAppointment);
                    System.out.println("AN APPOINTMENT ARRANGED FOR " + newAppointment.getUserIds() + " ON "+newAppointment.getDate()+" "+newAppointment.getTime());
                }
            }
            else {
                throw new DuplicatedEventIdException(number);        
            }
        }
        catch(EventNotFoundException ex) {
        }
    }

    private boolean intersectedNotRepeated(Event event1, Event event2) {
        
        //System.out.println("Event 1: "+event1.toString(-1)+" and Event 2: "+event2.toString(-2));

        long start1 = event1.getStartingMoment();
        long start2 = event2.getStartingMoment();
        long end1 = event1.getEndingMoment();
        long end2 = event2.getEndingMoment();

        //System.out.println("Event 1: " + start1 + "-" + end1 + " and Event 2: " + start2 + "-" + end2);

        if (start2<=start1 && end2>start1) {
            //System.out.println("  - TRUE1");
            return true;
        }

        if (start1<=start2 && end1>start2) {
            //System.out.println("  - TRUE2");
            return true;
        }
        //System.out.println("  - FALSE");
        return false;
    }

    private boolean intersectedTime(Event event1, Event event2) {

        if (!event1.isRepeated() && !event2.isRepeated())
            return intersectedNotRepeated(event1, event2);
        else if (event1.isRepeated() && !event2.isRepeated()) {

            while(event1.getStartingMoment() <= event2.getEndingMoment()) {
                if (intersectedNotRepeated(event1, event2))
                    return true;
                else {
                    RepeatedEvent repeatedEvent = (RepeatedEvent)event1;
                    event1 = repeatedEvent.generateNext();
                }                     
            }
        }
        else if (!event1.isRepeated() && event2.isRepeated())
            return intersectedTime(event2, event1);
        else {
            long start1 = event1.getStartingMoment();
            long start2 = event2.getStartingMoment();

            if (start1>start2) {
                return intersectedTime(event2, event1);
            }
            else {
                while(event1.getStartingMoment() <= event2.getEndingMoment()) {
                    if (intersectedNotRepeated(event1, event2))
                        return true;
                    else {
                        RepeatedEvent repeatedEvent = (RepeatedEvent)event1;
                        event1 = repeatedEvent.generateNext();
                    }                     
                }
            }
        }
            
        return false;
    }

    private boolean isAvailable(User user, Event event) {
        for(Event agendaEvent : events) {
            if (agendaEvent!=event) {
                if (agendaEvent.isAttendable() && event.isAttendable()) {
                    AttendableEvent attendabe = (AttendableEvent)agendaEvent;
                    if (attendabe.isAttended(user)) {
                        if (intersectedTime(agendaEvent, event)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void attendUsers(Event event, String description, String[] userIds) {
        int added = 0;
        for(String userId : userIds) {
            try {
                User user = getUser(Integer.parseInt(userId), true);
                AttendableEvent attendable = (AttendableEvent)event;
                if (isAvailable(user, event) && !attendable.isAttended(user)) {
                    attendable.attend(user);
                    added++;
                }
                else {
                    System.out.println("UNAVAILABLE USER: "+userId);
                }
            }
            catch(UserNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            catch(QuotaFullException ex) {   
                System.out.println(ex.getMessage());
            }    
        }
        System.out.println(added+" USERS ADDED TO ATTENDANCE LIST OF "+description);
    }

    private void attendCourse(String[] parts) throws EventNotFoundException {

        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.getEventType().equals("COURSE")) {
                String[] userIds = parts[3].split(",");
                Course course = (Course)event;
                attendUsers(event, course.getDescription(), userIds);
                return;
            }                
        }
        throw new EventNotFoundException(number, "COURSE");
    }

    private void attendEvent(String[] parts) throws EventNotFoundException {

        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.isEntertaimentEvent()) {
                String[] userIds = parts[3].split(",");
                EntertaimentEvent entertaimentEvent = (EntertaimentEvent)event;
                attendUsers(event, entertaimentEvent.getDescription(), userIds);
                return;
            }                
        }
        throw new EventNotFoundException(number, "EVENT");

    }

    private void cancelCourse(String[] parts) throws EventNotFoundException {

        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.getEventType().equals("COURSE")) {
                events.remove(event);
                System.out.println("COURSE CANCELLED:"+number);
                return;
            }                
        }
        throw new EventNotFoundException(number, "COURSE");

    }

    private void cancelEvent(String[] parts) throws EventNotFoundException {

        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.isEntertaimentEvent()) {
                events.remove(event);
                System.out.println("EVENT CANCELLED:"+number);
                return;
            }                
        }
        throw new EventNotFoundException(number, "EVENT");
    }

    private void cancelMeeting(String[] parts) throws EventNotFoundException {
        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.getEventType().equals("MEETING")) {
                events.remove(event);
                System.out.println("MEETING CANCELLED:"+number);
                return;
            }                
        }
        throw new EventNotFoundException(number, "MEETING");

    }

    private void cancelAppointment(String[] parts) throws EventNotFoundException {
        int number = Integer.parseInt(parts[2]);

        for(Event event : events) {
            if (event.getId()==number && event.getEventType().equals("APPOINTMENT")) {
                events.remove(event);
                System.out.println("APPOINTMENT CANCELLED:"+number);
                return;
            }                
        }
        throw new EventNotFoundException(number, "APPOINTMENT");
    }

    /**
     * Entry point to program
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length>0) {
            Main m = new Main();
            try {
                m.run(args[0]);
            }
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        else {
            System.out.println("Argument(s) are missing");
        }
    }

    private void listEvents(String[] parts, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        try {
            cal.setTime(sdf.parse(parts[2]));  
            int number = Integer.parseInt(parts[3]);

            long from = cal.getTimeInMillis();
            cal.add(Calendar.DATE, days);
            long to = cal.getTimeInMillis();

            ArrayList<Event> listedEvents = new ArrayList<Event>();
            for(Event event : events) {
                if (event.forUser(number)) {
                    if (event.isRepeated()) {
                        while (event.getStartingMoment()<to) {
                            RepeatedEvent repeatedEvent = (RepeatedEvent)event;                     
                            if (event.getStartingMoment()>=from)
                                listedEvents.add(event);

                             event = repeatedEvent.generateNext();
                        }
                    }
                    else {
                        if (event.getStartingMoment()>=from && event.getStartingMoment()<to)
                           listedEvents.add(event);
                    }
                }
            }
            Collections.sort(listedEvents);
            for(Event event : listedEvents)
                System.out.println(event.toString(number));
        }
        catch(ParseException ex) {
        }
    }        

    /**
     * Parsing function
     * Reads file line by line, parse commands and execute
     * Throws FileNotFoundException if file doesn't exist
     *
     * @param fileName file name to read and execute
     */
    private void run(String fileName) throws FileNotFoundException { 
        Scanner scanner = new Scanner(new File(fileName));

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.trim().length()>0)
            {
                System.out.println("COMMAND:"+command);
                String[] parts = command.split(" ");

                try {
                    if (parts[0].equals("SAVE")) {
                        if (parts[1].equals("USER")) {
                            addUser(parts);
                        }
                        else if (parts[1].equals("ANNIVERSARY")) {
                            addAnniversary(parts);
                        }
                        else if (parts[1].equals("BIRTHDAY")) {
                            addBirthday(parts);
                        }
                    }
                    else if (parts[0].equals("ARRANGE")) {
                        if (parts[1].equals("COURSE")) {
                            arrangeCourse(parts);
                        }
                        else if (parts[1].equals("SPORT")) {
                            arrangeEntertaimentEvent(parts);
                        }
                        else if (parts[1].equals("THEATER")) {
                            arrangeEntertaimentEvent(parts);
                        }
                        else if (parts[1].equals("CONCERT")) {
                            arrangeEntertaimentEvent(parts);
                        }
                        else if (parts[1].equals("MEETING")) {
                            arrangeNewMeeting(parts);
                        }
                        else if (parts[1].equals("APPOINTMENT")) {
                            arrangeNewAppointment(parts);
                        }
                    }
                    else if (parts[0].equals("ATTEND")) {
                        if (parts[1].equals("COURSE")) {
                            attendCourse(parts);
                        }
                        else if (parts[1].equals("EVENT")) {
                            attendEvent(parts);
                        }
                    }
                    else if (parts[0].equals("CANCEL")) {
                        if (parts[1].equals("COURSE")) {
                            cancelCourse(parts);
                        }
                        else if (parts[1].equals("EVENT")) {
                            cancelEvent(parts);
                        }
                        else if (parts[1].equals("MEETING")) {
                            cancelMeeting(parts);     
                        }
                        else if (parts[1].equals("APPOINTMENT")) {
                            cancelAppointment(parts);
                        } 
                    }
                    else if (parts[0].equals("LIST")) {
                        if (parts[1].equals("DAILY")) {
                            listEvents(parts, 1);
                        }
                        else if (parts[1].equals("WEELY")) {
                            listEvents(parts, 7);
                        }
                        else if (parts[1].equals("MONTHLY")) {
                            listEvents(parts, 30);
                        }
                    }
                }
                catch(Exception ex1) {
                    System.out.println(ex1.getMessage());
                    //ex1.printStackTrace();
                }
            } 
        }
    }
}