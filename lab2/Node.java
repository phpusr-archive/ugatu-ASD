/**
 * Created with IntelliJ IDEA.
 * User: phpusr
 * Date: 30.03.13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */

/**
 * Узел
 */
public class Node {
    /** Поле с данными */
    private Integer data;
    /** Левая ветка */
    private Node left;
    /** Правая ветка */
    private Node right;

    public Node() {}

    public Node(Integer data) {
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

    /** Вывод узла в текстовом виде */
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
