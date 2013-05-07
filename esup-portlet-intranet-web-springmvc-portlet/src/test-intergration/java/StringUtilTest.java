import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.Test;


public class StringUtilTest {
	
	@Test
	public void testStr() throws UnsupportedEncodingException{
		String tmp = "/default-domain/workspaces/devq2/Raymond/C'est l'été !";
		
		System.out.println(Charset.defaultCharset().toString());

        String accentedE = tmp;

        String utf8 = new String(accentedE.getBytes("ISO-8859-1"), Charset.forName("UTF-8"));
        System.out.println(utf8);
        
        utf8 = new String(accentedE.getBytes("UTF-8"), Charset.forName("ISO-8859-1"));
        System.out.println(utf8);
        
        utf8 = new String(accentedE.getBytes(), Charset.forName("UTF-8"));
        System.out.println(utf8);
        
        utf8 = new String(accentedE.getBytes("utf-8"));
        System.out.println(utf8);
        
        utf8 = new String(accentedE.getBytes());
        System.out.println(utf8);
		
	}

}
