#1
def name_average(names)
  total = 0.0
  names.each{|x| total = total + x.length}
  total/names.length
end

names = 'Amar', 'Ananta', 'Camilo', 
'Christopher', 'Cody', 'Dylan',
'Hieu', 'Jesus', 'Jose', 'Joseph', 
'Josue', 'Kayla', 'Mary', 'Michael',
'Oscar', 'Owais', 'Ryan', 'Suman', 'Varun', 'Venkat', 'Wei'

puts "#{name_average(names)}"


#2
def count_first_letter_in_name(names)

  first_letter = []
  names.each do |i|
    first_letter.push(i.chars.first)
  end

  counter = Hash.new(0)
  first_letter.each{|i| counter[i]+=1}

  counter.each do |x, y|
    print "#{x}=#{y}\n"

  end
end

count_first_letter_in_name(names)