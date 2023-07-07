CC = gcc
all: bin/skip_list bin/skip_list_tests #bin/sorted_array_collect_data_report

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

#bin/sorted_array_collect_data_report: build/merge_binary_insertion_sort.o build/sorted_array_collect_data_report.o $(COMMON_DEPS)
#	$(CC) -o bin/generate_report build/merge_binary_insertion_sort.o build/sorted_array_collect_data_report.o

bin/skip_list: build/skip_list.o build/skip_list_main.o $(COMMON_DEPS)
	$(CC) -o bin/main_ex2 build/skip_list.o build/skip_list_main.o

bin/skip_list_tests: build/skip_list_tests.o build/skip_list.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/skip_list_tests build/skip_list_tests.o build/skip_list.o build/unity.o

clean:
	rm -f build/* bin/*