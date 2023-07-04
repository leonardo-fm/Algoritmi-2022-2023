#include <stdio.h>
#include <stdlib.h>
#include "unity.h"
#include "merge_binary_insertion_sort.h"

//precedence relation used in tests
static int compar_int(const void *val1, const void *val2) {
    if (*(int *)val1 > *(int *)val2)
        return 1;
    else if (*(int *)val1 < *(int *)val2)
        return -1;
    else
        return 0;
}

//Data elements that are initialized before each test
static int *array;
static size_t nitems;
static size_t size;

void setUp(void){
    //{0, 5, 4, 3, 2, 1, 0}
    array = (int *)malloc(sizeof(int) * 7);
    array[0] = 0;
    array[1] = 5;
    array[2] = 4;
    array[3] = 3;
    array[4] = 2;
    array[5] = 1;
    array[6] = 0;

    nitems = 7;
    size = sizeof(int);
}

void tearDown(void){
  free(array);
}

static void test_merge_sort_array() {
    merge_binary_insertion_sort(array, nitems, size, 0, compar_int);
    int expArray[7] = {0, 0, 1, 2, 3, 4, 5};
    TEST_ASSERT_EQUAL_INT_ARRAY(expArray, array, nitems);
}

static void test_binary_insertion_sort_array() {
    merge_binary_insertion_sort(array, nitems, size, 1000, compar_int);
    int expArray[7] = {0, 0, 1, 2, 3, 4, 5};
    TEST_ASSERT_EQUAL_INT_ARRAY(expArray, array, nitems);
}

static void test_merge_sort_array_one_elent() {
    int singleArray[1] = {8}; 
    merge_binary_insertion_sort(singleArray, 1, size, 0, compar_int);
    int expArray[1] = {8};
    TEST_ASSERT_EQUAL_INT_ARRAY(expArray, singleArray, 1);
}

static void test_binary_insertion_sort_array_single_array() {
    int singleArray[1] = {8};
    merge_binary_insertion_sort(singleArray, 1, size, 1000, compar_int);
    int expArray[1] = {8};
    TEST_ASSERT_EQUAL_INT_ARRAY(expArray, singleArray, 1);
}

int main(void) {

  //test session
  UNITY_BEGIN();
  
  RUN_TEST(test_merge_sort_array);
  RUN_TEST(test_binary_insertion_sort_array);
  RUN_TEST(test_merge_sort_array_one_elent);
  RUN_TEST(test_binary_insertion_sort_array_single_array);
  
  return UNITY_END();
}

