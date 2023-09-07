package atm.service;

public interface AccountService {

    void singUp(String name, String lastName);

    void singIn();

    void balance();

    void deposit();

    void sendToFriend();

    void withdrawMoney();

}
