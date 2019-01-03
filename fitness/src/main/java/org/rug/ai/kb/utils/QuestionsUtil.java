package org.rug.ai.kb.utils;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.IOUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.*;
import org.jgrapht.io.*;
import org.rug.ai.kb.domainmodel.OptionTextValue;
import org.rug.ai.kb.domainmodel.Survey;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.*;

public final class QuestionsUtil {

	private static String getQuestionsGraph() throws Exception {
		Resource resource = new ClassPathResource("questionsGraphUP.gml");
		List<String> list = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
		return String.join("", list);
		
	}

	public  static Graph<CustomVertex, RelationshipEdge> getGraph() throws Exception {
		
		Graph<CustomVertex, RelationshipEdge> g  = new DirectedPseudograph<>(RelationshipEdge.class);

		VertexProvider<CustomVertex> vp = (id, attributes) -> {
            return new CustomVertex(id,attributes.get("label").toString(), attributes.get("display").toString() );
        };
        
        
        EdgeProvider<CustomVertex, RelationshipEdge> ep = (from, to, label, attributes) -> {
            
        	RelationshipEdge edge = new RelationshipEdge(attributes.get("label").toString());
            return edge;
        };
		
		GmlImporter<CustomVertex, RelationshipEdge> importer = new GmlImporter<>(vp, ep);
		
		importer.importGraph(g, new StringReader(getQuestionsGraph()));
		//importer.importGraph(g, new StringReader(input));
		return g;
	}
	
	public static Survey getSurveyInstance(Graph<CustomVertex, RelationshipEdge> g, CustomVertex cv) throws Exception{
		Survey survey = new Survey();
	
		Set<RelationshipEdge> edges = g.outgoingEdgesOf(cv);
		String[] options = new String[edges.size()];
		String[] optionsValues = new String[edges.size()];
		OptionTextValue[]  optV = new OptionTextValue[edges.size()];
		final AtomicInteger indexHolder = new AtomicInteger();
		edges.forEach(element -> {
			int i = indexHolder.getAndIncrement();
			options[i] = element.getLabel();
			optionsValues[i] = element.getLabel() + "x";
			String value  = cv.getLabel() + "%" + options[i] + "%" + g.getEdgeTarget(element).getId() + "%" + g.getEdgeTarget(element).getLabel()
				       + "%"  + g.getEdgeTarget(element).getDisplay(); //when you click on 'no' what should be the next value //concatenate
			optV[i] = new OptionTextValue(element.getLabel(),value );
		});
		survey.setOptions(options);
		survey.setOptionsValues(optionsValues);
		survey.setOptionTextValues(optV);
		survey.setQuestion(cv.getLabel());
		survey.setDisplayType(cv.getDisplay());
		return survey;
	}
	
	public static void main(String args[]) {
		try {
			Graph<CustomVertex, RelationshipEdge> g = getGraph();
			

			CustomVertex cv = new CustomVertex("2", "What is your goal?", "checkbox");
			Set<RelationshipEdge> edges = g.outgoingEdgesOf(cv);
			edges.forEach(element -> {
				System.out.println(element.getLabel());
				System.out.println(g.getEdgeSource(element).getDisplay());
				System.out.println(g.getEdgeTarget(element).getLabel());
				//System.out.println(g);
			});
			System.out.println(edges.size());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
