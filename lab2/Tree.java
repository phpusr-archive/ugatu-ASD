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
 * Дерево<br/>
 * Сгенерировать 23, 2-х значных неповторяющихся чисел<br/>
 * Вывести их на экран<br/>
 * Представить в виде поискового дерева<br/>
 * Вывести элементы дерева в симметричном порядке<br/>
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
    public Tree(List<Integer> array, String indent, String empty) {
        this.indent = indent;
        this.empty = empty;

        if (array.size() == 1) {
            array = generateRandomArray(array.get(0));
        }

        //Вывод Рандомного массива
        printRandomArray(array);

        System.out.println(">> Build Search Tree");
        root = new Node(array.get(0));
        array.remove(0);
        for (Integer el : array) {
            addR(el, root);
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

    /** Вывод дерева в Симметричном порядке */
    public void printSimmetr() {
        System.out.println(">> Print Back Tree");
        List<Integer> backArray = new ArrayList<Integer>();
        printSimmetrR(root, backArray);

        for (Integer el : backArray) {
            System.out.print(el + ", ");
        }
        System.out.println();
    }

    /** Рекурсивный Симметричный вывод элементов */
    private void printSimmetrR(Node node, List<Integer> backArray) {
        if (node != null) {
            printSimmetrR(node.getLeft(), backArray);
            backArray.add(node.getData());
            printSimmetrR(node.getRight(), backArray);
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

        System.out.println("Height: " + height + "\n");

        generateGraphicalTreeR(root, 1);

        // Вывод Дерева
        for (String row : graph) {
            System.out.println(row);
        }
        System.out.println();
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
        //Кол-во рандомных элементов
        List<Integer> size = Arrays.asList(23);

        //TODO Создаем класс Tree: size - кол-во элементов, ".." - разделитель, "xx" - пустые узлы
        Tree tree = new Tree(size, "..", "xx");
        tree.printTree();
        tree.printGraphicalTree();
        tree.printSimmetr();
    }
}


