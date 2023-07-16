package au.edu.sydney.soft3202.reynholm.erp.billingsystem

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class BSFacadeImplTest {

    private BSFacade bsFacade;
    private AuthToken AT;
    private AuthToken ATbasic;
    private AuthorisationModule authModule;
    private AuthenticationModule authen;
    private ClientReporting clientReporting;
    private ComplianceReporting complianceReporting;

    @BeforeEach
    public void setUp() {
        bsFacade = new BSFacadeImpl();

        clientReporting = mock(ClientReporting.class);
        complianceReporting = mock(ComplianceReporting.class);

        AT = mock(AuthToken.class);
        when(AT.getToken()).thenReturn("fallen");
        when(AT.getUsername()).thenReturn("ikaros");

        ATbasic = mock(AuthToken.class);
        when(ATbasic.getToken()).thenReturn("basic");
        when(ATbasic.getUsername()).thenReturn("nymph");

        authModule = mock(AuthorisationModule.class);
        when(authModule.authorise(AT,true)).thenReturn(true);
        when(authModule.authorise(ATbasic,false)).thenReturn(true);

        authen = mock(AuthenticationModule.class);
        when(authen.login("ikaros","1234")).thenReturn(AT);
        when(authen.login("nymph","4321")).thenReturn(ATbasic);
        when(authen.authenticate("fallen")).thenReturn(true);
        when(authen.authenticate("basic")).thenReturn(true);
    }

//    @Test
//    public void testAuthenticate() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            authen.login(null,"a");
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            authen.login("a",null);
//        });
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            authen.authenticate(null);
//        });
//    }
//
//    @Test
//    public void AuthorisationModule() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            authModule.authorise(null,true);
//        });
//    }



    @Test
    public void testlogin() {
        bsFacade.injectAuth(authen,authModule);

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.login(null,"a");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.login("a",null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.login("","a");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.login("a","");
        });

        assertTrue(bsFacade.login("ikaros","1234"));
        assertFalse(bsFacade.login("ikaros","7777"));
        assertFalse(bsFacade.login("icarus","1234"));

    }

    @Test
    public void testaddProject() {
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addProject("NewName","NewClient",1.11,3.33);
        });

        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addProject("NewName","NewClient",1.11,3.33);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");


        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addProject("","New Client",1.11,3.33);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addProject("New name","",1.11,3.33);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addProject("New name","new client",-1.1,3.33);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addProject("New name","new client",1.1,-3.33);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addProject("New name","new client",1.1,1.11);
        });


    }

    @Test
    public void testremoveTask(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.removeProject(1);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");

        Project p =  bsFacade.addProject("NewName","NewClient",1.11,3.33);
        verify(bsFacade,times(1)).removeProject(p.getId());

        assertThrows(IllegalStateException.class, () -> {
            bsFacade.removeProject(100);
        });
    }

    @Test
    public void testaddTask(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addTask(1,"New Task",2,true);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        Project p1 = bsFacade.addProject("NewName","NewClient",1.11,3.33);
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addTask(p1.getId(), "New Task 1", 2,true);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");
        Project p = bsFacade.addProject("NewName","NewClient",1.11,3.33);

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addTask(p.getId(),null,1,true);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addTask(p.getId(),"NewTask",0,true);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.addTask(p.getId(),"NewTask",101,true);
        });

        assertTrue(bsFacade.addTask(p.getId(),"New Task",2,true));
        assertFalse(bsFacade.addTask(p.getId(),"New Task",100,false));
    }

    public void testsetProjectCeiling(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.setProjectCeiling(1,101);
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.setProjectCeiling(1,101);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");

        Project p = bsFacade.addProject("NewName","NewClient",1.11,3.33);

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.setProjectCeiling(p.getId(),0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.setProjectCeiling(p.getId(),101);
        });

        verify(bsFacade,times(1)).setProjectCeiling(1,99);
    }


    public void testfindProjectID(){

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.findProjectID(null, null);
        });
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.findProjectID("name", "client");
        });
        bsFacade.addProject("NewName","NewClient",1.11,3.33);
        assertEquals(1,bsFacade.findProjectID("NewName","NewClient"));

    }

    public void testsearchProjects(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.searchProjects(null);
        });
        assertEquals(0,bsFacade.searchProjects("").size());
        bsFacade.addProject("NewName","NewClient",1.11,3.33);
        assertEquals(1,bsFacade.searchProjects("NewName").size());
    }

    public void testgetAllProjects(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertEquals(0,bsFacade.getAllProjects().size());
        bsFacade.addProject("NewName","NewClient",1.11,3.33);
        assertEquals(1,bsFacade.getAllProjects().size());
    }

    public void testaudit(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.audit();
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.audit();
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");
        verify(bsFacade,times(1)).audit();
    }

    public void testfinaliseProject(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.finaliseProject(1);
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.finaliseProject(1);
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("ikaros","1234");
        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.finaliseProject(0);
        });
        Project p = bsFacade.addProject("NewName","NewClient",1.11,3.33);
        verify(bsFacade,times(1)).finaliseProject(p.getId());
    }

    public void testinjectclient(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        verify(bsFacade,times(1)).injectClient(clientReporting);
        verify(bsFacade,times(1)).injectClient(null);
    }

    public void testinjectcomplianceReport(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        verify(bsFacade,times(1)).injectCompliance(complianceReporting);
        verify(bsFacade,times(1)).injectCompliance(null);
    }

    public void testLogout(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.logout();
        });
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        bsFacade.logout();
        verify(bsFacade,times(1)).logout();
    }
}
