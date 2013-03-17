class HashVar2

  # Конструктор
  def initialize(num, show_hash_el, array)
    @num = num
    @show_hash_el = show_hash_el
    @array = array ? array : []
  end

  # Рандомная генерация массива
  def generate_random_array
    if @array.size == 0
      @num.times {
        @array << get_rand_uniq
      }
    end

    print_random_array
  end

  # Построение хеш-таблицы
  def build_hash_table
    hash_array_size = (@array.size * 1.5).to_i
    @hash_array = Array.new(hash_array_size)
    @prob_array = []

    puts 'Hash elements:' if @show_hash_el
    @array.each { |el|
      @prob_array << put_el(el, 0)
    }

    print_hash_table
  end

  # Коеф. заполнения
  def k_zap
    count = 0
    @hash_array.each { |el|
      count+=1 if el
    }

    (count.to_f / @hash_array.size).round(3)
  end

  # Среднее кол-во проб
  def sr_prob
    sum = 0
    @prob_array.each { |el|
      sum += el
    }

    (sum.to_f / @array.size).round(3)
  end

  private

  # Функция хеширования
  def hash(el)
    (el**2) / 10 % 100
  end

  # Возвращает рандомное уникальное значение
  def get_rand_uniq
    value = (rand(90)+10)
    if  @array.find {|el| el == value} == nil
      value
    else
      get_rand_uniq
    end
  end

  # Помещение элемента в хеш-таблицу
  def put_el(el, value)
    if value == 0
      index = hash(el) % @hash_array.size
      puts "#{el}->#{index}" if @show_hash_el
    else
      index = (hash(el)+value) % @hash_array.size
    end

    if @hash_array[index] == nil
      @hash_array[index] = el
      value+1
    else
      put_el(el, value+1)
    end
  end

  # Вывод рандомного массива
  def print_random_array
    puts "Random array (size: #{@array.size}):"
    puts @array.join(', ')
    puts
  end

  # Вывод хеш-таблицы
  def print_hash_table
    puts
    puts "Hash array (size: #{@hash_array.size}):"

    @hash_array.each_with_index { |el, index|
      print "#{index}->#{el}"
      print ",\t\t" if index < @hash_array.size-1
      puts if (index+1) % 5 == 0
    }
    puts "\n\n"
  end

end

array = [79,58,93,48,21]
hash_var2 = HashVar2.new(47, false, nil)
hash_var2.generate_random_array
hash_var2.build_hash_table
puts "  k zap: #{hash_var2.k_zap}"
puts "Sr prob: #{hash_var2.sr_prob}"









