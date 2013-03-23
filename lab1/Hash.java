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
    /** Кол-во элементов в Рандомном массиве */
    private int num;
    /** Отладка */
    private boolean debug;
    /** Массив элементов */
    private List<Integer> array;
    /** Размер Хеш-таблицы */
    private int hashMapSize;
    /** Хеш-таблица */
    private Map<Integer, Integer> hashMap;
    /** Массив, который содержит кол-во проб
     * при добавлении элемента в Хеш-таблицу */
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

        //Вывод Рандомного массива
        printRandomArray();
    }

    /** Возвращает рандомное уникальное значение, размерности = 2 */
    private int getRandUniq() {
        //Генерация случайного числа Размерности = 2
        int value = Math.round((float)Math.random()*89) + 10;

        //Если число есть в Рандомном массиве то Генерируем заново, если нет то добаавляем в массив
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

    /** Построение Хеш-таблицы */
    public void buildHashTable() {
        //Размер Хеш-таблицы = Размер Рандомного массива * 1.5
        hashMapSize = Math.round((float)(array.size() * 1.5));
        hashMap = new HashMap<Integer, Integer>();
        probArray = new ArrayList<Integer>();

        if (debug) System.out.println("\nHash elements:");
        //Добавляем все элементы из Рандомного массива в Хеш-таблицу, вызываю функцию putEl(...)
        for (Integer el : array) {
            probArray.add(putEl(el, 0));
        }

        //Выводим Хеш-таблицу
        printHashTable();
    }

    /** Помещение элемента в Хеш-таблицу */
    private int putEl(int el, int value) {
        int index;
        if (value == 0) { //Если в функцию зашли Первый раз, то получаем Хеш-код элемента
            index = hash(el) % hashMapSize;
            if (debug) System.out.println(el + "->" + index);
        } else { //Если НЕ Первый раз, то используем метод: Квадратичных проб для разрешения коллизии
            index = (hash(el) + value*value) % hashMapSize;
            if (debug) System.out.println("  value: " + value + "; index: " + index);
        }

        //Если по вычесленному хеш-коду свободно, то ставим элмент по этому индексу
        if (hashMap.get(index) == null) {
            hashMap.put(index, el);
            return value + 1;
        } else {
            return putEl(el, value+1);
        }
    }

    /** Функция хеширования */
    private int hash(int el) {
        //Хеш-функция - первая цифра квадрата ключа
        return Integer.parseInt(Integer.toString(el * el).substring(0, 1));
    }

    /** Вывод Хеш-таблицы */
    private void printHashTable() {
        System.out.println("\nHash array (size: " + hashMapSize + "):");
        for (int index=0; index<hashMapSize; index++) {
            //Получаем элемент из хеш-таблицы
            Integer el = hashMap.get(index);
            //Если элемент есть, то Выводим его, иначе Выводим: " -"
            String els = el != null ? el.toString() : " -";
            System.out.print(index + "->" + els);
            //Если элемент не последний, то Разделяем его от остальных
            if (index < hashMapSize-1) System.out.print(",\t\t");
            //Переводим строку каждые 5 элементов
            if ((index+1) % 5 == 0) System.out.println();
        }

        System.out.println("\n");
    }

    /** Коеф. заполнения */
    public float k_zap() {
        //Кол-во заполненных элементов Хеш-таблицы / Размер Хеш-таблицы
        return (float)hashMap.size() / hashMapSize;
    }

    /** Среднее кол-во проб */
    public float sr_prob() {
        int sum = 0;
        //Считаем сумму всех проб
        for (Integer el : probArray) {
            sum += el;
        }

        //Сумму всех проб / кол-во элементов в Рандомном массиве
        return (float)sum / array.size();
    }

    /** Главная функция */
    public static void main(String[] args) {
        //Массив для проверки
        List<Integer> array = Arrays.asList(69,85,73,54,12,23,47);

        //Создаем класс Hash: 47 - кол-во элементов, false - выкл-е отладки, null - массив пустой
        Hash hash = new Hash(47, false, null);
        //Запускаем  рандомную генерацию массива
        hash.generateRandomArray();
        //Строим Хеш-таблицу
        hash.buildHashTable();

        //Выводим Коеффициенты
        System.out.println("  k zap: " + hash.k_zap());
        System.out.println("sr prob: " + hash.sr_prob());
    }
}
