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

    /**
     * Конструктор
     * (если в array несколько элементов, то создастся дерево из него,
     * если нет, то сгенерирутся список из кол-во равного array[0] элементов)
     */
    public Tree(List<Integer> array, String indent, String empty) {
        this.indent = indent;
        this.empty = empty;

        if (array.size() == 1) { //Если размер списка: array = 1
            array = generateRandomArray(array.get(0)); //Генерируем посл-ть элементов, кол-во берется из array[0]
        }

        //Вывод массива
        printArray(array);

        //Создание поискового дерева
        System.out.println(">> Build Search Tree");
        root = new Node(array.get(0)); //Корнем дерева, назначается первый элемент из списка: array
        array.remove(0);
        for (Integer el : array) {
            addR(el, root); //В цикле передаем все элементы списка: array, в ф-ю addR()
        }

    }

    /** Рекурсивное добавление элемента в Поисковое дерево */
    private Node addR(int el, Node node) {
        if (node == null) { //Если узел = null
            node = new Node(el); //Создаем новый узел, знач-е узла берем из el
        } else if (el > node.getData()) { //Если el > текущего узла
            node.setRight(addR(el, node.getRight())); //Передаем его в правое поддерево
        } else { //Если el <= текущего узла
            node.setLeft(addR(el, node.getLeft())); //Передаем его в левое поддерево
        }

        return node; //Ф-я возвращает текущий узел
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

    /** Вывод массива */
    private void printArray(List<Integer> array) {
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
        List<Integer> simmetrArray = new ArrayList<Integer>(); //Создаем список, который будет содержать эл-ты дерева в симметр. порядке
        printSimmetrR(root, simmetrArray); //Добавляем в simmetrArray элементы дерева в симметр. порядке

        //Выводим дерево в симметричном порядке
        for (Integer el : simmetrArray) {
            System.out.print(el + ", ");
        }
        System.out.println();
    }

    /** Рекурсивный Симметричный вывод элементов */
    private void printSimmetrR(Node node, List<Integer> backArray) {
        if (node != null) {
            printSimmetrR(node.getLeft(), backArray); //Запускаем ф-ю для левого поддерева
            backArray.add(node.getData()); //Добавляем узел в backArray
            printSimmetrR(node.getRight(), backArray); //Запускаем ф-ю для правого поддерева
        }
    }


    /** Вывод Дерева Графически */
    public void printGraphicalTree() {
        System.out.println(">> Print Graphical Tree");
        height = getHeight(); //Получаем max высоту дерева
        graph = new ArrayList<String>(); //Создаем список, который будет содержать графическое представление дерева

        for (int i = 0; i < height; i++) { //Добавляем height раз пустых строк в graph
            graph.add("");
        }

        System.out.println("Height: " + height + "\n");

        generateGraphicalTreeR(root, 1); //Запускаем ф-ю графического представления дерева

        // Вывод Дерева
        for (String row : graph) {
            System.out.println(row);
        }
        System.out.println();
    }

    /** Генерация Графического представления Дерева Рекурсивно */
    private void generateGraphicalTreeR(Node node, int lvl) {
        if (lvl == 1) { //Если уровень в дереве = 1
            generateGraphicalNode(node, lvl); //Генерируем графич. представление для ветки node
        } else if (lvl <= height) { //Если уровень <= height
            Node tmpNode = node != null ? node.getLeft() : null;
            generateGraphicalNode(tmpNode, lvl); //Генерируем графич. предсавление для левой ветки, если она не равна null

            tmpNode = node != null ? node.getRight() : null;
            generateGraphicalNode(tmpNode, lvl); //Генерируем графич. предсавление для правой ветки, если она не равна null
        }
    }

    /** Генерация графического представления для ветки */
    private void generateGraphicalNode(Node node, int lvl) {
        printIndent(lvl, false); //Выводим отступы слева от узла
        if (node != null) { //Если узел не равен null
            graph.set(lvl-1, graph.get(lvl-1) + node.getData()); //Добавляем к уровню lvl узел
        } else { //Если узел равен null
            graph.set(lvl-1, graph.get(lvl-1) + empty); //Добавляем строку empty
        }
        printIndent(lvl, true); //Выводим отступы справа от узла

        generateGraphicalTreeR(node, lvl+1); //Генерируем графич. предсавление для текущего узла
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

        //Создаем класс Tree: size - кол-во элементов, ".." - разделитель, "xx" - пустые узлы
        //Создаем поисковое дерево
        Tree tree = new Tree(size, "--", "||");

        //Вывод текстового представления дерева
        //tree.printTree();

        //Вывод графического представления дерева
        tree.printGraphicalTree();

        //Вывод дерева в Симметричном порядке
        tree.printSimmetr();
    }
}


