package eeit.OldProject.yuni.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import eeit.OldProject.yuni.Service.ProgressService;

@Controller
public class ProgressController{
    @Autowired
    private ProgressService progressService;
}
