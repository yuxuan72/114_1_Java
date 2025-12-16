package streaming.subscription;

import streaming.Subscription;

public abstract class StandardPlan extends Subscription {
    public StandardPlan() {
        super("Standard", "FHD", 2, false);
    }
}