#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include "gtthread.h"

// Constant
#define MAX_OP_COUNT    (1000)

// Global variables
int gnGlobal_Num_X;

// Predefine functions
void* HandleWrite(void *);
void* HandleRead(void *);

void ExitByError(char* sErrorMessage);

// Syncronization objects
gtthread_mutex_t mutex;// Synchronization object for preventing threads from access 'gnGlobal_Num' at the same time

int main (int argc, const char * argv[])
{
  gtthread_t* gtthreads_Write;
  gtthread_t* gtthreads_Read;
  
  gtthread_attr_t thread_attr;

  // Check out user arguments
  printf("\n======== New Test started! ========");
  printf("\nX is [%d] \n\n", gnGlobal_Num_X);
  
  // Initialize mutex
  if (0 != gtthread_mutex_init(&mutex))
    {
      ExitByError("Failed to initialize mutex");
    }
  
  // Initialize thread
  gtthread_attr_init(&thread_attr);
  
  // Allocate heap memory
  gtthreads_Write = (gtthread_t *)malloc(sizeof(gtthread_t));
  gtthreads_Read = (gtthread_t *)malloc(sizeof(gtthread_t));
  
  // Initialize heap memory
  memset(gtthreads_Write, '\0', sizeof(gtthread_t));
  memset(gtthreads_Read, '\0', sizeof(gtthread_t));
  

  // Create threads
  // 1) "Increase" thread
  if (0 != gtthread_create(gtthreads_Write, &thread_attr, HandleWrite, (void *)NULL))
    {
      ExitByError("Failed to create thread");
    }

  // 2) "Decrease" thread
  if (0 != gtthread_create(gtthreads_Read, &thread_attr, HandleRead, (void *)NULL))
    {
      ExitByError("Failed to create thread");
    }

  // Wait for threads to finish jobs
  if (0 != gtthread_join((gtthread_t)(*gtthreads_Read), 0))
    {
      ExitByError("Failed to join thread");
    }
  if (0 != gtthread_join((gtthread_t)(*gtthreads_Write), 0))
    {
      ExitByError("Failed to join thread");
    }

  printf("\n\n========== Test ended ==========\n\n");
  
  
  // Free allocated heap memories 
  free(gtthreads_Write);
  free(gtthreads_Read);
  
  exit(0);
}

// thread 1 - write
void* HandleWrite(void* pstArg)
{
  while (1)
    {
      gtthread_mutex_lock(&mutex);
      
        if (MAX_OP_COUNT == gnGlobal_Num_X)
		{
            printf("\n\n    ** HandleWrite() exit!");
            gtthread_mutex_unlock(&mutex);
			gtthread_exit(NULL);
        }        
        
      gnGlobal_Num_X++;
      printf("\nThread 1 writes X: [%d] ", gnGlobal_Num_X);
      
      gtthread_mutex_unlock(&mutex);
    }
  
  return 0;
}

// thread 2 - read
void* HandleRead(void* pstArg)
{
  while (1)
    {
      gtthread_mutex_lock(&mutex);
      
      printf("\n\tThread 2 reads X: [%d] ", gnGlobal_Num_X);
        
        if (MAX_OP_COUNT <= gnGlobal_Num_X)
		{
            printf("\n\n    ** HandleRead() exit!");
            gtthread_mutex_unlock(&mutex);
			gtthread_exit(NULL);
        }

      gtthread_mutex_unlock(&mutex);
    }
  
  return 0;
}

void ExitByError(char* sErrorMessage)
{
  fputs(sErrorMessage,stderr);
  fputc('\n',stderr);
  
  exit(1);
}
