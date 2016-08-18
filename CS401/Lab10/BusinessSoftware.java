/* Written by
   Simonseth Roffe
   Lab: Tuesday 4-5:50 pm
*/

/** Represents a business software which is a child class
    of software
    @author Simonseth Roffe
*/
public class BusinessSoftware extends Software {
    
    private String kind;
    
    /** Constructor that initializes all of the fields from the
	Software Class as well as the kind field for this class.
	@param name The name of the software
	@param price The price of the software (must be positive)
	@param quantity The amount of product in stock (must be positive)
	@param company The software house that produces the software
	@param type The kind of software it is.
    */
    public BusinessSoftware(String name, double price, int quantity, SoftwareHouse company, String type) {
	super(name, price, quantity, company);
	setKind(type);
    }

    /** Mutator method for the kind of software.
	@param type The kind of software.
    */
    public void setKind(String type) {
	kind = type;
    }

    /** Accessor method for the kind of software.
	@return The kind of software.
    */
    public String getKind() {
	return kind;
    }
}
