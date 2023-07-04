CC = gcc
all: bin/merge_binary_insertion_sort bin/sorted_array_tests

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/merge_binary_insertion_sort: build/merge_binary_insertion_sort.o build/sorted_array_main.o $(COMMON_DEPS)
	$(CC) -o bin/main_ex1 build/merge_binary_insertion_sort.o build/sorted_array_main.o

bin/sorted_array_tests: build/sorted_array_tests.o build/merge_binary_insertion_sort.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/sorted_array_tests build/sorted_array_tests.o build/merge_binary_insertion_sort.o build/unity.o

clean:
	rm -f build/* bin/*