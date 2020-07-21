package Ehnes.Izzy.NumberSystems;

import java.security.InvalidParameterException;

public class Decimal
{
    private Double decimal = 0.0;



    public Decimal()
    {
    }



    public Decimal(Double inDecimal)
    {
        decimal = inDecimal;
    }



    public double getDecimal()
    {
        return decimal;
    }



    public void setDecimal(double inDouble)
    {
        this.decimal = inDouble;
    }



    public Decimal add(Decimal inDecimal)
    {
        return new Decimal(decimal + inDecimal.decimal);
    }



    public Decimal subtract(Decimal inDecimal)
    {
        return new Decimal(decimal - inDecimal.decimal);
    }



    public Decimal multiply(Decimal inDecimal)
    {
        return new Decimal(decimal * inDecimal.decimal);
    }



    public Decimal divide(Decimal inDecimal)
    {
        Decimal zero = new Decimal(0.0);

        if ((inDecimal.decimal).equals(0.0))
        {
            throw new InvalidParameterException("Error: Cannot divide by zero.");
        }

        else
        {
            return new Decimal(decimal / inDecimal.decimal);
        }
    }



    public Decimal addDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.add(inDecimal);

        return answer;
    }



    public Decimal subtractDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.subtract(inDecimal);

        return answer;
    }



    public Decimal multiplyDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        answer = this.multiply(inDecimal);

        return answer;
    }



    public Decimal divideDecimal(Decimal inDecimal)
    {
        Decimal answer = new Decimal();

        try
        {
            answer = this.divide(inDecimal);
        }

        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }

        return answer;
    }



    @Override
    public String toString()
    {
        return Double.toString(decimal);
    }



    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        Decimal myDecimal = (Decimal) obj;

        if (decimal == myDecimal.decimal)
        {
            System.out.println("WHY NOT???");
        }

        return (decimal == myDecimal.decimal);
    }
}