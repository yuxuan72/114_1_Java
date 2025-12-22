public class User {
    private String accountId;
    private Subscription subscription;
    private ArrayList<Profile> profiles; // 一個帳號多個使用者檔案

    public int getAge() { return profiles.get(0).getAge(); } // 簡化邏輯
}