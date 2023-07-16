package au.edu.sydney.soft3202.reynholm.erp.billingsystem;

import au.edu.sydney.soft3202.reynholm.erp.cheatmodule.ERPCheatFactory;
import au.edu.sydney.soft3202.reynholm.erp.client.ClientReporting;
import au.edu.sydney.soft3202.reynholm.erp.compliance.ComplianceReporting;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthToken;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthenticationModule;
import au.edu.sydney.soft3202.reynholm.erp.permissions.AuthorisationModule;
import au.edu.sydney.soft3202.reynholm.erp.project.Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
        when(authen.authenticate(AT)).thenReturn(true);
        when(authen.authenticate(ATbasic)).thenReturn(true);
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

//    @Test
//    public void testSomething() {
//        Project yourProjectMock = mock(Project.class);
//
//        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
//            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
//                    .thenReturn(yourProjectMock);
//
//            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block
//            BSFacadeImpl fixture = new BSFacadeImpl();
//            Project p = fixture.addProject("name", "client", 1, 50);
//            assertThat(p, equalTo(yourProjectMock));
//        }
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
//        assertThrows(IllegalArgumentException.class, () -> {
//            bsFacade.login("","a");
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            bsFacade.login("a","");
//        });

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
        bsFacade.login("nymph","4321");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addProject("","New Client",1,50);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addProject(null,"New Client",1,50);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addProject("name",null,1,50);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addProject("New name","",1,50);
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
            assertThat(p, equalTo(myProjectMock));
        }
    }

    @Test
    public void testremoveproject(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.removeProject(1);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");


        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(myProjectMock));

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.removeProject(-1);
            });

            bsFacade.injectAuth(authen,authModule);
            bsFacade.login("ikaros","1234");
            int id = p.getId();
            bsFacade.removeProject(p.getId());
            boolean flag = true;
            for(Project project : bsFacade.getAllProjects()) {
                if (project.getId() == id) {
                    flag = false;
                }
            }
            assertTrue(flag);
        }
    }


    @Test
    public void testaddTask(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addTask(1,"New Task",2,true);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

//        Project p1 = bsFacade.addProject("NewName","NewClient",1.11,3.33);
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.addTask(1, "New Task 1", 2,true);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(myProjectMock));

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.addTask(p.getId(), "New Task 1", 2,true);
            });

            bsFacade.injectAuth(authen,authModule);
            bsFacade.login("ikaros","1234");

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addTask(p.getId(),null,1,true);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addTask(p.getId(),"NewTask",0,true);
            });
            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.addTask(p.getId(),"NewTask",101,true);
            });

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.addTask(100000000, "New Task 1", 2,true);
            });

            assertTrue(bsFacade.addTask(p.getId(),"New Task",70,true));

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.addTask(p.getId(), "New Task 1", 2,false);
            });
        }
    }

    @Test
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
        bsFacade.login("nymph","4321");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            Project p1 = bsFacade.addProject("name1", "client1", 1, 50);
            assertThat(p, equalTo(myProjectMock));

            when(p.getId()).thenReturn(1);
            when(p1.getId()).thenReturn(2);

            bsFacade.injectAuth(authen,authModule);
            bsFacade.login("ikaros","1234");

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.setProjectCeiling(p.getId(),0);
            });

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.setProjectCeiling(p.getId(),10000000);
            });

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.setProjectCeiling(10000,10);
            });

            bsFacade.setProjectCeiling(p.getId(),20);
            bsFacade.setProjectCeiling(p1.getId(),70);
            assertTrue(bsFacade.addTask(p.getId(),"New Task",70,true));

            bsFacade.injectAuth(authen,authModule);
            bsFacade.login("nymph","4321");
            assertFalse(bsFacade.addTask(p1.getId(), "New Task",30,false));
        }
    }

    @Test
    public void testfindProjectID(){

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        Project p = bsFacade.addProject("name", "client", 1, 50);

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.findProjectID(null, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.findProjectID("name", null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.findProjectID(null, "client");
        });

        assertEquals(p.getId(),bsFacade.findProjectID("name","client"));
    }

    @Test
    public void testsearchProjects(){

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(myProjectMock));

            assertThrows(IllegalArgumentException.class, () -> {
                bsFacade.searchProjects(null);
            });

            assertEquals(0,bsFacade.searchProjects("").size());
            assertEquals(1,bsFacade.searchProjects("client").size());
        }
    }

    @Test
    public void testgetAllProjects(){

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(myProjectMock));
            assertEquals(1,bsFacade.getAllProjects().size());
        }
    }

    @Test
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

        bsFacade.injectCompliance(complianceReporting);
        bsFacade.audit();
        verify(complianceReporting,times(0)).sendReport(anyString(),anyInt(),eq(AT));
    }

    @Test
    public void testauditWithOverCeiling(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block

            Project p = bsFacade.addProject("name", "client", 1, 50);
            assertThat(p, equalTo(myProjectMock));

            bsFacade.injectAuth(authen,authModule);
            bsFacade.login("ikaros","1234");

            bsFacade.setProjectCeiling(p.getId(),50);
            bsFacade.addTask(p.getId(),"Description",99, true);
            bsFacade.injectCompliance(complianceReporting);
            bsFacade.audit();
            verify(complianceReporting,times(1)).sendReport(eq(p.getName()),anyInt(),eq(AT));
        }
    }

    @Test
    public void testfinaliseProject(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.finaliseProject(1);
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        assertThrows(IllegalStateException.class, () -> {
            bsFacade.finaliseProject(1);
        });

        when(authModule.authorise(AT,false)).thenReturn(true);
        bsFacade.injectAuth(authen,authModule);

        bsFacade.login("ikaros","1234");

        Project myProjectMock = mock(Project.class);
        try (MockedStatic<Project> mock = mockStatic(Project.class)) {
            mock.when(() -> Project.makeProject(anyInt(), anyString(), anyDouble(), anyDouble()))
                    .thenReturn(myProjectMock);

            // You need to place your code that uses makeProject (i.e. BSFacade.addProject) in this try-with-resources block


            Project p = bsFacade.addProject("name", "client", 1, 50);

            assertThrows(IllegalStateException.class, () -> {
                bsFacade.finaliseProject(-1);
            });
            bsFacade.injectClient(clientReporting);

            int id  = p.getId();
            assertThat(p, equalTo(myProjectMock));
            bsFacade.finaliseProject(p.getId());
            verify(clientReporting,times(1)).sendReport(eq("client"),anyString(),eq(AT));

            boolean flag = true;
            for(Project project : bsFacade.getAllProjects()) {
                if (project.getId() == id) {
                    flag = false;
                }
            }
            assertTrue(flag);
            assertEquals(AT.getToken(), "fallen");
        }
    }

    @Test
    public void testinjectclient(){
        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        bsFacade.injectClient(clientReporting);
        verify(clientReporting,times(0)).sendReport(anyString(),anyString(),eq(ATbasic));
    }

    @Test
    public void testinjectcompliance(){

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");

        bsFacade.injectCompliance(complianceReporting);
        verify(complianceReporting,times(0)).sendReport(anyString(),anyInt(),eq(ATbasic));
    }

    @Test
    public void testinjectAuth(){

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.injectAuth(null,authModule);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            bsFacade.injectAuth(authen,null);
        });
    }

    @Test
    public void testLogout(){
        assertThrows(IllegalStateException.class, () -> {
            bsFacade.logout();
        });

        bsFacade.injectAuth(authen,authModule);
        bsFacade.login("nymph","4321");
        bsFacade.logout();
        verify(authen,times(1)).logout(ATbasic);
    }
}
