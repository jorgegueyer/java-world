package com.jgy.micro.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfig {

    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    /*@Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);
        return this.fillInDatabase(mongoTemplate);
    }

    private MongoTemplate fillInDatabase(MongoTemplate mongoTemplate) {

        DBObject objectToSave = BasicDBObjectBuilder.start()
                                                  .add("id","0")
                                                  .add("message", "mensaje")
                                                  .add("description", "descripcion")
                                                  .get();
        mongoTemplate.save(objectToSave,"infos");
        return mongoTemplate;
    }*/
}
