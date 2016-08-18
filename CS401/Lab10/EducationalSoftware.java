/* Written by
   Simonseth Roffe
   Lab: Tuesday 4-5:50 pm
*/

/** Represents an Educational Software, which is a child class
    of the Software.
    @author Simonseth Roffe
*/
public class EducationalSoftware extends Software {
    
    private String subject;
    private String level;

    /** Constructor initializing all of the fields from the Software
	class as well as the subject and the level of education
	@param name The name of Software
	@param price The price of the product (must be positive)
	@param quantity The amount of product in stock (must be positive)
	@param company The software house that produces the software
	@param sub The educational subject (must be health, literature, math, science, or social studies)
	@param lev The level of education (must be kindergarden, elementary, middle school, high school, or college)
    */
    public EducationalSoftware(String name, double price, int quantity, SoftwareHouse company, String sub, String lev) {
	super(name, price, quantity, company);
	setSubject(sub);
	setLevel(lev);
    }

    /** mutator method for the subject. Must be health, literature,
	math, science, or social studies. Not case sensitive.
	@param sub The educational subject
    */
    public void setSubject(String sub) {
	if (!sub.equalsIgnoreCase("health") && !sub.equalsIgnoreCase("literature") && !sub.equalsIgnoreCase("math") && !sub.equalsIgnoreCase("science") && !sub.equalsIgnoreCase("social studies")) {
	    IllegalArgumentException iae = new IllegalArgumentException("Subject must be health, literature, math, science, or social studies!");
	    throw iae;
	}
	subject = sub;
    }

    /** Mutator method for level of education. Must be kindergarden, elementary,
	middle school, high school, or college. Not case sensitive.
	@param lev The level of education.
    */
    public void setLevel(String lev) {
	if (!lev.equalsIgnoreCase("kindergarden") && !lev.equalsIgnoreCase("elementary") && !lev.equalsIgnoreCase("middle school") && !lev.equalsIgnoreCase("high school") && !lev.equalsIgnoreCase("college")) {
	    IllegalArgumentException iae = new IllegalArgumentException("Level must be kindergarden, elementary, middle school, high school, or college!");
	    throw iae;
	}
	level = lev;
    }
    
    /** Accessor method for the subject.
	@return the educational subject
    */
    public String getSubject() {
	return subject;
    }

    /** Accessor method for educational level
	@return the level of education
    */
    public String getLevel() {
	return level;
    }    
}
