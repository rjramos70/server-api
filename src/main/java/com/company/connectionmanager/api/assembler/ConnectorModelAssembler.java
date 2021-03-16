package com.company.connectionmanager.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.connectionmanager.api.model.ConnectorModel;
import com.company.connectionmanager.domain.model.Connector;

@Component
public class ConnectorModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ConnectorModel toModel(Connector connector) {
		return modelMapper.map(connector, ConnectorModel.class);
	}
	
	public List<ConnectorModel> toCollectionModel(List<Connector> connectors){
		return connectors.stream()
					.map(connector -> toModel(connector))
					.collect(Collectors.toList());
	}
	
}
