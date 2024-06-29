package modules.snmp;

import modules.snmp.data.SnmpValue;
import modules.snmp.enums.SnmpVersion;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SnmpUtil {
    private static final int SNMP_PORT = 161;

    public static <T> String get(String ip, int version, String community, T oid, int retries, int timeout) {
        version = snmpVersionConverter(version);

        CommunityTarget header = makeHeader(ip, version, community, retries, timeout);
        PDU pdu = makePdu(oid, PDU.GET);
        Snmp snmp = null;
        ResponseEvent response;
        String result = null;
        try {
            snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();
            response = snmp.send(pdu, header);
            if (response.getResponse() != null) {
                PDU resPDU = response.getResponse();
                result = resPDU.getVariableBindings().firstElement().toString();
            }
            snmp.close();
        } catch (IOException e) {
            try {
                if (snmp != null) {
                    snmp.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return result;
    }


    public static <T> List<SnmpValue> snmpWalk(String ip, int version, String community, T oid, int retries, int timeout){
        version = snmpVersionConverter(version);

        CommunityTarget header = makeHeader(ip, version, community, retries, timeout);
        Snmp snmp = null;
        List<SnmpValue> result = new ArrayList<>();
        try {
            snmp = new Snmp(new DefaultUdpTransportMapping());
            snmp.listen();

            List<TreeEvent> events;
            if(oid instanceof int[]){
                int[] oidVal = (int[]) oid;
                events = new TreeUtils(snmp, new DefaultPDUFactory()).getSubtree(header, new OID(oidVal));
            }else{
                String oidVal = oid.toString();
                events = new TreeUtils(snmp, new DefaultPDUFactory()).getSubtree(header, new OID(oidVal));
            }

            if(events != null && !events.isEmpty()){
                for(TreeEvent event:events){
                    VariableBinding[] variableBindings = event.getVariableBindings();
                    if(variableBindings != null){
                        for(VariableBinding v:variableBindings){
                            result.add(new SnmpValue(v.getOid().toString(), v.getVariable().getSyntaxString(), v.getVariable().toString()));
                        }
                    }
                }
            }else{
                snmp.close();
            }
        } catch (IOException e) {
            try {
                if (snmp != null) {
                    snmp.close();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return result;
    }



    private static CommunityTarget makeHeader(String ip, int version, String community, int retries, int timeout) {
        CommunityTarget header = new CommunityTarget();
        header.setCommunity(new OctetString(community));
        header.setVersion(version);
        header.setRetries(retries);
        header.setTimeout(timeout);
        try {
            header.setAddress(new UdpAddress(InetAddress.getByName(ip), SNMP_PORT));
        } catch (UnknownHostException e) {
            return null;
        }
        return header;
    }



    private static <T> PDU makePdu(T oid, int method) {
        PDU pdu = new PDU();
        pdu.setType(method);

        if(oid instanceof int[]){
            int[] oidVal = (int[]) oid;
            pdu.addOID(new VariableBinding(new OID(oidVal)));
        }else{
            String oidVal = oid.toString();
            pdu.addOID(new VariableBinding(new OID(oidVal)));
        }
        return pdu;
    }

    private static int snmpVersionConverter(int version){
        /*
            input 2 => v2c
            input 3 => v3
        */
        return version == 2? SnmpVersion.SNMP_VERSION_2C.value() : SnmpVersion.SNMP_VERSION_3.value();

    }

}
