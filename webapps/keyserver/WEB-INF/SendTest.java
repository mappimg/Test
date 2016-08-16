import java.net.*;
import java.io.*;

public class SendTest
{
	public static void main(String args[])
	{
		Packet p = new Packet();
		InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 22226);
		p.SendCheckRequest((long)17185, 112233, addr);
	}
}