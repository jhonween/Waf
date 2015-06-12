package com.willard.waf.cache;

import java.io.File;

import android.content.Context;

/**
 * @CacheUtil.java
 * @author willard
 * @2015-6-12
 */
public class CacheUtil {
	public final static CacheUtil instance = new CacheUtil();
	private static BitmapCache mBitmapCacheInstance;
	private static BitmapLruCache mBitmapLruCacheInstance;
	private static DiskBitmapCache mDiskBitmapCacheInstance;
	private static NoCache mNoCacheInstance;
	private static DiskBasedCache mDiskBasedCache;
	private CacheUtil()
	{
		
	}
	public static CacheUtil getInstance()
	{
		return CacheUtil.instance;
	}
	
	public static NoCache getNoCacheInstance()
	{
		if (mNoCacheInstance == null)
		{
			mNoCacheInstance = new NoCache();
		}
		return mNoCacheInstance;
	}
	
	public static DiskBasedCache getDiskBasedCacheInstance(File cacheDir)
	{
		if (mDiskBasedCache == null)
		{
			mDiskBasedCache = new DiskBasedCache(cacheDir);
		}
		return mDiskBasedCache;
	}
	
	public static DiskBasedCache getDiskBasedCacheInstance(File cacheDir, int maxCacheSizeInBytes)
	{
		if (mDiskBasedCache == null)
		{
			mDiskBasedCache = new DiskBasedCache(cacheDir,maxCacheSizeInBytes);
		}
		return mDiskBasedCache;
	}
	
	public static BitmapCache getBitmapCacheInstance(Context context)
	{
		if (mBitmapCacheInstance == null)
		{
			mBitmapCacheInstance = new BitmapCache(context);
		}
		return mBitmapCacheInstance;
	}
	
	public static BitmapLruCache getBitmapLruCacheInstance(int maxSize)
	{
		if (mBitmapLruCacheInstance == null)
		{
			mBitmapLruCacheInstance = new BitmapLruCache(maxSize);
		}
		return mBitmapLruCacheInstance;
	}
	
	public static DiskBitmapCache getDiskBitmapCacheInstance(File cacheDir)
	{
		if (mDiskBitmapCacheInstance == null)
		{
			mDiskBitmapCacheInstance = new DiskBitmapCache(cacheDir);
		}
		return mDiskBitmapCacheInstance;
	}
	
	public static DiskBitmapCache getDiskBitmapCacheInstance(File cacheDir, int maxCacheSizeInBytes)
	{
		if (mDiskBitmapCacheInstance == null)
		{
			mDiskBitmapCacheInstance = new DiskBitmapCache(cacheDir,maxCacheSizeInBytes);
		}
		return mDiskBitmapCacheInstance;
	}
	
}
