package com.example.workschedule.utils

interface ConvertibleToDto<T> {
    fun toDTO():T
}