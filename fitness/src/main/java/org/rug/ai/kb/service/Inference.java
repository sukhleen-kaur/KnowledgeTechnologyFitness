package org.rug.ai.kb.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.rug.ai.kb.controller.KBController;
import org.rug.ai.kb.domainmodel.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * inference class
 */
public class Inference {
	private static Logger logger = LoggerFactory.getLogger(Inference.class);

	private static List<String> UPPER_HARD_BE = Arrays.asList("Push Ups", "Walkout"); //hard constraints for beginner
	private static List<String> UPPER_HARD_AD = Arrays.asList("Chest Press", "Pull Ups/Chin Ups"); //hard constraints for advanced

	/**
	 * it populates the recommendations in the fact instance
	 *
	 * @param fact
	 * @param exerciseMap
	 */
	public static void inferRules(UserProfile fact, Map<String, String[]>  exerciseMap) {
		
		List<String> inference =  new ArrayList<>();
		
		if (fact.getAge() != null) {
			inference = Arrays.asList(exerciseMap.get(fact.getAge()));

		}

		if(fact.getGoal() != null){
			List<String> tempList = Arrays.asList(exerciseMap.get(fact.getGoal()));
			//find the intersection between the previous state of the inference list with the current one
			inference = intersect(inference , tempList);
		}

		if(fact.getBodyParts() != null) {
			List<String> tempList = Arrays.asList(exerciseMap.get(fact.getBodyParts().get(0)));
			inference = intersect(inference , tempList);

			/**
			 * iterate over all the chosen body parts and merge all the recommendations
			 */
			if( fact.getBodyParts().size() > 1 ) {
				for(int i=1; i < fact.getBodyParts().size(); i++){
					tempList = Arrays.asList(exerciseMap.get(fact.getBodyParts().get(i)));
					inference = merge(tempList, inference);
				}
			}
		}

		if(fact.getLevel() != null) {
			List<String> tempList = Arrays.asList(exerciseMap.get(fact.getLevel()));
			inference = intersect(inference , tempList);
		}


		/**
		 * apply the time constraints
		 */
		if(fact.getTime() != null) {
			if(fact.getTime().equals("30 minutes")) {
				inference = inference.stream().limit(4).collect(Collectors.toList());
				//hard constraint
				if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Advanced"))
				{
					inference.removeAll(UPPER_HARD_AD);
					inference.addAll(0,UPPER_HARD_AD);
					inference = inference.stream().limit(4).collect(Collectors.toList());
				} else if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Beginner")){
					inference.removeAll(UPPER_HARD_BE);
					inference.addAll(0,UPPER_HARD_BE);
					inference = inference.stream().limit(4).collect(Collectors.toList());
				}
			} else if(fact.getTime().equals("45 minutes")) {
				inference = inference.stream().limit(6).collect(Collectors.toList());
				//hard constraint
				if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Advanced"))
				{
					inference.removeAll(UPPER_HARD_AD);
					inference.addAll(0,UPPER_HARD_AD);
					inference = inference.stream().limit(6).collect(Collectors.toList());
				} else if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Beginner")){
					inference.removeAll(UPPER_HARD_BE);
					inference.addAll(0,UPPER_HARD_BE);
					inference = inference.stream().limit(6).collect(Collectors.toList());
				}
			}else if(fact.getTime().equals("60 minutes (1 hour)")) {
				inference = inference.stream().limit(8).collect(Collectors.toList());
				//hard constraint
				if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Advanced"))
				{
					inference.removeAll(UPPER_HARD_AD);
					inference.addAll(0,UPPER_HARD_AD);
					inference = inference.stream().limit(8).collect(Collectors.toList());
				} else if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Beginner")){
					inference.removeAll(UPPER_HARD_BE);
					inference.addAll(0,UPPER_HARD_BE);
					inference = inference.stream().limit(8).collect(Collectors.toList());
				}
			}else if(fact.getTime().equals("90 minutes (1 hour and 30 minutes)")){
				inference = inference.stream().limit(10).collect(Collectors.toList());
				//hard constraint
				if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Advanced"))
				{
					inference.removeAll(UPPER_HARD_AD);
					inference.addAll(0,UPPER_HARD_AD);
					inference = inference.stream().limit(10).collect(Collectors.toList());
				} else if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Beginner")){
					inference.removeAll(UPPER_HARD_BE);
					inference.addAll(0,UPPER_HARD_BE);
					inference = inference.stream().limit(10).collect(Collectors.toList());
				}
			}else if(fact.getTime().equals("120 minutes (2 hours)")){
				inference = inference.stream().limit(12).collect(Collectors.toList());
				//hard constraint
				if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Advanced"))
				{
					inference.removeAll(UPPER_HARD_AD);
					inference.addAll(0,UPPER_HARD_AD);
					inference = inference.stream().limit(12).collect(Collectors.toList());
				} else if(fact.getBodyParts() != null && fact.getBodyParts().contains("Upper") && fact.getLevel().equals("Beginner")){
					inference.removeAll(UPPER_HARD_BE);
					inference.addAll(0,UPPER_HARD_BE);
					inference = inference.stream().limit(12).collect(Collectors.toList());
				}
			}
			
		}


		logger.info("inference=" + inference.toString() );
		//set the final recommendations
		fact.setRecommendations(inference);
	}

	/**
	 * merge two lists together and remove duplicates
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	private static List<String> merge(List<String> l1,List<String> l2) {
		List<String> j = Stream.concat(l1.stream(), l2.stream())
				.collect(Collectors.toList());
		return j.stream().distinct().collect(Collectors.toList());

	}

	/**
	 *filter to only show things in common (in l2)
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	private static List<String> intersect(List<String> l1,List<String> l2) {
		return l1.stream().filter(x -> l2.contains(x)).collect(Collectors.toList());
	}


}
