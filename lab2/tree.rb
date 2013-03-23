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
  def initialize(root, list, ideal)
    @root = Node.new(root)
    if ideal
      puts '>> Build Ideal Tree'
	  list = generate_random_array(25) if list.nil? #TODO
      @root = build_ideal_r(list, @root, list.size+1)
    else
      puts '>> Build Search Tree'
      list.each { |el| add(el) }
    end
  end

  # Рандомная генерация массива
  def generate_random_array(num)
	@array = [] if @array.nil?
    if @array.size == 0
      num.times {
        @array << get_rand_uniq
      }
    end

    print_random_array
	@array
  end

  # Добавление элемента в Поисковое дерево
  def add(el)
    add_r(el, @root)
  end

  # Вывод Поискового дерева
  def print_tree
    puts '>> Print Tree'
    print_r(@root)
	puts
  end
  
  # Вывод Дерева Графически
  def print_graph_tree
	puts '>> Print Graph Tree'
	@count = 0
	@count_l = 0
	@count_r = 0
	@height = 0
	@count_down = 0
  @a = []
	
	height_ideal_r(@root)
	calcs_graph(@root)
  (@height+1).times { |num|
    @a[num] = ''
  }
	
	puts "height: #{@height}; count_down: #{@count_down}; count: #{@count}; count_l: #{@count_l}; count_r: #{@count_r}"
	
	generate_graph_tree_r(@root, 1)
    puts @a
  end
  
  # Генерация Графического представления Дерева
  def generate_graph_tree_r(node, lvl)
    if lvl == 1
      generate_graph_node(node, lvl)
    elsif node
      generate_graph_node(node.left, lvl)
      generate_graph_node(node.right, lvl)
    end
  end

  # Генерация графического представления для ветки
  def generate_graph_node(node, lvl)
    return if lvl > @height

    empty = 'xxx'
    print_indent(lvl, false)
    if node
      @a[lvl] << node.data.to_s
    else
      @a[lvl] << empty
    end
    print_indent(lvl, true)

    generate_graph_tree_r(node, lvl+1)
  end

  # Вывод Отступа справа и слева
  def print_indent(lvl, right)
    return if lvl > @height
    count = 2**(@height-lvl)-1
    count += 1 if right
    count.times{@a[lvl] << '---'}
  end
  
  # Вывод Дерева Графически Рукурсионно
  def calcs_graph(node)	
	if node		
		@count += 1
		@count_l += 1 if node.left
		@count_r += 1 if node.right
		calcs_graph(node.left)
		calcs_graph(node.right)		
		unless node.left || node.right
			@count_down += 1
		end		
	end
  end
  
  # Высота Идеал дерева
  def height_ideal_r(node)
	if node
		@height += 1
		height_ideal_r(node.left)
	end
  end
  
  # Вывод дерева в обратном порядке
  def print_back
	puts '>> Print Back Tree'
    print_back_r(@root)
	puts "\n\n"
  end

  private

  # Возвращает рандомное уникальное значение
  def get_rand_uniq
    value = (rand(900)+100)
    if  @array.find {|el| el == value} == nil
      value
    else
      get_rand_uniq
    end
  end

  # Вывод рандомного массива
  def print_random_array
    puts "Random array (size: #{@array.size}):"
    puts @array.join(', ')
    puts
  end

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

  # Рекурсивный Обратный вывод элементов в Поисковом дереве
  def print_back_r(node)
    unless node.nil?      
      print_back_r(node.left)
      print_back_r(node.right)
	  print "#{node.data}, "
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

end

array = [8, 91, 113, 22, 125, 128, 45, 55, 50, 61, 58]
tree = Tree.new(29, array, true)
tree.print_tree
tree.print_back
tree.print_graph_tree