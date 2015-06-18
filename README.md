## Waf简介
  Waf是一个开源的Android开发框架,其在网络请求和数据库操作、缓存、图片加载等方面进行了比较好的封装，并提供了大部分常用的 开发工具，Waf结合了当前一些开源框架(Volley和xUtils)中部分好的模块，并对这些模块进行了改进和优化，是一个性能极佳、可扩展性强的开发框架。
  
## Waf特点
  (1)提供封装了其他比较常用的工具，比如日志，图片，SharePreference，流处理工具。    
  (2)请求采用异步方式处理，并使用工作队列处理异步请求。   
  (3)扩展性强。框架大部分基于接口进行设计。   
  (4)提供了ORM框架。简化了对数据库的操作。<br/>
  (5)提供了常用的缓存方式。包含了内存缓存、文件缓存<br/>
## 目前Waf的主要模块
  * Cache模块：  
    * 提供了现成的几个缓存类和工具类，可随意选择所需的缓存，包括内存缓存和文件缓存;  
    * 包含了网络缓存和图片缓存接口，通过实现该接口可自定义自己的缓存类，用作网络请求的缓存类。     
  * NetWork模块：   
    * 请求采用异步方式处理，并使用工作队列处理异步请求；   
    * 使用了CacheDispatcher和NetworkDispatcher分别对缓存请求和网络请求进行分发处理；   
    * 提供了多种请求方式，如StringRequest、JsonRequest、ImageRequest对不同类型的请求进行处理；   
    * 扩展性强。框架大部分基于接口进行设计。   
  * Db模块:   
    * 提供了ORM框架。简化了对数据库的操作；  
    * 提供了注解对表进行定义，自动创建表和数据库；      
    * 支持延时加载；      
    * 支持事务处理；     
    * 支持各数据库操作。        
  * Exception模块：     
    * 提供了各类运行时异常和非运行时异常基类和子类，用户可直接使用该类或扩展该类进行使用。<br/>  

## 使用Waf开发框架需要有以下权限：
  ```xml
  <uses-permission android:name="android.permission.INTERNET" />
  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
  <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
  ```
## 混淆时注意事项：
 * 添加Android默认混淆配置${sdk.dir}/tools/proguard/proguard-android.txt
 * 不要混淆注解类型，添加混淆配置：-keep class * extends java.lang.annotation.Annotation { *; }
 * 对使用Db模块持久化的实体类不要混淆，或者注解所有表和列名称@Table(name="xxx")，@Id(column="xxx")，@Column(colum       n="xxx"),@Foreign(column="xxx",foreign="xxx")；  
 

## Cache缓存模块
  * 以图片缓存举例，自定义MyBitmapLruCache
 ```java
public class MyBitmapLruCache implements ImageCache {
	/** Get the maximum number of bytes the heap can expand to*/
	private static final int MAX_LRU_MEMORY = (int) Runtime.getRuntime().maxMemory();
	/** The ratio of the MAX_LRU_MEMORY can allocate to the app */
	private static int mRatio=8;
	private DiskBitmapCache mDiskBitmapCache;
	private BitmapLruCache mBitmapLruCache;

	public MyBitmapLruCache(int maxLruSize, File rootDirectory, int maxDiskSize) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory, maxDiskSize);
	}

	public MyBitmapLruCache(int maxLruSize, File rootDirectory) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(rootDirectory);
	}

	public MyBitmapLruCache(int maxLruSize, Context context) {
		mBitmapLruCache = new BitmapLruCache(maxLruSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}

	public MyBitmapLruCache(Context context) {
		int mcacheSize = MAX_LRU_MEMORY/mRatio;
		mBitmapLruCache = new BitmapLruCache(mcacheSize);
		mDiskBitmapCache = new DiskBitmapCache(context.getCacheDir());
	}
	...

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
  ```
  *  使用Cache 
  ```java
	 /**使用自定义Cache  */ 
         mImageLoader = new ImageLoader(mVolleyQueue,
			  new MyBitmapLruCache(this));
	 //使用框架缓存工具类CacheUtil获取Cache
	mImageLoader = new ImageLoader(mVolleyQueue,
				CacheUtil.getBitmapCacheInstance(this));
	//使用框架缓存类
	int mcacheSize = 99999;
	mImageLoader = new ImageLoader(mVolleyQueue,
				new BitmapLruCache(mcacheSize) );
        ```
  
  
## Network模块使用方法
## Db模块使用方法
## 其他工具使用方法
## 关于 免责声明 感谢 联系方式 q群 

