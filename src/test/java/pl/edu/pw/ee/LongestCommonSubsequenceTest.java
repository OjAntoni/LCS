package pl.edu.pw.ee;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class LongestCommonSubsequenceTest {

    @Test
    public void exampleFromLesson(){
        var l = new LongestCommonSubsequence("LOKOMOTYWA", "KOMPOTY");
        assertEquals("KOMOTY", l.findLCS());
    }

    @Test
    public void nullArgs(){
        assertThrows(IllegalArgumentException.class, ()->new LongestCommonSubsequence(null, "smth"));
    }

    @Test
    public void emptyArgs(){
        var l = new LongestCommonSubsequence("","");
        String lcs = l.findLCS();
        assertEquals("", lcs);
    }

    @Test
    public void blankArgs(){
        var l = new LongestCommonSubsequence("  "," ");
        assertEquals(" ", l.findLCS());
    }

    @Test
    public void specialCharacters(){
        var l = new LongestCommonSubsequence("We\nlike\ntravelling", "We\tdo\nlike\ttraversing\ntree");
        assertEquals("We\nliketraveing", l.findLCS());
    }

}
