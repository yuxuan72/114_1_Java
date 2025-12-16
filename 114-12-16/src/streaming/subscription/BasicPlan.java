package streaming.subscription;

import streaming.Subscription;

public abstract class BasicPlan extends Subscription {
    public BasicPlan() {
        super("Basic", "HD", 1, false);
    }
}