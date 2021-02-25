import java.time.LocalDateTime;

public class Order {
   private Client client;
   private LocalDateTime data;
   private int sumOrder;

    public Order() {

    }

    public Order(Client client, LocalDateTime data, int sumOrder) {
        this.client = client;
        this.data = data;
        this.sumOrder = sumOrder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getSumOrder() {
        return sumOrder;
    }

    public void setSumOrder(int sumOrder) {
        this.sumOrder = sumOrder;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client.toString() +
                ", data=" + data +
                ", sumOrder=" + sumOrder +
                '}';
    }
}
