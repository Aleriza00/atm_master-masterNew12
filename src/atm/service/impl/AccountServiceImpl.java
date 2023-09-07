package atm.service.impl;

import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;

import java.util.Random;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public void singUp(String name, String lastName) {
        try {
            UserAccount userAccount1 = new UserAccount();
            userAccount1.setName(name);
            userAccount1.setLastName(lastName);
            userAccount1.setCardNumber(String.valueOf(random.nextInt(1000_0000, 9999_9999)));
            userAccount1.setPinCode(String.valueOf(random.nextInt(1000, 9_999)));
            accountDao.getUserAccounts().add(userAccount1);
            System.out.println(userAccount1);
        }catch (RuntimeException e ){
        e.printStackTrace();
        }

        }




    @Override
    public void singIn() {
        try {
                System.out.println("Имя: ");
                String userName = scanner.nextLine();
                System.out.println("Фамилия: ");
                String userLastName = scanner.nextLine();
                for (UserAccount userAccount : accountDao.getUserAccounts()) {
                    if (userAccount.getName().equals(userName) && userAccount.getLastName().equals(userLastName)) {

                        while (true) {
                            System.out.println("[1] - Для просмотра  баланса\n " +
                                    "[2] - Для получение депозита\n " +
                                    "[3] - Для оправки денег другу\n " +
                                    "[4] - Для снятия наличных денег");
                            int san = scanner.nextInt();
                            switch (san) {
                                case 1 -> {
                                    balance();
                                }
                                case 2 -> {
                                    deposit();
                                }
                                case 3 -> {
                                    sendToFriend();
                                }
                                case 4 -> {
                                    withdrawMoney();
                                }
                            }
                        }
                    }
                }

        } catch (Exception e) {
            System.out.println(" Un correct !");
        }
    }



    @Override
    public void balance() {
        System.out.println("Введите номер карты ");
        String cardN =  String.valueOf(scanner.nextInt());
        System.out.println("Введите пин код ");
        String pin = String.valueOf(scanner.nextInt());
        UserAccount userN =   accountDao.getUserAccounts().stream().filter(userAccount -> userAccount.getCardNumber().equals(cardN) && userAccount.getPinCode().equals(pin))
                .findFirst().orElseThrow(()-> new RuntimeException("Wrong card number or PIN"));
        System.out.println("Balance: "+userN.getBalance());
    }

    @Override
    public void deposit() {
        try {
            System.out.println("введите номер карты ");
            String cardN = String.valueOf(scanner.nextInt());
            System.out.println("введите пин код ");
            String pin = String.valueOf(scanner.nextInt());
            System.out.println("На какую сумму хотите поплнить? ");
            int money = scanner.nextInt();
            UserAccount user =   accountDao.getUserAccounts().stream().filter(userAccount -> userAccount.getCardNumber().equals(cardN) && userAccount.getPinCode().equals(pin) )
                    .findFirst().orElseThrow(()-> new RuntimeException("Wrong card number or PIN"));
             user.setBalance(user.getBalance()+money);


            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                System.out.println(userAccount);
            }
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void sendToFriend() {
        UserAccount friend = new UserAccount();
        UserAccount user = new UserAccount();
        System.out.println("Введите номер карты ");
        String cardN = String.valueOf(scanner.nextInt());
        System.out.println("Введите пин код ");
        String pin = String.valueOf(scanner.nextInt());

        try {
            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                if (cardN.equals(userAccount.getCardNumber()) && pin.equals(userAccount.getPinCode())) {
                    user = userAccount;
                }
            }
//            if (user != null && user.getCardNumber().equals(cardN) && user.getPinCode().equals(pin) ){
//                throw new RuntimeException("Incorrectly entered card number or PIN");
//            }
            System.out.println("Введите номер карты вашего друга ");
            String friendCard = String.valueOf(scanner.nextInt());
            for (UserAccount userAccount : accountDao.getUserAccounts()){
                if (userAccount.getCardNumber().equals(friendCard)) {
                    friend = userAccount;
                }
            }

            System.out.println("Какую сумму хотите перевести? ");
            int summa = scanner.nextInt();
            if (user.getBalance() > summa){
                  user.setBalance(user.getBalance()-summa);

                friend.setBalance(friend.getBalance() + summa);

            } else {
                System.out.println("Не хватает средств на карте ");
            }
            System.out.println(friend);

        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void withdrawMoney() {
        System.out.println("введите номер карты ");
        String cardN =scanner.nextLine();
        System.out.println("введите пин код ");
        String pin = scanner.nextLine();
        UserAccount user =   accountDao.getUserAccounts().stream()
                .filter(userAccount -> userAccount.getCardNumber().equals(cardN) && userAccount.getPinCode().equals(pin) )
                .findFirst().orElseThrow(()-> new RuntimeException("Wrong card number or PIN"));
        System.out.println("Ваш баланс "+user.getBalance());
        System.out.println("Сколько хотите снять ");
        int mon= scanner.nextInt();
        if (user.getBalance()>= mon) {
            StringBuilder builder = new StringBuilder();
                if (mon >= 1000) {
                    builder.append(mon / 1000).append(" по тысяче");
                    mon = mon % 1000;
                }

            }

    }
}
