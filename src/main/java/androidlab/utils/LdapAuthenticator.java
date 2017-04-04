package androidlab.utils;

import com.unboundid.ldap.sdk.BindRequest;
import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SimpleBindRequest;

/**
 * @author Muehlenstaedt
 */
public class LdapAuthenticator {

    public static boolean authenticate(String username, String password) {
        try {
            //String ldapUrl = "ldap://ldap.ibr.cs.tu-bs.de/"; //sdk only excepts ip
            //LDAPConnection ldapConnection = new LDAPConnection("ldap.ibr.cs.tu-bs.de", 389);
            if (username.equals("") && password.equals("")) { //TODO: debug
                return true;
            }
            LDAPConnection ldapConnection = new LDAPConnection("localhost", 389);

            BindRequest bindRequest =
                    new SimpleBindRequest(
                            "uid=" + username + ",ldap flags", password);
            BindResult bindResult;
            bindResult = ldapConnection.bind(bindRequest);

            if (bindResult.getResultCode().equals(ResultCode.SUCCESS)) {
                System.out.println("[LDAP] Authentication succesfull!");
                ldapConnection.close();
                return true;
            }
            ldapConnection.close();
        } catch (LDAPException e) {
            System.out.println("[LDAP] Authentication Failed with ErrorCode: " + e.getResultCode() + " !");
        }
        return false;
    }
}
