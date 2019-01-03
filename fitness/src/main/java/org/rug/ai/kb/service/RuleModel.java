package org.rug.ai.kb.service;

import org.rug.ai.kb.domainmodel.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class RuleModel {

	private static Logger logger = LoggerFactory.getLogger(RuleModel.class);

	public static void populate(UserProfile fact, String question, String answer){
		
		if(question.equals("How long do you want to workout for?")) {
			fact.init();
			fact.setTime(answer);
		} else if(question.equals("Which age group do you belong to?")) {
			fact.setAge(answer);
		} else if(question.equals("What is your level of experience?")) {
			fact.setLevel(answer);
		} else if(question.equals("Which part of the body do you want to focus on?")) {
			fact.setBodyParts(Arrays.asList(answer.split(",")));
		} else if(question.equals("What is your goal?")){
			fact.setGoal(answer);
		}else {
			logger.info("Question did not match any=" +  question);
		}
	
	}

}
