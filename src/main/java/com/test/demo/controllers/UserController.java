package com.test.demo.controllers;

import com.test.demo.entities.LoginForm;
import com.test.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.test.demo.repositories.UserRepository;
import com.test.demo.services.IExcelDataService;
import com.test.demo.services.IFileUploaderService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    IFileUploaderService fileService;

    @Autowired
    IExcelDataService excelservice;


    @PostMapping("/adduser")
    public User adduser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @PostMapping("/login")
    public int login(@RequestBody LoginForm login) {
        User user = userRepository.findByEmail(login.getEmail());
        if(user.getEmail().equals(login.getEmail()) && user.getPassword().equals(login.getPassword())) {
            return 0;
        }else return 1;
    }
    @GetMapping("/users")
    public List<User> getAll() {
        return userRepository.findAll();
    }
    

    @PostMapping("/export")
    public String exportUser(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
        fileService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message",
                "You have successfully uploaded '" + file.getOriginalFilename() + "' !");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "ok";
    }

    @GetMapping("/saveData")
    public String saveExcelData(User user) {

        List<User> excelDataAsList = excelservice.getExcelDataAsList();
        int noOfRecords = excelservice.saveExcelData(excelDataAsList);
       // userRepository.saveAll(excelDataAsList);
        return "success";
    }
    @GetMapping("/test")
    public void emailChecker() {
        final String uri = "http://apilayer.net/api/check?access_key=11d35930dd328ecb377376b862d67ef2&email=ons.tliba@insat.u-carthage.tn&smtp=1&format=1";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
        //return true;
    }


}
