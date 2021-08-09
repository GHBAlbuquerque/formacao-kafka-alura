package br.com.alura.ecommerce.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {

    public static final String TYPE_CONFIG = "br.com.alura.ecommerce.type_config";
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;


    //preciso descobrir classe em tempo de execucao, portanto ao criar meu deseriablizador devo passar a classe para ele
    // como? passo as configuracoes do kafka para dentro do deserializador
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG)); //properties declarada no consumer
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type for deserialization does not exist in class path.");
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return gson.fromJson(new String(bytes), type);
    }
}
