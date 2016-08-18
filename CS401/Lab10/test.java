
public class test {
    public static void main(String[] args) {
	SoftwareHouse h = new SoftwareHouse("House","Blah","Marlboro","NJ","07746");
	Software s = new Software();
	s.setName("testing");
	s.setPrice(-9);
	//s.setPrice(19);
	s.setQuantity(-2);
	//s.setQuantity(4);
	s.setSoftwareHouse(h);

	Software s2 = new Software(s);
	System.out.println(s.toString());
	//s.setSoftwareHouse(new SoftwareHouse());

	SoftwareHouse h2 = new SoftwareHouse(h);
	h2.setName("Another Name");
	h2.setStreet("Another street");
	s2.setQuantity(5);
	s2.setPrice(6);
	s2.setSoftwareHouse(h2);
	System.out.println(s2.toString());


	h.setStreet("NEW STREET");
	h.setCity("NEW CITY");
	Software s3 = new Software("AHHH",13.2,8,h);
	System.out.println(s3.toString());

	EducationalSoftware es = new EducationalSoftware("esoft", 13.1313, 15, h2, "math", "high school");
	System.out.println(es.toString());
    }
}
