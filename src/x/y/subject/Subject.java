package x.y.subject;

import java.util.Collection;
import java.util.List;

import x.y.web.authz.Permission;

public interface Subject {
	
	  	final String sessionKey =  "SESSION_SUBJECT";
	  
	    boolean isPermitted(String permission);
	    
	    boolean[] isPermitted(String... permissions);
	    
	    boolean isPermittedAll(String... permissions);

	    boolean hasRole(String roleIdentifier);

	    boolean[] hasRoles(List<String> roleIdentifiers);

	    boolean hasAllRoles(List<String> roleIdentifiers);
	   
	    boolean isAuthenticated();
	    
}
