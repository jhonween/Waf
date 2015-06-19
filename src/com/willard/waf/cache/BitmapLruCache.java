/*
 * Copyright 2015 jhonween
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.willard.waf.cache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.willard.waf.network.toolbox.ImageLoader.ImageCache;

/**
 * @BitmapLruCache.java
 * @author willard
 * @2015-6-19
 */
public class BitmapLruCache extends LruCache<String,Bitmap> implements ImageCache {
	/** Get the maximum number of bytes the heap can expand to*/
	private static final int MAX_LRU_MEMORY = (int) Runtime.getRuntime().maxMemory();
    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }
    
    public BitmapLruCache() {
        super(MAX_LRU_MEMORY/4);
    }
    
    /**
     * Override the sizeOf
     * It mean that it will occupy how many numbers of bytes
     * default 1 
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
 
    @Override
    public Bitmap getBitmap(String url) {
        return (Bitmap)get(url);
    }
 
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
