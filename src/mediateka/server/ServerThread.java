package mediateka.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import mediateka.Commands;
import mediateka.datamanagers.Manager;
import mediateka.datamanagers.Managers;
import mediateka.db.BlackListRecord;
import mediateka.db.Disc;
import mediateka.db.Film;
import mediateka.db.HistoryRecord;
import mediateka.db.Person;
import mediateka.db.Record;
import mediateka.db.Records;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

public class ServerThread extends Thread {

    private final Synchronizer s;
    private final Socket client;

    public ServerThread(Socket socket, Synchronizer sync) {
        super();
        s = sync;
        client = socket;
    }

    void notifyClient() {
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(client.getOutputStream());
            output.writeInt(1);
            output.write(Commands.ServerCommands.NOTIFY.ordinal());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        DataInputStream input = null;
        DataOutputStream output = null;
        try {
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
            while (true) {
                if (input.available() > 0) {
                    synchronized (s) {
                        try {
                            if (s.isLocked()) {
                                s.wait();
                            }
                            s.setLocked(true);
                            byte[] b = new byte[input.readInt()];
                            input.read(b);
                            Inflater decomp = new Inflater();
                            decomp.setInput(b);
                            byte[] buffer = new byte[b.length * 3];
                            int len = decomp.inflate(buffer);
                            decomp.end();
                            Manager m = null;
                            Record r = null;
                            byte t = buffer[1];
                            if (len > 2) {
                                Document doc = DocumentHelper.parseText(new String(buffer, "UTF-8"));
                                switch (t) {
                                    case 1:
                                        m = Managers.getInstance().getBlListManager();
                                        r = new BlackListRecord(
                                                (DefaultElement) doc.getRootElement());
                                        break;
                                    case 2:
                                        m = Managers.getInstance().getDiscsManager();
                                        r = new Disc(
                                                (DefaultElement) doc.getRootElement());
                                        break;
                                    case 3:
                                        m = Managers.getInstance().getFilmsManager();
                                        r = new Film(
                                                (DefaultElement) doc.getRootElement());
                                        break;
                                    case 4:
                                        m = Managers.getInstance().getHistManager();
                                        r = new HistoryRecord(
                                                (DefaultElement) doc.getRootElement());
                                        break;
                                    case 5:
                                        m = Managers.getInstance().getPersManager();
                                        r = new Person(
                                                (DefaultElement) doc.getRootElement());
                                        break;
                                    default:
                                }
                            }
                            Commands.ServerCommands out = Commands.ServerCommands.ERR;
                            Element elem = null;
                            switch (Commands.ClientCommands.values()[buffer[0]]) {
                                case ADD:
                                    out = m.add(r) ? Commands.ServerCommands.OK
                                            : Commands.ServerCommands.ERR_ADD;
                                    break;
                                case DELETE:
                                    out = m.delete(r.getID()) ? Commands.ServerCommands.OK
                                            : Commands.ServerCommands.ERR_DEL;
                                    break;
                                case FIND:
                                    Records rs = m.find(r);
                                    if (rs.size() > 0) {
                                        elem = rs.toXmlElement();
                                    }
                                    out = (elem != null) ? Commands.ServerCommands.OK
                                            : Commands.ServerCommands.ERR_FIND;
                                    break;
                                case FIND_BY_ID:
                                    out = ((elem = m.find(r.getID()).toXmlElement()) != null) ? Commands.ServerCommands.OK
                                            : Commands.ServerCommands.ERR_FIND;
                                    break;
                                case UPDATE:
                                    out = m.edit(r) ? Commands.ServerCommands.OK
                                            : Commands.ServerCommands.ERR_UPD;
                                    break;
                                case QUIT:
                                    return;
                                default:
                                    out = Commands.ServerCommands.ERR_UNRECOGNIZED;
                                    break;
                            }
                            String str = elem == null ? "" : elem.asXML();
                            b = str.getBytes("UTF-8");
                            buffer = new byte[b.length + 2];
                            buffer[0] = (byte) out.ordinal();
                            buffer[1] = t;
                            System.arraycopy(b, 0, buffer, 2, b.length);
                            b = new byte[buffer.length * 2];
                            Deflater compr = new Deflater();
                            compr.setInput(buffer);
                            compr.finish();
                            len = compr.deflate(b);
                            output.writeInt(len);
                            output.write(b, 0, len);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            s.setLocked(false);
                            s.notify();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
