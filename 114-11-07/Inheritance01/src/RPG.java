public class RPG {
    public static void main(String[] args) {
        // 建立劍客和魔法師角色
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20);
        SwordsMan swordsMan_dark = new SwordsMan("黑暗劍士", 100, 25);

        Magician magician_light = new Magician("光明法師", 80, 30, 10);
        Magician magician_dark = new Magician("黑暗法師", 80, 20, 15);

        System.out.println("戰鬥開始!");
    // 戰鬥回合}
        int round = 1;
        while (swordsMan_light.isAlive() && swordsMan_dark.isAlive()) {
            System.out.println("\n第 " + round + " 回合:");
            // 劍客攻擊
            swordsMan_light.attack(swordsMan_dark);
            if (!swordsMan_dark.isAlive()) {
                System.out.println(swordsMan_dark.getName() + " 已被擊敗!");
                break;
            }

            swordsMan_dark.attack(swordsMan_light);
            if (!swordsMan_light.isAlive()) {
                System.out.println(swordsMan_light.getName() + " 已被擊敗!");
                break;
            }

            // 魔法師攻擊
            magician_light.attack(magician_dark);
            if (!magician_dark.isAlive()) {
                System.out.println(magician_dark.getName() + " 已被擊敗!");
                break;
            }

            magician_dark.attack(magician_light);
            if (!magician_light.isAlive()) {
                System.out.println(magician_light.getName() + " 已被擊敗!");
                break;
            }

            //光明法師治癒光明劍士
            magician_light.heal(swordsMan_light);
            //黑暗法師治癒黑暗劍士
            magician_dark.heal(swordsMan_dark);

            // 顯示雙方角色狀態
            System.out.println("\n回合結束，雙方角色狀態:");
            System.out.println(swordsMan_light);
            System.out.println(swordsMan_dark);
            System.out.println(magician_light);
            System.out.println(magician_dark);

            round++;
        }

        System.out.println("\n戰鬥結束!");
    }
}
