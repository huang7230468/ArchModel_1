package ant.dubbo.entity;

public class TradeDetail {

    private long tradeCode ;
    private String deatil ;

    public long getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(long tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getDeatil() {
        return deatil;
    }

    public void setDeatil(String deatil) {
        this.deatil = deatil;
    }

    @Override
    public String toString() {
        return "TradeDetail{" +
                "tradeCode=" + tradeCode +
                ", deatil='" + deatil + '\'' +
                '}';
    }
}
