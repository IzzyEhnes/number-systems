package Ehnes.Izzy.NumberSystems;

import java.util.HashMap;

public abstract class NumberSystem<T>
{
    HashMap<Character, Integer> hexMap = new HashMap<>();

    NumberSystem()
    {
        hexMap.put('0', 0);
        hexMap.put('1', 1);
        hexMap.put('2', 2);
        hexMap.put('3', 3);
        hexMap.put('4', 4);
        hexMap.put('5', 5);
        hexMap.put('6', 6);
        hexMap.put('7', 7);
        hexMap.put('8', 8);
        hexMap.put('9', 9);
        hexMap.put('A', 10);
        hexMap.put('B', 11);
        hexMap.put('C', 12);
        hexMap.put('D', 13);
        hexMap.put('E', 14);
        hexMap.put('F', 15);
    }

    public abstract T add(T addend);
    public abstract T subtract(T subtrahend);
    public abstract T multiply(T multiplier);
    public abstract T divide(T divisor, int scale);

    public abstract String toString();
}
