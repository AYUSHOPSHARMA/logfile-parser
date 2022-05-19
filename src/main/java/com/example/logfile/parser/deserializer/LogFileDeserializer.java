package com.example.logfile.parser.deserializer;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.logfile.parser.bean.ApplicationServerEventData;
import com.example.logfile.parser.bean.EventData;
import com.example.logfile.parser.bean.ServerLogData;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LogFileDeserializer extends StdDeserializer<EventData> {

	private static final Logger logger = LogManager.getLogger(LogFileDeserializer.class);

	private static final long serialVersionUID = 1L;

	protected LogFileDeserializer() {
		super(LogFileDeserializer.class);
	}

	@Override
	public EventData deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
		TreeNode node = parser.readValueAsTree();
        TreeNode type = node.get("type");
        if(type!=null) {
        	 return parser.getCodec().treeToValue(node, ApplicationServerEventData.class);
        }
        return parser.getCodec().treeToValue(node,  ServerLogData.class);

	}

}
