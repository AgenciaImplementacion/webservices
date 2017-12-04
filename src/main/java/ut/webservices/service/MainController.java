/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut.webservices.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ut.webservices.domain.APIVersionsResponse;

/**
 *
 * @author felipe
 */
@RestController
public class MainController {

    @RequestMapping("api")
    public ResponseEntity<?> index() {
        APIVersionsResponse r = new APIVersionsResponse();
        r.addVersion("v1", "/api/v1");
        return (ResponseEntity<?>) new ResponseEntity<APIVersionsResponse>(r, HttpStatus.OK);
    }

}
