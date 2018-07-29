package org.chiwooplatform.samples.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.chiwooplatform.samples.message.Code;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/codes")
@Api(value = "Example of MVC controller with Swagger documentation")
public class CodeController {

    private final Map<Integer, Code> data = new LinkedHashMap<>();
    private Integer codeId = 1;

    private Integer generateId() {
        return codeId++;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Find Code by given Id")
    @ApiResponses({ @ApiResponse(code = 200, message = "Code with given id has been found and returned", response = Code.class),
            @ApiResponse(code = 404, message = "Code with given id does not exist") })
    public ResponseEntity<Code> get(@ApiParam(value = "Id of a Code", required = true) @PathVariable(name = "id") Integer id) {
        if (data.containsKey(id)) {
            return ResponseEntity.ok(data.get(id));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation("List all codes")
    @ApiResponse(code = 200, message = "List of all articles", responseContainer = "List", response = Code.class)
    public ResponseEntity<List<Code>> query() {
        final List<Code> result = new ArrayList<>();
        result.addAll(data.values());

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/")
    @ApiOperation("Create new code")
    @ApiResponses({ @ApiResponse(code = 201, message = "Code created"),
            @ApiResponse(code = 400, message = "Code with no content cannot be created") })
    public ResponseEntity<Integer> add(@RequestBody Code model) {
        if (model == null || model.getCdname() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            final Integer cdid = generateId();
            model.setCdid(cdid);
            data.put(cdid, model);

            final URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getCdid()).toUri();

            return ResponseEntity.status(HttpStatus.CREATED).location(location).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ApiOperation("Update code with given ID")
    @ApiResponses({ @ApiResponse(code = 200, message = "Code has beed updated"),
            @ApiResponse(code = 400, message = "Code ID is different than ID in path"),
            @ApiResponse(code = 404, message = "Code with given ID has not been found") })
    public ResponseEntity<Void> modifyById(@ApiParam(value = "Id of a Code", required = true) @PathVariable(name = "id") final Integer id,
            @ApiParam(value = "Code content", required = true) @RequestBody Code model) {
        if (data.containsKey(id)) {
            if (Objects.equals(id, model.getCdid())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            data.put(id, model);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation("Delete code with given ID")
    @ApiResponses({ @ApiResponse(code = 200, message = "Code has been deleted successfuly"),
            @ApiResponse(code = 404, message = "Code with given ID has not been found") })
    public ResponseEntity<Void> remove(@ApiParam(value = "Id of a Code", required = true) @PathVariable(name = "id") Integer id) {
        if (data.containsKey(id)) {
            data.remove(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
