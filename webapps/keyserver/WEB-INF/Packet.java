import java.net.*;
import java.io.*;

public class Packet
{
	public final int HEADER_SIZE = 20;
	
	public static final byte CMDTYPE_CHECK = 0x10;
	public static final byte CMDTYPE_OPEN = 0x20;
	public static final byte CMDTYPE_CLOSE = 0x21;
        byte[] packet;
	
	public byte[] SourceID;
	public byte[] DestinationID;
	public byte[] ServiceID;
	public byte[] Length[];
	public byte[] CRC16;
	public byte[] Data;
	public short nServiceID;
	public short nLength;
	

	public byte[] DoorCommand(byte cmdType, int doorNum, long serverid, InetSocketAddress addr)
	{
		byte[] dev = ParseByteArray.fromLong(doorNum);
		byte[] server = ParseByteArray.fromLong(serverid);
		byte[] service = ParseByteArray.fromShort((short)5000);
		byte[] cmd = new byte[] {0x02, cmdType, 0x00};
		byte[] len = ParseByteArray.fromShort((short)23);
		
		byte[] p = new byte[27];
		int i = 0;
		System.arraycopy(dev, 0, p, i, 8); i += 8;
		System.arraycopy(server, 0, p, i, 8); i += 8;
		System.arraycopy(service, 0, p, i, 2); i += 2;
		System.arraycopy(len, 0, p, i, 2); i += 2;
		System.arraycopy(cmd, 0, p, i, 3); i += 3;
		
		DatagramSocket socket = null;
		try
		{
			socket = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(p, p.length, addr);
			socket.send(dp);
			
			byte[] rec = new byte[1479];
			DatagramPacket rdp = new DatagramPacket(rec, rec.length);
			socket.receive(rdp);			
			
			if(cmdType == CMDTYPE_CHECK)
			{
				if(rdp.getLength() < 26)
                                	return null;
                        	byte[] data = rdp.getData();
                        	return new byte[] {data[23],data[24],data[25]};
			}
			else
			{
				if(rdp.getLength() < 23)
                                        return null;
                                byte[] data = rdp.getData();
                                return new byte[] {data[20],data[21],data[22]};
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(socket != null)
			{
				socket.close();
			}
		}
		return null;		
	}
	
	public byte[] SendCheckRequest(long devid, long serverid, InetSocketAddress addr)
	{
		byte[] dev = ParseByteArray.fromLong(devid);
		byte[] server = ParseByteArray.fromLong(serverid);
		byte[] service = ParseByteArray.fromShort((short)5000);
		byte[] cmd = new byte[] {0x02, 0x10, 0x00};
		byte[] len = ParseByteArray.fromShort((short)23);
		
		byte[] p = new byte[27];
		int i = 0;
		System.arraycopy(dev, 0, p, i, 8); i += 8;
		System.arraycopy(server, 0, p, i, 8); i += 8;
		System.arraycopy(service, 0, p, i, 2); i += 2;
		System.arraycopy(len, 0, p, i, 2); i += 2;
		System.arraycopy(cmd, 0, p, i, 3); i += 3;
		
		DatagramSocket socket = null;
		try
		{
			socket = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(p, p.length, addr);
			socket.send(dp);
			
			byte[] rec = new byte[1479];
			DatagramPacket rdp = new DatagramPacket(rec, rec.length);
			socket.receive(rdp);			
			if(rdp.getLength() < 26)
				return null;
			byte[] data = rdp.getData();
			return new byte[] {data[23],data[24],data[25]};
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(socket != null)
			{
				socket.close();
			}
		}
		return null;
	}
}
  