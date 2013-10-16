require 'open-uri'
require 'nokogiri'
require 'benchmark'

def read_file	
	IO.read(File.dirname(__FILE__) + "/cities.txt")
end

def fetch_xml(id)
	open("http://weather.yahooapis.com/forecastrss?w=#{id.chomp}&u=f")
end
	
def parse_xml(raw_xml)
	source = Nokogiri::XML(raw_xml)
	location = source.xpath("//yweather:location")
	condition = source.xpath("//item//yweather:condition")
	forecast = source.xpath("//item//yweather:forecast")
	[location[0]['region'], location[0]['city'], condition[0]['temp'].to_i, condition[0]['text'], forecast[0]['high'], forecast[0]['low']]
end

def fetch_result(city_list)
	weather = Hash.new
	max_city = Array.new
	min_city = Array.new
	city_list.each do |id|
		city_weather = parse_xml(fetch_xml(id))
		max_city = max(max_city, city_weather)
		min_city = min(min_city, city_weather)
		weather[[city_weather[0], city_weather[1]]] = [city_weather[2], city_weather[3], city_weather[4], city_weather[5]]
	end
	[weather.sort, max_city, min_city]
end

def multithread_fetch_result(city_list)
	weather = Hash.new
	max_city = Array.new
	min_city = Array.new
	mutex = Mutex.new
	thread_list = []
	city_list.each_with_index do |id, index|
		thread_list[index] = Thread.new {
			city_weather = parse_xml(fetch_xml(id))
			mutex.synchronize do
				max_city = max(max_city, city_weather)
				min_city = min(min_city, city_weather)
				weather[[city_weather[0], city_weather[1]]] = [city_weather[2], city_weather[3], city_weather[4], city_weather[5]]
			end
		}
	end
	thread_list.map {|t| t.join}
	[weather.sort, max_city, min_city]
end

def max(max_city, city_weather)
	max_city.empty? || max_city[2] < city_weather[2] ? city_weather : max_city
end

def min(min_city, city_weather)
	min_city.empty? || min_city[2] > city_weather[2] ? city_weather : min_city
end

def display_result(weather, max_city, min_city)	
	puts "State \t City \t\t High \t Low \t Current Condition"
	weather.each do |k, v|
	  print("#{k[0]} \t #{k[1]} \t #{v[2]} \t #{v[3]} \t #{v[0]} \t #{v[1]}\n")
	end
	puts "The city with the highest temperature:"
	print("#{max_city[0]} \t #{max_city[1]} \t #{max_city[4]} \t #{max_city[5]} \t #{max_city[2]} \t #{max_city[3]} \n")
	puts "The city with the lowest temperature:"
	print("#{min_city[0]} \t #{min_city[1]} \t #{min_city[4]} \t #{min_city[5]} \t #{min_city[2]} \t #{min_city[3]} \n")
end

city_list = read_file.split(",")
result = Array.new
print("\nSingle thread:\n")
total_time = Benchmark.realtime{ result = fetch_result(city_list) }
print("Total time is #{total_time}\n\n")
display_result(result[0], result[1], result[2])

print("\nMultithread:\n")
total_time = Benchmark.realtime{ result = multithread_fetch_result(city_list) }
print("Total time is #{total_time}\n\n")
display_result(result[0], result[1], result[2])