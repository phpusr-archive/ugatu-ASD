/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 30.03.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Узел
 */
class Node {
    private Integer data;
    private Node left;
    private Node right;

    Node() {}

    Node(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        String tmp = "N: " + data;
        tmp += ";\t L: ";
        tmp += left != null ? left.data : "null";
        tmp += ";\t\t R: ";
        tmp += right != null ? right.data : "null";

        return tmp;
    }
}

/**
 * Дерево
 */
public class Tree {
    /** Отступ слева и справа */
    private String indent;
    /** TODO */
    private String empty;
    /** Корень дерева */
    private Node root;



    /**
     * Конструктор
     * (если array список, то создастся дерево из него,
     * если нет, то сгенерирутся список из array элментов)
     */
    public Tree(List<Integer> array, boolean ideal, String indent, String empty) {
        if (array.size() == 1) {
            array = generateRandomArray(array.get(0));
        }

        //Вывод Рандомного массива
        printRandomArray(array);

        if (ideal) {
            System.out.println(">> Build Ideal Tree");
            root = buildIdealR(array, new Node(), array.size());
        } else {
            System.out.println(">> Build Search Tree");
            root = new Node(array.get(0));
            array.remove(0);
            for (Integer el : array) {
                addR(el, root);
            }
        }
    }

    /** Рекурсивное добавление элемента в Поисковое дерево */
    private Node addR(int el, Node node) {
        if (node == null) {
            node = new Node(el);
        } else if (el > node.getData()) {
            node.setRight(addR(el, node.getRight()));
        } else {
            node.setLeft(addR(el, node.getLeft()));
        }

        return node;
    }

    /** Рекурсивное построение Идеально-сбалансированного дерева */
    private Node buildIdealR(List<Integer> array, Node node, int size) {
        if (size != 0) {
            if (node.getData() == null) {
                node.setData(array.get(0));
                array.remove(0);
            }
            node.setLeft(buildIdealR(array, new Node(), size/2));
            node.setRight(buildIdealR(array, new Node(), size-size/2-1));
        }

        return node;
    }

    /** Текстовый вывод дерева */
    public void printTree() {
        System.out.println(">> Print Tree");
        printR(root);
        System.out.println();
    }

    /** Рекурсивный вывод элементов в Поисковом дереве */
    private void printR(Node node) {
        if (node != null && node.getData() != null) {
            System.out.println(node);
            printR(node.getLeft());
            printR(node.getRight());
        }
    }

    /** Рандомная генерация массива */
    private List<Integer> generateRandomArray(int size) {
        List<Integer> array = new ArrayList<Integer>();
        for(int i=0; i<size; i++) {
            array.add(getRandUniq(array));
        }

        return array;
    }

    /** Возвращает рандомное уникальное значение, размерности = 2 */
    private int getRandUniq(List<Integer> array) {
        //Генерация случайного числа Размерности = 2
        int value = Math.round((float)Math.random()*89) + 10;

        //Если число есть в Рандомном массиве то Генерируем заново, если нет то добаавляем в массив
        if (array.contains(value)) {
            return getRandUniq(array);
        } else {
            return value;
        }
    }

    /** Вывод рандомного массива */
    private void printRandomArray(List<Integer> array) {
        System.out.println("Random array (size: " + array.size() + "):");
        for(Integer el : array) {
            System.out.print(el + ", ");
        }
        System.out.println();
    }

    /** Главная функция */
    public static void main(String[] args) {
        //Массив для проверки
        List<Integer> array = new LinkedList<Integer>(Arrays.asList(69, 85, 73, 54, 12, 23, 47));
        List<Integer> size = Arrays.asList(5);

        //TODO Создаем класс Hash: 45 - кол-во элементов, false - выкл-е отладки, null - массив пустой
        Tree tree = new Tree(array, false, "...", "xxx");
        tree.printTree();
    }
}


