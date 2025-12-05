package com.rpg.roles.ranged;
import com.rpg.core.Role;
import com.rpg.interfaces.Healable;

public class Priest extends RangedRole implements Healable {  // ← 實作 Healable 介面
    private int healPower;

    public Priest(String name, int health, int attackPower, int healPower,
                  int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }

    @Override
    public void attack(Role opponent) {
        if (!consumeEnergy(10)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放神聖攻擊！");
            return;
        }

        System.out.println("✨ " + getName() + " 施放 神聖光束 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：神聖治療          ║");
        System.out.println("║ 技能描述：治療隊友的傷口    ║");
        System.out.println("║ 技能效果：恢復大量生命值      ║");
        System.out.println("║ 額外效果：淨化負面狀態      ║");
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("║ 治療力：" + healPower + " 點            ║");  // ← 新增治療力顯示
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之光熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為神聖光芒，緩緩消散在空氣中。");
        System.out.println("📜 聖經掉落在地上，散發著微弱的光芒。");
        System.out.println("---");
    }

    @Override
    public void heal(Role target) {

    }

    @Override
    public int getHealPower() {
        return 0;
    }

    @Override
    public String getRangedAttackType() {
        return "";
    }

    @Override
    protected void onRangedPrepare() {

    }

    @Override
    protected void onRangedRecover() {

    }
}