/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 23.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;

/**
 * <b>15 вариант</b><br/>
 * Построить хеш - таблицу, содержащую последовательность из m=45 элементов размерности n=2.<br/>
 * Элементы генерируются с помощью датчика случ. чисел.<br/>
 * Хеш - функция - первая цифра квадрата ключа.<br/>
 * Метод разрешения конфликта - квадратичные пробы.<br/>
 */
public class Hash {
    /** TODO */
    private int num;
    private boolean debug;
    private List<Integer> array;
    private int hashMapSize;
    private Map<Integer, Integer> hashMap;
    private List<Integer> probArray;

    /** Конструктор */
    public Hash(int num, boolean debug, List<Integer> array) {
        this.num = num;
        this.debug = debug;
        this.array = array != null ? array : new ArrayList<Integer>();
    }

    /** Рандомная генерация массива */
    public void generateRandomArray() {
        if (array.size() == 0) {
            for(int i=0; i<num; i++) {
                array.add(getRandUniq());
            }
        }

        printRandomArray();
    }

    /** Возвращает рандомное уникальное значение, размерности = 2 */
    private int getRandUniq() {
        int value = Math.round((float)Math.random()*89) + 10;
        if (array.contains(value)) {
            return getRandUniq();
        } else {
            return value;
        }
    }

    /** Вывод рандомного массива */
    private void printRandomArray() {
        System.out.println("Random array (size: " + array.size() + "):");
        for(Integer el : array) {
            System.out.print(el + ", ");
        }
        System.out.println();
    }

    /** Построение хеш-таблицы */
    public void buildHashTable() {
        hashMapSize = Math.round((float)(array.size() * 1.5));
        hashMap = new HashMap<Integer, Integer>();
        probArray = new ArrayList<Integer>();

        if (debug) System.out.println("\nHash elements:");
        for (Integer el : array) {
            probArray.add(putEl(el, 0));
        }

        printHashTable();
    }

    /** Помещение элемента в хеш-таблицу */
    private int putEl(int el, int value) {
        int index;
        if (value == 0) {
            index = hash(el) % hashMapSize;
            if (debug) System.out.println(el + "->" + index);
        } else {
            index = (hash(el) + value*value) % hashMapSize;
            if (debug) System.out.println("  value: " + value + "; index: " + index);
        }

        if (hashMap.get(index) == null) {
            hashMap.put(index, el);
            return value + 1;
        } else {
            return putEl(el, value+1);
        }
    }

    /** Функция хеширования */
    private int hash(int el) {
        return Integer.parseInt(Integer.toString(el * el).substring(0, 1));
    }

    /** Вывод хеш-таблицы */
    private void printHashTable() {
        System.out.println("\nHash array (size: " + hashMapSize + "):");
        for (int index=0; index<hashMapSize; index++) {
            Integer el = hashMap.get(index);
            String els = el != null ? el.toString() : " -";
            System.out.print(index + "->" + els);
            if (index < hashMapSize-1) System.out.print(",\t\t");
            if ((index+1) % 5 == 0) System.out.println();
        }

        System.out.println("\n");
    }

    /** Коеф. заполнения */
    public float k_zap() {
        return (float)hashMap.size() / hashMapSize;
    }

    /** Среднее кол-во проб */
    public float sr_prob() {
        int sum = 0;
        for (Integer el : probArray) {
            sum += el;
        }

        return (float)sum / array.size();
    }

    /** Главная функция */
    public static void main(String[] args) {
        List<Integer> array = Arrays.asList(69,85,73,54,12,23,47); //Для отладки
        Hash hash = new Hash(47, false, null);
        hash.generateRandomArray();
        hash.buildHashTable();

        System.out.println("  k zap: " + hash.k_zap());
        System.out.println("sr prob: " + hash.sr_prob());
    }
}
