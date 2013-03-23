# Узел
class Node
  attr_accessor :data, :left, :right

  def initialize(data)
    @data = data
  end

  def to_s
    "#{object_id}:  #{@data};\t L: #{@left.data unless @left.nil?};\t R: #{@right.data unless @right.nil?}"
  end
end

# Дерево
class TreeExm

  # Конструктор
  def initialize(root, list, ideal)
    @root = Node.new(root)
    if ideal
      puts '>> Build Ideal Tree'
      @root = build_ideal_r(list, @root, list.size+1)
    else
      puts '>> Build Search Tree'
      list.each { |el| add(el) }
    end
  end

  # Добавление элемента в Поисковое дерево
  def add(el)
    add_r(el, @root)
  end

  # Вывод Поискового дерева
  def print
    puts '>> Print Tree'
    print_r(@root)
  end

  private

  # Рекурсивное добавление элемента в Поисковое дерево
  def add_r(el, node)
    if node.nil?
      node = Node.new(el)
    elsif node.data > el
      node.left = add_r(el, node.left)
    else
      node.right = add_r(el, node.right)
    end

    node # TODO Проверить в Java (если убрать, дерево будет состоять только из конечных листьев)
  end

  # Рекурсивный вывод элементов в Поисковом дереве
  def print_r(node)
    unless node.nil?
      puts node.to_s
      print_r(node.left)
      print_r(node.right)
    end
  end

  # Рекурсивное построение Идеально-сбалансированного дерева
  def build_ideal_r(list, node, count)
    if count == 0
      nil
    else
      if node.data.nil?
        node.data = list.first
        list.delete_at(0)
      end
      node.left = build_ideal_r(list, Node.new(nil), count/2)
      node.right = build_ideal_r(list, Node.new(nil), count-count/2-1)
      node
    end
  end

end

array = [8, 91, 113, 22, 125, 128, 45, 55, 50, 61, 58]
tree = TreeExm.new(29, array, false)
tree.print