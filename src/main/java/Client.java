public class Client {
    private String name;
    private String surname;
    private String phoneNamber;
    private String address;
    private String namberBonusnoyKard;
    private String password;

    public Client() {

    }

    public Client(String name, String surname, String phoneNamber, String address, String namberBonusnoyKard, String password) {
        this.name = name;
        this.surname = surname;
        this.phoneNamber = phoneNamber;
        this.address = address;
        this.namberBonusnoyKard = namberBonusnoyKard;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNamber() {
        return phoneNamber;
    }

    public void setPhoneNamber(String phoneNamber) {
        this.phoneNamber = phoneNamber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNamberBonusnoyKard() {
        return namberBonusnoyKard;
    }

    public void setNamberBonusnoyKard(String namberBonusnoyKard) {
        this.namberBonusnoyKard = namberBonusnoyKard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phoneNamber='" + phoneNamber + '\'' +
                ", address='" + address + '\'' +
                ", namberBonusnoyKard='" + namberBonusnoyKard + '\'' +
                '}';
    }
}
