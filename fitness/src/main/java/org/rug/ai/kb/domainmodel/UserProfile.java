package org.rug.ai.kb.domainmodel;

import java.io.Serializable;
import java.util.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.ScopedProxyMode;


@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfile  implements Serializable {
	private String age;
	private String level;
	private List<String> bodyParts;
	private String goal;
	private String time;
	private List<String> recommendations;

	public void init() {
		this.age = null;
		this.level = null;
		this.bodyParts = null;
		this.goal = null;
		this.time = null;
		this.recommendations = null;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	public List<String> getBodyParts() {
		return bodyParts;
	}

	public void setBodyParts(List<String> bodyParts) {
		this.bodyParts = bodyParts;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getTime() {
		return time;
	}

	public List<String> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<String> recommendations) {
		this.recommendations = recommendations;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "UserProfile{" +
				"age='" + age + '\'' +
				", level='" + level + '\'' +
				", bodyParts=" + bodyParts +
				", goal='" + goal + '\'' +
				", time='" + time + '\'' +
				", recommendations=" + recommendations +
				'}';
	}
}
