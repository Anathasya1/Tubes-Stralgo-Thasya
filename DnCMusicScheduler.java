import java.util.ArrayList;
import java.util.List;

class Edge{
    public int source;
    public int destination;
    public boolean weight;

    public Edge(int source, int destination, boolean weight){
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}
public class DnCMusicScheduler {
    private static Instrument[] INSTRUMENTS_NEEDED = {
        Instrument.PIANO,
        Instrument.DRUMS,
        Instrument.GUITAR,
        Instrument.GUITAR
    };

    private static final int WEEKS_IN_MONTH = 4;
    private int solutionCount = 0;
    private List<Musician> allMusicians;
    private List<Edge> edges;
    private int parents[];
    

    public DnCMusicScheduler(List<Musician> musicians){
        this.allMusicians = musicians;
    }

    int findRootParent(int index){
        while (parents[index] != index) {
            index = parents[index];
        }
        return index;
    }

    public void generateSchedules(){
        Musician[][] schedule = new Musician[WEEKS_IN_MONTH][INSTRUMENTS_NEEDED.length];

        this.solutionCount = 0;
        System.out.println("Finding all valid music schedules...");
        System.out.println("-------------------------------------");

        placeMusician(schedule, 0,0);

        System.out.println("-------------------------------------");
        System.out.println("Found a total of " + this.solutionCount + " possible schedules.");
    }

    private void placeMusician(Musician[][] schedule, int week, int instrumentIndex){
        if (week == WEEKS_IN_MONTH) {
            this.solutionCount++;
            printSchedule(schedule);
            System.out.println("Do u want to see more solutions? (y/n)");
            String response = Dialog.readLine();
            switch (response) {
                case "y":
                    return;
                case "n":
                    System.exit(0);    
                    break;
                default:
                    break;
            }
            
        }

        int nextInstrumentIndex = instrumentIndex + 1;
        int nextWeek = week;
        if (nextInstrumentIndex == INSTRUMENTS_NEEDED.length) {
            nextInstrumentIndex = 0;
            nextWeek = week + 1;
        }

        Instrument instrumentToFill = INSTRUMENTS_NEEDED[instrumentIndex];

        for (Musician personToTry : allMusicians) {
            
            if (canAssign(personToTry, schedule, week, instrumentToFill)) {
                
                //nyimpen nama orannya di tabel
                schedule[week][instrumentIndex] = personToTry;

                //cek next slotnya
                placeMusician(schedule, nextWeek, nextInstrumentIndex);

                //backtrack
                schedule[week][instrumentIndex] = null;
            }
        }
    }

    private boolean canAssign(Musician musician, Musician[][] schedule, int week, Instrument instrument){
        if (!musician.canPlayInstrument(instrument)) {
            return false;
        }

        if (!musician.isAvailable(week)) {
            return false;
        }

        for (int i = 0; i < INSTRUMENTS_NEEDED.length; i++) {
            if (schedule[week][i] != null && schedule[week][i].equals(musician)) {
                return false;
            }
        }

        return true;
    }

    private void printSchedule(Musician[][] schedule) {
        System.out.println("\nSchedule Solution #" + this.solutionCount);

        
        System.out.printf("%-10s", ""); // 10 spaces for week column
        for (Instrument inst : INSTRUMENTS_NEEDED) {
            System.out.printf("%-15s", inst.name());
        }
        System.out.println();
        System.out.println("----------------------------------------------------------------");

        // Print schedule
        for (int week = 0; week < WEEKS_IN_MONTH; week++) {
            System.out.printf("%-10s", "Week " + (week + 1));
            for (int inst = 0; inst < INSTRUMENTS_NEEDED.length; inst++) {
                if (schedule[week][inst] != null) {
                    System.out.printf("%-15s", schedule[week][inst].name);
                } 
            }
            System.out.println();
        }
    }


     public static void main(String[] args) {
        List<Musician> musicianPool = new ArrayList<>();

        musicianPool.add(new Musician("Alice", List.of(true, false, true, false), List.of(true, true, true,true)));
        musicianPool.add(new Musician("Bob", List.of(false, true, false, false), List.of(true, true, false, true)));
        musicianPool.add(new Musician("Charlie", List.of(false, false, true, true), List.of(false, true, true, true)));
        musicianPool.add(new Musician("David", List.of(true, false, false, false), List.of(true, false, true, true)));
        musicianPool.add(new Musician("Eve", List.of(false, true, false, false), List.of(false, true, true, true)));
        musicianPool.add(new Musician("Frank", List.of(false, false, true, true), List.of(true, true, true, true)));
        musicianPool.add(new Musician("Grace", List.of(false, false, true, true), List.of( true, false, true, true)));
        musicianPool.add(new Musician("Lala", List.of(true, false, false, false), List.of( true, false, true, true)));
        // musicianPool.add(new Musician("Alice", Instrument.PIANO, true, true, true, true));
        // musicianPool.add(new Musician("Bob", Instrument.DRUMS, true, true, false, true));
        // musicianPool.add(new Musician("Charlie", Instrument.GUITAR, false, true, true, true));
        // musicianPool.add(new Musician("David", Instrument.PIANO, true, false, true, true));
        // musicianPool.add(new Musician("Eve", Instrument.DRUMS, false, true, true, true));
        // musicianPool.add(new Musician("Frank", Instrument.GUITAR, true, true, true, true));
        // musicianPool.add(new Musician("Grace", Instrument.GUITAR, true, false, true, true));

        // --- Run the scheduler ---
        DnCMusicScheduler scheduler = new DnCMusicScheduler(musicianPool);
        scheduler.generateSchedules();
    }
}
