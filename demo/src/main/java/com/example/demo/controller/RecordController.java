package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.example.demo.domain.BalanceRecord;
import com.example.demo.domain.TradingRecord;
import com.example.demo.kisAPI.dto.uapi.overseas_stock.v1.trading.inquire_ccnl_DTO;
import com.example.demo.service.AccountService;
import com.example.demo.service.BalanceRecordService;
import com.example.demo.service.TradingRecordService;

@Controller
public class RecordController {
  private final BalanceRecordService balanceRecordService;
  private final TradingRecordService tradingRecordService;
  private final AccountService accountService;

  public RecordController(BalanceRecordService balanceRecordService, TradingRecordService tradingRecordService,
      AccountService accountService) {
    this.balanceRecordService = balanceRecordService;
    this.tradingRecordService = tradingRecordService;
    this.accountService = accountService;
  }

  public void TradeRecording(Long accountId, TradingRecord tradingRecord) {
    // 당일 잔고 조회
    BalanceRecord balanceRecord = balanceRecordService.findByAccountIdAndRecordDate(accountId,
        tradingRecord.getTradeTime().substring(0, 10));

    // 당일 잔고가 없다면 마지막 잔고 기록 날짜 조회
    if (balanceRecord == null) {
      String lastRecordDate = GetLastRecordDate(accountId);

      // 마지막 잔고 기록 날짜가 없다면 초기 잔고 0으로 설정
      if (lastRecordDate == null)
        balanceRecord = new BalanceRecord(accountId, 0.0, tradingRecord.getTradeTime().substring(0, 10));
      // 마지막 잔고 기록 날짜가 있다면 마지막 잔고로 설정
      else
        balanceRecord = new BalanceRecord(accountId,
            balanceRecordService.findByAccountIdAndRecordDate(accountId, lastRecordDate).getBalance(),
            tradingRecord.getTradeTime().substring(0, 10));
    }

    // 거래내역에 따라 잔고 조정
    if (tradingRecord.getMethod().equals("buy"))
      balanceRecord.setBalance(balanceRecord.getBalance() - tradingRecord.getAmount() * tradingRecord.getPrice());
    else if (tradingRecord.getMethod().equals("sell"))
      balanceRecord.setBalance(balanceRecord.getBalance() + tradingRecord.getAmount() * tradingRecord.getPrice());
    else
      throw new IllegalArgumentException("Invalid method");

    // 잔고 기록 저장
    balanceRecord = balanceRecordService.save(balanceRecord);
    tradingRecord.setBalanceRecordId(balanceRecord.getId());
    tradingRecordService.save(tradingRecord);
  }

  public String GetLastRecordDate(Long accountId) {
    List<BalanceRecord> balanceRecords = balanceRecordService.findByAccountId(accountId);
    if (balanceRecords.size() == 0)
      return null;
    String lastRecordDate = "1000:01:01";
    for (BalanceRecord balanceRecord : balanceRecords) {
      if (balanceRecord.getRecordDate().compareTo(lastRecordDate) > 0)
        lastRecordDate = balanceRecord.getRecordDate();
    }
    return lastRecordDate;
  }

  public TradingRecord GetLastTradingRecord(Long accountId) {
    List<TradingRecord> tradingRecords = tradingRecordService.findByAccountId(accountId);
    if (tradingRecords.size() == 0)
      return null;
    TradingRecord lastTradingRecord = tradingRecords.get(0);
    for (TradingRecord tradingRecord : tradingRecords) {
      if (tradingRecord.getTradeTime().compareTo(lastTradingRecord.getTradeTime()) > 0)
        lastTradingRecord = tradingRecord;
    }
    return lastTradingRecord;
  }

  // public BalanceRecord GetLastRecord(Long accountId) {
  // List<BalanceRecord> balanceRecords =
  // balanceRecordService.findByAccountId(accountId);
  // if (balanceRecords.size() == 0)
  // return null;
  // String lastRecordDate =
  // for (BalanceRecord balanceRecord : balanceRecords) {
  // System.out.println(balanceRecord.getRecordDate());
  // }
  // return balanceRecords.get(balanceRecords.size() - 1);
  // }

  public void SyncHistory(Long accountId) {
    // 마지막 거래내역 조회
    TradingRecord lastTradingRecord = GetLastTradingRecord(accountId);

    String start;
    if (lastTradingRecord == null) {
      // 거래내역이 없을 경우
      start = "20240101";
    } else {
      // 마지막 거래내역의 거래일자로 조회
      String lastTradingDate = lastTradingRecord.getTradeTime().substring(0, 10);
      start = lastTradingDate.split(":")[0] + lastTradingDate.split(":")[1] + lastTradingDate.split(":")[2];
    }

    // 거래내역 조회 기간 설정
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String end = format.format(new Date());

    // 해외 거래내역 조회
    List<inquire_ccnl_DTO.ResBody> historyOverseas = accountService.getHistoriesOverseas(accountId, start, end);

    // 로깅
    // log.info("거래 내역: " + historyOverseas.size());
    // historyOverseas.forEach(resBody -> {
    //   log.info("거래 내역: " + resBody.toString());
    // });

    // 거래내역 저장
    List<TradingRecord> tradingRecords = new ArrayList<TradingRecord>();

    historyOverseas.forEach(resBody -> {
      resBody.getOutput().forEach(history -> {
        // 이미 저장된 거래내역이거나 체결되지 않은 거래내역은 저장하지 않음
        if (tradingRecordService.findByOrderNo(history.getOdno()) != null ||
            history.getFt_ord_qty().compareTo(history.getFt_ccld_qty()) != 0) {
          // log.info("거래내역이 이미 저장되었거나 체결되지 않은 거래내역입니다.");
          return;
        }

        // 거래일시, 거래유형 설정
        String tradeTime = history.getDmst_ord_dt().substring(0, 4) + ":" + history.getDmst_ord_dt().substring(4, 6)
            + ":" + history.getDmst_ord_dt().substring(6, 8) + ":" + history.getOrd_tmd().substring(0, 2) + ":"
            + history.getOrd_tmd().substring(2, 4) + ":" + history.getOrd_tmd().substring(4, 6);
        String tradeType = history.getSll_buy_dvsn_cd().equals("02") ? "buy" : "sell";

        // 거래내역 저장
        TradingRecord tradingRecord = new TradingRecord(accountId, tradeType, history.getPdno(),
            Integer.parseInt(history.getFt_ccld_qty()), Double.parseDouble(history.getFt_ccld_unpr3()), tradeTime,
            history.getOdno());
        tradingRecords.add(tradingRecord);
      });
    });

    // log.info("거래 내역 갯수: " + tradingRecords.size());

    // 거래일시가 빠른 순서로 거래내역 저장
    tradingRecords.sort((a, b) -> a.getTradeTime().compareTo(b.getTradeTime()));
    tradingRecords.forEach(tradingRecord -> {
      TradeRecording(accountId, tradingRecord);
    });
  }

  // @GetMapping("api/testTradingRecord")
  // @ResponseBody
  // public String getMethodName() {
  // TradeRecording(6L, new TradingRecord(6L, "buy", "TSLA", 3, 21000.3,
  // "2024:05:15:21:02:39"));
  // return "seccess";
  // }

}
