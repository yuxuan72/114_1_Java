public class Account {
    // 帳戶號碼
    private String accountNumber;
    // 帳戶餘額
    private double balance;

    /**
     * 建構子：初始化帳戶號碼與初始餘額
     *
     * @param accountNumber  帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);
        try{
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤:" + e.getMessage()+",預設餘額為0");
            this.balance = 0; // 設定預設餘額為0
        }
    }

    /**
     * 取得帳戶號碼
     *
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     *
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }
    /**
     * 存款方法
     *
     * @param balance  存款金額
     * @throws IllegalArgumentException 如果存款金額小於或等於零
     */
    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * 存款方法
     *
     * @param amount 存款金額
     * @throws IllegalArgumentException 如果存款金額小於或等於零
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    public void deposit(int amount, String currency) {
        double convertedAmount;
        switch (currency) {
            case "USD":
                convertedAmount = amount * 30.0; // 假設1 USD = 30 TWD
                break;
            case "EUR":
                convertedAmount = amount * 33.0; // 假設1 EUR = 33 TWD
                break;
            case "JPN":
                convertedAmount = amount * 0.28; // 假設1 JPN = 0.28 TWD
                break;
            default:
                throw new IllegalArgumentException("不支援的貨幣類型");
        }
        deposit(convertedAmount);
    }
    public void deposit (double...amounts) {
        for (double amount : amounts) {
            if (amount <= 0) {
                throw new IllegalArgumentException("存款金額必須為正數");
            }
            this.balance += amount;
        }
    }
     /**
     * 提款方法
     *
     * @param amount 提款金額
     * @throws IllegalArgumentException 如果提款金額無效或餘額不足
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("提款金額無效或餘額不足");
        }
    }
}