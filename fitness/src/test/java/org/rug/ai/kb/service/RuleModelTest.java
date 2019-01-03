package org.rug.ai.kb.service;

import java.lang.reflect.Array;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rug.ai.kb.domainmodel.UserProfile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
public class RuleModelTest {

    @Test
    public void testPopulate(){

        UserProfile fact = new UserProfile();
        String question = "What is your level of experience?";
        String answer = "Advanced";
        RuleModel.populate(fact, question, answer);
        assertEquals(answer, fact.getLevel());

        question = "How long do you want to workout for?";
        answer = "30 minutes";
        RuleModel.populate(fact, question, answer);
        assertEquals(answer, fact.getTime());


        question = "Which part of the body do you want to focus on?";
        List <String> answer1 = Arrays.asList("Core");
        RuleModel.populate(fact, question, answer1.get(0));
        assertEquals(answer1, fact.getBodyParts());

        question = "Which age group do you belong to?";
        answer = "less than 15 years old";
        RuleModel.populate(fact, question, answer);
        assertEquals(answer, fact.getAge());

        question = "What is your goal?";
        answer = "Weight Loss";
        RuleModel.populate(fact, question, answer);
        assertEquals(answer, fact.getGoal());



    }
}