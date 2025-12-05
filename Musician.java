import java.util.List;

public class Musician {
    String name;
    // Instrument instrument; ini diganti pkae list
    List<Boolean> instrument;
    // boolean[] availability; ini diganti pake list
    List<Boolean> availability;


    public Musician(String name, List<Boolean> instrument, List<Boolean> availability){
        this.name = name;
        this.instrument = instrument;
        this.availability = availability;
    }

    public boolean isAvailable(int week){
        if (week >= 0 && week < availability.size()) {
            return availability.get(week);
        }
        return false;
    }

    // public boolean canPlayInstrument(Instrument instrumentSearch){
    //     int indexInstrument = instrumentSearch.ordinal(); //ini tuh buat dapetin index dari enumnya gitu dia alat musik mana

    //     //buat dapetin si instrumentnya
    //     if (indexInstrument >=0 && indexInstrument < instrument.size()) {
    //         return instrument.get(indexInstrument);
    //     }
    //     return false;
    // }
}
