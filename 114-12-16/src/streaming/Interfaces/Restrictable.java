package streaming.Interfaces;
import streaming.Core.User;

import javax.swing.plaf.synth.Region;
import java.util.ArrayList;

public interface Restrictable {
    AgeRating getAgeRating();
    ArrayList<Region> getRegionRestrictions();
    boolean isAccessibleBy(User user);

    class AgeRating {
    }
}