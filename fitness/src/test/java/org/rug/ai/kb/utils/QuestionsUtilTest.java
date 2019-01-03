package org.rug.ai.kb.utils;

import static org.junit.Assert.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rug.ai.kb.domainmodel.Survey;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@RunWith(SpringRunner.class)
public class QuestionsUtilTest {

    @Test
    public void testGetExercisesKey() throws Exception{
        Map<String, String[]> mapTest = KnowledgeBase.getDomainKnowledgeMap();
        boolean found = mapTest.containsKey("16 to 60 years old");
        assertTrue(found);
    }

    @Test
    public void testGetValueKey() throws Exception{
        String keyValue = "Core=Run/Jog%Planks%Dead Bug%Rowing%Crunches%Mountain Climbers%Burpees%Leg Raises%Donkey Kicks%Skip Rope%Jumping Jacks%Elliptical%Deadlift";
        String[] option =  keyValue.split("=");
        String key = option[0];
        String[] values = option[1].split("%");
        Map<String, String[]> mapTest = KnowledgeBase.getDomainKnowledgeMap();
        String[] var = mapTest.get(key);
        assertArrayEquals(values, var);
    }

    @Test
    public void testGetGraph() throws Exception{
        Graph<CustomVertex, RelationshipEdge> graph = QuestionsUtil.getGraph();
        assertNotNull(graph);
        CustomVertex cv = new CustomVertex("1", "Do you have any health problems or injuries?", "radio");
        boolean contains = graph.containsVertex(cv);
        assertTrue(contains);
        int noEdge = graph.outgoingEdgesOf(cv).size();
        assertEquals(2, noEdge);

    }

    @Test
    public void testGetSurveyInstance() throws Exception{
        Graph<CustomVertex, RelationshipEdge> graph = QuestionsUtil.getGraph();
        CustomVertex cv = new CustomVertex("1", "Do you have any health problems or injuries?", "radio");
        Survey surveyRet = QuestionsUtil.getSurveyInstance(graph, cv);
        Survey expectedSurvey = new Survey();
        expectedSurvey.setDisplayType("radio");
        expectedSurvey.setQuestion("Do you have any health problems or injuries?");
        assertEquals(expectedSurvey.getDisplayType(), surveyRet.getDisplayType());
        assertEquals(expectedSurvey.getQuestion(), surveyRet.getQuestion());
    }

}