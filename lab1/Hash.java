/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 23.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;

/**
 * 15 вариант
 * Построить хеш - таблицу, содержащую последовательность из m=45 элементов размерности n=2. Элементы генерируются с помощью датчика случ. чисел.
 * Хеш - функция - первая цифра квадрата ключа.
 * Метод разрешения конфликта - квадратичные пробы.
 */
public class Hash {
    /** TODO */
    private int num;
    private boolean showHashEl;
    private List<Integer> array;
    private int hashMapSize;
    private Map<Integer, Integer> hashMap;
    private List<Integer> probArray;

    /** Конструктор */
    public Hash(int num, boolean showHashEl, List<Integer> array) {
        this.num = num;
        this.showHashEl = showHashEl;
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
        int value = Math.round((float)Math.random()*90) + 10;
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

        if (showHashEl) System.out.println("Hash elements:");
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
            if (showHashEl) System.out.println(el + "->" + index);
        } else {
            index = (hash(el) + value) % hashMapSize;
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

        System.out.println();
    }

    /** Главная функция */
    public static void main(String[] args) {
        List<Integer> array = Arrays.asList(79,58,93,48,21);
        Hash hash = new Hash(47, true, null);
        hash.generateRandomArray();
        hash.buildHashTable();
    }
}
