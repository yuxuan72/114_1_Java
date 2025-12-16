package streaming.subscription;

import streaming.Subscription;

public abstract class FreeTier extends Subscription {
    public FreeTier() {
        super("Free", "SD", 1, true);
    }
}