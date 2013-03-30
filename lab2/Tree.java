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

/** TODO
 * Сгенерировать 25 3-х значных неповторяющихся элементов<br/>
 * Вывести их на экран<br/>
 * Представить в виде идельно-сбалансированного дерева<br/>
 * Вывести элементы дерева в обратном порядке<br/>
 */

/**
 * Дерево
 */
public class Tree {
    /** Отступ слева и справа */
    private String indent;
    /** Пустые Узлы */
    private String empty;
    /** Корень дерева */
    private Node root;
    /** Высота дерева */
    private int height;
    /** Список строк графического Дерева */
    private List<String> graph;

    //TODO добавить комментарии к функциям, проверить все TODO

    /**
     * Конструктор
     * (если в array несколько элементов, то создастся дерево из него,
     * если нет, то сгенерирутся список из кол-во равного array[0] элементов)
     */
    public Tree(List<Integer> array, boolean ideal, String indent, String empty) {
        this.indent = indent;
        this.empty = empty;

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
            return node;
        }
        return null;
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

    /** Вывод Дерева Графически */
    public void printGraphicalTree() {
        System.out.println(">> Print Graphical Tree");
        height = getHeight();
        graph = new ArrayList<String>();

        for (int i = 0; i < height; i++) {
            graph.add("");
        }

        System.out.println("Height: " + height);

        generateGraphicalTreeR(root, 1);

        // Вывод Дерева
        for (String row : graph) {
            System.out.println(row);
        }

    }

    /** Генерация Графического представления Дерева Рекурсивно */
    private void generateGraphicalTreeR(Node node, int lvl) {
        if (lvl == 1) {
            generateGraphicalNode(node, lvl);
        } else if (lvl <= height) {
            Node tmpNode = node != null ? node.getLeft() : null;
            generateGraphicalNode(tmpNode, lvl);

            tmpNode = node != null ? node.getRight() : null;
            generateGraphicalNode(tmpNode, lvl);
        }
    }

    /** Генерация графического представления для ветки */
    private void generateGraphicalNode(Node node, int lvl) {
        printIndent(lvl, false);
        if (node != null) {
            graph.set(lvl-1, graph.get(lvl-1) + node.getData());
        } else {
            graph.set(lvl-1, graph.get(lvl-1) + empty);
        }
        printIndent(lvl, true);

        generateGraphicalTreeR(node, lvl+1);
    }

    /** Вывод Отступа справа и слева */
    private void printIndent(int lvl, boolean right) {
        int count = (int)Math.pow(2, height-lvl)-1;
        if (right) count++;
        for (int i = 0; i < count; i++) {
            graph.set(lvl-1, graph.get(lvl-1) + indent);
        }
    }

    /** Высота Дерева */
    private int getHeight() {
        List<Integer> heights = new ArrayList<Integer>();
        getHeightR(root, 1, heights);

        int max = 0;
        for (Integer h : heights) {
            if (h > max) max = h;
        }

        return max;
    }

    /** Высота Дерева рекурсивно */
    private void getHeightR(Node node, int lvl, List<Integer> heights) {
        if (node != null) {
            getHeightR(node.getLeft(), lvl+1, heights);
            getHeightR(node.getRight(), lvl+1, heights);
        } else {
            heights.add(lvl-1);
        }
    }

    /** Главная функция */
    public static void main(String[] args) {
        //Массив для проверки
        List<Integer> array = new LinkedList<Integer>(Arrays.asList(69, 85, 73, 54, 12, 23, 47));
        List<Integer> size = Arrays.asList(25);

        //TODO Создаем класс Tree: size - кол-во элементов, true - идеально сбал. дер-во, ".." - разделитель, "xx" - пустые узлы
        Tree tree = new Tree(size, true, "..", "xx");
        tree.printTree();
        tree.printGraphicalTree();
    }
}


