package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.domain.Account;
import com.example.demo.domain.Member;
import com.example.demo.service.AccountService;
import com.example.demo.service.MemberService;

import io.micrometer.common.lang.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AccountController {
  private final AccountService accountService;
  private final MemberService memberService;

  public AccountController(AccountService accountService, MemberService memberService) {
    this.accountService = accountService;
    this.memberService = memberService;
  }

  @GetMapping("/addAccount")
  public String addAccount(@SessionAttribute(name = "id", required = false) Long id, Model model) {
    if (id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    model.addAttribute("member", member);
    return "signin/addAccount";
  }

  // @GetMapping("/accounts")

  @PostMapping("/addAccount")
  public String postAddAccount(AccountForm form, @SessionAttribute(name = "id", required = false) Long id,
      Model model) {
    if (id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    Account account = new Account(id, Integer.parseInt(form.getAccountNumber()),
        Short.parseShort(form.getAccountProdCode()), form.getAccountName(),
        form.isVirtual(), form.getAPP_KEY(), form.getAPP_SECRET());

    accountService.join(account);

    return "redirect:/home";
  }

  @GetMapping("/getAccessToken/{accountId}")
  public String accessToken(@PathVariable("accountId") Long accountId,
      @SessionAttribute(name = "id", required = false) Long id, Model model) {
    if (accountId == null || id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    Account account = accountService.findOne(accountId);
    if (account == null) {
      return "redirect:/home";
    }

    accountService.getAccessToken(accountId);
    return "redirect:/home";
  }

  @ResponseBody
  @GetMapping("/getAccountInfo/{accountId}")
  public String getAccountInfo(@PathVariable("accountId") Long accountId,
      @SessionAttribute(name = "id", required = false) Long id, Model model) {
    if (accountId == null || id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    Account account = accountService.findOne(accountId);
    if (account == null) {
      return "redirect:/home";
    }

    com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.inquire_balance_DTO.ResBody balanceDomestic = accountService
        .getAccountInfoDomestic(accountId);
    model.addAttribute("balanceDomestic", balanceDomestic);

    com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_balance_DTO.ResBody balanceOverseas = accountService
        .getAccountInfoOverseas(accountId);
    model.addAttribute("balanceOverseas", balanceOverseas);

    return "<h2>국내 주식 잔고:</h2>" + balanceDomestic.toString() + ",</br></br><h2>해외 주식 잔고:</h2>"
        + balanceOverseas.toString();
  }

  @GetMapping("/order")
  public String orderInfo(@RequestParam("accountId") Long accountId,
      @RequestParam("priceMarketType") String priceMarketType, @RequestParam("stockCode") String stockCode,
      @SessionAttribute(name = "id", required = false) Long id, Model model) {
    if (accountId == null || id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    Account account = accountService.findOne(accountId);
    if (account == null) {
      return "redirect:/home";
    }

    model.addAttribute("account", account);

    if (stockCode == null || stockCode.isEmpty() || priceMarketType == null || priceMarketType.isEmpty()) {
      return "/signin/order";
    } else {
      model.addAttribute("stockCode", stockCode);
      model.addAttribute("priceMarketType", priceMarketType);
      if (priceMarketType.equals("domestic")) {
        return "/signin/order";
      } else {
        String response = accountService.getPriceOverseas(account.getId(), stockCode).toString();
        model.addAttribute("stockInfo", response);

        return "/signin/order";
      }
    }
  }

  @ResponseBody
  @PostMapping("/order")
  public String order(@RequestParam("accountId") Long accountId, OrderForm form,
      @SessionAttribute(name = "id", required = false) Long id) {
    if (accountId == null || id == null) {
      return "redirect:/signin";
    }
    Member member = memberService.getSigninMember(id);
    if (member == null) {
      return "redirect:/signin";
    }

    Account account = accountService.findOne(accountId);
    if (account == null) {
      return "redirect:/home";
    }

    String response;
    String stockCode = form.getStockCode();
    log.info(stockCode);
    String orderAmount = String.valueOf(form.getOrderAmount());
    String orderPrice = String.valueOf(form.getOrderPrice());
    if (stockCode == null || stockCode.isEmpty() || orderAmount == null || orderAmount.isEmpty() || orderPrice == null
        || orderPrice.isEmpty()) {
      return "주식 코드, 주문 수량, 주문 가격을 입력해주세요.";
    }

    if (form.isOverseas()) {
      com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ResBody resBody = accountService
          .orderOverseas(accountId, com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.order_DTO.ReqBody
              .from(account, stockCode, orderAmount, orderPrice, form.isBuy()), form.isBuy());
      response = resBody.toString();
    } else {
      com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ResBody resBody;

      if (form.isMarketPrice()) {
        resBody = accountService.orderDomestic(accountId,
            com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqBody.from(account, stockCode,
                orderAmount),
            form.isBuy());
      } else {
        resBody = accountService.orderDomestic(accountId,
            com.example.demo.kisAPI.dto.uapi.domestic_stock.v1.trading.order_cash_DTO.ReqBody.from(account, stockCode,
                orderAmount, orderPrice),
            form.isBuy());
      }
      response = resBody.toString();
    }

    return response;
  }

}

class OrderForm {
  @NonNull
  private final String stockCode;
  private final long orderAmount;
  private final long orderPrice;
  private final String orderType;
  private final String orderPriceType;
  private final String orderMarketType;

  public OrderForm(String stockCode, long orderAmount, long orderPrice, String orderType, String orderPriceType,
      String orderMarketType) {
    this.stockCode = stockCode;
    this.orderAmount = orderAmount;
    this.orderPrice = orderPrice;
    this.orderType = orderType;
    this.orderPriceType = orderPriceType;
    this.orderMarketType = orderMarketType;
  }

  @NonNull
  public String getStockCode() {
    return stockCode;
  }

  public long getOrderAmount() {
    return orderAmount;
  }

  public long getOrderPrice() {
    return orderPrice;
  }

  public String getOrderType() {
    return orderType;
  }

  public String getOrderPriceType() {
    return orderPriceType;
  }

  public String getOrderMarketType() {
    return orderMarketType;
  }

  public boolean isOverseas() {
    return orderMarketType.equals("overseas") ? true : false;
  }

  public boolean isMarketPrice() {
    return orderPriceType.equals("market") ? true : false;
  }

  public boolean isBuy() {
    return orderType.equals("buy") ? true : false;
  }
}

class AccountForm {
  private final String accountNumber;
  private final String accountProdCode;
  private final String accountName;
  private final boolean isVirtual;
  private final String APP_KEY;
  private final String APP_SECRET;

  public AccountForm(String accountNumber, String accountProdCode, String accountName, boolean isVirtual,
      String APP_KEY, String APP_SECRET) {
    this.accountNumber = accountNumber;
    this.accountProdCode = accountProdCode;
    this.accountName = accountName;
    this.isVirtual = isVirtual;
    this.APP_KEY = APP_KEY;
    this.APP_SECRET = APP_SECRET;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountProdCode() {
    return accountProdCode;
  }

  public String getAccountName() {
    return accountName;
  }

  public boolean isVirtual() {
    return isVirtual;
  }

  public String getAPP_KEY() {
    return APP_KEY;
  }

  public String getAPP_SECRET() {
    return APP_SECRET;
  }
}
