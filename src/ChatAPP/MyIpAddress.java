package ChatAPP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;
//////////___________________________gives the ip address of local host in the form of string
public class MyIpAddress {

    public  String main() {
        // doPortForwarding();

        MyIpAddress myIpAddress = new MyIpAddress();

        // get default address
        String yourIp = myIpAddress.getYourIp(myIpAddress
                .getDefaultGateWayAddress());
       return yourIp;

        // get

    } // amin

    // return ip address for which u need to do port forwarding
    String getYourIp(String defaultAddress) {

        String temp = defaultAddress.substring(0, 11);
        String ipToForward = "";

        TreeSet<String> ipAddrs = getIpAddressList();
        for (Iterator<String> iterator = ipAddrs.iterator(); iterator.hasNext();) {

            String tempIp = iterator.next();
            if (tempIp.contains(temp)) {
                ipToForward = tempIp;
                break;
            }
        }

        return ipToForward;

    }// ipForPortForwarding

    // get the ipaddress list
    private TreeSet<String> getIpAddressList() {
        TreeSet<String> ipAddrs = new TreeSet<String>();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {

                    InetAddress addr = addresses.nextElement();

                    ipAddrs.add(addr.getHostAddress());

                }// 2 nd while
            }// 1 st while
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ipAddrs;

    }// getIpAddressList

    // get default gateway address in java

    String getDefaultGateWayAddress() {
        String defaultAddress = "";
        try {
            Process result = Runtime.getRuntime().exec("netstat -rn");

            BufferedReader output = new BufferedReader(new InputStreamReader(
                    result.getInputStream()));

            String line = output.readLine();
            while (line != null) {
                if (line.contains("0.0.0.0")) {

                    StringTokenizer stringTokenizer = new StringTokenizer(line);
                    stringTokenizer.nextElement();// first string is 0.0.0.0
                    stringTokenizer.nextElement();// second string is 0.0.0.0
                    defaultAddress = (String) stringTokenizer.nextElement(); // this
                                                                                // is
                                                                                // our
                                                                                // default
                                                                                // address
                    break;
                }

                line = output.readLine();

            }// while
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return defaultAddress;

    }// getDefaultAddress

}