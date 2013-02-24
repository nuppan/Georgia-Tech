#include "gtthread.h"
#include <stdio.h>

#define DEFAULT_STACKSIZE (1024 * 1024)

static const default_gtthread_attr = {
  .stack_base = NULL,
  .flags = 0,
  .stack_size = DEFAULT_STACKSIZE
};

int gtthread_attr_init(gtthread_attr_t * attr){
  *attr = default_gtthread_attr;
  return 0;
}

int gtthread_create(gtthread_t *thread, gtthread_attr_t const * attr,
               void *(*start_routine)(void *), void * arg)
{
  //allocate memory for the thread and initialize to 0
  gtthread_t* gt_thread = calloc(sizeof(*gt_thread),1);
  if(gt_thread==NULL){
    return -1;
  }
	
  //check the thread attribute object
  if(attr==NULL){
    *attr = default_gtthread_attr;
  }

  //create or retrieve a new stack for the thread if NULL
  size_t stack_size = attr->stack_size;
  uint8_t stack_base = attr->stack_base;
  if(stack_base==NULL){
    stack_base = malloc(stack_size);
	if(stack_base==NULL){ //check for unsuccessful malloc
	  return -1;
	}
  }
  stack_base = stack_base + stack_size;
  int flags = CLONE_FILES | CLONE_FS | CLONE_VM | CLONE_SIGHAND |
                  CLONE_THREAD | CLONE_SYSVSEM | CLONE_DETACHED;

  int tid = __pthread_clone((int(*)(void*))start_routine, stack_base, flags, arg);
  if(tid<0){ //check for unsuccessful clone
    return -1;
  }
  int init_errno = _init_thread(thread, tid, (pthread_attr_t*) attr, stack_base, 1);

  return 0;
}

int gtthread_join(gtthread_t thid, void ** ret_val){
  return 0;
}

void gtthread_exit(void *ret_val){

}

int gtthread_mutex_init(gtthread_mutex_t *mutex){
  mutex->in_use = 0;
  return 0;
}

int gtthread_mutex_lock(gtthread_mutex_t *mutex){
  while(1){
    if(mutex->in_use==0){
      return 0;
	}
  }
  return 0;
}

int gtthread_mutex_unlock(gtthread_mutex_t *mutex){
  mutex->in_use=0;
  return 0;
}
