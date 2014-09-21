package zipcode1;
public class ZipCodeList
{
    public String city;
    public String ZipCode;
    public ZipCodeList next;

    public ZipCodeList(String c, String z, ZipCodeList n)
    {
	city=c;
	ZipCode=z;
	next=n;
    }
}
