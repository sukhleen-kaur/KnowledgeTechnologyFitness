package org.rug.ai.kb.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnowledgeBase {
    public  static Map<String,String[]> getDomainKnowledgeMap() throws Exception {
        Resource resource = new ClassPathResource("exercise.txt");
        List<String> list = IOUtils.readLines(resource.getInputStream(), StandardCharsets.UTF_8);
        Map<String, String[]> execMap = new HashMap<>();

        list.forEach(x -> {
            String[] values= x.split("=");
            execMap.put(values[0], values[1].split("%"));
        });
        return execMap;
    }
}
