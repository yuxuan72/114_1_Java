public abstract class Role {
    // 角色名稱
    private String name;
    // 生命值
    private int health;
    // 攻擊力
    private int attackPower;

    // 建構子：初始化角色的名稱、生命值和攻擊力
    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // 取得角色名稱
    public String getName() {
        return name;
    }

    // 取得生命值
    public int getHealth() {
        return health;
    }

    // 取得攻擊力
    public int getAttackPower() {
        return attackPower;
    }
    // 設定生命值
    public void setHealth(int health) {
        this.health = health;
    }

    // 檢查角色是否存活
    public boolean isAlive() {
        return health > 0;
    }

    public abstract void attack(Role opponent);

    /**
     * 展示角色的特殊技能
     * 為什麼設計成抽象方法？
     * 1. 每個角色都有特殊技能（共同規格）
     * 2. 但每個角色的技能內容都不同（個別實作）
     * 3. 我們無法在 Role 類別中提供一個「適合所有角色」的預設實作
     */
    public abstract void showSpecialSkill();


    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("💥 " + name + " 受到 " + damage + " 點傷害！目前生命值：" + health);

        if (!isAlive()) {
            onDeath(); // 呼叫抽象方法
        }
    }
    /**
     * 死亡時的處理（抽象方法）
     * 每個角色死亡時的表現方式都不同
     */
    public abstract void onDeath();

    // 戰鬥前的準備動作
    public abstract void prepareBattle();

    // 戰鬥後的行為
    public abstract void afterBattle();
}
