package Ehnes.Izzy.NumberSystems;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Tests for Decimal class");

        Decimal d1 = new Decimal();
        Decimal d2 = new Decimal(2.5);
        Decimal d3 = new Decimal(9.5);
        Decimal d4 = new Decimal(50.0);
        Decimal d5 = new Decimal(64.0);
        Decimal d6 = new Decimal(140.7);
        Decimal d7 = new Decimal(261.98);

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d2.addDecimal(d3));
        System.out.println(d2.subtractDecimal(d3));
        System.out.println(d2.multiplyDecimal(d3));
        System.out.println(d2.divideDecimal(d3));
        // System.out.println(d2.divideDecimal(d1)); // should throw divide by zero error

        System.out.println("\ndecimalToBinary: ");
        System.out.println(d3.decimalToBinary());
        System.out.println(d4.decimalToBinary());
        System.out.println(d5.decimalToBinary());
        System.out.println(d6.decimalToBinary());
        System.out.println(d7.decimalToBinary());





        System.out.println("\nTests for Binary class");

        Binary b1 = new Binary();
        Binary b2 = new Binary("101");
        Binary b3 = new Binary("010");
        Binary b4 = new Binary("100101");
        Binary b5 = new Binary("10101");
        Binary b6 = new Binary("1111");
        Binary b7 = new Binary("1111");
        Binary b8 = new Binary("00011100");
        Binary b9 = new Binary("0012001");

        System.out.println("\nisBinary: ");
        System.out.println(b8.isBinary());
        System.out.println(b9.isBinary());

        System.out.println("\naddBinary: ");
        System.out.println(b2.addBinary(b3));
        System.out.println(b5.addBinary(b4));
        System.out.println(b6.addBinary(b7));

        System.out.println("\nonesComplement: ");
        System.out.println(b4.onesComplement());
        System.out.println(b6.onesComplement());

        System.out.println("\ntwosComplement: ");
        System.out.println(b8.twosComplement());
        System.out.println(b2.twosComplement());
        System.out.println(b3.twosComplement());
        System.out.println(b6.twosComplement());

        System.out.println("\nsubtractBinary: ");
        System.out.println(b2.subtractBinary(b3));
        System.out.println(b4.subtractBinary(b5));

        System.out.println("\nmultiplyBinary: ");
        System.out.println(b2.multiplyBinary(b3));
        System.out.println(b3.multiplyBinary(b2));

        System.out.println("\nbinaryToDecimal: ");
        System.out.println(b2.binaryToDecimal());
        System.out.println(b4.binaryToDecimal());





        System.out.println("\nTests for Octal class");
        Octal o1 = new Octal();
        Octal o2 = new Octal("16.0");
        Octal o3 = new Octal("275.12");
        Octal o4 = new Octal("1.0");
        Octal o5 = new Octal("37513.127314");
        Octal o6 = new Octal("35.57");
        Octal o7 = new Octal("642.351");
        Octal o8 = new Octal("1679.0");

        o1.setOctal("3.0");
        System.out.println(o1);

        System.out.println("\naddOctal: ");
        System.out.println(o2.addOctal(o3));
        System.out.println(o4.addOctal(o5));
        System.out.println(o6.addOctal(o7));

        System.out.println("\nisOctal: ");
        System.out.println(o1.isOctal());
        System.out.println(o2.isOctal());
        System.out.println(o3.isOctal());
        System.out.println(o8.isOctal());

        System.out.println("\nsevensComplement: ");
        System.out.println(o3);
        System.out.println(o3.sevensComplement());
        System.out.println(o5);
        System.out.println(o5.sevensComplement());

        System.out.println("\neightsComplement: ");
        System.out.println(o3);
        System.out.println(o3.eightsComplement());
        System.out.println(o5);
        System.out.println(o5.eightsComplement());

    }
}
