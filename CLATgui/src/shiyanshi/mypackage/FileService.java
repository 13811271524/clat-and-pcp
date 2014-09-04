package shiyanshi.mypackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.os.Environment;

public class FileService {
	private Context context;

	public FileService(Context context) {
		this.context = context;
	}

	// openFileOutput方法的第一个参数用于指定文件名称，不能包含路径分割符“/”，
	// 参数二为对文件的访问权限，如果希望有多个权限，则写成如下形式：
	// Context.MODE_PRIVATE + Context.MODE_APPEND (注意是用“+”号)
	// 如果文件不存在，Android会自动创建它。
	// 创建的文件保存在/data/data/Activity所在的包/files目录，
	// 如/data/data/com.henii.android/files/myText.txt，
	/**
	 * 保存文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */

	// MODE_PRIVATE，为默认操作模式，代表该文件是私有数据，只能被应用本身访问，在该模式下，写入的内容会覆盖原文件的内容，
	// 如果想把新写入的内容追加到原文件中。
	// filename
	public void save(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	// MODE_APPEND，模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
	public void saveAppend(String filename, String content) throws Exception {// ctrl+shift+y/x
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_APPEND);
		outStream.write(content.getBytes());
		outStream.close();
	}

	public void saveReadable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	public void saveWriteable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	public void saveRW(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE + Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filename
	 *            文件名称
	 * @return 文件内容
	 * @throws Exception
	 */
	public String read(String filename) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		FileInputStream inStream = context.openFileInput(filename);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		byte[] data = outStream.toByteArray();
		return new String(data);
	}

	/**
	 * 把文件保存到SDCard上
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveToSDCard(String filename, String content) throws Exception {
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}

}
