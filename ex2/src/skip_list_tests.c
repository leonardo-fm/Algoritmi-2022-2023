#include <stdio.h>
#include <stdlib.h>
#include "unity.h"
#include "skip_list.h"

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
int i1, i2, i3;
struct SkipList *skipList; 

void setUp(void){
    i1 = 10;
    i2 = 50;
    i3 = -5;

    new_skiplist(&skipList, 5, compar_int);
}

void tearDown(void){
  clear_skiplist(&skipList);
}

static void test_insert() {
    insert_skiplist(skipList, &i1);
    insert_skiplist(skipList, &i2);
    insert_skiplist(skipList, &i3);
    TEST_ASSERT_TRUE(search_skiplist(skipList, &i1));
}

static void test_insert_all_equal() {
    insert_skiplist(skipList, &i1);
    insert_skiplist(skipList, &i1);
    insert_skiplist(skipList, &i1);
    TEST_ASSERT_TRUE(search_skiplist(skipList, &i1));
}

static void test_search() {
    insert_skiplist(skipList, &i1);
    insert_skiplist(skipList, &i2);
    insert_skiplist(skipList, &i3);
    TEST_ASSERT_NOT_NULL(search_skiplist(skipList, &i2));
}

static void test_search_not_found() {
    insert_skiplist(skipList, &i1);
    insert_skiplist(skipList, &i2);
    TEST_ASSERT_NULL(search_skiplist(skipList, &i3));
}

// For some reason even if the result is null the test crash the suit of tests
static void test_search_not_found_empty() {
    TEST_ASSERT_NULL(search_skiplist(skipList, &i1));
}

int main(void) {

  //test session
  UNITY_BEGIN();
  
  RUN_TEST(test_insert);
  RUN_TEST(test_insert_all_equal);
  RUN_TEST(test_search);
  RUN_TEST(test_search_not_found);
  RUN_TEST(test_search_not_found_empty);
  
  return UNITY_END();
}

