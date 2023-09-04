import atm.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.singUp("Aleriza","Sultangazyeva");
        accountService.singUp("Akmaral","Bermetova");
        while (true){
            accountService.singIn("Aleriza","Sultangazyeva");
        }

    }
}