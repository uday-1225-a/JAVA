import java.util.Objects;

public class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;

    private static long customerCounter = 100;

    private static synchronized String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Step 3: Public static nested class Address
    public static class Address {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    public Customer(String name, String email, String mobile, Address address) {
        this(name, email, mobile);
        this.address = address;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getCustomerId() { return customerId; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    // Step 4: clone() method implementation (Deep copy of Address)
    @Override
    public Object clone() throws CloneNotSupportedException {
        Customer cloned = (Customer) super.clone();
        if (this.address != null) {
            cloned.address = new Address(this.address.getLine(), this.address.getCity(), this.address.getPincode());
        }
        return cloned;
    }

    @Override
    public String toString() {
        return "Customer [" + customerId + " | " + name + " | " + email + " | " + mobile + 
               (address != null ? " | Address: " + address : "") + "]";
    }
}

