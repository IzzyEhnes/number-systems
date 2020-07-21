package Ehnes.Izzy.NumberSystems;

public class Main
{
    public static void main(String[] args)
    {
        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(2.5);
        Decimal d3 = new Decimal(3.5);

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d2.addDecimal(d3));
        System.out.println(d2.subtractDecimal(d3));
        System.out.println(d2.multiplyDecimal(d3));
        System.out.println(d2.divideDecimal(d3));
        // System.out.println(d2.divideDecimal(d1));
    }
}
