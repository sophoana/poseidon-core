package edu.mum.se.poseidon.core.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.se.poseidon.core.controllers.dto.BlockDto;
import edu.mum.se.poseidon.core.controllers.mapper.BlockMapper;
import edu.mum.se.poseidon.core.repositories.models.Block;
import edu.mum.se.poseidon.core.services.BlockService;

@Controller
public class AdminBlockController {

	private BlockService blockService;
	private BlockMapper blockMapper;

	@Autowired
	public AdminBlockController(BlockService blockService, BlockMapper blockMapper) {
		this.blockService = blockService;
		this.blockMapper = blockMapper;
	}
	
	@RequestMapping(path="/blocks/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody BlockDto blockDto) {
		try {
			Block block = this.blockService.createBlock(blockDto);
			BlockDto bdo = blockMapper.getBlockDto(block);
			return new ResponseEntity<>(bdo, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path="/blocks/edit", method = RequestMethod.POST)
	public ResponseEntity<?> edit(@RequestBody BlockDto blockDto) {
		try {
			Block block = this.blockService.editBlock(blockDto);
			BlockDto bdo = blockMapper.getBlockDto(block);
			return new ResponseEntity<>(bdo, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path="/blocks/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getBlock(@PathVariable long id){
		try {
			Block block = blockService.getBlock(id);
			BlockDto bdo = blockMapper.getBlockDto(block);
			return new ResponseEntity<>(bdo, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path="/blocks", method = RequestMethod.GET)
	public ResponseEntity<?> getBlockList(){
		try {
			List<Block> blocks = blockService.getBlockList();
			List<BlockDto> dtos = blocks.stream()
					.map(b -> blockMapper.getBlockDto(b))
					.collect(Collectors.toList());
			return new ResponseEntity<>(dtos, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(path="/blocks/{id}/delete", method = RequestMethod.GET)
	public ResponseEntity<?> deleteBlock(@PathVariable long id){
		try {
			blockService.deleteBlock(id);
			return new ResponseEntity<>(new BlockDto(), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
