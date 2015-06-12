package com.willard.waf.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.willard.waf.network.toolbox.ImageLoader.ImageCache;

/**
 * 
 * Include the BitmapLruCache and DiskBitmapCache,priority to use the BitmapLruCache and then the DiskBitmapCache.
 * @author willard
 * @2015-6-11
 */
public class BitmapCache implements ImageCache {
	/** Get the maximum number of bytes the heap can expand to*/
	private static final int MAX_LRU_MEMORY = (int) Runtime.getRuntime().maxMemory();
	/** The ratio of the MAX_LRU_MEMORY can allocate to the app */
	private static int mRatio=8;
	private DiskBitmapCache mDiskBitmapCache;
	private BitmapLruCache mBitmapLruCache;

	public BitmapCache(int maxLruSize, File rootDirectory, int maxDiskSize) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory, maxDiskSize);
	}

	public BitmapCache(int maxLruSize, File rootDirectory) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory);
	}

	public BitmapCache(int maxLruSize, Context context) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}

	public BitmapCache(Context context) {
		int mcacheSize = MAX_LRU_MEMORY/mRatio;
		mBitmapLruCache = new BitmapLruCache(mcacheSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}

	public BitmapCache(File rootDirectory) {
		// The maximum number of bytes the heap allocate to the app
		int mcacheSize = MAX_LRU_MEMORY/mRatio;
		mBitmapLruCache = new BitmapLruCache(mcacheSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory);
	}
	
	public BitmapCache( File rootDirectory,int maxDiskSize) {
		int mcacheSize = MAX_LRU_MEMORY/mRatio;
		mBitmapLruCache = new BitmapLruCache(mcacheSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory, maxDiskSize);
	}
	

	@Override
	public Bitmap getBitmap(String url) {
		if (mBitmapLruCache != null && mBitmapLruCache.getBitmap(url) != null) {
			System.out.println("######## BitmapCache GET ######## " + url);
			return mBitmapLruCache.getBitmap(url);
		}
		if (mDiskBitmapCache != null && mDiskBitmapCache.getBitmap(url) != null) {
			Bitmap bitmap = mDiskBitmapCache.getBitmap(url);
			if (mBitmapLruCache != null) {
				mBitmapLruCache.putBitmap(url, bitmap);
			}
			return bitmap;
		}
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		System.out.println("######## BitmapCache PUT ######## " + url);
		if (mBitmapLruCache != null) {
			mBitmapLruCache.putBitmap(url, bitmap);
		}
		if (mDiskBitmapCache != null) {
			mDiskBitmapCache.putBitmap(url, bitmap);
		}
	}

}
