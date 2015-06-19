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

import java.io.File;

import android.content.Context;

/**
 * @CacheUtil.java
 * @author willard
 * @2015-6-12
 */
public class CacheUtil {
	private static BitmapCache mBitmapCacheInstance;
	// private static BitmapLruCache mBitmapLruCacheInstance;
	private static DiskBitmapCache mDiskBitmapCacheInstance;
	// private static NoCache mNoCacheInstance;
	private static DiskBasedCache mDiskBasedCache;

	private CacheUtil() {

	}

	// private static class InstanceHolder {
	// private final static CacheUtil instance = new CacheUtil();
	// }

	/**
	 * Lazy singleton
	 * 
	 * @CacheUtil.java
	 * @author willard
	 * @2015-6-19
	 */
	private static class NoCacheInstanceHolder {
		private final static NoCache instance = new NoCache();
	}

	// public static CacheUtil getInstance() {
	// return InstanceHolder.instance;
	// }

	public static NoCache getNoCacheInstance() {
		return NoCacheInstanceHolder.instance;

	}

	public static DiskBasedCache getDiskBasedCacheInstance(File cacheDir) {
		if (mDiskBasedCache == null) {
			synchronized (DiskBasedCache.class) {
				DiskBasedCache temp = mDiskBasedCache;
				if (temp == null) {
					temp = new DiskBasedCache(cacheDir);
					mDiskBasedCache = temp;
				}
			}
		}
		return mDiskBasedCache;
	}

	/**
	 * Double Lock singleton
	 * 
	 * @param context
	 * @return
	 */
	public static BitmapCache getBitmapCacheInstance(Context context) {
		if (mBitmapCacheInstance == null) {
			synchronized (BitmapCache.class) {
				BitmapCache temp = mBitmapCacheInstance;
				if (temp == null) {
					temp = new BitmapCache(context);
					mBitmapCacheInstance = temp;
				}
			}
		}
		return mBitmapCacheInstance;
	}

	private static class BitmapLruCacheInstanceHolder {
		private final static BitmapLruCache instance = new BitmapLruCache();
	}

	public static BitmapLruCache getBitmapLruCacheInstance() {
		return BitmapLruCacheInstanceHolder.instance;
	}

	public static DiskBitmapCache getDiskBitmapCacheInstance(File cacheDir) {
		if (mDiskBitmapCacheInstance == null) {
			synchronized (DiskBitmapCache.class) {
				DiskBitmapCache temp = mDiskBitmapCacheInstance;
				if (temp == null) {
					temp = new DiskBitmapCache(cacheDir);
					mDiskBitmapCacheInstance = temp;
				}
			}
		}
		return mDiskBitmapCacheInstance;
	}

}
