#ifndef SKIP_LIST_H_GFIGbgYHGFjk
#define SKIP_LIST_H_GFIGbgYHGFjk

struct SkipList {
    struct Node **heads;
    size_t max_level;
    size_t max_height;
    int (*compare)(const void*, const void*);
};

struct Node {
    struct Node **next;
    size_t size;
    void *item;
};

/** 
* Generate a new skip-list given a pointer the max_height and the comnpare function
* The max_height for best performance has to be Log2(nitemes) or if the total size is not
* know bettere an height value like: 36 for 1000000 or 46 for 1000000000
*/
void new_skiplist(struct SkipList **list, size_t max_height, int (*compar)(const void*, const void*));

/**
* Remove all the memory allocated for the skip-list and all the pointers to the values
*/
void clear_skiplist(struct SkipList **list);

/** 
* Insert a new element into the data structure in the right place according the the 
* compare function given in the creation 
*/
void insert_skiplist(struct SkipList *list, void *item);

/** 
* Return the pointer of the element iff found otherwise return a NULL pointer
*/
const void* search_skiplist(struct SkipList *list, void *item);

#endif