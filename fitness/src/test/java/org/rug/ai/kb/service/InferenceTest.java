package org.rug.ai.kb.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rug.ai.kb.domainmodel.UserProfile;
import org.rug.ai.kb.utils.KnowledgeBase;
import org.rug.ai.kb.utils.QuestionsUtil;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class InferenceTest {

    @Test
    public void testInferRules() throws Exception{
        UserProfile fact = getProfile();
        Map<String, String[]> exerciseMap = KnowledgeBase.getDomainKnowledgeMap();

        List<String> inference = Arrays.asList("Run/Jog", "Deadlift", "Planks", "Mountain Climbers");

        Inference.inferRules(fact, exerciseMap);
        List <String> checkInfer = fact.getRecommendations();

        assertEquals(4, checkInfer.size());
        assertEquals(inference, checkInfer);

    }

    private UserProfile getProfile()  {
        UserProfile fact = new UserProfile();
        fact.setAge("16 to 60 years old");
        fact.setBodyParts(Arrays.asList("Core"));
        fact.setGoal("Muscle Gain");
        fact.setTime("30 minutes");

        return fact;
    }

    @Test
    public void testAge16To60() throws Exception {
        UserProfile fact = new UserProfile();
        fact.setAge("16 to 60 years old");
        Map<String, String[]> exerciseMap = KnowledgeBase.getDomainKnowledgeMap();

        Inference.inferRules(fact, exerciseMap);
        assertEquals(33, fact.getRecommendations().size());
        fact.setGoal("Muscle Gain");
        Inference.inferRules(fact, exerciseMap);
        assertEquals(33, fact.getRecommendations().size());
        fact.setBodyParts(Arrays.asList("Lower", "Core"));
        Inference.inferRules(fact, exerciseMap);
        System.out.println("After choosing body parts: " + fact.getRecommendations());
        assertEquals(22, fact.getRecommendations().size());
        fact.setLevel("Beginner");
        Inference.inferRules(fact, exerciseMap);
        System.out.println("After choosing level: " + fact.getRecommendations());
        assertEquals(20, fact.getRecommendations().size());
    }

    @Test
    public void test60Plus() throws Exception {
        UserProfile fact = new UserProfile();
        fact.setAge("60 years or older");
        Map<String, String[]> exerciseMap = KnowledgeBase.getDomainKnowledgeMap();

        Inference.inferRules(fact, exerciseMap);
        assertEquals(12, fact.getRecommendations().size());
    }

}