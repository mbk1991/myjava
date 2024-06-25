package modules.snmp.data;

public class SnmpValue {

    final private String oid;
    final private String syntax;
    final private String value;

    public SnmpValue(String oid, String syntax, String value) {
        this.oid = oid;
        this.syntax = syntax;
        this.value = value;
    }

    public String getOid() {
        return this.oid;
    }

    public String getSyntax() {
        return this.syntax;
    }

    public String getValue() {
        return this.value;
    }

    private String checkSyntax(){
        //todo: add syntax Parsing
        return "";
    }


    enum ValueSyntax {
        Integer32("Integer32"),
        BIT_STRING("BIT STRING"),
        OCTET_STRING("OCTET STRING"),
        OBJECT_IDENTIFIER("OBJECT IDENTIFIER"),
        TimeTicks("TimeTicks"),
        Counter("Counter"),
        Counter64("Counter64"),
        EndOfMibView("EndOfMibView"),
        Gauge("Gauge"),
        Unsigned32("Unsigned32"),
        IpAddress("IpAddress"),
        NoSuchInstance("NoSuchInstance"),
        NoSuchObject("NoSuchObject"),
        Null("Null"),
        Opaque("Opaque");

        ValueSyntax(String syntaxString) {
            this.syntaxString = syntaxString;
        }

        final String syntaxString;

        String str() {
            return this.syntaxString;
        }
    }
}