package streaming.subscription;

import streaming.Subscription;

public abstract class PremiumPlan extends Subscription {
    public PremiumPlan() {
        super("Premium", "4K", 4, false);
    }
}