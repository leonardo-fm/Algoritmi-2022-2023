#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static size_t random_level(size_t max_height);
static struct Node* create_node(void *item, size_t lvl);
static void clear_node(struct Node *node);

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
    for (int i = 0; i < list->max_level - 1; i++) {
        clear_node(list->heads[i]);
    }
    free(list);
}

void insert_skiplist(struct SkipList *list, void *item) {
    struct Node *newNode = create_node(item, random_level(list->max_height));
    if (newNode->size > list->max_level) {
        list->max_level = newNode->size;
    }

    struct Node **currentNode = list->heads;
    for (int i = list->max_level - 1; i >= 0; i--) {
        if (currentNode[i] == NULL || currentNode[i]->item >= item) {
            if (newNode->size >= i) {
                newNode->next[i] = currentNode[i];
                currentNode[i] = newNode;
            }          
        } else {
            currentNode = currentNode[i]->next;
            i++;
        }
    } 
}

const void* search_skiplist(struct SkipList *list, void *item) {
    struct Node **currentNode = list->heads;
    for (int i = list->max_level - 1; i >= 0; i--) {
        while (currentNode[i]->next[i]->item != NULL
            && list->compare(currentNode[i]->next[i]->item, item) <= 0) {
            currentNode = currentNode[i]->next;
        }
    }
    
    if (currentNode[0]->item == item) {
        return currentNode[0]->item;
    } else {
        return NULL;
    }
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

static void clear_node(struct Node *node) {
    if (node->next != NULL) {
        clear_node(node->next);
    }
    free(node);
}
