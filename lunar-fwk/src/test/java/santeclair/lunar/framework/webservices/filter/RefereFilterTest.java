package santeclair.lunar.framework.webservices.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.message.Message;
import org.easymock.EasyMock;
import org.junit.Test;


public class RefereFilterTest {

	@SuppressWarnings("rawtypes")
	@Test
    public void handleRequestGoodReferer() {		
		Map<String, List> headersMock = new HashMap<String, List>();
		headersMock.put("referer", Arrays.asList("http://www.santeclair.fr/test"));
		
		Message message = EasyMock.createNiceMock(Message.class);
		EasyMock.expect(message.get(Message.PATH_INFO)).andReturn("url/webservice/mock").anyTimes();
		EasyMock.expect(message.get(Message.PROTOCOL_HEADERS)).andReturn(headersMock).anyTimes();
		EasyMock.replay(message);
		
		RefererFilter filter = new RefererFilter();
		filter.setTrustHosts(Arrays.asList(".santeclair.fr"));
		assertNull(filter.handleRequest(message, null));
	}

	@SuppressWarnings("rawtypes")
	@Test
    public void handleRequestBadReferer() {
		Map<String, List> headersMock = new HashMap<String, List>();
		headersMock.put("referer", Arrays.asList("http://www.baddomaine.fr/test"));
		
		Message message = EasyMock.createNiceMock(Message.class);
		EasyMock.expect(message.get(Message.PATH_INFO)).andReturn("url/webservice/mock").anyTimes();
		EasyMock.expect(message.get(Message.PROTOCOL_HEADERS)).andReturn(headersMock).anyTimes();
		EasyMock.replay(message);
		
		RefererFilter filter = new RefererFilter();
		filter.setTrustHosts(Arrays.asList(".santeclair.fr"));
		assertEquals(Status.UNAUTHORIZED.getStatusCode() ,filter.handleRequest(message, null).getStatus());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
    public void handleRequestEmptyReferer() {
		Map<String, List> headersMock = new HashMap<String, List>();
		headersMock.put("referer", Arrays.asList(""));
		
		Message message = EasyMock.createNiceMock(Message.class);
		EasyMock.expect(message.get(Message.PATH_INFO)).andReturn("url/webservice/mock").anyTimes();
		EasyMock.expect(message.get(Message.PROTOCOL_HEADERS)).andReturn(headersMock).anyTimes();
		EasyMock.replay(message);
		
		RefererFilter filter = new RefererFilter();
		filter.setTrustHosts(Arrays.asList(".santeclair.fr"));
		assertEquals(Status.UNAUTHORIZED.getStatusCode() ,filter.handleRequest(message, null).getStatus());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
    public void handleRequestNoReferer() {
		Map<String, List> headersMock = new HashMap<String, List>();
				
		Message message = EasyMock.createNiceMock(Message.class);
		EasyMock.expect(message.get(Message.PATH_INFO)).andReturn("url/webservice/mock").anyTimes();
		EasyMock.expect(message.get(Message.PROTOCOL_HEADERS)).andReturn(headersMock).anyTimes();
		EasyMock.replay(message);
		
		RefererFilter filter = new RefererFilter();
		filter.setTrustHosts(Arrays.asList(".santeclair.fr"));
		assertEquals(Status.UNAUTHORIZED.getStatusCode() ,filter.handleRequest(message, null).getStatus());
	}
	
	@SuppressWarnings("rawtypes")
	@Test
    public void handleRequestURLExclusionHttpMethodOPTION() {
		Map<String, List> headersMock = new HashMap<String, List>();
				
		Message message = EasyMock.createNiceMock(Message.class);
		EasyMock.expect(message.get(Message.PATH_INFO)).andReturn("url/webservice/mock").anyTimes();
		EasyMock.expect(message.get(Message.PROTOCOL_HEADERS)).andReturn(headersMock).anyTimes();
		EasyMock.expect(message.get(Message.HTTP_REQUEST_METHOD)).andReturn("OPTION").anyTimes();
		EasyMock.replay(message);
		
		RefererFilter filter = new RefererFilter();
		filter.setTrustHosts(Arrays.asList(".santeclair.fr"));
		
		Map<String, List<String>> mapExclusionUrl = new HashMap<>();
		mapExclusionUrl.put("OPTION", Arrays.asList("url/webservice/mock"));
		filter.setMapExclusionUrl(mapExclusionUrl);
		
		assertNull(filter.handleRequest(message, null));
	}
	

}
