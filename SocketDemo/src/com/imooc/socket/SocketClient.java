package com.imooc.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.imooc.entity.File;
import com.imooc.entity.Users;
import com.imooc.service.FileService;
import com.imooc.service.UsersService;
import com.imooc.util.CommendTranser;

public class SocketClient {
	Scanner scan = new Scanner(System.in);
	private Socket socket = null;
	// Socket ss = new Socket("localhost", 1346);
	// Scanner scan = new Scanner(System.in);
	// String in = scan.next();
	// InputStream is = ss.getInputStream();
	// InputStreamReader isr = new InputStreamReader(is);
	// BufferedReader bfr=new BufferedReader(isr);
	// String info;
	// while((info=bfr.readLine())!=null){
	// System.out.println("���ǿͻ��� "+"\t"+"������˵"+info);
	// }
	//
	//
	// OutputStream os = ss.getOutputStream();
	// PrintWriter pw = new PrintWriter(os);
	// pw.write(in);

	public void showMainMenu() {
		System.out.println("******��ӭʹ��imooc�ϴ���*******");
		System.out.println("1 �û���¼  ��2 �û�ע�� ��3 �˳�");
		System.out.println("***************************");
		System.out.println("��ѡ�񣺡���������������");
		int choose = scan.nextInt();
		switch (choose) {
		case 1:
			showlogin();
			break;
		case 2:
			showzhuce();
			break;
		case 3:
			System.out.println("�ټ��ˣ���ը��");
			System.exit(0);
		default:
			System.out.println(" ��������");
			System.exit(0);
		}
	}

	public void showlogin() {
		Users users = new Users();
		System.out.println("��ӭʹ�õ�¼");
		CommendTranser transer = new CommendTranser();
		int count = 0;
		while (true) {
			if (count >= 3) {
				System.out.println("���Ѿ���������ʧ�ܣ��ټ�");
				System.exit(0);
			}
			System.out.println("�������û���");
			users.setUsername(scan.next());
			System.out.println("����������");
			users.setPassword(scan.next());
			transer.setCmd("login");
			transer.setData(users);
			count++;
			try {
				socket = new Socket("localhost", 1346);
				sendData(transer); 
				transer=getDate();
				System.out.println("   " + transer.getResult());
				System.out.println("***********************");
				if (transer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				clossAll();
			}

		}
		showUploadFile();

	}

	public void showzhuce() {
		Users users = new Users();
		System.out.println("��ӭʹ��ע��");
		CommendTranser transer = new CommendTranser();
		while (true) {
			System.out.println("�������û���");
			users.setUsername(scan.next());
			System.out.println(" ����������");
			users.setPassword(scan.next());
			System.out.println("���ٴ���������");
			String rePassword = scan.next();
			if (!users.getPassword().equals(rePassword)) {
				System.out.println("�������벻һ��");
				System.out.println("**************");
				continue;
			}
			transer.setCmd("zhuce");
			transer.setData(users);
			try {
				socket = new Socket("localhost", 1346);
				sendData(transer); 
				transer=getDate();
				System.out.println("  " + transer.getResult());
				System.out.println("***********************");
				if (transer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				clossAll();
			}
		}
		showUploadFile();
	}

	public void showUploadFile() {
		System.out.println("�������ϴ��ľ���·�� �磺 (e://imooc//dog.jpg)");
		String path = scan.next();

		File file = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		String fname = path.substring(path.lastIndexOf("/") + 1);
		try {
			fis = new FileInputStream(path);
			byte[] focntent = new byte[fis.available()];
			bis = new BufferedInputStream(fis);
			bis.read(focntent);
			file = new File(fname, focntent);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		CommendTranser transer = new CommendTranser();
		transer.setCmd("uploadFile");
		transer.setData(file);
		try {
			socket = new Socket("localhost", 1346);
			sendData(transer); 
			transer=getDate();
			System.out.println(transer.getResult());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clossAll();
		}
	}


	public CommendTranser sendData(CommendTranser transer) {
	    ObjectOutputStream oos=null;//���������
		    try {
		     oos=new ObjectOutputStream(socket.getOutputStream());
		     oos.writeObject(transer);
          	return transer;
		    } catch (IOException e) {
				e.printStackTrace();
			} 
	return null;
	}
	
	
	public CommendTranser  getDate(){
		ObjectInputStream ois =null;//����������
		CommendTranser transer = null;
		 try {
			ois=new ObjectInputStream(socket.getInputStream());
			transer= (CommendTranser) ois.readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return transer;
	}

	public void clossAll() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
