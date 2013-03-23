# Узел
class Node
  attr_accessor :data, :left, :right

  def initialize(data)
    @data = data
  end

  def to_s
    "N:  #{@data};\t L: #{@left.data unless @left.nil?};\t R: #{@right.data unless @right.nil?}"
  end
end

=begin
Сгенерировать 25 3-х значных неповторяющихся элементов
Вывести их на экран
Пердставить в виде идельно-сбалансированного дерева
Вывести элементы дерева в обратном порядке
=end


# Дерево
class Tree

  # Конструктор
  # (если array список, то создастся дерево из него,
  # если нет, то сгенерирутся список из array элментов)
  def initialize(array, ideal, indent, empty)
    @indent = indent
    @empty = empty
    array = generate_random_array(array, []) unless array.kind_of?(Array)

    if ideal
      puts '>> Build Ideal Tree'
      @root = build_ideal_r(array, Node.new(nil), array.size)
    else
      puts '>> Build Search Tree'
      @root = Node.new(array.first)
      array.delete_at(0)
      array.each { |el| add_r(el, @root) }
    end
  end

  # Добавление элемента в Поисковое дерево
  def add(el)
    add_r(el, @root)
  end

  # Текстовый вывод дерева
  def print_tree
    puts '>> Print Tree'
    print_r(@root)
	  puts
  end
  
  # Вывод Дерева Графически
  def print_graphical_tree
    puts '>> Print Graph Tree'
    @height = get_height
    @graph = Array.new(@height)

    (@height).times { |num| @graph[num] = '' }

    puts "Height: #{@height}"

    generate_graphical_tree_r(@root, 1)
    puts @graph
  end

  # Высота Дерева
  def get_height
    heights = []
    get_height_r(@root, 1, heights)

    heights.max-1
  end

  # Вывод дерева в обратном порядке
  def print_back
    puts '>> Print Back Tree'
    back_array = []
    print_back_r(@root, back_array)
    puts back_array.join(', ')
    puts "\n\n"
  end

  private

  # Рандомная генерация массива
  def generate_random_array(num, array)
    if array.size == 0
      num.times { array << get_rand_uniq(array) }
    end

    print_random_array(array)
    array
  end

  # Возвращает рандомное уникальное значение
  def get_rand_uniq(array)
    value = (rand(900)+100)
    if  array.find {|el| el == value} == nil
      value
    else
      get_rand_uniq(array)
    end
  end

  # Вывод рандомного массива
  def print_random_array(array)
    puts "Random array (size: #{array.size}):"
    puts array.join(', ')
    puts
  end

  # Рекурсивное добавление элемента в Поисковое дерево
  def add_r(el, node)
    if node.nil?
      node = Node.new(el)
    elsif el > node.data
      node.right = add_r(el, node.right)
    else
      node.left = add_r(el, node.left)
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

  # Рекурсивный Обратный вывод элементов в Поисковом дереве
  def print_back_r(node, back_array)
    unless node.nil?      
      print_back_r(node.left, back_array)
      print_back_r(node.right, back_array)
	    back_array << node.data
    end
  end

  # Рекурсивное построение Идеально-сбалансированного дерева
  def build_ideal_r(list, node, count)
    unless count == 0    
      if node.data.nil?
        node.data = list.first
        list.delete_at(0)
      end
      node.left = build_ideal_r(list, Node.new(nil), count/2)
      node.right = build_ideal_r(list, Node.new(nil), count-count/2-1)
      node
    end
  end

  # Высота Дерева рекурсивно
  def get_height_r(node, lvl, heights)
    if node
      get_height_r(node.left, lvl+1, heights)
      get_height_r(node.right, lvl+1, heights)
    else
      heights << lvl
    end
  end

  # Генерация Графического представления Дерева Рекурсивно
  def generate_graphical_tree_r(node, lvl)
    if lvl == 1
      generate_graphical_node(node, lvl)
    elsif lvl <= @height
      tmp_node = !node.nil? ? node.left : nil
      generate_graphical_node(tmp_node, lvl)

      tmp_node = !node.nil? ? node.right : nil
      generate_graphical_node(tmp_node, lvl)
    end
  end

  # Генерация графического представления для ветки
  def generate_graphical_node(node, lvl)
    print_indent(lvl, false)
    if node
      @graph[lvl-1] << node.data.to_s
    else
      @graph[lvl-1] << @empty
    end
    print_indent(lvl, true)

    generate_graphical_tree_r(node, lvl+1)
  end

  # Вывод Отступа справа и слева
  def print_indent(lvl, right)
    count = 2**(@height-lvl)-1
    count += 1 if right
    count.times{ @graph[lvl-1] << @indent }
  end

end

array = [29, 8, 91, 113, 22, 125, 128, 45, 55, 50, 61, 58]
tree = Tree.new(array, true, '...', 'xxx')
#tree.print_tree
tree.print_back
tree.print_graphical_tree