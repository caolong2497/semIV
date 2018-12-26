/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import service.ChatService;
import service.CommentService;
import service.CoupleService;
import service.MemoryService;
import service.UserInforService;

/**
 *
 * @author chinam
 */
@ApplicationPath(value = "/rest")
public class RestConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        addResource(resources);
        return resources;
    }

    private void addResource(Set<Class<?>> resources) {
        resources.add(UserInforService.class);
        resources.add(CoupleService.class);
        resources.add(ChatService.class);
        resources.add(MemoryService.class);
        resources.add(CommentService.class);
    }
}
