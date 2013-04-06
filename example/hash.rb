
# Класс для хеширования
class Hash

  def initialize(src)
    @src = src          # Массив источник
    @tmp = nil          # Временный массив
  end

  # Открытая адресация с Линейным опробыванием
  def open_linear
    puts '>>open_linear'
    size = (@src.size * 1.5).to_i
    puts "size tmp array: #{size}"
    @tmp = Array.new(size)
    @src.each { |el|
      put_el(el, 0, :linear)
    }

    print_tmp(false)
  end

  # Открытая адресация с Квадратичным опробыванием
  def open_squad
    puts '>>open_squad'
    size = (@src.size * 1.5).to_i
    puts "size tmp array: #{size}"
    @tmp = Array.new(size)
    @src.each { |el|
      put_el(el, 0, :squad)
    }

    print_tmp(false)
  end

  # Метод Цепочек
  def chain_method
    puts '>>chain_method'
    size = (@src.size / 2).to_i
    puts "size tmp array: #{size}"
    @tmp = Array.new(size)
    @src.each { |el|
      put_el_chain(el)
    }

    print_tmp(true)
  end

  private
  # Вставка элемента во временный массив (Открытая адресация)
  def put_el(el, value, type)
    if value == 0
      index = func(el)
      puts "#{el}->#{index}"
    else
      index = type == :linear ? (func(el)+value) % @tmp.size : (func(el)+value**2) % @tmp.size
      puts "\t#{value}->#{index}"
    end

    if @tmp[index] == nil then
      @tmp[index] = el
    else
      put_el(el, value+1, type)
    end
  end

  # Вставка элемента во временный массив (Метод Цепочек)
  def put_el_chain(el)
    index = func(el)
    @tmp[index] = [] if @tmp[index] == nil
    @tmp[index] << el
  end

  # Функция хеширования
  def func(el)
    el % @tmp.size
  end

  # Вывод временного массива
  def print_tmp(wrap)
    puts
    @tmp.each_with_index { |el, index|
      print "#{index}->#{el}"
      print ', ' if index < @tmp.size-1
      puts if wrap
    }
    puts "\n\n"
  end

end


array = [6,5,11,12,9,2,3,7,20]
array2 = [29,8,91,113,777,22,125,65,75,85]
hash = Hash.new(array2)
hash.open_linear
hash.open_squad
hash.chain_method

#puts 9 % 5