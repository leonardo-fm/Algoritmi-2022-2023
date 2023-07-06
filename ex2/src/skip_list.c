#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static size_t random_level(size_t max_height);
static struct Node* create_node(void *item, size_t lvl);

void new_skiplist(struct SkipList *list, size_t max_height, int (*compar)(const void*, const void*)) {
    if(max_height > 0) {
        fprintf(stderr, "new_skiplist: the max_height must be greater then 0");
        exit(EXIT_FAILURE);
    }

    if(compar == NULL) {
        fprintf(stderr, "new_skiplist: the compar function can't be NULL");
        exit(EXIT_FAILURE);
    }

    list = (struct SkipList *)malloc(sizeof(struct SkipList));
    list->max_height = max_height;
    list->compare = compar;
}

void clear_skiplist(struct SkipList *list) {
    
}

void insert_skiplist(struct SkipList *list, void *item) {

    struct Node *newNode = create_node(item, random_level(list->max_height));
    if (newNode->size > list->max_level) {
        list->max_level = newNode->size;
    }

    struct Node **currentNode = list->heads;
    for (int k = list->max_level - 1; k >= 0; k--) {
        if (currentNode[k] == NULL || item < currentNode[k]->item) {
            if (k < newNode->size) {
                newNode->next[k] = currentNode[k];
                currentNode[k] = newNode;
            } else {
                currentNode = currentNode[k]->next;
                k++;
            }
        }
    } 

    /*
    insertSkipList(list, item):
    new = createNode(item, randomLevel(list->max_height))
    if new->size > list->max_level:
        list->max_level = new->size

    x = list->heads
    for k = list->max_level downto 1:
        if x[k] == NULL or item < x[k]->item:
            if k < new->size:
              new->next[k] = x[k]
              x[k] = new
        else:
            x = x[k]->next
            k++
     */
}

const void* search_skiplist(struct SkipList *list, void *item) {
    struct SkipList *currentList = list;
    struct Node **currentNode = currentList->heads;
    for (int i = currentList->max_level - 1; i >= 0; i--) {
        while (currentNode[i]->next[i]->item != NULL
            && currentList->compare(currentNode[i]->next[i]->item, item) <= 0) {
            currentNode = currentNode[i]->next;
        }
    }
    
    if (currentNode[1]->item == item) {
        return currentNode[1]->item;
    } else {
        return NULL;
    }
    /*
    searchSkipList(list, item):
    x = list->heads

    // loop invariant: x[i]->item <= item or item < first element of level i in list
    for i = list->max_level downto 1:
        while x[i]->next[i] != NULL and x[i]->next[i]->item <= item:
            x = x[i]->next

    // loop end: x[1]->item <= item or item < first element in list
    if x[1]->item == item then
        return x[1]->item
    else
        return failure
    */
}

static size_t random_level(size_t max_height) {
    size_t lvl = 1;
    while ((double)rand() / (double)RAND_MAX < 0.5 && lvl < max_height) {
        lvl++;
    }
    return lvl;
}

static struct Node* create_node(void *item, size_t lvl) {
    struct Node *newNode = (struct Node *)malloc(sizeof(struct Node));
    newNode->item = item;
    newNode->size = lvl;
    return newNode;
}