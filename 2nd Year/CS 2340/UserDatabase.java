package android.manager.database;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class UserDatabase{
	public static final String KEY_NAME = "user_name";
	public static final String KEY_PASSWORD = "user_password";
	public static final String KEY_ROWID = "_id";
	 
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final String DATABASE_NAME = "User_database";
	private static final String DATABASE_TABLE = "User";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE =
			"create table " + DATABASE_TABLE + " (" + KEY_ROWID + " integer primary key autoincrement, "
	    + KEY_NAME +" text not null, " + KEY_PASSWORD + " text not null);";
	 
	private final Context mCtx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		    onCreate(db);
		}
		
	}
	
	public UserDatabase(Context ctx) {
	    this.mCtx = ctx;
	  }
	 
	  public UserDatabase open(){
	
	    mDbHelper = new DatabaseHelper(mCtx);
	    mDb = mDbHelper.getWritableDatabase();
	    return this;
	  }
	 
	  public void close() {
	    mDbHelper.close();
	  }
	 
	  public long createUser(String userName, String userPassWord) {
	    ContentValues initialValues = new ContentValues();
	    initialValues.put(KEY_NAME, userName);
	    initialValues.put(KEY_PASSWORD, userPassWord);
	  
	 
	    return mDb.insert(DATABASE_TABLE, null, initialValues);
	  }
	 
	  public boolean deleteUsers(long rowId) {
	 
	    return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	  }
	 
	  public Cursor fetchAllUsers() {
	 
	    return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
	        KEY_PASSWORD}, null, null, null, null, null);
	  }
	 
	  public Cursor fetchUser(long userId) throws SQLException {
	 
	    Cursor mCursor =
	 
	    	mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
	          KEY_NAME, KEY_PASSWORD}, KEY_ROWID + "=" + userId, null,
	          null, null, null, null);
	    if (mCursor != null) {
	      mCursor.moveToFirst();
	    }
	    return mCursor;
	 
	  }
	 
	  public boolean updateUser(int userId, String userName, String userPassWord, String userEmail) {
	    ContentValues args = new ContentValues();
	    args.put(KEY_NAME, userName);
	    args.put(KEY_PASSWORD, userPassWord);
	 
	    return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + userId, null) > 0;
	  }
	
	
}