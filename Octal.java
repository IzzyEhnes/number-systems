package Ehnes.Izzy.NumberSystems;

public class Octal
{

    private Double octal = 0.0;



    public Octal()
    {
    }



    public Octal(Double inDouble)
    {
        octal = inDouble;
    }



    public Double getOctal()
    {
        return this.octal;
    }



    public void setOctal(Double inDouble)
    {
        this.octal = inDouble;
    }



    public boolean isOctal()
    {
        String octalString = Double.toString(this.octal);

        for (int i = 0; i < octalString.length(); i++)
        {
            if ((octalString.charAt(i) < '0' || octalString.charAt(i) > '7')
                && octalString.charAt(i) != '.')
            {
                return false;
            }
        }

        return true;
    }



    @Override
    public String toString()
    {
        return Double.toString(octal);
    }
}
