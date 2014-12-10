package net.bitpot.railways.parser.route;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * @author Basil Gren
 *         on 09.12.2014.
 */
@RunWith(Parameterized.class)
public class RouteActionParserTest {

    private String myActionStr;
    private RouteActionChunk[] expectedChunks;

    public RouteActionParserTest(String routeStr, RouteActionChunk[] chunks) {
        myActionStr = routeStr;
        expectedChunks = chunks;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> createParseLineData() {
        return Arrays.asList(new Object[][]{
                {"api/users#edit", new RouteActionChunk[]{
                        chunkContainer("api/users"), chunkAction("#edit")}
                },

                {"api/users", new RouteActionChunk[]{
                        chunkContainer("api/users")}
                },

                {"", new RouteActionChunk[] {}}
        });
    }

    private static RouteActionChunk chunkContainer(String text) {
        return new RouteActionChunk(text, RouteActionChunk.CONTAINER, 0);
    }

    private static RouteActionChunk chunkAction(String text) {
        return new RouteActionChunk(text, RouteActionChunk.ACTION, 0);
    }



    @Test
    public void testParseRoute() {
        RouteActionChunk[] chunks = RouteActionParser.parse(myActionStr);

        assertEquals("Chunk lists have the same length",
                expectedChunks.length, chunks.length);

        for(int i = 0; i < chunks.length; i++) {
            RouteActionChunk expectedChunk = expectedChunks[i];
            RouteActionChunk chunk = chunks[i];

            assertEquals("Chunk types are the same",
                    expectedChunk.getType(), chunk.getType());

            assertEquals("Chunk text is the same",
                    expectedChunk.getText(), chunk.getText());
        }
    }
}
