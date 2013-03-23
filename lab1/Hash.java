/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 23.03.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.List;

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

    /** Главная функция */
    public static void main(String[] args) {
        Hash hash = new Hash(47, false, null);
        hash.generateRandomArray();
    }
}
