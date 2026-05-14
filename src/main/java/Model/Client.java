package Model;

/**
 * Represents a client entity in the system.
 * This class maps to the "client" table in the database and holds
 * personal information such as name, address, email, and age.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    /**
     * Default constructor required for reflection-based instantiation.
     */
    public Client() {}

    /**
     * Constructs a new Client with the specified details.
     * @param name The full name of the client.
     * @param address The residential address.
     * @param email The contact email address.
     * @param age The age of the client.
     */
    public Client(String name, String address, String email, int age) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age + "]";
    }
}