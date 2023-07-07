#include <stdlib.h>
#include <stdio.h>
#include "skip_list.h"

static size_t random_level(size_t max_height);
static struct Node* create_node(void *item, size_t lvl);
static void clear_node(struct Node *node);

void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void*, const void*)) {
    if(max_height <= 0) {
        fprintf(stderr, "new_skiplist: the max_height must be greater then 0");
        exit(EXIT_FAILURE);
    }

    if(compar == NULL) {
        fprintf(stderr, "new_skiplist: the compar function can't be NULL");
        exit(EXIT_FAILURE);
    }

    *list = (struct SkipList *)malloc(sizeof(struct SkipList));
    if (*list == NULL) {
        fprintf(stderr, "new_skiplist: unable to allocate memory for creating new skiplist");
        exit(EXIT_FAILURE);
    }
    (*list)->max_height = max_height;
    (*list)->max_level = 1;
    (*list)->compare = compar;
    (*list)->heads = (struct Node **)malloc(sizeof(struct Node) * max_height);
    if ((*list)->heads == NULL) {
        fprintf(stderr, "create_node: unable to allocate memory for creating new heands");
        exit(EXIT_FAILURE);
    }
    
    for (size_t i = 0; i < max_height; i++) {
        (*list)->heads[i] = NULL;
    }
}

void clear_skiplist(struct SkipList **list) {
    if(list == NULL){
        fprintf(stderr, "clear_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    clear_node((*list)->heads[0]);
    free((*list)->heads);
    free((*list));
}

void insert_skiplist(struct SkipList *list, void *item) {
    if(list == NULL){
        fprintf(stderr, "insert_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(item == NULL){
        fprintf(stderr, "insert_skiplist: the item parameter is a null pointer");
        exit(EXIT_FAILURE);
    }   

    struct Node *newNode = create_node(item, random_level(list->max_height));
    if (newNode->size > list->max_level) {
        list->max_level = newNode->size;
    }
    
    struct Node **currentNode = list->heads;
    for (int i = (int)list->max_level - 1; i >= 0; i--) {
        if (currentNode[i] == NULL 
            || currentNode[i]->item == NULL 
            || list->compare(currentNode[i]->item, item) > 0) {
            if (newNode->size > (size_t)i) {
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
    if(list == NULL){
        fprintf(stderr, "search_skiplist: the list parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    if(item == NULL){
        fprintf(stderr, "search_skiplist: the item parameter is a null pointer");
        exit(EXIT_FAILURE);
    }

    struct Node **currentNode = list->heads;
    for (int i = (int)list->max_level - 1; i >= 0; i--) {
        while (currentNode[i] != NULL
                && currentNode[i]->item != NULL
                && list->compare(currentNode[i]->item, item) < 0) {
            currentNode = currentNode[i]->next;
        }
    }
    
    if (currentNode[0] != NULL && currentNode[0]->item == item) {
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
    if (newNode == NULL) {
        fprintf(stderr, "create_node: unable to allocate memory for creating new node");
        exit(EXIT_FAILURE);
    }

    newNode->item = item;
    newNode->size = lvl;
    struct Node **next = (struct Node **)malloc(sizeof(struct Node) * lvl);
    if (next == NULL) {
        fprintf(stderr, "create_node: unable to allocate memory for creating new nexts");
        exit(EXIT_FAILURE);
    }
    newNode->next = next;
    return newNode;
}

static void clear_node(struct Node *node) {
    if (node->next[0] != NULL) {
        clear_node(node->next[0]);
    }

    free(node->next);
    free(node);
}