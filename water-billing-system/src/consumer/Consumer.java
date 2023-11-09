import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Consumer {
    private final SimpleIntegerProperty no;
    private final SimpleIntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty middleName;
    private final StringProperty lastName;
    private final StringProperty suffix;
    private final StringProperty emailAd;
    private final StringProperty contactNo;
    private final StringProperty meterNumber;
    private final SimpleStringProperty status;
    private final StringProperty country;
    private final StringProperty region;
    private final StringProperty province;
    private final StringProperty municipality;
    private final StringProperty baranggay;
    private final StringProperty purok;
    private final StringProperty postalCode;
    
    public Consumer(int no,int id, String firstName, String middleName, String lastName, 
                    String suffix, String contactNo, String emailAd, String meterNumber,
                    String country, String region, String province, String municipality, 
                    String baranggay,String purok, String postalCode, String status) {
        this.no = new SimpleIntegerProperty(no);
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.middleName = new SimpleStringProperty(middleName);
        this.lastName = new SimpleStringProperty(lastName);
        this.suffix = new SimpleStringProperty(suffix);
        this.emailAd = new SimpleStringProperty(emailAd);
        this.meterNumber = new SimpleStringProperty(meterNumber);
        this.contactNo = new SimpleStringProperty(contactNo);
        this.country = new SimpleStringProperty(country);
        this.region = new SimpleStringProperty(region);
        this.province = new SimpleStringProperty(province);
        this.municipality = new SimpleStringProperty(municipality);
        this.baranggay = new SimpleStringProperty(baranggay);
        this.purok = new SimpleStringProperty(purok);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.status = new SimpleStringProperty(status);
    }
    public SimpleIntegerProperty noProperty() {
        return no;
    }
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }
    public StringProperty suffixProperty() {
        return suffix;
    }
    public StringProperty emailAdProperty() {
        return emailAd;
    }
    public StringProperty contactNoProperty() {
        return contactNo;
    }
    public StringProperty meterNumberProperty() {
        return meterNumber;
    }
    public StringProperty countryProperty() {
        return country;
    }
    public StringProperty regionProperty() {
        return region;
    }
    public StringProperty provinceProperty() {
        return province;
    }
    public StringProperty municipalityProperty() {
        return municipality;
    }
    public StringProperty baranggayProperty() {
        return baranggay;
    }
    public StringProperty purokProperty() {
        return purok;
    }
    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
    
}
