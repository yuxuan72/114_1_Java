public class AccountTest {
    private int customerCount;
    public  static void main(String[] args){
        Account[] customers = new Account[10];
        Account acc1 = new Account("A001","Alice",5000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002","Bob",3000);
        addCustomer(customers, acc2);

        Account acc3 = new Account("A003","Charlie",-100);
        addCustomer(customers, acc3);
    }

    public static void addCustomer(Account[] customers,Account newAccount){
        for(int i=0;i<customers.length;i++){
            if(customers[i]==null){
                customers[i]=newAccount;
                System.out.printf("成功新增客戶: %s, 帳號: %s, 餘額: %.2f%n",
                        newAccount.ownerName,
                        newAccount.getAccountNumber(),
                        newAccount.getBalance());
                return;
            }
        }
        System.out.println("無法新增客戶，客戶數已達上限");
    }

    public static void printCustomerAccounts(Account[] customers) {
        //     System.out.println("客戶帳戶列表:");
        for (int i = 0; i < customerCount; i++) {
            printCustomerInfo(customers[i]);
        }
    }

    public static void printCustomerInfo(Account account) {
        System.out.println("帳戶號碼: " + account.getAccountNumber() +
                "持有人: " + account.getOwnerName() +
                ", 餘額: " + account.getBalance());
    }
}

