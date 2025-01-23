package controller;

import account.Account;
import account.AccountDAO;
import client.Client;
import client.ClientDAO;
import utils.Utils;
import java.util.regex.Pattern;

public class Controller {
	private AccountDAO accountDAO;
	private ClientDAO clientDAO;
	private Client loggedInClient;

	public Controller() {
		accountDAO = new AccountDAO();
		clientDAO = new ClientDAO();
	}

	public void run() {
		while (true) {
			System.out.println("======== 더조은은행 ========");
			System.out.println("[1] 관리자");
			System.out.println("[2] 사용자");
			System.out.println("[0] 종료");
			int choice = Utils.getIntInput("메뉴 선택[0-2] 입력 : ");

			if (choice == 1) {
				adminMenu();
			} else if (choice == 2) {
				userMenu();
			} else if (choice == 0) {
				System.out.println("프로그램 종료");
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	private void adminMenu() {
		while (true) {
			System.out.println("======== 관리자 메뉴 ========");
			System.out.println("[1] 회원목록");
			System.out.println("[2] 회원수정");
			System.out.println("[3] 회원 삭제");
			System.out.println("[4] 데이터 저장");
			System.out.println("[5] 데이터 불러오기");
			System.out.println("[0] 뒤로가기");
			int choice = Utils.getIntInput("메뉴 선택[0-5] 입력 : ");

			switch (choice) {
			case 1:
				listClients();
				break;
			case 2:
				modifyClient();
				break;
			case 3:
				deleteClient();
				break;
			case 4:
				saveData();
				break;
			case 5:
				loadData();
				break;
			case 0:
				return;
			default:
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	private void listClients() {
		if(clientDAO.getClients().isEmpty()) {
			System.out.println("[ 회원이 존재하지 않습니다 ]");
			return;
		}
		System.out.println("회원 목록:");
		for (Client client : clientDAO.getClients()) {
			System.out.println("아이디: " + client.getClientId() + ", 이름: " + client.getName());
		}
	}

	private void modifyClient() {
		if(clientDAO.getClients().isEmpty()) {
			System.out.println("[ 회원이 존재하지 않습니다 ]");
			return;
		}
		String clientId = Utils.getStringInput("수정할 회원 아이디 입력: ");
		Client client = clientDAO.getClient(clientId);
		if (client != null) {
			String newPassword = Utils.getStringInput("새 비밀번호 입력: ");
			String newName = Utils.getStringInput("새 이름 입력: ");
			client.setPassword(newPassword);
			client.setName(newName);
			System.out.println("회원 정보가 수정되었습니다.");
		} else {
			System.out.println("해당 아이디의 회원이 존재하지 않습니다.");
		}
	}

	private void deleteClient() {
		if(clientDAO.getClients().isEmpty()) {
			System.out.println("[ 회원이 존재하지 않습니다 ]");
			return;
		}
		String clientId = Utils.getStringInput("삭제할 회원 아이디 입력: ");
		if (clientDAO.removeClient(clientId)) {
			System.out.println("회원이 삭제되었습니다.");
		} else {
			System.out.println("해당 아이디의 회원이 존재하지 않습니다.");
		}
	}

	private void saveData() {
	    Utils.saveClients(clientDAO.getClients()); // 클라이언트 정보를 파일에 저장
	    System.out.println("데이터가 저장되었습니다.");
	}

	private void loadData() {
	    Utils.loadClients(clientDAO); // 클라이언트 정보를 파일에서 불러오기
	    System.out.println("데이터가 불러와졌습니다.");
	}


	private void userMenu() {
		while (true) {
			System.out.println("======== 사용자 메뉴 ========");
			System.out.println("[1] 회원가입");
			System.out.println("[2] 로그인");
			System.out.println("[0] 뒤로가기");
			int choice = Utils.getIntInput("사용자 메뉴 선택[0-2] 입력 : ");

			if (choice == 1) {
				registerClient();
			} else if (choice == 2) {
				loginClient();
			} else if (choice == 0) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	private void registerClient() {
		String clientId = Utils.getStringInput("아이디 입력: ");
		if (clientDAO.getClient(clientId) != null) {
			System.out.println("아이디가 중복되었습니다.");
			return;
		}
		String name = Utils.getStringInput("이름 입력: ");
		String password = Utils.getStringInput("비밀번호 입력: ");

		Client client = new Client(clientId, name, password);
		clientDAO.addClient(client);
		System.out.println("회원가입이 완료되었습니다.");
	}

	private void loginClient() {
		String clientId = Utils.getStringInput("아이디 입력: ");
		String password = Utils.getStringInput("비밀번호 입력: ");

		loggedInClient = clientDAO.getClient(clientId);
		if (loggedInClient != null && loggedInClient.getPassword().equals(password)) {
			System.out.println("로그인 성공");
			clientActions();
		} else {
			System.out.println("잘못된 아이디 또는 비밀번호입니다.");
		}
	}

	private void clientActions() {
		while (true) {
			System.out.println("======== 고객 메뉴 ========");
			System.out.println("[1] 계좌 추가");
			System.out.println("[2] 계좌 삭제");
			System.out.println("[3] 입금");
			System.out.println("[4] 출금");
			System.out.println("[5] 이체");
			System.out.println("[6] 탈퇴");
			System.out.println("[7] 마이페이지 [0] 로그아웃");
			int choice = Utils.getIntInput("사용자 메뉴 선택[0-7] 입력 : ");

			switch (choice) {
			case 1:
				addAccount();
				break;
			case 2:
				removeAccount();
				break;
			case 3:
				deposit();
				break;
			case 4:
				withdraw();
				break;
			case 5:
				transfer();
				break;
			case 6:
				secessionClient();
				break;
			case 7:
				myPage();
				break;
			case 0:
				loggedInClient = null;
				return;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}

	private void addAccount() {
		if (loggedInClient.getAccounts().size() >= 3) {
			System.out.println("계좌는 최대 3개까지만 생성할 수 있습니다.");
			return;
		}

		String accountNumber = Utils.getStringInput("계좌번호 입력 (형식: 0000-0000-0000): ");
		if (!Pattern.matches("\\d{4}-\\d{4}-\\d{4}", accountNumber)) {
			System.out.println("계좌번호 형식이 잘못되었습니다.");
			return;
		}

		if (accountDAO.getAccount(accountNumber) != null) {
			System.out.println("이미 존재하는 계좌번호입니다.");
			return;
		}

		Account account = new Account(accountNumber, 0);
		loggedInClient.addAccount(account);
		accountDAO.addAccount(account);
		System.out.println("계좌가 추가되었습니다.");
	}

	private void removeAccount() {
		String accountNumber = Utils.getStringInput("삭제할 계좌번호 입력: ");
		if (loggedInClient.removeAccount(accountNumber)) {
			accountDAO.removeAccount(accountNumber);
			System.out.println("계좌가 삭제되었습니다.");
		} else {
			System.out.println("본인 계좌만 삭제 가능합니다.");
		}
	}

	private void deposit() {
		String accountNumber = Utils.getStringInput("입금할 계좌번호 입력: ");
		Account account = accountDAO.getAccount(accountNumber);
		if (account != null) {
			double amount = Utils.getDoubleInput("입금할 금액 입력 (100 이상): ");
			if (amount >= 100) {
				account.deposit(amount);
				System.out.println("입금이 완료되었습니다.");
			} else {
				System.out.println("입금 금액은 100 이상이어야 합니다.");
			}
		} else {
			System.out.println("계좌가 존재하지 않습니다.");
		}
	}

	private void withdraw() {
		String accountNumber = Utils.getStringInput("출금할 계좌번호 입력: ");
		Account account = accountDAO.getAccount(accountNumber);
		if (account != null) {
			double amount = Utils.getDoubleInput("출금할 금액 입력: ");
			if (amount <= account.getBalance() && amount >= 100) {
				account.withdraw(amount);
				System.out.println("출금이 완료되었습니다.");
			} else {
				System.out.println("잔액이 부족하거나, 출금 금액이 100 미만입니다.");
			}
		} else {
			System.out.println("계좌가 존재하지 않습니다.");
		}
	}

	private void transfer() {
		String fromAccountNumber = Utils.getStringInput("이체할 계좌번호 입력: ");
		Account fromAccount = accountDAO.getAccount(fromAccountNumber);
		if (fromAccount != null) {
			String toAccountNumber = Utils.getStringInput("받는 계좌번호 입력: ");
			Account toAccount = accountDAO.getAccount(toAccountNumber);
			if (toAccount != null && !fromAccountNumber.equals(toAccountNumber)) {
				double amount = Utils.getDoubleInput("이체할 금액 입력: ");
				if (amount <= fromAccount.getBalance() && amount >= 100) {
					fromAccount.withdraw(amount);
					toAccount.deposit(amount);
					System.out.println("이체가 완료되었습니다.");
				} else {
					System.out.println("잔액이 부족하거나, 이체 금액이 100 미만입니다.");
				}
			} else {
				System.out.println("받는 계좌가 존재하지 않거나, 동일한 계좌로는 이체할 수 없습니다.");
			}
		} else {
			System.out.println("보내는 계좌가 존재하지 않습니다.");
		}
	}

	private void secessionClient() {
		String clientId = loggedInClient.getClientId();
		String password = Utils.getStringInput("비밀번호를 입력하세요: ");
		if (loggedInClient.getPassword().equals(password)) {
			clientDAO.removeClient(clientId);
			System.out.println("회원 탈퇴가 완료되었습니다.");
			loggedInClient = null;
		} else {
			System.out.println("비밀번호가 틀렸습니다.");
		}
	}

	private void myPage() {
		System.out.println("======== 마이페이지 ========");
		System.out.println("아이디: " + loggedInClient.getClientId());
		System.out.println("이름: " + loggedInClient.getName());
		System.out.println("계좌 목록:");
		for (Account account : loggedInClient.getAccounts()) {
			System.out.println("계좌번호: " + account.getAccountNumber() + ", 잔액: " + account.getBalance());
		}
	}
}
