import atm.dao.AccountDao;
import atm.service.AccountService;
import atm.service.impl.AccountServiceImpl;

public class Main {
    public static void main(String[] args) {
        AccountService service = new AccountServiceImpl();
        service.singUp("Алериза", "Султангазыева");
        service.singUp("Акмарал", "Берметова");

        while (true) {
                service.singIn();
            }

        }

    }
