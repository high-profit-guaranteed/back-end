package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Account;
import com.example.demo.domain.BalanceRecord;
import com.example.demo.domain.Member;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBodyOutput1;
import com.example.demo.service.AccountService;
import com.example.demo.service.BalanceRecordService;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Controller
public class APIController {

  private final MemberService memberService;
  private final AccountService accountService;
  private final BalanceRecordService balanceRecordService;
  private final RecordController recordController;

  public APIController(MemberService memberService, AccountService accountService,
      BalanceRecordService balanceRecordService, RecordController recordController) {
    this.memberService = memberService;
    this.accountService = accountService;
    this.balanceRecordService = balanceRecordService;
    this.recordController = recordController;
  }

  @PostMapping("api/signin")
  public ResponseEntity<String> apiSignin(@RequestBody SignIn login, HttpServletRequest httpServletRequest) {

    String uid = login.getUid();
    String password = login.getPassword();

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 멤버 존재 확인
    Member member = memberService.signin(uid, password);

    if (member == null) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // 로그인 성공 => 세션 생성

    // 세션을 생성하기 전에 기존의 세션 파기
    httpServletRequest.getSession().invalidate();
    HttpSession session = httpServletRequest.getSession(true); // Session이 없으면 생성
    // 세션에 userId를 넣어줌
    session.setAttribute("id", member.getId());
    session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

    final HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>("Signin Success", httpHeaders, HttpStatus.OK);
  }

  @PostMapping("api/signup")
  public ResponseEntity<String> apiSignup(@RequestBody SignUp signup, HttpServletRequest httpServletRequest) {

    String uid = signup.getUid();
    String password = signup.getPassword();
    String name = signup.getName();
    String email = signup.getEmail();

    String emailName = email.split("@")[0];
    String emailDomain = email.split("@")[1];

    if (uid == null || uid.isEmpty() || password == null || password.isEmpty() || name == null || name.isEmpty()
        || email == null || email.isEmpty()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    if (emailName == null || emailDomain == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    Member member = memberService.signup(uid, password, name, emailName, emailDomain);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return new ResponseEntity<String>("SignUp Success", HttpStatus.OK);
  }

  @GetMapping("api/checkSession")
  public ResponseEntity<String> postMethodName(@SessionAttribute(name = "id", required = false) Long id) {
    if (CheckSession(id) == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    else
      return ResponseEntity.status(HttpStatus.OK).body("Success");
  }

  @GetMapping("api/signout")
  public ResponseEntity<String> signout(HttpServletRequest request) {

    HttpSession session = request.getSession(false); // Session이 없으면 null return
    if (session != null)
      session.invalidate(); // Session 파기

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("api/accounts")
  public ResponseEntity<Accounts> getAccounts(@SessionAttribute(name = "id", required = false) Long id) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Accounts accounts = new Accounts();
    accountService.findByMemberId(member.getId()).forEach(account -> {
      accounts.addAccount(account.getAccountName(), account.getId());
    });

    return ResponseEntity.ok(accounts);
  }

  @GetMapping("api/balance")
  public ResponseEntity<Balance> getBalance(@SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    recordController.SyncHistory(accountId);

    Double realBalance = balanceRecordService
        .findByAccountIdAndRecordDate(accountId, recordController.GetLastRecordDate(accountId))
        .getBalance();
    Double stockBalance = 0.0;
    for (ResBodyOutput1 output : accountService.getAccountInfoOverseas(accountId).getOutput1()) {
      stockBalance += Double.parseDouble(output.getOvrs_stck_evlu_amt());
    }

    Balance balance = new Balance(100000 + realBalance + stockBalance);

    return ResponseEntity.ok(balance);
  }

  @GetMapping("api/balanceRecord")
  public ResponseEntity<List<BalanceRecord>> getBalanceRecords(@SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    return ResponseEntity.ok(balanceRecordService.findByAccountId(accountId));
  }

  @GetMapping("api/accessToken")
  public ResponseEntity<String> getAccessToken(@SessionAttribute(name = "id", required = false) Long id) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Accounts accounts = new Accounts();
    accountService.findByMemberId(member.getId()).forEach(account -> {
      accounts.addAccount(account.getAccountName(), account.getId());
    });

    accounts.getAccounts().forEach(account -> {
      accountService.getAccessToken(account.getAccountId());
    });

    return ResponseEntity.ok("Success");
  }

  @GetMapping("api/stocksEvaluationBalance")
  public ResponseEntity<List<StockBalance>> getStocksEvaluationBalance(
      @SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    List<ResBodyOutput1> output1 = accountService.getAccountInfoOverseas(accountId).getOutput1();

    List<StockBalance> stockBalances = new ArrayList<>();
    for (ResBodyOutput1 output : output1) {
      StockBalance stockBalance = new StockBalance(output.getOvrs_pdno(), output.getOvrs_item_name(),
          Double.parseDouble(output.getNow_pric2()), Integer.parseInt(output.getOvrs_cblc_qty()),
          Double.parseDouble(output.getFrcr_evlu_pfls_amt()));
      stockBalances.add(stockBalance);
    }

    return ResponseEntity.ok(stockBalances);
  }

  @GetMapping("api/syncTradingRecord")
  public ResponseEntity<String> SyncTradingRecord(@SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    recordController.SyncHistory(accountId);

    return ResponseEntity.ok("Success");
  }

  @PostMapping("api/trade")
  public ResponseEntity<String> trade(@SessionAttribute(name = "id", required = false) Long id,
      @RequestParam("accountId") Long accountId, @RequestBody StockOrderForm form) {

    Member member = CheckSession(id);
    if (member == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    Account account = accountService.findById(accountId);
    if (account == null)
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    if (!accountService.isOwner(member.getId(), account.getId()))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    if (!form.getMethod().equals("buy") && !form.getMethod().equals("sell"))
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

    com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ResBody resBody = accountService
        .orderOverseas(accountId, com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ReqBody
            .from(account, form.getStockCode(), String.valueOf(form.getOrderAmount()), String.valueOf(form.getOrderPrice()), form.getMethod().equals("buy")), form.getMethod().equals("buy"));

    if (resBody.getRt_cd().equals("0"))
      return ResponseEntity.ok(form.getStockCode() + " " + form.getOrderAmount() + "주 " + (form.getMethod().equals("buy") ? "매수" : "매도") + " 성공");
    else
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }

  private Member CheckSession(Long id) {
    if (id == null)
      return null;
    Member member = memberService.getSigninMember(id);
    if (member == null)
      return null;
    else
      return member;
  }
}

@Data
class SignIn {
  private String uid;
  private String password;
}

@Data
class SignUp {
  private String uid;
  private String password;
  private String name;
  private String email;
}

@Data
class Accounts {
  private List<AccountObj> accounts;

  public Accounts() {
    accounts = new ArrayList<>();
  }

  public void addAccount(String accountName, Long accountId) {
    accounts.add(new AccountObj(accountName, accountId));
  }
}

@Data
@NonNull
@AllArgsConstructor
class AccountObj {
  private String accountName;
  private Long accountId;
}

@Data
@AllArgsConstructor
class Balance {
  private Double balance;
}

@Data
@AllArgsConstructor
class StockBalance {
  private String ticker;
  private String name;
  private Double price;
  private int amount;
  private Double difference;
}

@Data
class StockOrderForm {
  private String stockCode;
  private int orderAmount;
  private Double orderPrice;
  private String method;
}