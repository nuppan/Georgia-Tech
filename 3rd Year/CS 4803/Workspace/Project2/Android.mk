LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_SRC_FILES := gtthreadut.c
LOCAL_SHARED_LIBRARIES := libc libgtthread
LOCAL_MODULE := gtthreadut
include $(BUILD_EXECUTABLE)
