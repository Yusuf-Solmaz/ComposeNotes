/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bluromatic

// Notification Channel constants

// Name of Notification Channel for verbose notifications of background work
val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
    "Verbose WorkManager Notifications"
const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
    "Shows notifications whenever work starts"
val NOTIFICATION_TITLE: CharSequence = "WorkRequest Starting"
const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
const val NOTIFICATION_ID = 1

// The name of the image manipulation work
const val IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work"

// Other keys
const val OUTPUT_PATH = "blur_filter_outputs"
const val KEY_IMAGE_URI = "KEY_IMAGE_URI"
const val WORKER_ERROR = "WORKER_ERROR"
const val TAG_OUTPUT = "OUTPUT"
const val KEY_BLUR_LEVEL = "KEY_BLUR_LEVEL"

const val DELAY_TIME_MILLIS: Long = 3000

const val STOP_TIMEOUT_MILLIS: Long = 5000
